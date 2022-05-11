package me.mechanic.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import me.mechanic.main.EntityHandler

public class GuiCommand extends CommandBase {
    public String getCommandName() {
        return "killall";
    }

    public String 	getCommandUsage(ICommandSender sender) {
        return "/killall [Override Config (true:false)] [Dimension_ID]";
    }

    public void 	processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            EntityHandler.clearLag(sender, false);
        } else if (args.length > 0) {
            if (args[0].toLowerCase().equals("true") || args[0].toLowerCase().equals("false")) {
                if (args.length > 1) {
                    try {
                        int i = Integer.parseInt(args[1]);
                        EntityHandler.clearLag(sender, i, Boolean.parseBoolean(args[0]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        showHelp(sender);
                    }
                } else {
                    EntityHandler.clearLag(sender, Boolean.parseBoolean(args[0]));
                }
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    EntityHandler.clearLag(sender, i, false);
                } catch (Exception e) {
                    showHelp(sender);
                }
            }
        }
    }

    public void showHelp(ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + 	getCommandUsage(sender)));
    }
}
