package com.pahimar.ee3.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.pahimar.ee3.EquivalentExchange3;
import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.reference.GUIs;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.RenderIds;
import com.pahimar.ee3.tileentity.TileEntityTransmutationTablet;

public class BlockTransmutationTablet extends BlockTileEntityEE {

    public BlockTransmutationTablet() {
        super(Material.rock);
        setCreativeTab(null);
        this.setHardness(2.0f);
        this.setBlockName(Names.Blocks.TRANSMUTATION_TABLET);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
        if (isStructureValid(iBlockAccess, x, y, z)) {
            this.setBlockBounds(0f, 0f, 0f, 1f, 0.625f, 1f);
        } else {
            this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(ModBlocks.ashInfusedStoneSlab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int sideHit,
            float hitX, float hitY, float hitZ) {
        if (entityPlayer.isSneaking()) {
            return false;
        } else {
            if (!world.isRemote) {
                if (world.getTileEntity(x, y, z) instanceof TileEntityTransmutationTablet
                        && isStructureValid(world, x, y, z)) {
                    entityPlayer
                            .openGui(EquivalentExchange3.instance, GUIs.TRANSMUTATION_TABLET.ordinal(), world, x, y, z);
                }
            }

            return true;
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int sideHit, float hitX, float hitY, float hitZ,
            int metaData) {
        return metaData;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metaData) {
        return new TileEntityTransmutationTablet();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.tabletSlab;
    }

    private boolean isStructureValid(IBlockAccess iBlockAcces, int xCoord, int yCoord, int zCoord) {
        return ((iBlockAcces.getBlock(xCoord - 1, yCoord, zCoord - 1) instanceof BlockAshInfusedStoneSlab
                && iBlockAcces.getBlockMetadata(xCoord - 1, yCoord, zCoord - 1) == 1)
                && (iBlockAcces.getBlock(xCoord, yCoord, zCoord - 1) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord, yCoord, zCoord - 1) == 2)
                && (iBlockAcces.getBlock(xCoord + 1, yCoord, zCoord - 1) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord + 1, yCoord, zCoord - 1) == 3)
                && (iBlockAcces.getBlock(xCoord - 1, yCoord, zCoord) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord - 1, yCoord, zCoord) == 4)
                && (iBlockAcces.getBlock(xCoord + 1, yCoord, zCoord) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord + 1, yCoord, zCoord) == 5)
                && (iBlockAcces.getBlock(xCoord - 1, yCoord, zCoord + 1) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord - 1, yCoord, zCoord + 1) == 6)
                && (iBlockAcces.getBlock(xCoord, yCoord, zCoord + 1) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord, yCoord, zCoord + 1) == 7)
                && (iBlockAcces.getBlock(xCoord + 1, yCoord, zCoord + 1) instanceof BlockAshInfusedStoneSlab
                        && iBlockAcces.getBlockMetadata(xCoord + 1, yCoord, zCoord + 1) == 8));
    }
}
