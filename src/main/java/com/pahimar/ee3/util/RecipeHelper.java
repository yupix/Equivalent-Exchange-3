package com.pahimar.ee3.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.pahimar.ee3.exchange.OreStack;
import com.pahimar.ee3.exchange.WrappedStack;

/**
 * Equivalent-Exchange-3
 * <p/>
 * RecipeHelper
 *
 * @author pahimar
 */
public class RecipeHelper {

    /**
     * Returns a list of elements that constitute the input in a crafting recipe
     *
     * @param recipe The IRecipe being examined
     * @return List of elements that constitute the input of the given IRecipe. Could be an ItemStack or an Arraylist
     */
    public static List<WrappedStack> getRecipeInputs(IRecipe recipe) {
        ArrayList<WrappedStack> recipeInputs = new ArrayList<WrappedStack>();

        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shapedRecipe = (ShapedRecipes) recipe;

            for (int i = 0; i < shapedRecipe.recipeItems.length; i++) {
                if (shapedRecipe.recipeItems[i] instanceof ItemStack) {
                    ItemStack itemStack = shapedRecipe.recipeItems[i].copy();

                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }

                    recipeInputs.add(WrappedStack.wrap(itemStack));
                }
            }
        } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapelessRecipe = (ShapelessRecipes) recipe;

            for (Object object : shapelessRecipe.recipeItems) {
                if (object instanceof ItemStack) {
                    ItemStack itemStack = ((ItemStack) object).copy();

                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }

                    recipeInputs.add(WrappedStack.wrap(itemStack));
                }
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;

            if (validateOreDictionaryRecipe(Arrays.asList(shapedOreRecipe.getInput()))) {
                for (int i = 0; i < shapedOreRecipe.getInput().length; i++) {
                    /*
                     * If the element is a list, then it is an OreStack
                     */
                    if (shapedOreRecipe.getInput()[i] instanceof ArrayList) {
                        WrappedStack oreStack = WrappedStack.wrap(shapedOreRecipe.getInput()[i]);

                        if (oreStack != null && oreStack.getWrappedObject() instanceof OreStack) {
                            recipeInputs.add(oreStack);
                        }
                    } else if (shapedOreRecipe.getInput()[i] instanceof ItemStack) {
                        ItemStack itemStack = ((ItemStack) shapedOreRecipe.getInput()[i]).copy();

                        if (itemStack.stackSize > 1) {
                            itemStack.stackSize = 1;
                        }

                        recipeInputs.add(WrappedStack.wrap(itemStack));
                    }
                }
            }
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessOreRecipe = (ShapelessOreRecipe) recipe;

            if (validateOreDictionaryRecipe(shapelessOreRecipe.getInput())) {
                for (Object object : shapelessOreRecipe.getInput()) {

                    if (object instanceof ArrayList) {
                        recipeInputs.add(WrappedStack.wrap(object));
                    } else if (object instanceof ItemStack) {

                        ItemStack itemStack = ((ItemStack) object).copy();

                        if (itemStack.stackSize > 1) {
                            itemStack.stackSize = 1;
                        }

                        recipeInputs.add(WrappedStack.wrap(itemStack));
                    }
                }
            }
        }

        return collateInputStacks(recipeInputs);
    }

    /**
     * Collates an uncollated, unsorted List of Objects into a sorted, collated List of WrappedStacks
     *
     * @param uncollatedStacks List of objects for collating
     * @return A sorted, collated List of WrappedStacks
     */
    public static List<WrappedStack> collateInputStacks(List<?> uncollatedStacks) {
        List<WrappedStack> collatedStacks = new ArrayList<WrappedStack>();

        WrappedStack stack;
        boolean found;

        for (Object object : uncollatedStacks) {
            found = false;

            if (WrappedStack.canBeWrapped(object)) {
                stack = WrappedStack.wrap(object);

                if (collatedStacks.isEmpty()) {
                    collatedStacks.add(stack);
                } else {

                    for (WrappedStack collatedStack : collatedStacks) {
                        if (collatedStack.getWrappedObject() != null) {
                            if (stack.getWrappedObject() instanceof ItemStack
                                    && collatedStack.getWrappedObject() instanceof ItemStack) {
                                if (ItemStackUtils.equals(
                                        (ItemStack) stack.getWrappedObject(),
                                        (ItemStack) collatedStack.getWrappedObject())) {
                                    collatedStack.setStackSize(collatedStack.getStackSize() + stack.getStackSize());
                                    found = true;
                                }
                            } else if (stack.getWrappedObject() instanceof OreStack
                                    && collatedStack.getWrappedObject() instanceof OreStack) {
                                        if (OreStack.compareOreNames(
                                                (OreStack) stack.getWrappedObject(),
                                                (OreStack) collatedStack.getWrappedObject())) {
                                            collatedStack
                                                    .setStackSize(collatedStack.getStackSize() + stack.getStackSize());
                                            found = true;
                                        }
                                    }
                        }
                    }

                    if (!found) {
                        collatedStacks.add(stack);
                    }
                }
            }
        }
        Collections.sort(collatedStacks);
        return collatedStacks;
    }

    /**
     * Validates the list of recipe inputs for an OreDictionary recipe to ensure that if there is an OreDictionary entry
     * found in the recipe (a list) that it is not empty (that there are ItemStacks associated with that particular
     * OreDictionary entry)
     *
     * @param objects a list of recipe inputs for an OreDictionary recipe
     * @return true if there exists no "invalid" (empty) OreDictionary entries in the provided list of recipe inputs
     */
    // FIXME This is not working as intended
    private static boolean validateOreDictionaryRecipe(List objects) {
        for (Object object : objects) {
            if (object != null) {
                if (!WrappedStack.canBeWrapped(object)) {
                    return false;
                }
            }
        }

        return true;
    }
}
