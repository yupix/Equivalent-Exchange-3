package com.pahimar.ee3.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.exchange.EnergyValueRegistry;
import com.pahimar.ee3.exchange.OreStack;
import com.pahimar.ee3.exchange.WrappedStack;

public class EnergyValueHelper {

    public static EnergyValue computeEnergyValueFromRecipe(Map<WrappedStack, EnergyValue> stackValueMappings,
            WrappedStack outputStack, List<WrappedStack> inputStacks) {
        float computedValue = 0f;

        for (WrappedStack wrappedStack : inputStacks) {
            EnergyValue wrappedStackValue;
            int stackSize = -1;
            if (wrappedStack.getWrappedObject() instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) wrappedStack.getWrappedObject();

                // Check if we are dealing with a potential fluid
                if (FluidContainerRegistry.getFluidForFilledItem(itemStack) != null) {
                    if (itemStack.getItem().getContainerItem(itemStack) != null) {
                        stackSize = FluidContainerRegistry.getFluidForFilledItem(itemStack).amount
                                * wrappedStack.getStackSize();
                        wrappedStackValue = EnergyValueRegistry.getInstance().getEnergyValueFromMap(
                                stackValueMappings,
                                FluidContainerRegistry.getFluidForFilledItem(itemStack));
                    } else {
                        wrappedStackValue = EnergyValueRegistry.getInstance()
                                .getEnergyValueFromMap(stackValueMappings, wrappedStack);
                    }
                } else if (itemStack.getItem().getContainerItem(itemStack) != null) {
                    ItemStack containerItemStack = itemStack.getItem().getContainerItem(itemStack);

                    if (EnergyValueRegistry.getInstance().hasEnergyValue(itemStack)
                            && EnergyValueRegistry.getInstance().hasEnergyValue(containerItemStack)) {
                        float itemStackValue = EnergyValueRegistry.getInstance()
                                .getEnergyValueFromMap(stackValueMappings, itemStack).getValue();
                        float containerStackValue = EnergyValueRegistry.getInstance()
                                .getEnergyValueFromMap(stackValueMappings, containerItemStack).getValue();
                        wrappedStackValue = new EnergyValue(itemStackValue - containerStackValue);
                    } else {
                        wrappedStackValue = new EnergyValue(0);
                    }
                } else if (!itemStack.getItem().doesContainerItemLeaveCraftingGrid(itemStack)) {
                    wrappedStackValue = new EnergyValue(0);
                } else if (OreDictionary.getOreIDs(itemStack).length > 0) {
                    wrappedStackValue = EnergyValueRegistry.getInstance()
                            .getEnergyValueFromMap(stackValueMappings, wrappedStack, true);
                } else {
                    wrappedStackValue = EnergyValueRegistry.getInstance()
                            .getEnergyValueFromMap(stackValueMappings, wrappedStack);
                }
            } else if (wrappedStack.getWrappedObject() instanceof OreStack) {
                OreStack oreStack = (OreStack) wrappedStack.getWrappedObject();
                wrappedStackValue = EnergyValueRegistry.getInstance()
                        .getEnergyValueFromMap(stackValueMappings, wrappedStack);
                for (ItemStack itemStack : OreDictionary.getOres(oreStack.oreName)) {
                    if (!itemStack.getItem().doesContainerItemLeaveCraftingGrid(itemStack)) {
                        wrappedStackValue = new EnergyValue(0);
                    }
                }
            } else {
                wrappedStackValue = EnergyValueRegistry.getInstance()
                        .getEnergyValueFromMap(stackValueMappings, wrappedStack);
            }

            if (wrappedStackValue != null) {
                if (stackSize == -1) {
                    stackSize = wrappedStack.getStackSize();
                }

                computedValue += wrappedStackValue.getValue() * stackSize;
            } else {
                return null;
            }
        }

        return factorEnergyValue(new EnergyValue(computedValue), outputStack.getStackSize());
    }

    public static EnergyValue factorEnergyValue(EnergyValue energyValue, int factor) {
        return factorEnergyValue(energyValue, (float) factor);
    }

    public static EnergyValue factorEnergyValue(EnergyValue energyValue, float factor) {
        if ((Float.compare(factor, 0f) != 0) && (energyValue != null)) {
            return new EnergyValue(
                    new BigDecimal(energyValue.getValue() * 1f / factor).setScale(3, BigDecimal.ROUND_HALF_EVEN)
                            .floatValue());
        } else {
            return null;
        }
    }
}
