package com.pahimar.ee3.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.pahimar.ee3.EquivalentExchange3;
import com.pahimar.ee3.reference.GUIs;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.RenderIds;
import com.pahimar.ee3.tileentity.TileEntityResearchStation;

public class BlockResearchStation extends BlockTileEntityEE {

    public BlockResearchStation() {
        super(Material.rock);
        this.setHardness(2.0f);
        this.setBlockName(Names.Blocks.RESEARCH_STATION);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metaData) {
        return new TileEntityResearchStation();
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
        return RenderIds.researchStation;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        if (player.isSneaking()) {
            return false;
        } else {
            if (!world.isRemote) {
                if (world.getTileEntity(x, y, z) instanceof TileEntityResearchStation) {
                    player.openGui(EquivalentExchange3.instance, GUIs.RESEARCH_STATION.ordinal(), world, x, y, z);
                }
            }

            return true;
        }
    }
}
