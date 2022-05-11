package me.mechanic.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import me.mechanic.data.Config;

public class EntityHandler {
    public static Map<String, Boolean> entityConfig = new HashMap<String, Boolean>();

    public static Map<Integer, Boolean> dimensionConfig = new HashMap<Integer, Boolean>();

    public static void appendDataList() {
        loadEntityList();
        loadDimensionList();
    }

    private static void addEntityToList(String e) {
        if (entityConfig.containsKey(e))
            return;
        entityConfig.put(e, Boolean.valueOf(true));
    }

    private static void addDimensionToList(int e) {
        if (dimensionConfig.containsKey(Integer.valueOf(e)))
            return;
        dimensionConfig.put(Integer.valueOf(e), Boolean.valueOf(true));
    }

    public static void clearLag(ICommandSender s, int dimID, boolean ignoreConfig) {
        String dimName = "";
        List<Entity> entitiesToClear = new ArrayList<Entity>();
        for (WorldServer worldServer : (MinecraftServer.func_71276_C()).field_71305_c) {
            if (((World)worldServer).field_73011_w.func_177502_q() == dimID) {
                dimName = ((World)worldServer).field_73011_w.func_80007_l();
                if (!canExecuteInDim(((World)worldServer).field_73011_w.func_177502_q()) &&
                        !ignoreConfig) {
                    s.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "This dimension is disabled in the config"));
                    return;
                }
                if (((World)worldServer).field_72996_f.size() < 1) {
                    s.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Dimension \"" + ((World)worldServer).field_73011_w.func_80007_l() + "\" is not loaded."));
                    return;
                }
                for (Object e : ((World)worldServer).field_72996_f) {
                    if (!(e instanceof Entity) || e instanceof net.minecraft.entity.player.EntityPlayer || (
                            !canKill(EntityList.func_75621_b((Entity)e)) &&
                                    !ignoreConfig))
                        continue;
                    entitiesToClear.add((Entity)e);
                }
                for (int i = 0; i < entitiesToClear.size(); i++) {
                    for (Object player : ((World)worldServer).field_73010_i) {
                        if (player instanceof EntityPlayerMP)
                            ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S13PacketDestroyEntities(new int[] { ((Entity)entitiesToClear.get(i)).func_145782_y() }));
                    }
                    ((Entity)entitiesToClear.get(i)).func_70106_y();
                }
            }
        }
        if (Config.showOutputToPlayers) {
            for (WorldServer worldServer : (MinecraftServer.func_71276_C()).field_71305_c) {
                for (Object player : ((World)worldServer).field_73010_i) {
                    if (player instanceof EntityPlayerMP)
                        ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S02PacketChat((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[Lag Removal] Cleared " + entitiesToClear.size() + " entities in " + dimName)));
                }
            }
        } else {
            s.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[Lag Removal] Cleared " + entitiesToClear.size() + " entities in " + dimName));
        }
    }

    public static void clearLag(ICommandSender s, boolean ignoreConfig) {
        List<Entity> entitiesToClear = new ArrayList<Entity>();
        for (WorldServer worldServer : (MinecraftServer.func_71276_C()).field_71305_c) {
            if (canExecuteInDim(((World)worldServer).field_73011_w.func_177502_q()) ||
                    ignoreConfig) {
                for (Object e : ((World)worldServer).field_72996_f) {
                    if (!(e instanceof Entity) || e instanceof net.minecraft.entity.player.EntityPlayer || (
                            !canKill(EntityList.func_75621_b((Entity)e)) &&
                                    !ignoreConfig))
                        continue;
                    entitiesToClear.add((Entity)e);
                }
                for (int i = 0; i < entitiesToClear.size(); i++) {
                    for (Object player : ((World)worldServer).field_73010_i) {
                        if (player instanceof EntityPlayerMP)
                            ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S13PacketDestroyEntities(new int[] { ((Entity)entitiesToClear.get(i)).func_145782_y() }));
                    }
                    ((Entity)entitiesToClear.get(i)).func_70106_y();
                }
            }
        }
        if (Config.showOutputToPlayers) {
            for (WorldServer worldServer : (MinecraftServer.func_71276_C()).field_71305_c) {
                for (Object player : ((World)worldServer).field_73010_i) {
                    if (player instanceof EntityPlayerMP)
                        ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S02PacketChat((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[Lag Removal] Cleared " + entitiesToClear.size() + " entities")));
                }
            }
        } else {
            s.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[Lag Removal] Cleared " + entitiesToClear.size() + " entities"));
        }
    }

    public static boolean canKill(String s) {
        if (entityConfig.containsKey(s))
            return ((Boolean)entityConfig.get(s)).booleanValue();
        return false;
    }

    public static boolean canExecuteInDim(int id) {
        if (dimensionConfig.containsKey(Integer.valueOf(id)))
            return ((Boolean)dimensionConfig.get(Integer.valueOf(id))).booleanValue();
        return false;
    }

    private static void saveEntityList() {
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "lagRemoal_EntityConfig.json";
        File dir = new File(fileDir);
        if (!dir.exists())
            dir.mkdirs();
        String json = JSONData.mapToJson(entityConfig);
        try {
            FileWriter fw = new FileWriter(dir + "/" + fileName);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadEntityList() {
        entityConfig.clear();
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "lagRemoal_EntityConfig.json";
        File dir = new File(fileDir + fileName);
        if (!dir.exists()) {
            setupEntityList();
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileDir + fileName));
            StringBuilder b = new StringBuilder();
            try {
                String line;
                while ((line = br.readLine()) != null)
                    b.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(b.toString());
            if (b.toString().length() > 0)
                entityConfig = JSONData.jsonToMap(b.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setupEntityList();
    }

    private static void setupEntityList() {
        for (Object e : EntityList.field_75625_b.keySet())
            addEntityToList((String)e);
        saveEntityList();
    }

    private static void saveDimensionList() {
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "lagRemoal_DimensionConfig.json";
        File dir = new File(fileDir);
        if (!dir.exists())
            dir.mkdirs();
        String json = JSONData.mapToJsonIB(dimensionConfig);
        try {
            FileWriter fw = new FileWriter(dir + "/" + fileName);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadDimensionList() {
        dimensionConfig.clear();
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "lagRemoal_DimensionConfig.json";
        File dir = new File(fileDir + fileName);
        if (!dir.exists()) {
            setupDimensionList();
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileDir + fileName));
            StringBuilder b = new StringBuilder();
            try {
                String line;
                while ((line = br.readLine()) != null)
                    b.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b.toString().length() > 0)
                dimensionConfig = JSONData.jsonToMapIB(b.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setupDimensionList();
    }

    private static void setupDimensionList() {
        for (WorldServer worldServer : (MinecraftServer.func_71276_C()).field_71305_c)
            addDimensionToList(((World)worldServer).field_73011_w.func_177502_q());
        saveDimensionList();
    }

    public static void updateDimension(ICommandSender sender, int dimID, boolean b) {
        dimensionConfig.put(Integer.valueOf(dimID), Boolean.valueOf(b));
        sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Updated config for Dimension ID " + dimID));
        saveDimensionList();
    }

    public static void updateEntity(ICommandSender sender, String ent, boolean b) {
        if (entityConfig.containsKey(ent)) {
            entityConfig.put(ent, Boolean.valueOf(b));
            saveEntityList();
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Updated config for Entity " + ent));
        } else {
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Entity " + ent + " does not exist"));
        }
    }
}
