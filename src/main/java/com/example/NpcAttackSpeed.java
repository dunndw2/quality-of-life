package com.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Hardcoded attack speed lookup table (in ticks) for popular bosses,
 * Inferno enemies, and Fortis Colosseum enemies.
 *
 * Used as the primary source before falling back to NPCComposition.getAttackSpeed()
 * and then the user's configured default.
 */
public final class NpcAttackSpeed
{
	private static final Map<Integer, Integer> ATTACK_SPEEDS = new HashMap<>();

	static
	{
		// ===== God Wars Dungeon =====
		ATTACK_SPEEDS.put(2215, 6);  // General Graardor
		ATTACK_SPEEDS.put(3162, 3);  // Kree'arra
		ATTACK_SPEEDS.put(2205, 2);  // Commander Zilyana
		ATTACK_SPEEDS.put(3129, 6);  // K'ril Tsutsaroth

		// ===== Barrows =====
		ATTACK_SPEEDS.put(1672, 6);  // Ahrim the Blighted
		ATTACK_SPEEDS.put(1673, 7);  // Dharok the Wretched
		ATTACK_SPEEDS.put(1674, 5);  // Guthan the Infested
		ATTACK_SPEEDS.put(1675, 4);  // Karil the Tainted
		ATTACK_SPEEDS.put(1676, 5);  // Torag the Corrupted
		ATTACK_SPEEDS.put(1677, 5);  // Verac the Defiled
		// DT2 Ancient Vault variants
		ATTACK_SPEEDS.put(12316, 6); ATTACK_SPEEDS.put(12322, 6); // Ahrim (DT2)
		ATTACK_SPEEDS.put(12317, 7); ATTACK_SPEEDS.put(12323, 7); // Dharok (DT2)
		ATTACK_SPEEDS.put(12318, 5); ATTACK_SPEEDS.put(12324, 5); // Guthan (DT2)
		ATTACK_SPEEDS.put(12319, 4); ATTACK_SPEEDS.put(12325, 4); // Karil (DT2)
		ATTACK_SPEEDS.put(12320, 5); ATTACK_SPEEDS.put(12326, 5); // Torag (DT2)
		ATTACK_SPEEDS.put(12321, 5); ATTACK_SPEEDS.put(12327, 5); // Verac (DT2)

		// ===== Zulrah =====
		ATTACK_SPEEDS.put(2042, 3);  // Zulrah (Serpentine / green)
		ATTACK_SPEEDS.put(2043, 3);  // Zulrah (Magma / red)
		ATTACK_SPEEDS.put(2044, 3);  // Zulrah (Tanzanite / blue)

		// ===== Vorkath =====
		ATTACK_SPEEDS.put(8058, 5);  // Vorkath
		ATTACK_SPEEDS.put(8059, 5);  // Vorkath
		ATTACK_SPEEDS.put(8060, 5);  // Vorkath
		ATTACK_SPEEDS.put(8061, 5);  // Vorkath (active combat)

		// ===== Cerberus =====
		ATTACK_SPEEDS.put(5862, 6);  // Cerberus
		ATTACK_SPEEDS.put(5863, 6);  // Cerberus
		ATTACK_SPEEDS.put(5866, 6);  // Cerberus

		// ===== Abyssal Sire =====
		ATTACK_SPEEDS.put(5886, 7);  // Abyssal Sire (phase 1)
		ATTACK_SPEEDS.put(5887, 7);  // Abyssal Sire (phase 2)
		ATTACK_SPEEDS.put(5888, 7);  // Abyssal Sire (phase 3 stage 1)
		ATTACK_SPEEDS.put(5891, 7);  // Abyssal Sire (phase 3 stage 2)

		// ===== The Nightmare / Phosani's Nightmare =====
		ATTACK_SPEEDS.put(378, 6);   // The Nightmare
		ATTACK_SPEEDS.put(377, 6);   // Phosani's Nightmare
		ATTACK_SPEEDS.put(9423, 6);  // Phosani's Nightmare
		for (int id = 9425; id <= 9433; id++) { ATTACK_SPEEDS.put(id, 6); } // Nightmare phases
		ATTACK_SPEEDS.put(9460, 6);  // Nightmare

		// ===== Nex =====
		ATTACK_SPEEDS.put(11278, 4); // Nex (Smoke phase)
		ATTACK_SPEEDS.put(11279, 4); // Nex (Shadow phase)
		ATTACK_SPEEDS.put(11280, 4); // Nex (Blood phase)
		ATTACK_SPEEDS.put(11281, 4); // Nex (Ice phase)
		ATTACK_SPEEDS.put(11282, 4); // Nex (Zaros phase)

		// ===== Corporeal Beast =====
		ATTACK_SPEEDS.put(319, 4);   // Corporeal Beast

		// ===== King Black Dragon =====
		ATTACK_SPEEDS.put(239, 4);   // King Black Dragon
		ATTACK_SPEEDS.put(2642, 4);  // King Black Dragon (alt)

		// ===== Dagannoth Kings =====
		ATTACK_SPEEDS.put(2265, 4);  // Dagannoth Supreme
		ATTACK_SPEEDS.put(2266, 4);  // Dagannoth Prime
		ATTACK_SPEEDS.put(2267, 4);  // Dagannoth Rex

		// ===== Grotesque Guardians =====
		ATTACK_SPEEDS.put(7851, 6); ATTACK_SPEEDS.put(7854, 6); ATTACK_SPEEDS.put(7855, 6); // Dusk
		ATTACK_SPEEDS.put(7882, 6); ATTACK_SPEEDS.put(7883, 6); ATTACK_SPEEDS.put(7886, 6); // Dusk
		ATTACK_SPEEDS.put(7887, 6); ATTACK_SPEEDS.put(7888, 6); ATTACK_SPEEDS.put(7889, 6); // Dusk (final phase)
		ATTACK_SPEEDS.put(7852, 6); ATTACK_SPEEDS.put(7853, 6); // Dawn
		ATTACK_SPEEDS.put(7884, 6); ATTACK_SPEEDS.put(7885, 6); // Dawn

		// ===== Alchemical Hydra =====
		ATTACK_SPEEDS.put(8615, 6);  // Serpentine phase
		ATTACK_SPEEDS.put(8619, 6);  // Electric phase
		ATTACK_SPEEDS.put(8620, 6);  // Fire phase
		ATTACK_SPEEDS.put(8621, 4);  // Extinguished (final) phase

		// ===== Skotizo =====
		ATTACK_SPEEDS.put(7286, 6);  // Skotizo

		// ===== Scurrius =====
		ATTACK_SPEEDS.put(7221, 4);  // Scurrius (group)
		ATTACK_SPEEDS.put(7222, 4);  // Scurrius (solo)

		// ===== Chambers of Xeric (CoX) =====
		ATTACK_SPEEDS.put(7540, 3); ATTACK_SPEEDS.put(7541, 3); ATTACK_SPEEDS.put(7542, 3); // Tekton
		ATTACK_SPEEDS.put(7543, 3); // Tekton (enraged)
		ATTACK_SPEEDS.put(7544, 3); // Tekton CM (enraged)
		ATTACK_SPEEDS.put(7545, 3); // Tekton CM
		ATTACK_SPEEDS.put(7527, 4); // Vanguard (melee)
		ATTACK_SPEEDS.put(7528, 4); // Vanguard (ranged)
		ATTACK_SPEEDS.put(7529, 4); // Vanguard (magic)
		ATTACK_SPEEDS.put(7530, 3); ATTACK_SPEEDS.put(7531, 3); ATTACK_SPEEDS.put(7532, 3); // Vespula
		ATTACK_SPEEDS.put(7533, 2); // Abyssal Portal
		ATTACK_SPEEDS.put(7604, 4); ATTACK_SPEEDS.put(7605, 4); ATTACK_SPEEDS.put(7606, 4); // Skeletal Mystic
		ATTACK_SPEEDS.put(6766, 4); ATTACK_SPEEDS.put(6767, 4); // Lizardman Shaman
		ATTACK_SPEEDS.put(7744, 4); ATTACK_SPEEDS.put(7745, 4); // Lizardman Shaman (CoX)
		ATTACK_SPEEDS.put(8565, 4); // Lizardman Shaman (temple)
		ATTACK_SPEEDS.put(7562, 4); // Muttadile (small)
		ATTACK_SPEEDS.put(7561, 4); ATTACK_SPEEDS.put(7563, 4); // Muttadile (large)
		ATTACK_SPEEDS.put(7584, 3); // Ice Demon
		ATTACK_SPEEDS.put(7585, 3); // Ice Demon (CM)
		ATTACK_SPEEDS.put(7551, 4); // Great Olm (normal)
		ATTACK_SPEEDS.put(7554, 4); // Great Olm (CM)

		// ===== Theatre of Blood (ToB) =====
		// Maiden of Sugadinti
		ATTACK_SPEEDS.put(10814, 10);                                                    // Entry
		ATTACK_SPEEDS.put(8360, 10); ATTACK_SPEEDS.put(8361, 10);
		ATTACK_SPEEDS.put(8362, 10); ATTACK_SPEEDS.put(8363, 10);                       // Normal
		ATTACK_SPEEDS.put(10822, 10);                                                    // Hard mode
		// Pestilent Bloat
		ATTACK_SPEEDS.put(10812, 1); ATTACK_SPEEDS.put(8359, 1); ATTACK_SPEEDS.put(10813, 1);
		// Nylocas Vasilias
		ATTACK_SPEEDS.put(10786, 4); ATTACK_SPEEDS.put(10787, 4); ATTACK_SPEEDS.put(10788, 4); ATTACK_SPEEDS.put(10789, 4);
		ATTACK_SPEEDS.put(8354, 4);  ATTACK_SPEEDS.put(8355, 4);  ATTACK_SPEEDS.put(8356, 4);  ATTACK_SPEEDS.put(8357, 4);
		ATTACK_SPEEDS.put(10807, 4); ATTACK_SPEEDS.put(10808, 4); ATTACK_SPEEDS.put(10809, 4); ATTACK_SPEEDS.put(10810, 4);
		// Sotetseg
		ATTACK_SPEEDS.put(10864, 5); ATTACK_SPEEDS.put(10865, 5);
		ATTACK_SPEEDS.put(8387, 5);  ATTACK_SPEEDS.put(8388, 5);
		ATTACK_SPEEDS.put(10867, 5); ATTACK_SPEEDS.put(10868, 5);
		// Xarpus
		ATTACK_SPEEDS.put(10766, 4); ATTACK_SPEEDS.put(10767, 4); ATTACK_SPEEDS.put(10768, 4); ATTACK_SPEEDS.put(10769, 4);
		ATTACK_SPEEDS.put(8338, 4);  ATTACK_SPEEDS.put(8339, 4);  ATTACK_SPEEDS.put(8340, 4);  ATTACK_SPEEDS.put(8341, 4);
		ATTACK_SPEEDS.put(10770, 4); ATTACK_SPEEDS.put(10771, 4); ATTACK_SPEEDS.put(10772, 4); ATTACK_SPEEDS.put(10773, 4);
		// Verzik Vitur P1
		ATTACK_SPEEDS.put(10830, 14); ATTACK_SPEEDS.put(8369, 14); ATTACK_SPEEDS.put(10847, 14);
		// Verzik Vitur P2
		ATTACK_SPEEDS.put(10831, 4); ATTACK_SPEEDS.put(10833, 4); ATTACK_SPEEDS.put(10834, 4);
		ATTACK_SPEEDS.put(8370, 4);  ATTACK_SPEEDS.put(8372, 4);  ATTACK_SPEEDS.put(8373, 4);
		ATTACK_SPEEDS.put(10848, 4); ATTACK_SPEEDS.put(10850, 4); ATTACK_SPEEDS.put(10851, 4);
		// Verzik Vitur P3
		ATTACK_SPEEDS.put(10832, 7); ATTACK_SPEEDS.put(10835, 7); ATTACK_SPEEDS.put(10836, 7);
		ATTACK_SPEEDS.put(8371, 7);  ATTACK_SPEEDS.put(8374, 7);  ATTACK_SPEEDS.put(8375, 7);
		ATTACK_SPEEDS.put(10849, 7); ATTACK_SPEEDS.put(10852, 7); ATTACK_SPEEDS.put(10853, 7);

		// ===== Tombs of Amascut (ToA) =====
		// Kephri
		ATTACK_SPEEDS.put(11719, 6); ATTACK_SPEEDS.put(11720, 6); ATTACK_SPEEDS.put(11721, 6);
		// Akkha
		for (int id = 11789; id <= 11796; id++) { ATTACK_SPEEDS.put(id, 6); }
		// Ba-Ba
		ATTACK_SPEEDS.put(11778, 6); ATTACK_SPEEDS.put(11779, 6); ATTACK_SPEEDS.put(11780, 6);
		// Zebak
		ATTACK_SPEEDS.put(11730, 7); // Normal
		ATTACK_SPEEDS.put(11732, 4); // Enraged
		// Elidinis' Warden
		ATTACK_SPEEDS.put(11746, 7); ATTACK_SPEEDS.put(11748, 7);
		ATTACK_SPEEDS.put(11753, 7); ATTACK_SPEEDS.put(11754, 7); ATTACK_SPEEDS.put(11755, 7);
		ATTACK_SPEEDS.put(11761, 7); ATTACK_SPEEDS.put(11763, 7);
		// Tumeken's Warden
		ATTACK_SPEEDS.put(11747, 8); ATTACK_SPEEDS.put(11749, 8);
		ATTACK_SPEEDS.put(11756, 8); ATTACK_SPEEDS.put(11757, 8); ATTACK_SPEEDS.put(11758, 8);
		ATTACK_SPEEDS.put(11762, 8); ATTACK_SPEEDS.put(11764, 8);

		// ===== Desert Treasure II =====
		ATTACK_SPEEDS.put(12191, 5);  // Duke Sucellus
		ATTACK_SPEEDS.put(12204, 10); ATTACK_SPEEDS.put(12205, 10); // The Whisperer
		ATTACK_SPEEDS.put(12206, 10); ATTACK_SPEEDS.put(12207, 10); // The Whisperer (awakened)
		ATTACK_SPEEDS.put(12223, 5);  ATTACK_SPEEDS.put(12426, 5);  // Vardorvis
		ATTACK_SPEEDS.put(12224, 5);  ATTACK_SPEEDS.put(12228, 5);  ATTACK_SPEEDS.put(12425, 5); // Vardorvis (awakened)

		// ===== The Inferno =====
		ATTACK_SPEEDS.put(7691, 4);  // Jal-Nib (nibbler)
		ATTACK_SPEEDS.put(7708, 3);  // Jal-MejJak
		ATTACK_SPEEDS.put(7693, 6);  // Jal-Ak (blob)
		ATTACK_SPEEDS.put(7697, 4);  // Jal-ImKot (melee)
		ATTACK_SPEEDS.put(7698, 4);  // Jal-Xil (ranger)
		ATTACK_SPEEDS.put(7702, 4);  // Jal-Xil (ranger, alt)
		ATTACK_SPEEDS.put(7699, 4);  // Jal-Zek (mage)
		ATTACK_SPEEDS.put(7703, 4);  // Jal-Zek (mage, alt)
		ATTACK_SPEEDS.put(7700, 8);  // JalTok-Jad
		ATTACK_SPEEDS.put(7704, 8);  // JalTok-Jad (alt)
		ATTACK_SPEEDS.put(10623, 8); // JalTok-Jad (alt)
		ATTACK_SPEEDS.put(3128, 4);  // Yt-HurKot (Fight Caves)
		ATTACK_SPEEDS.put(7701, 4);  // Yt-HurKot (Inferno)
		ATTACK_SPEEDS.put(7705, 4);  // Yt-HurKot (Inferno, alt)
		ATTACK_SPEEDS.put(7706, 10); // TzKal-Zuk

		// ===== Fortis Colosseum =====
		ATTACK_SPEEDS.put(12816, 6);  // Fremennik warband berserker
		ATTACK_SPEEDS.put(12814, 6);  // Fremennik warband archer
		ATTACK_SPEEDS.put(12815, 6);  // Fremennik warband seer
		ATTACK_SPEEDS.put(12811, 5);  // Serpent shaman
		ATTACK_SPEEDS.put(12810, 5);  // Jaguar warrior
		ATTACK_SPEEDS.put(12812, 5);  // Minotaur
		ATTACK_SPEEDS.put(12813, 5);  // Minotaur (red flag)
		ATTACK_SPEEDS.put(12817, 5);  // Javelin Colossus
		ATTACK_SPEEDS.put(12819, 5);  // Shockwave Colossus
		ATTACK_SPEEDS.put(12818, 10); // Manticore
		ATTACK_SPEEDS.put(12821, 6);  // Sol Heredit
	}

	private NpcAttackSpeed()
	{
	}

	/**
	 * Returns the hardcoded attack speed in ticks for the given NPC ID,
	 * or -1 if the NPC is not in the table.
	 */
	public static int getAttackSpeed(int npcId)
	{
		return ATTACK_SPEEDS.getOrDefault(npcId, -1);
	}
}
