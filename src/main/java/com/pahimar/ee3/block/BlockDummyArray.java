package com.pahimar.ee3.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.reference.RenderIds;
import com.pahimar.ee3.tileentity.TileEntityAlchemyArray;
import com.pahimar.ee3.tileentity.TileEntityDummyArray;
import com.pahimar.ee3.tileentity.TileEntityEE;

public class BlockDummyArray extends BlockTileEntityEE {

    public BlockDummyArray() {
        super(Material.circuits);
        setCreativeTab(null);
        this.setBlockName(Names.Blocks.DUMMY_ARRAY);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.dummyArray;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityDummyArray tileEntityDummyArray = (TileEntityDummyArray) world.getTileEntity(x, y, z);

            return tileEntityDummyArray.getLightLevel();
        }

        return 0;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z)
                && (world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)
                        || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)
                        || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)
                        || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)
                        || world.isSideSolid(x, y - 1, z, ForgeDirection.UP)
                        || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN));
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int sideHit) {
        ForgeDirection side = ForgeDirection.getOrientation(sideHit);
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z)
                && ((side == ForgeDirection.DOWN && world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))
                        || (side == ForgeDirection.UP && world.isSideSolid(x, y - 1, z, ForgeDirection.UP))
                        || (side == ForgeDirection.NORTH && world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH))
                        || (side == ForgeDirection.SOUTH && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH))
                        || (side == ForgeDirection.WEST && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST))
                        || (side == ForgeDirection.EAST && world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)));
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int sideHit, float hitX, float hitY, float hitZ,
            int metaData) {
        return sideHit;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        ((TileEntityEE) world.getTileEntity(x, y, z)).setOrientation(world.getBlockMetadata(x, y, z));
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z), 3);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int sideHit,
            float hitX, float hitY, float hitZ) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onBlockActivated(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        entityPlayer,
                        sideHit,
                        hitX,
                        hitY,
                        hitZ);
            }
        }

        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onBlockClicked(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        entityPlayer);
            }
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onBlockDestroyedByExplosion(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        explosion);
            }
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metaData) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onBlockDestroyedByPlayer(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        metaData);
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onEntityCollidedWithBlock(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        entity);
            }
        }
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityAlchemyArray tileEntityAlchemyArray = ((TileEntityDummyArray) world.getTileEntity(x, y, z))
                    .getAssociatedTileEntity();

            if (tileEntityAlchemyArray != null) {
                tileEntityAlchemyArray.onFallenUpon(
                        world,
                        x,
                        y,
                        z,
                        tileEntityAlchemyArray.xCoord,
                        tileEntityAlchemyArray.yCoord,
                        tileEntityAlchemyArray.zCoord,
                        entity,
                        fallDistance);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            int trueXCoord = ((TileEntityDummyArray) world.getTileEntity(x, y, z)).getTrueXCoord();
            int trueYCoord = ((TileEntityDummyArray) world.getTileEntity(x, y, z)).getTrueYCoord();
            int trueZCoord = ((TileEntityDummyArray) world.getTileEntity(x, y, z)).getTrueZCoord();

            if (world.getTileEntity(trueXCoord, trueYCoord, trueZCoord) instanceof TileEntityAlchemyArray) {
                TileEntityAlchemyArray tileEntityAlchemyArray = (TileEntityAlchemyArray) world
                        .getTileEntity(trueXCoord, trueYCoord, trueZCoord);
                boolean invalidateAlchemyArray = false;

                if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.UP
                        && !world.isSideSolid(x, y - 1, z, ForgeDirection.UP, true)) {
                    invalidateAlchemyArray = true;
                } else if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.DOWN
                        && !world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN, true)) {
                            invalidateAlchemyArray = true;
                        } else
                    if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.NORTH
                            && !world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH, true)) {
                                invalidateAlchemyArray = true;
                            } else
                        if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.SOUTH
                                && !world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH, true)) {
                                    invalidateAlchemyArray = true;
                                } else
                            if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.EAST
                                    && !world.isSideSolid(x - 1, y, z, ForgeDirection.EAST, true)) {
                                        invalidateAlchemyArray = true;
                                    } else
                                if (tileEntityAlchemyArray.getOrientation() == ForgeDirection.WEST
                                        && !world.isSideSolid(x + 1, y, z, ForgeDirection.WEST, true)) {
                                            invalidateAlchemyArray = true;
                                        }

                if (invalidateAlchemyArray) {
                    world.getTileEntity(x, y, z).invalidate();
                    tileEntityAlchemyArray.invalidate();
                    world.setBlockToAir(x, y, z);
                    world.setBlockToAir(trueXCoord, trueYCoord, trueZCoord);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2) {
        return null;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
        if (iBlockAccess.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntityDummyArray tileEntityDummyArray = (TileEntityDummyArray) iBlockAccess.getTileEntity(x, y, z);

            switch (tileEntityDummyArray.getOrientation()) {
                case DOWN: {
                    this.setBlockBounds(0f, 1f, 0f, 1f, 1 - 0.0625f, 1f);
                    break;
                }
                case UP: {
                    this.setBlockBounds(0f, 0f, 0f, 1f, 0.0625f, 1f);
                    break;
                }
                case NORTH: {
                    this.setBlockBounds(0f, 0f, 1 - 0.0625f, 1f, 1f, 1f);
                    break;
                }
                case SOUTH: {
                    this.setBlockBounds(0f, 0f, 0f, 1f, 1f, 0.0625f);
                    break;
                }
                case EAST: {
                    this.setBlockBounds(0f, 0f, 0f, 0.0625f, 1f, 1f);
                    break;
                }
                case WEST: {
                    this.setBlockBounds(1f, 0f, 0f, 1 - 0.0625f, 1f, 1f);
                    break;
                }
                case UNKNOWN: {
                    break;
                }
            }
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int metaData) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityDummyArray) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            super.breakBlock(
                    world,
                    ((TileEntityDummyArray) tileEntity).getTrueXCoord(),
                    ((TileEntityDummyArray) tileEntity).getTrueYCoord(),
                    ((TileEntityDummyArray) tileEntity).getTrueZCoord(),
                    block,
                    metaData);
        }
        super.breakBlock(world, x, y, z, block, metaData);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param world
     * @param metaData
     */
    @Override
    public TileEntity createNewTileEntity(World world, int metaData) {
        return new TileEntityDummyArray();
    }
}
