package com.pahimar.ee3.block;

import net.minecraft.block.material.Material;

import com.pahimar.ee3.reference.Names;

public class BlockAshInfusedStone extends BlockEE {

    public BlockAshInfusedStone() {
        super(Material.rock);
        this.setBlockName(Names.Blocks.ASH_INFUSED_STONE);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(soundTypeStone);
    }
}
