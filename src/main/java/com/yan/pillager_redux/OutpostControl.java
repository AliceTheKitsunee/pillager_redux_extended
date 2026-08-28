//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yan.pillager_redux;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class OutpostControl {
    public OutpostControl() {
    }

    private static StructureStart getOutpost(ServerLevel level, BlockPos pos) {
        Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
        Structure outpost = (Structure)registry.get(BuiltinStructures.PILLAGER_OUTPOST);
        return outpost == null ? null : level.structureManager().getStructureAt(pos, outpost);
    }

    public static boolean isInsideOutpost(ServerLevel level, BlockPos pos) {
        return level.structureManager().getStructureWithPieceAt(pos, BuiltinStructures.PILLAGER_OUTPOST).isValid();
    }

    public static boolean isInsideOutpostArea(ServerLevel level, BlockPos pos) {
        StructureStart structureStart = getOutpost(level, pos);
        if (structureStart == null) {
            return false;
        } else if (!structureStart.isValid()) {
            return false;
        } else {
            BurntOutpostSavedData data = BurntOutpostSavedData.get(level);
            BoundingBox box = structureStart.getBoundingBox();
            BlockPos center = box.getCenter();
            int dx = Math.abs(pos.getX() - center.getX());
            int dz = Math.abs(pos.getZ() - center.getZ());
            int dy = Math.abs(pos.getY() - center.getY());
            if(!data.entryExists( getOutpostUniqueID(getOutpostChunk(level, pos), data))){

                data.createNewEntry(getOutpostUniqueID(getOutpostChunk(level, pos), data));
                //Debug.NewEntryLogged(getOutpostUniqueID(getOutpostChunk(level, pos), data));
            }
            return dx <= 72 && dz <= 72 && dy <= 56;
        }
    }

    public static ChunkPos getOutpostChunk(ServerLevel level, BlockPos pos) {
        StructureStart structureStart = getOutpost(level, pos);
        if (structureStart == null) {
            return null;
        } else {
            return !structureStart.isValid() ? null : structureStart.getChunkPos();
        }
    }

    public static boolean isOutpostBurning(ServerLevel level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof FireBlock ? isInsideOutpost(level, pos) : false;
    }

    public static void markOutpostBurnt(ServerLevel level, ChunkPos pos) {
        BurntOutpostSavedData data = BurntOutpostSavedData.get(level);
        String id = getOutpostUniqueID(pos, data);
        //Debug.LogCoOrds(pos.x, 0, pos.z);
        data.markBurnt(id);
    }

    public static boolean isOutpustBurnt(ServerLevel level, ChunkPos pos) {
        BurntOutpostSavedData data = BurntOutpostSavedData.get(level);
        String id = getOutpostUniqueID(pos, data);
        return data.isBurnt(id);
    }

    public static int getOutpostKillCounts(ServerLevel level, ChunkPos pos){
        if(Config.maxPillagerKills == 0){
            return -1;
        }
        BurntOutpostSavedData data = BurntOutpostSavedData.get(level);
        String id = getOutpostUniqueID(pos, data);


        return data.getOutpostKillCounter(id);
    }



    public static void addOutpostKill(ServerLevel level, ChunkPos pos){
        BurntOutpostSavedData data = BurntOutpostSavedData.get(level);
        String id = getOutpostUniqueID(pos, data);
        int currentKills = data.getOutpostKillCounter(id);

        //Debug.LogCoOrds(pos.x, 0, pos.z);

        data.setOutpostKillCounter(id, currentKills + 1);



    }


    public static String getOutpostUniqueID(ChunkPos pos, BurntOutpostSavedData data) {
        String rawStr = "X" + pos.x + "Z" + pos.z + "OUTPOST";
        String newStr;

        if(!data.entryExists(rawStr) || data.getOutpostKillCounter(rawStr) == 0){

            newStr = rawStr + "_0";
            return newStr;
        }

        String str1 = rawStr;
        newStr = rawStr + "_" + data.getOutpostKillCounter(str1);


        //Debug.EntryReturned(newStr, data);

        return newStr;
    }
}
