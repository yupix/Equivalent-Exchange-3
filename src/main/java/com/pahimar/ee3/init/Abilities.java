package com.pahimar.ee3.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.api.blacklist.BlacklistRegistryProxy;
import com.pahimar.ee3.exchange.OreStack;

public class Abilities {

    public static void init() {

        for (String oreName : OreDictionary.getOreNames()) {
            if (oreName.startsWith("ore")) {
                OreDictionary.getOres(oreName).forEach(BlacklistRegistryProxy::setAsNotLearnable);
                BlacklistRegistryProxy.setAsNotLearnable(new OreStack(oreName));
            }
        }

        BlacklistRegistryProxy.setAsNotLearnable(new ItemStack(Blocks.coal_ore));
        BlacklistRegistryProxy.setAsNotLearnable(ModItems.shardMinium);
        BlacklistRegistryProxy.setAsNotLearnable(new ItemStack(ModItems.alchemicalDust, 1, 1));
        BlacklistRegistryProxy.setAsNotLearnable(new ItemStack(ModItems.alchemicalDust, 1, 2));
    }
}
