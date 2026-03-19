package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("enemyattacktimer")
public interface EnemyAttackTimerConfig extends Config
{
	@ConfigItem(
		keyName = "defaultAttackSpeed",
		name = "Default Attack Speed (ticks)",
		description = "Fallback attack speed in ticks for NPCs whose speed cannot be determined"
	)
	@Range(min = 1, max = 10)
	default int defaultAttackSpeed()
	{
		return 4;
	}

	@ConfigItem(
		keyName = "showTickCount",
		name = "Show Tick Count",
		description = "Display the tick countdown number above the enemy"
	)
	default boolean showTickCount()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showProgressBar",
		name = "Show Progress Bar",
		description = "Display a progress bar showing time remaining until the next attack"
	)
	default boolean showProgressBar()
	{
		return true;
	}
}
