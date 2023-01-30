package com.pahimar.ee3.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.pahimar.ee3.EquivalentExchange3;
import com.pahimar.ee3.reference.Colors;
import com.pahimar.ee3.reference.GUIs;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.util.ColorHelper;
import com.pahimar.ee3.util.IOwnable;
import com.pahimar.ee3.util.ItemStackUtils;
import com.pahimar.ee3.util.NBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlchemicalBag extends ItemEE implements IOwnable {

    private static final String[] ALCHEMICAL_BAG_ICONS = { "open", "closed", "symbolTier1", "symbolTier2",
            "symbolTier3" };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemAlchemicalBag() {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName(Names.Items.ALCHEMICAL_BAG);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[ALCHEMICAL_BAG_ICONS.length];

        for (int i = 0; i < ALCHEMICAL_BAG_ICONS.length; i++) {
            icons[i] = iconRegister.registerIcon(
                    Textures.RESOURCE_PREFIX + Names.Items.ALCHEMICAL_BAG + "." + ALCHEMICAL_BAG_ICONS[i]);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        if (renderPass == 0) {
            if (NBTHelper.hasKey(itemStack, Names.NBT.ALCHEMICAL_BAG_GUI_OPEN)) {
                return icons[0];
            } else {
                return icons[1];
            }
        } else {
            return icons[2 + MathHelper.clamp_int(itemStack.getItemDamage(), 0, 3)];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < 3; ++meta) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (!world.isRemote) {

            // Set the owner of the bag if one doesn't exist already
            if (ItemStackUtils.getOwnerUUID(itemStack) == null) {
                ItemStackUtils.setOwner(itemStack, entityPlayer);
            }

            // Set an UUID on the bag if one doesn't exist already
            if (NBTHelper.getUUID(itemStack) == null) {
                NBTHelper.setUUID(itemStack, UUID.randomUUID());
            }

            // TODO Do a scan of inventory and if we find a bag with the same UUID, change it's UUID
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                if (entityPlayer.inventory.getStackInSlot(i) != null) {
                    ItemStack itemStack1 = entityPlayer.inventory.getStackInSlot(i);

                    if (itemStack1.getItem() instanceof ItemAlchemicalBag) {
                        if (NBTHelper.hasUUID(itemStack1)
                                && NBTHelper.getUUID(itemStack).equals(NBTHelper.getUUID(itemStack1))) {
                            NBTHelper.setUUID(itemStack1, UUID.randomUUID());
                        }
                    }
                }
            }

            NBTHelper.setBoolean(itemStack, Names.NBT.ALCHEMICAL_BAG_GUI_OPEN, true);
            entityPlayer.openGui(
                    EquivalentExchange3.instance,
                    GUIs.ALCHEMICAL_BAG.ordinal(),
                    entityPlayer.worldObj,
                    (int) entityPlayer.posX,
                    (int) entityPlayer.posY,
                    (int) entityPlayer.posZ);
        }

        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        int bagColor = this.getColor(itemStack);

        if (bagColor < 0) {
            bagColor = Integer.parseInt(Colors.PURE_WHITE, 16);
        }

        return bagColor;
    }

    public boolean hasColor(ItemStack itemStack) {
        return ColorHelper.hasColor(itemStack);
    }

    public int getColor(ItemStack itemStack) {
        return ColorHelper.getColor(itemStack);
    }

    public void setColor(ItemStack itemStack, int color) {
        if (itemStack != null) {
            if (itemStack.getItem() instanceof ItemAlchemicalBag) {
                ColorHelper.setColor(itemStack, color);
            }
        }
    }

    public void removeColor(ItemStack itemStack) {
        if (itemStack != null) {
            NBTTagCompound nbtTagCompound = itemStack.getTagCompound();

            if (nbtTagCompound != null) {
                NBTTagCompound displayTagCompound = nbtTagCompound.getCompoundTag(Names.NBT.DISPLAY);

                if (displayTagCompound.hasKey(Names.NBT.COLOR)) {
                    displayTagCompound.removeTag(Names.NBT.COLOR);
                }
            }
        }
    }
}
