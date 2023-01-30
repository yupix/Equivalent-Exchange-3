package com.pahimar.ee3.recipe;

import java.util.Arrays;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;

import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;

public class RecipesFluidContainers {

    public static void registerRecipes() {
        for (FluidContainerData fluidContainerData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (fluidContainerData.fluid != null) {
                if (fluidContainerData.filledContainer != null && fluidContainerData.emptyContainer != null) {
                    RecipeRegistryProxy.addRecipe(
                            fluidContainerData.filledContainer.copy(),
                            Arrays.asList(fluidContainerData.fluid.copy(), fluidContainerData.emptyContainer.copy()));
                }
            }
        }
    }
}
