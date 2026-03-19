package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("enemyattacktimer")
public interface EnemyAttackTimerConfig extends Config
{
	@ConfigSection(
		name = "Tick Count",
		description = "Options for the tick countdown number",
		position = 0
	)
	String tickCountSection = "tickCount";

	@ConfigSection(
		name = "Progress Bar",
		description = "Options for the progress bar",
		position = 1
	)
	String progressBarSection = "progressBar";

	@ConfigSection(
		name = "General",
		description = "General plugin settings",
		position = 2
	)
	String generalSection = "general";

	// ── Tick Count ────────────────────────────────────────────────────────────

	@ConfigItem(
		keyName = "showTickCount",
		name = "Show Tick Count",
		description = "Display the tick countdown number above the enemy",
		section = tickCountSection
	)
	default boolean showTickCount()
	{
		return true;
	}

	@ConfigItem(
		keyName = "tickCountFontSize",
		name = "Font Size",
		description = "Font size of the tick countdown number",
		section = tickCountSection
	)
	@Range(min = 8, max = 32)
	default int tickCountFontSize()
	{
		return 16;
	}

	@ConfigItem(
		keyName = "tickCountZOffset",
		name = "Height Offset",
		description = "How high above the NPC to draw the tick count (increase to raise it)",
		section = tickCountSection
	)
	@Range(min = 0, max = 300)
	default int tickCountZOffset()
	{
		return 40;
	}

	// ── Progress Bar ──────────────────────────────────────────────────────────

	@ConfigItem(
		keyName = "showProgressBar",
		name = "Show Progress Bar",
		description = "Display a progress bar showing time remaining until the next attack",
		section = progressBarSection
	)
	default boolean showProgressBar()
	{
		return true;
	}

	@ConfigItem(
		keyName = "progressBarZOffset",
		name = "Height Offset",
		description = "How high above the NPC to draw the progress bar (increase to raise it)",
		section = progressBarSection
	)
	@Range(min = 0, max = 300)
	default int progressBarZOffset()
	{
		return 60;
	}

	// ── General ───────────────────────────────────────────────────────────────

	@ConfigItem(
		keyName = "defaultAttackSpeed",
		name = "Default Attack Speed (ticks)",
		description = "Fallback attack speed in ticks for NPCs not in the built-in table",
		section = generalSection
	)
	@Range(min = 1, max = 10)
	default int defaultAttackSpeed()
	{
		return 4;
	}
}
