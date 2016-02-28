package com.github.lunatrius.tracer.command.client;

import com.github.lunatrius.tracer.handler.ConfigurationHandler;
import com.github.lunatrius.tracer.handler.TraceHandler;
import com.github.lunatrius.tracer.reference.Names;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandTracer extends CommandBase {
    @Override
    public String getCommandName() {
        return Names.Command.NAME;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return Names.Command.Message.USAGE;
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Names.Command.REGISTER, Names.Command.UNREGISTER, Names.Command.CLEAR);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase(Names.Command.REGISTER)) {
                return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());
            } else if (args[0].equalsIgnoreCase(Names.Command.UNREGISTER)) {
                return getListOfStringsMatchingLastWord(args, ConfigurationHandler.getRegisteredEntityNames());
            }
        }

        return null;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        if (args[0].equalsIgnoreCase(Names.Command.REGISTER)) {
            if (args.length == 2) {
                final String entityName = args[1];
                ConfigurationHandler.registerTraceRenderInformation(entityName);
                ConfigurationHandler.loadConfiguration();
                sender.addChatMessage(new ChatComponentTranslation(Names.Command.Message.REGISTER, entityName));
            } else {
                throw new WrongUsageException(Names.Command.Message.REGISTER_USAGE);
            }
        } else if (args[0].equalsIgnoreCase(Names.Command.UNREGISTER)) {
            if (args.length == 2) {
                final String entityName = args[1];
                ConfigurationHandler.unregisterEntityName(entityName);
                ConfigurationHandler.loadConfiguration();
                sender.addChatMessage(new ChatComponentTranslation(Names.Command.Message.UNREGISTER, entityName));
            } else {
                throw new WrongUsageException(Names.Command.Message.UNREGISTER_USAGE);
            }
        } else if (args[0].equalsIgnoreCase(Names.Command.CLEAR)) {
            final int count = TraceHandler.INSTANCE.clearTraces();
            sender.addChatMessage(new ChatComponentTranslation(Names.Command.Message.CLEAR, count));
        }
    }
}