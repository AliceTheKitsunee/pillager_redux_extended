package com.yan.pillager_redux;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(
        modid = "pillager_redux",
        bus = Bus.MOD
)
public class Config {
    private static final Builder BUILDER = new Builder();
    private static final BooleanValue SHOULD_STOP_SPAWN;
    private static final IntValue MAX_KILLS;
    static final ForgeConfigSpec SPEC;
    public static boolean shouldStopSpawn;
    public static int maxPillagerKills;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private static boolean validateItemName(Object obj) {
        boolean var10000;
        if (obj instanceof String) {
            String itemName = (String)obj;
            if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        shouldStopSpawn = (Boolean)SHOULD_STOP_SPAWN.get();
        maxPillagerKills = (Integer)MAX_KILLS.get();
    }

    static {
        SHOULD_STOP_SPAWN = BUILDER.comment("Whether to stop illagers from spawning when outpost is set on fire: ").define("shouldStopSpawn", true);
        MAX_KILLS = BUILDER.comment("Max Illager Kills - Outpost will stop spawning illagers after THIS amount of kills: (0 means this feature is disabled)").defineInRange("maxPillagerKills", 10, 0, 512);SPEC = BUILDER.build();
    }
}