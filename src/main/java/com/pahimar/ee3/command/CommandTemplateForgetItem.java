package com.pahimar.ee3.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import com.pahimar.ee3.knowledge.TransmutationKnowledgeRegistry;
import com.pahimar.ee3.reference.Messages;
import com.pahimar.ee3.reference.Names;

public class CommandTemplateForgetItem extends CommandBase {

    @Override
    public String getCommandName() {
        return Names.Commands.TEMPLATE_FORGET_ITEM;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Messages.Commands.TEMPLATE_FORGET_ITEM_USAGE;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException(Messages.Commands.TEMPLATE_FORGET_ITEM_USAGE);
        } else {
            Item item = getItemByText(commandSender, args[1]);
            int metaData = 0;

            if (args.length >= 3) {
                metaData = parseInt(commandSender, args[2]);
            }

            ItemStack itemStack = new ItemStack(item, 1, metaData);

            if (args.length >= 4) {
                String stringNBTData = func_147178_a(commandSender, args, 3).getUnformattedText();

                try {
                    NBTBase nbtBase = JsonToNBT.func_150315_a(stringNBTData);

                    if (!(nbtBase instanceof NBTTagCompound)) {
                        func_152373_a(
                                commandSender,
                                this,
                                Messages.Commands.INVALID_NBT_TAG_ERROR,
                                new Object[] { "Not a valid tag" });
                        return;
                    }

                    itemStack.setTagCompound((NBTTagCompound) nbtBase);
                } catch (Exception exception) {
                    func_152373_a(
                            commandSender,
                            this,
                            Messages.Commands.INVALID_NBT_TAG_ERROR,
                            new Object[] { exception.getMessage() });
                    return;
                }
            }

            TransmutationKnowledgeRegistry.getInstance().makeTemplateForget(itemStack);
            func_152373_a(
                    commandSender,
                    this,
                    Messages.Commands.TEMPLATE_FORGET_ITEM_SUCCESS,
                    new Object[] { commandSender.getCommandSenderName(), itemStack.func_151000_E() });
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        if (args.length == 2) {
            return getListOfStringsFromIterableMatchingLastWord(args, Item.itemRegistry.getKeys());
        }

        return null;
    }
}
