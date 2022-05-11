package me.mechanic.main;

import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import me.mechanic.command.GuiCommand;
import me.mechanic.command.CommandRLConfig;
import me.mechanic.data.Config;

@Mod(modid = "SbModFixer", version = "1.6.3", acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.8]")
public class Main {
    public static final String MODID = "SbModFixer";

    public static final String VERSION = "1.6.3";

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        if (event.getSide() == Side.SERVER) {
            event.registerServerCommand((ICommand) new GuiCommand());
            event.registerServerCommand((ICommand) new CommandRLConfig());
            Config.LoadConfig();
        }
    }

    public static void start() {
        EntityHandler.appendDataList();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                EntityHandler.clearLag(null, false);
            }
        } (60000 * Config.executeMinutes), (60000 * Config.executeMinutes));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}

