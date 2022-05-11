package me.mechanic.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import me.mechanic.main.EntityHandler;

public class CommandRLConfig extends CommandBase {
    public String getCommandName() {
        return "RLConfig";
    }

    public String 	getCommandUsage(ICommandSender sender) {
        return "/RLConfig <add:remove> <dimension_id:entity_name>";
    }

    public void 	processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            showHelp(sender);
        } else if (args.length > 0) {
            if (args[0].toLowerCase().equals("add")) {
                if (args.length > 1) {
                    try {
                        int dimID = Integer.parseInt(args[1]);
                        EntityHandler.updateDimension(sender, dimID, true);
                    } catch (Exception e) {
                        String entity = args[1];
                        EntityHandler.updateEntity(sender, entity, true);
                    }
                } else {
                    showHelp(sender);
                }
            } else if (args[0].toLowerCase().equals("remove")) {
                if (args.length > 1) {
                    try {
                        int dimID = Integer.parseInt(args[1]);
                        EntityHandler.updateDimension(sender, dimID, false);
                    } catch (Exception e) {
                        String entity = args[1];
                        EntityHandler.updateEntity(sender, entity, false);
                    }
                } else {
                    showHelp(sender);
                }
            } else {
                showHelp(sender);
            }
        }
    }

    public void showHelp(ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + 	getCommandUsage(sender)));
    }
}
