package com.pahimar.ee3.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.api.knowledge.TransmutationKnowledgeRegistryProxy;
import com.pahimar.ee3.knowledge.AbilityRegistry;
import com.pahimar.ee3.reference.Messages;
import com.pahimar.ee3.reference.Names;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommandPlayerLearnCurrentItem extends CommandBase {

    @Override
    public String getCommandName() {
        return Names.Commands.PLAYER_LEARN_CURRENT_ITEM;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Messages.Commands.PLAYER_LEARN_CURRENT_ITEM_USAGE;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException(Messages.Commands.PLAYER_LEARN_CURRENT_ITEM_USAGE);
        } else {
            EntityPlayer entityPlayer = getPlayer(commandSender, args[1]);

            if (entityPlayer != null) {
                ItemStack itemStack = ((EntityPlayer) commandSender).getCurrentEquippedItem();

                if (itemStack != null) {
                    if (AbilityRegistry.getInstance().isLearnable(itemStack)) {
                        TransmutationKnowledgeRegistryProxy.teachPlayer(entityPlayer, itemStack);
                        func_152373_a(
                                commandSender,
                                this,
                                Messages.Commands.PLAYER_LEARN_CURRENT_ITEM_SUCCESS,
                                new Object[] { commandSender.getCommandSenderName(),
                                        entityPlayer.getCommandSenderName(), itemStack.func_151000_E() });
                    }
                } else {
                    throw new WrongUsageException(Messages.Commands.NO_ITEM);
                }
            } else {
                throw new WrongUsageException(Messages.Commands.PLAYER_NOT_FOUND_ERROR);
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(
                    args,
                    FMLCommonHandler.instance().getMinecraftServerInstance().getAllUsernames());
        }

        return null;
    }
}
