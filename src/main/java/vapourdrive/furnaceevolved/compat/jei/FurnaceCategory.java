package vapourdrive.furnaceevolved.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import vapourdrive.furnaceevolved.Reference;

public class FurnaceCategory extends FurnaceRecipeCategory
{
	private final IDrawable background;
	public FurnaceCategory(IGuiHelper guiHelper)
	{
		super(guiHelper);
		ResourceLocation location = new ResourceLocation("furnaceevolved", "textures/gui/container/furnace.png");
		background = guiHelper.createDrawable(location, 16, 16, 145, 36);
	}

	@Override
	public String getUid()
	{
		return Reference.ModID + "Evolved Furnace";
	}

	@Override
	public String getTitle()
	{
		return "Evolved Furnace Recipes";
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		guiStacks.init(0, true, 18, 0);
		guiStacks.init(1, false, 72, 0);
		
		guiStacks.setFromRecipe(0, recipeWrapper.getInputs());
		guiStacks.setFromRecipe(1, recipeWrapper.getOutputs());

	}

}
