package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class EnemyAttackTimerOverlay extends Overlay
{
	private static final int BAR_WIDTH = 40;
	private static final int BAR_HEIGHT = 6;
	private static final int Z_OFFSET_TEXT = 40;
	private static final int Z_OFFSET_BAR = 60;

	private static final Color COLOR_SAFE = Color.GREEN;
	private static final Color COLOR_CAUTION = Color.YELLOW;
	private static final Color COLOR_DANGER = Color.RED;
	private static final Color COLOR_BAR_BG = new Color(0, 0, 0, 180);
	private static final Color COLOR_BAR_BORDER = new Color(100, 100, 100, 200);

	private final Client client;
	private final EnemyAttackTimerPlugin plugin;
	private final EnemyAttackTimerConfig config;

	@Inject
	EnemyAttackTimerOverlay(Client client, EnemyAttackTimerPlugin plugin, EnemyAttackTimerConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (Map.Entry<NPC, Integer> entry : plugin.getAttackTimers().entrySet())
		{
			NPC npc = entry.getKey();
			int ticks = entry.getValue();
			Integer maxTicks = plugin.getMaxAttackSpeeds().get(npc);

			if (maxTicks == null || maxTicks <= 0)
			{
				continue;
			}

			LocalPoint localPoint = npc.getLocalLocation();
			if (localPoint == null)
			{
				continue;
			}

			int logicalHeight = npc.getLogicalHeight();

			if (config.showProgressBar())
			{
				renderProgressBar(graphics, localPoint, ticks, maxTicks, logicalHeight);
			}

			if (config.showTickCount())
			{
				renderTickCount(graphics, localPoint, ticks, logicalHeight);
			}
		}

		return null;
	}

	private void renderTickCount(Graphics2D graphics, LocalPoint localPoint, int ticks, int logicalHeight)
	{
		String text = String.valueOf(Math.max(0, ticks));
		net.runelite.api.Point point = Perspective.getCanvasTextLocation(
			client, graphics, localPoint, text, logicalHeight + Z_OFFSET_TEXT);

		if (point == null)
		{
			return;
		}

		OverlayUtil.renderTextLocation(graphics, point, text, getTickColor(ticks));
	}

	private void renderProgressBar(Graphics2D graphics, LocalPoint localPoint, int ticks, int maxTicks, int logicalHeight)
	{
		net.runelite.api.Point point = Perspective.getCanvasTextLocation(
			client, graphics, localPoint, "", logicalHeight + Z_OFFSET_BAR);

		if (point == null)
		{
			return;
		}

		int x = point.getX() - BAR_WIDTH / 2;
		int y = point.getY();

		// Background
		graphics.setColor(COLOR_BAR_BG);
		graphics.fillRect(x, y, BAR_WIDTH, BAR_HEIGHT);

		// Fill — depletes from full to empty as the attack approaches
		float progress = (float) Math.max(0, ticks) / maxTicks;
		int filledWidth = Math.round(BAR_WIDTH * progress);

		graphics.setColor(getTickColor(ticks));
		graphics.fillRect(x, y, filledWidth, BAR_HEIGHT);

		// Border
		graphics.setColor(COLOR_BAR_BORDER);
		graphics.drawRect(x, y, BAR_WIDTH, BAR_HEIGHT);
	}

	private Color getTickColor(int ticks)
	{
		if (ticks <= 1)
		{
			return COLOR_DANGER;
		}
		else if (ticks <= 2)
		{
			return COLOR_CAUTION;
		}
		return COLOR_SAFE;
	}
}
