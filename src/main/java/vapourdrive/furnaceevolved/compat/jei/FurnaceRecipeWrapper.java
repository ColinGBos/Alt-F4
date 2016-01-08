package vapourdrive.furnaceevolved.compat.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.gui.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipe;

public class FurnaceRecipeWrapper extends BlankRecipeWrapper
{
	protected final List<ItemStack> inputs;
	protected final List<ItemStack> outputs;
	protected final IDrawableAnimated flame;
	protected final IDrawableAnimated arrow;
	protected final String burntime;
	protected final String experience;
	protected final GuiHelper guiHelper = new GuiHelper();
	ResourceLocation background = new ResourceLocation("furnaceevolved", "textures/gui/container/furnace.png");

	public FurnaceRecipeWrapper(FurnaceRecipe recipe)
	{
		inputs = recipe.getInputStackList();
		outputs = new ArrayList<ItemStack>();
		outputs.add(recipe.getOutputStack());

		IDrawableStatic flameDrawable = guiHelper.createDrawable(background, 176, 0, 14, 14);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.TOP, true);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(background, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, recipe.getCookTime(), IDrawableAnimated.StartDirection.LEFT, false);

		burntime = String.valueOf(recipe.getCookTime());
		experience = String.valueOf(recipe.getExperience());
	}

	@Override
	public List getInputs()
	{
		return inputs;
	}

	@Override
	public List getOutputs()
	{
		return outputs;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
	{
		minecraft.fontRendererObj.drawString(burntime + " t.", 42, 19, Color.gray.getRGB());
		minecraft.fontRendererObj.drawString(experience + " XP", 75, 27, Color.gray.getRGB());
	}

	@Override
	public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
	{
		flame.draw(minecraft, 20, 20);
		arrow.draw(minecraft, 43, 0);
	}
}
