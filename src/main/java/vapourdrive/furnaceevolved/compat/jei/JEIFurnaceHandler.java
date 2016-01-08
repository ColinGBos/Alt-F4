package vapourdrive.furnaceevolved.compat.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import vapourdrive.furnaceevolved.Reference;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipe;

public class JEIFurnaceHandler implements IRecipeHandler<FurnaceRecipe>
{

	@Override
	public Class getRecipeClass()
	{
		return FurnaceRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return Reference.ModID + "Evolved Furnace";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(FurnaceRecipe recipe)
	{
		return new FurnaceRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(FurnaceRecipe recipe)
	{
		return recipe.getInputStack() != null && recipe.getOutputStack() != null;
	}

}
