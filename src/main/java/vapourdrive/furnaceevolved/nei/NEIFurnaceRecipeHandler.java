package vapourdrive.furnaceevolved.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import vapourdrive.furnaceevolved.blocks.GuiFurnaceEvolved;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipe;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipeHandler;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIFurnaceRecipeHandler extends TemplateRecipeHandler
{
	public class NEIFurnaceRecipe extends TemplateRecipeHandler.CachedRecipe
	{
		public PositionedStack ingredients;
		public PositionedStack output;
		public ArrayList<ItemStack> fuels = buildFuelList();

		public NEIFurnaceRecipe(List<ItemStack> inputs, ItemStack input, ItemStack out)
		{
			ingredients = new PositionedStack(inputs, 30, 6, true);
			output = new PositionedStack(out, 84, 6);
		}

		private ArrayList<ItemStack> buildFuelList()
		{
			ArrayList<ItemStack> afuels = new ArrayList();
			for (ItemStack item : ItemList.items)
			{
				int burnTime = net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(item);
				if (burnTime > 0)
				{
					afuels.add(item.copy());
				}
			}
			return afuels;
		}

		public NEIFurnaceRecipe(FurnaceRecipe recipe)
		{
			this(recipe.getInputStackList(), recipe.getInputStack(), recipe.getOutputStack());
		}

		@Override
		public PositionedStack getResult()
		{
			return output;
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 24, Arrays.asList(new PositionedStack[]
			{
				ingredients
			}));
		}

		@Override
		public PositionedStack getOtherStack()
		{
			return new PositionedStack(fuels.get(cycleticks / 24 % fuels.size()), 30, 42);
		}
	}
	
	@Override
	public void drawExtras(int recipe)
	{
		drawProgressBar(31, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(54, 5, 176, 14, 24, 16, 48, 0);
	}

	@Override
	public String getRecipeName()
	{
		return "Evolved Furnace";
	}

	@Override
	public String getGuiTexture()
	{
		return "furnaceevolved:textures/gui/container/furnace.png";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFurnaceEvolved.class;
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("Evolved Furnace") && getClass() == NEIFurnaceRecipeHandler.class)
		{
			List<FurnaceRecipe> recipes = FurnaceRecipeHandler.getFurnaceRecipes();
			for (FurnaceRecipe recipe : recipes)
			{
				NEIFurnaceRecipe res = new NEIFurnaceRecipe(recipe.getInputStackList(), recipe.getInputStack(), recipe.getOutputStack());
				arecipes.add(res);
			}
		}
		else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		List<FurnaceRecipe> recipes = FurnaceRecipeHandler.getFurnaceRecipes();
		for (FurnaceRecipe recipe : recipes)
		{
			if (recipe.getOutputStack().getItem() == result.getItem())
			{
				NEIFurnaceRecipe res = new NEIFurnaceRecipe(recipe.getInputStackList(), recipe.getInputStack(), recipe.getOutputStack());
				arecipes.add(res);
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		for (FurnaceRecipe irecipe : (List<FurnaceRecipe>) FurnaceRecipeHandler.getFurnaceRecipes())
		{
			NEIFurnaceRecipe recipe = null;
			if (irecipe instanceof FurnaceRecipe)
			{
				recipe = new NEIFurnaceRecipe((FurnaceRecipe) irecipe);
			}

			if (recipe == null || !recipe.contains(recipe.getIngredients(), ingredient.getItem()))
			{
				continue;
			}

			if (recipe.contains(recipe.getIngredients(), ingredient))
			{
				arecipes.add(recipe);
			}
		}
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(53, 24, 24, 16), "Evolved Furnace", new Object[0]));
	}

	@Override
	public int recipiesPerPage()
	{
		return 2;
	}

}
