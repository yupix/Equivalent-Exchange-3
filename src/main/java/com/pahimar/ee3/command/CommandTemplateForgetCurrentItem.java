package com.pahimar.ee3.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.knowledge.TransmutationKnowledgeRegistry;
import com.pahimar.ee3.reference.Messages;
import com.pahimar.ee3.reference.Names;

public class CommandTemplateForgetCurrentItem extends CommandBase {

    @Override
    public String getCommandName() {
        return Names.Commands.TEMPLATE_FORGET_CURRENT_ITEM;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Messages.Commands.TEMPLATE_FORGET_CURRENT_ITEM_USAGE;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (args.length < 1) {
            throw new WrongUsageException(Messages.Commands.TEMPLATE_FORGET_CURRENT_ITEM_USAGE);
        } else {
            ItemStack itemStack = ((EntityPlayer) commandSender).getCurrentEquippedItem();

            if (itemStack != null) {
                TransmutationKnowledgeRegistry.getInstance().makeTemplateForget(itemStack);
                func_152373_a(
                        commandSender,
                        this,
                        Messages.Commands.TEMPLATE_FORGET_CURRENT_ITEM_SUCCESS,
                        new Object[] { commandSender.getCommandSenderName(), itemStack.func_151000_E() });
            } else {
                throw new WrongUsageException(Messages.Commands.NO_ITEM);
            }
        }
    }
}
