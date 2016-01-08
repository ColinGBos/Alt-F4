package vapourdrive.furnaceevolved.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;

public class FurnaceRecipeHandler
{
	public static ArrayList<FurnaceRecipe> furnaceRecipe;

	public static int getCookTime(ItemStack stack)
	{
		if (getRecipe(stack) == null)
		{
			return 100;
		}
		return getRecipe(stack).getCookTime();
	}

	private static FurnaceRecipe getRecipe(ItemStack stack)
	{
		if (stack != null)
		{
			Iterator iterator = furnaceRecipe.iterator();
			while (iterator.hasNext())
			{
				FurnaceRecipe recipe = (FurnaceRecipe) iterator.next();
				ItemStack recipeInput = recipe.getInputStack();
				if (recipeInput.getItem() == stack.getItem() && recipe.getIgnoresMeta())
				{
					return recipe;
				}
				if (recipeInput.getItem() == stack.getItem() && recipeInput.getMetadata() == stack.getMetadata())
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public static ItemStack getSmeltingResult(ItemStack stack)
	{
		if (getRecipe(stack) == null)
		{
			return null;
		}
		return getRecipe(stack).getOutputStack();
	}

	public static Float getExperience(ItemStack stack)
	{
		if (getRecipe(stack) == null)
		{
			return 0.0f;
		}
		return getRecipe(stack).getExperience();
	}

	public static List getFurnaceRecipes()
	{
		return furnaceRecipe;
	}

}
