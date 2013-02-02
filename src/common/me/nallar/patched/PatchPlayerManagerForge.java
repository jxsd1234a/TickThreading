package me.nallar.patched;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import me.nallar.tickthreading.Log;
import me.nallar.tickthreading.patcher.Declare;
import net.minecraft.server.management.PlayerInstance;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

public abstract class PatchPlayerManagerForge extends PlayerManager {
	@Declare
	public java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock playerUpdateLock_;
	@Declare
	public java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock playersUpdateLock_;

	public void construct() {
		ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
		playersUpdateLock = reentrantReadWriteLock.writeLock();
		playerUpdateLock = reentrantReadWriteLock.readLock();
	}

	public PatchPlayerManagerForge(WorldServer par1WorldServer, int par2) {
		super(par1WorldServer, par2);
	}

	@Override
	@Declare
	public net.minecraft.util.LongHashMap getChunkWatchers() {
		return this.playerInstances;
	}

	@Override
	@Declare
	public List getChunkWatcherWithPlayers() {
		return this.chunkWatcherWithPlayers;
	}

	@Override
	public void updatePlayerInstances() {
		playersUpdateLock.lock();
		try {
			for (Object chunkWatcherWithPlayer : this.chunkWatcherWithPlayers) {
				if (chunkWatcherWithPlayer instanceof PlayerInstance) {
					((PlayerInstance) chunkWatcherWithPlayer).sendChunkUpdate();
				}
			}
			this.chunkWatcherWithPlayers.clear();
		} catch (Exception e) {
			Log.severe("Failed to send some chunks", e);
		} finally {
			playersUpdateLock.unlock();
		}

		if (this.players.isEmpty()) {
			this.theWorldServer.theChunkProviderServer.unloadAllChunks();
		}
	}
}
