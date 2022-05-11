package me.mechanic.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.mechanic.main.JSONData;
import me.mechanic.main.Main;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

public class Config {
    public static boolean showOutputToPlayers;

    public static int executeMinutes;

    private static Map<String, String> config = new HashMap<String, String>();

    private static void saveConfig() {
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "remove_lag_Config.json";
        File dir = new File(fileDir);
        if (!dir.exists())
            dir.mkdirs();
        String json = JSONData.mapToJsonSS(config);
        try {
            FileWriter fw = new FileWriter(dir + "/" + fileName);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadConfig() {
        config.clear();
        String fileDir = ((File)FMLInjectionData.data()[6]).getAbsolutePath() + "/config/SWDTeam/remove_lag/";
        String fileName = "remove_lag_Config.json";
        File dir = new File(fileDir);
        if (!dir.exists()) {
            initConfig();
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
                config = JSONData.jsonToMapSS(b.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initConfig();
    }

    private static void initConfig() {
        try {
            showOutputToPlayers = Boolean.parseBoolean(getConfigValue("Show output to players", "true"));
        } catch (Exception e) {
            showOutputToPlayers = true;
        }
        try {
            executeMinutes = Integer.parseInt(getConfigValue("Time to execute Remove Lag in minutes", "5"));
        } catch (Exception e) {
            executeMinutes = 5;
        }
        saveConfig();
        Main.start();
    }

    private static String getConfigValue(String desc, String defaultValue) {
        if (config.containsKey(desc))
            return config.get(desc);
        config.put(desc, defaultValue);
        return defaultValue;
    }
}
