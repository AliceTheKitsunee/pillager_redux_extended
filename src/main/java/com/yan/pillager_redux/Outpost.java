//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yan.pillager_redux;

import net.minecraft.world.level.ChunkPos;

public class Outpost {
    private boolean isDestroyed;
    private ChunkPos chunkPos;
    private String UID;
    private int garrisonStrength;
    private int killCount;

    public Outpost(boolean destroyed, ChunkPos pos, String id, int strength, int killCount) {
        this.isDestroyed = destroyed;
        this.chunkPos = pos;
        this.UID = id;
        this.garrisonStrength = strength;
        this.killCount = killCount;
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    public void setGarrisonStrength(int strength) {
        this.garrisonStrength = strength;
    }

    public String getUID() {
        return this.UID;
    }

    public int getGarrisonStrength() {
        return this.garrisonStrength;
    }

    public ChunkPos getChunkPos() {
        return this.chunkPos;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public int getKillCount(){
        return this.killCount;
    }

    public int setKillCount(int killCount){
        return this.killCount = killCount;
    }
}
