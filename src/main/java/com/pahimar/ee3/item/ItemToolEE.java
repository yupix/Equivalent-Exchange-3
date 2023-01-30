package com.pahimar.ee3.item;

import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.pahimar.ee3.creativetab.CreativeTab;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.util.IChargeable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolEE extends ItemTool {

    public ItemToolEE(float damageVsEntity, Item.ToolMaterial toolMaterial, Set blocksEffectiveAgainst) {
        super(damageVsEntity, toolMaterial, blocksEffectiveAgainst);
        this.setCreativeTab(CreativeTab.EE3_TAB);
        this.setNoRepair();
        this.maxStackSize = 1;
    }

    @Override
    public String getUnlocalizedName() {
        return String
                .format("item.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String
                .format("item.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister
                .registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        if (itemStack.getItem() instanceof IChargeable) {
            return ((IChargeable) itemStack.getItem()).getChargeLevel(itemStack) > 0;
        }

        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack) {
        if (itemStack.getItem() instanceof IChargeable) {
            return (double) (((IChargeable) itemStack.getItem()).getMaxChargeLevel()
                    - ((IChargeable) itemStack.getItem()).getChargeLevel(itemStack))
                    / (double) ((IChargeable) itemStack.getItem()).getMaxChargeLevel();
        }

        return 1d;
    }
}
