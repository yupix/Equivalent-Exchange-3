package com.pahimar.ee3.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.pahimar.ee3.reference.Messages;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockAlchemicalChest extends ItemBlock {

    public ItemBlockAlchemicalChest(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean flag) {
        int metaData = itemStack.getItemDamage();

        if (metaData == 0) {
            list.add(StatCollector.translateToLocal(StatCollector.translateToLocal(Messages.Tooltips.SMALL)));
        } else if (metaData == 1) {
            list.add(StatCollector.translateToLocal(StatCollector.translateToLocal(Messages.Tooltips.MEDIUM)));
        } else if (metaData == 2) {
            list.add(StatCollector.translateToLocal(StatCollector.translateToLocal(Messages.Tooltips.LARGE)));
        }
    }
}
