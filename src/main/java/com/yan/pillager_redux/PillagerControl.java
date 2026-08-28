//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yan.pillager_redux;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
        modid = "pillager_redux"
)
public class PillagerControl {
    public PillagerControl() {
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if(Config.shouldStopSpawn) {
            ServerLevel level = (ServerLevel) event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = event.getPlacedBlock();
            if (OutpostControl.isOutpostBurning(level, pos, state)) {
                ChunkPos chunkPos = OutpostControl.getOutpostChunk(level, pos);
                OutpostControl.markOutpostBurnt(level, chunkPos);
            }
        }

    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Level current = event.getLevel();


            if (current instanceof ServerLevel level) {
                Entity mob = event.getEntity();
                BlockPos mobSpawnBlock = mob.blockPosition();
                if (mob instanceof Raider) {
                    if (OutpostControl.isInsideOutpostArea(level, mobSpawnBlock)) {
                        ChunkPos chunkPos = OutpostControl.getOutpostChunk(level, mobSpawnBlock);
                        if (chunkPos != null) {
                            if (OutpostControl.isOutpustBurnt(level, chunkPos)) {
                                if (event.isCancelable()) {
                                    event.setCanceled(true);
                                }

                            }


                            //OutpostControl.addOutpostKill(level, chunkPos);



                        }
                    }
                }
            }
        }


    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event){
        Level current = event.getEntity().level();


        if(Config.maxPillagerKills > 0) {
            if (current instanceof ServerLevel level) {
                LivingEntity victim = event.getEntity();
                Entity killer = event.getSource().getEntity();

                BlockPos mobDeathBlock = victim.getOnPos();
                if(victim instanceof Raider){
                    if(OutpostControl.isInsideOutpost(level, mobDeathBlock)) {
                        if (killer instanceof LivingEntity livingEntity) {


                            ChunkPos chunkPos = OutpostControl.getOutpostChunk(level, mobDeathBlock);

                            OutpostControl.addOutpostKill(level, chunkPos);

                            //Debug.pillagerKilled();



                            if(OutpostControl.getOutpostKillCounts(level, chunkPos) >= Config.maxPillagerKills){

                                OutpostControl.markOutpostBurnt(level, chunkPos);
                                //Debug.markedBurnt();

                            }

                        }
                    }

                }

            }
        }

    }
}
