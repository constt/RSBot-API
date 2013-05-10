package org.powerbot.script.xenon;

import java.util.Arrays;

import org.powerbot.bot.Bot;
import org.powerbot.client.Client;
import org.powerbot.client.HashTable;
import org.powerbot.client.RSNPC;
import org.powerbot.client.RSNPCNode;
import org.powerbot.script.internal.Nodes;
import org.powerbot.script.xenon.util.Filter;
import org.powerbot.script.xenon.wrappers.Npc;
import org.powerbot.script.xenon.wrappers.Player;
import org.powerbot.script.xenon.wrappers.Tile;

public class Npcs {
	public static Npc[] getLoaded() {
		final Client client = Bot.client();
		if (client == null) return new Npc[0];

		final int[] indices = client.getRSNPCIndexArray();
		final HashTable npcTable = client.getRSNPCNC();
		if (indices == null || npcTable == null) return new Npc[0];

		final Npc[] npcs = new Npc[indices.length];
		int d = 0;
		for (final int index : indices) {
			Object npc = Nodes.lookup(npcTable, index);
			if (npc == null) continue;
			if (npc instanceof RSNPCNode) npc = ((RSNPCNode) npc).getRSNPC();
			if (npc instanceof RSNPC) npcs[d++] = new Npc((RSNPC) npc);
		}

		return Arrays.copyOf(npcs, d);
	}

	public static Npc[] getLoaded(final Filter<Npc> filter) {
		final Npc[] npcs = getLoaded();
		final Npc[] set = new Npc[npcs.length];
		int d = 0;
		for (final Npc npc : npcs) if (filter.accept(npc)) set[d++] = npc;
		return Arrays.copyOf(set, d);
	}

	public static Npc[] getLoaded(final int... ids) {
		return getLoaded(new Filter<Npc>() {
			@Override
			public boolean accept(final Npc npc) {
				final int npcId = npc.getId();
				for (final int id : ids) if (npcId == id) return true;
				return false;
			}
		});
	}

	public static Npc getNearest(final Filter<Npc> filter) {
		Npc nearest = null;
		double dist = 104d;

		final Player local = Players.getLocal();
		if (local == null) return null;

		final Tile pos = local.getLocation();
		if (pos == null) return null;
		final Npc[] npcs = getLoaded();
		for (final Npc npc : npcs) {
			final double d;
			if (filter.accept(npc) && (d = Movement.distance(pos, npc)) < dist) {
				nearest = npc;
				dist = d;
			}
		}

		return nearest;
	}

	public static Npc getNearest(final int... ids) {
		return getNearest(new Filter<Npc>() {
			@Override
			public boolean accept(final Npc npc) {
				final int npcId = npc.getId();
				for (final int id : ids) if (npcId == id) return true;
				return false;
			}
		});
	}
}
