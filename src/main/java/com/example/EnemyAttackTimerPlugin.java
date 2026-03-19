package com.example;

import com.google.inject.Provides;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Enemy Attack Timer",
	description = "Shows a visual tick overlay of enemy attack timers when in combat",
	tags = {"combat", "timer", "tick", "overlay", "attack", "pvm"}
)
public class EnemyAttackTimerPlugin extends Plugin
{
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

		// Keep maxAttackSpeeds in sync with attackTimers
		maxAttackSpeeds.keySet().retainAll(attackTimers.keySet());
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
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		attackTimers.remove(npc);
		maxAttackSpeeds.remove(npc);
	}

	private int resolveAttackSpeed(NPC npc)
	{
		NPCComposition composition = npc.getComposition();
		if (composition != null)
		{
			int speed = composition.getAttackSpeed();
			if (speed > 0)
			{
				return speed;
			}
		}
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

	@Provides
	EnemyAttackTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EnemyAttackTimerConfig.class);
	}
}
