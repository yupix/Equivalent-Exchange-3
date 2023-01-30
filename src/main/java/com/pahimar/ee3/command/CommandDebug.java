package com.pahimar.ee3.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.reference.Names;

public class CommandDebug extends CommandBase {

    @Override
    public String getCommandName() {
        return Names.Commands.DEBUG;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        EnergyValueRegistryProxy.dumpEnergyValueRegistryToLog(EnergyValueRegistryProxy.Phase.PRE_CALCULATION);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        return null;
    }
}
