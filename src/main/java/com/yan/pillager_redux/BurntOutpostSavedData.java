//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yan.pillager_redux;

import java.util.*;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class BurntOutpostSavedData extends SavedData {
    private static final String NAME = "burnt_outposts";

    private static final String TAG_KILL_COUNTS = "outposts_kill_counts";
    private Set<String> burntOutposts = new HashSet();

    public List<String> burntOutpostsList = new ArrayList<>(burntOutposts);

    public BurntOutpostSavedData() {
    }

    public static BurntOutpostSavedData load(CompoundTag tag) {
        BurntOutpostSavedData data = new BurntOutpostSavedData();
        ListTag list = tag.getList("Outposts", 8);
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Tag t = (Tag)var3.next();
            data.burntOutposts.add(t.getAsString());
        }

        return data;
    }

    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        Iterator var3 = this.burntOutposts.iterator();

        while(var3.hasNext()) {
            String s = (String)var3.next();
            list.add(StringTag.valueOf(s));
        }

        tag.put("Outposts", list);
        return tag;
    }

    public static BurntOutpostSavedData get(ServerLevel level) {
        return (BurntOutpostSavedData)level.getDataStorage().computeIfAbsent(BurntOutpostSavedData::load, BurntOutpostSavedData::new, "burnt_outposts");
    }

    public boolean isBurnt(String key) {

        for(int i=0; i< this.burntOutpostsList.size(); i++ ){
            if(this.burntOutpostsList.get(i).contains(key) && this.burntOutpostsList.get(i).charAt(0)=='+') {

                return true;


            }
        }

        return false;

        //return this.burntOutposts.contains(key);
    }

    public void updateList(){
        this.burntOutpostsList = new ArrayList<>(this.burntOutposts);
    }

    public void convertSet(){
        this.burntOutposts = new HashSet<>(this.burntOutpostsList);
        this.setDirty();
    }

    public int outpostKeyDispatcher(String key){

        String[] unwrappedKey = key.split("_");

        //Debug.printStringArray(unwrappedKey);


        if(unwrappedKey.length != 1) {
            try {
                int result = Integer.parseInt(unwrappedKey[1]);
                //Debug.DispatchedValueGot(result);
                return result;
            } catch (NumberFormatException e) {
                return -3;
            }
        }

        return 0;


    }

    public int getOutpostKillCounter(String key){

        String[] keySplit = key.split("_");

        for(int i=0; i< this.burntOutpostsList.size(); i++ ){

            //Debug.debugKey(key, this.burntOutpostsList.get(i));
            if(this.burntOutpostsList.get(i).contains(keySplit[0])) {

                return outpostKeyDispatcher(burntOutpostsList.get(i));

            }


        }


        //Debug.LogEntryNotFound(key, outpostKeyDispatcher(key));
        return 0;

    }


    public void setOutpostKillCounter(String key, int set){



        if(entryExists(key)){


            //int currentKills = getOutpostKillCounter(returnEntry(key));

            String[] splitEntry = returnEntry(key).split("_");

            String newEntry = splitEntry[0] + "_" + set;

            updateEntry(key, newEntry);



        }
    }

    public void updateEntry(String oldKey, String newKey){

        for(int i=0; i< this.burntOutpostsList.size(); i++ ){
            if(this.burntOutpostsList.get(i).contains(oldKey)) {

                this.burntOutpostsList.set(i, newKey);
                convertSet();


            }
        }
    }

    public boolean entryExists(String key){

        for(int i=0; i< this.burntOutpostsList.size(); i++ ){
            if(this.burntOutpostsList.get(i).contains(key)) {


                return true;


            }
        }


        return false;
    }

    public String returnEntry(String key){

        if(entryExists(key)){

            for(int i=0; i< this.burntOutpostsList.size(); i++ ){
                if(this.burntOutpostsList.get(i).contains(key)) {


                    return burntOutpostsList.get(i);


                }
            }

        }

        createNewEntry(key);

        return key;

    }

    public void createNewEntry(String key){


        this.burntOutposts.add(key);
        this.setDirty();
        this.updateList();
    }

    public void markBurnt(String key) {


        if(entryExists(key)){

            if(returnEntry(key).charAt(0) == '+'){
                return;
            }

            /*
            for(int i=0; i< this.burntOutpostsList.size(); i++ ){
                if(this.burntOutpostsList.get(i).contains(key)) {

                    this.burntOutpostsList.set(i, "+" + burntOutpostsList.get(i));
                    convertSet();
                    this.setDirty();
                    return;


                }
            }

             */

            updateEntry(key, "+" + key);
            return;

        }



        createNewEntry("+" + key);


        //this.burntOutposts.add(key);

    }
}
