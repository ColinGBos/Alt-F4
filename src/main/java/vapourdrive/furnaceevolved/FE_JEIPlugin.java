package vapourdrive.furnaceevolved;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import vapourdrive.furnaceevolved.blocks.ContainerEvolvedFurnace;
import vapourdrive.furnaceevolved.compat.jei.FurnaceCategory;
import vapourdrive.furnaceevolved.compat.jei.JEIFurnaceHandler;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipeHandler;

@JEIPlugin
public class FE_JEIPlugin implements IModPlugin
{
	public static IJeiHelpers jeiHelpers;
	public static IItemRegistry itemRegistry;
	public static IRecipeRegistry recipeRegistry;

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers)
	{
		FE_JEIPlugin.jeiHelpers = jeiHelpers;

	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry)
	{
		FE_JEIPlugin.itemRegistry = itemRegistry;

	}

	@Override
	public void register(IModRegistry registry)
	{
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new FurnaceCategory(guiHelper));
		registry.addRecipeHandlers(new JEIFurnaceHandler());
		IRecipeTransferRegistry reg = registry.getRecipeTransferRegistry();
		reg.addRecipeTransferHandler(ContainerEvolvedFurnace.class, "furnaceevolved:Evolved Furnace", 0, 1, 1, 46);
		registry.addRecipes(FurnaceRecipeHandler.getFurnaceRecipes());
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry)
	{
		FE_JEIPlugin.recipeRegistry = recipeRegistry;

	}

}
