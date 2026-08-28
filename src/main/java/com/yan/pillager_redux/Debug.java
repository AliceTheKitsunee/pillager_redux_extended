//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yan.pillager_redux;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.Arrays;

@EventBusSubscriber(
        modid = "pillager_redux"
)
public class Debug {
    public Debug() {
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent event) {
        Log("Config loaded.");
    }

    public static void Log(String message) {
        System.out.println("[Debug] " + message);
    }

    public static void LogEntryNotFound(String key, int dispatched){
        Log("KEY of wanted outpost is not found :C  Key: " + key + ", Dispatched value: " + dispatched);
    }


    public static void pillagerKilled(){
        Log("Pillager killed! ");
    }

    public static void markedBurnt(){
        Log("Outpost marked burnt! ");
    }

    public static void NewEntryLogged(String key){
        Log("NEW ENTRY LOGGED: " + key);
    }

    public static void EntryReturned(String key, BurntOutpostSavedData data){
        Log("Outpost key returned in getOutpostUniqueID: " + key + ", Dispatcher: " + data.getOutpostKillCounter(key));
    }

    public static void debugKey(String key, String getI){
        Log("Debug Key: " + key + ", get[i]: " + getI);
    }

    public static void DispatchedValueGot(int var){
        Log("VALUE OF string dispatched: " + var);
    }

    public static void printStringArray(String[] key){

        for(int i = 0; i < key.length; i++) {
            Log("VALUE OF Unwrapped Key [" + i + "]: " + key[i]);
        }
    }

    public static void LogCoOrds(float x, float y, float z) {
        Log("DEBUG!! OUTPOST IS BURNING: " + "X: " + x + ", Y: " + y + ", Z: " + z);
    }
}
