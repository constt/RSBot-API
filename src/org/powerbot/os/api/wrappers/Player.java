package org.powerbot.os.api.wrappers;

import org.powerbot.os.api.MethodContext;
import org.powerbot.os.client.Client;
import org.powerbot.os.client.PlayerComposite;

import java.lang.ref.SoftReference;

public class Player extends Actor {
	private final SoftReference<org.powerbot.os.client.Player> player;

	public Player(final MethodContext ctx, final org.powerbot.os.client.Player player) {
		super(ctx);
		this.player = new SoftReference<org.powerbot.os.client.Player>(player);
	}

	@Override
	protected org.powerbot.os.client.Actor getActor() {
		return player.get();
	}

	@Override
	public String getName() {
		final org.powerbot.os.client.Player player = this.player.get();
		final String str = player != null ? player.getName() : "";
		return str != null ? str : "";
	}

	@Override
	public int getCombatLevel() {
		final org.powerbot.os.client.Player player = this.player.get();
		return player != null ? player.getCombatLevel() : -1;
	}

	public int getTeam() {
		final org.powerbot.os.client.Player player = this.player.get();
		return player != null ? player.getTeam() : -1;
	}

	public int[] getAppearance() {
		final org.powerbot.os.client.Player player = this.player.get();
		final PlayerComposite composite = player != null ? player.getComposite() : null;
		final int[] arr = composite != null ? composite.getAppearance() : new int[0];
		return arr != null ? arr.clone() : new int[0];
	}

	@Override
	public boolean isValid() {
		final Client client = ctx.getClient();
		final org.powerbot.os.client.Player player = this.player.get();
		if (client == null || player == null) return false;
		final org.powerbot.os.client.Player[] arr = client.getPlayers();
		for (final org.powerbot.os.client.Player a : arr) {
			if (a == player) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s[name=%s/level=%d/team=%d]",
				Player.class.getName(), getName(), getCombatLevel(), getTeam());
	}
}
