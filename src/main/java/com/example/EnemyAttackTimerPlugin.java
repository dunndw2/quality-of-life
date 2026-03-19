package com.example;

import com.google.inject.Provides;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
	name = "Enemy Attack Timer",
	description = "Shows a visual tick overlay of enemy attack timers when in combat",
	tags = {"combat", "timer", "tick", "overlay", "attack", "pvm"}
)
public class EnemyAttackTimerPlugin extends Plugin
{
	private static final Logger log = LoggerFactory.getLogger(EnemyAttackTimerPlugin.class);

	/**
	 * Palette of visually distinct colors for distinguishing simultaneous enemies.
	 * Avoids red/yellow which are reserved for the danger state in the overlay.
	 */
	static final List<Color> NPC_COLORS = Collections.unmodifiableList(Arrays.asList(
		new Color(0, 200, 255),   // Cyan
		new Color(255, 140, 0),   // Orange
		new Color(200, 0, 255),   // Purple
		new Color(0, 255, 128),   // Mint green
		new Color(255, 80, 180),  // Hot pink
		new Color(30, 144, 255),  // Dodger blue
		new Color(255, 215, 0),   // Gold
		new Color(127, 255, 212)  // Aquamarine
	));

	@Inject
	private Client client;

	@Inject
	private EnemyAttackTimerConfig config;

	@Inject
	private EnemyAttackTimerOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	// NPC -> ticks remaining until next attack
	private final Map<NPC, Integer> attackTimers = new HashMap<>();

	// NPC -> max ticks (attack speed) for progress bar calculation
	private final Map<NPC, Integer> maxAttackSpeeds = new HashMap<>();

	// NPC -> assigned display color for multi-enemy distinction
	private final Map<NPC, Color> npcColors = new HashMap<>();

	private int nextColorIndex = 0;

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		log.debug("Enemy Attack Timer started");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		attackTimers.clear();
		maxAttackSpeeds.clear();
		npcColors.clear();
		nextColorIndex = 0;
		log.debug("Enemy Attack Timer stopped");
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		attackTimers.replaceAll((npc, ticks) -> ticks - 1);

		// Remove NPCs that are no longer targeting the player, have timed out, or despawned
		attackTimers.entrySet().removeIf(entry ->
		{
			NPC npc = entry.getKey();
			return entry.getValue() < 0 || !isValidTarget(npc);
		});

		// Keep auxiliary maps in sync with attackTimers
		maxAttackSpeeds.keySet().retainAll(attackTimers.keySet());
		npcColors.keySet().retainAll(attackTimers.keySet());
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (!(event.getActor() instanceof NPC))
		{
			return;
		}

		NPC npc = (NPC) event.getActor();

		// Only track NPCs actively attacking the local player
		if (npc.getAnimation() == AnimationID.IDLE || npc.getInteracting() != client.getLocalPlayer())
		{
			return;
		}

		int attackSpeed = resolveAttackSpeed(npc);
		attackTimers.put(npc, attackSpeed);
		maxAttackSpeeds.put(npc, attackSpeed);

		// Assign a color if this is the first time we see this NPC
		npcColors.computeIfAbsent(npc, n ->
		{
			Color color = NPC_COLORS.get(nextColorIndex % NPC_COLORS.size());
			nextColorIndex++;
			return color;
		});
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		attackTimers.remove(npc);
		maxAttackSpeeds.remove(npc);
		npcColors.remove(npc);
	}

	private int resolveAttackSpeed(NPC npc)
	{
		// Hardcoded table takes highest priority (most accurate)
		int tableSpeed = NpcAttackSpeed.getAttackSpeed(npc.getId());
		if (tableSpeed > 0)
		{
			return tableSpeed;
		}

		// Fall back to user-configured default
		return config.defaultAttackSpeed();
	}

	private boolean isValidTarget(NPC npc)
	{
		return npc != null && npc.getInteracting() == client.getLocalPlayer();
	}

	public Map<NPC, Integer> getAttackTimers()
	{
		return Collections.unmodifiableMap(attackTimers);
	}

	public Map<NPC, Integer> getMaxAttackSpeeds()
	{
		return Collections.unmodifiableMap(maxAttackSpeeds);
	}

	public Color getNpcColor(NPC npc)
	{
		return npcColors.getOrDefault(npc, Color.WHITE);
	}

	@Provides
	EnemyAttackTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EnemyAttackTimerConfig.class);
	}
}
