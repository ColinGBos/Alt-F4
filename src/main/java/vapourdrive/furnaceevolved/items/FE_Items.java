package vapourdrive.furnaceevolved.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vapourdrive.furnaceevolved.utils.RandomUtils;
import cpw.mods.fml.common.registry.GameRegistry;

public class FE_Items
{
	public static Item xpCrystal;

	public static void preInit()
	{
		xpCrystal = new ExperienceCrystal();
		RegisterRecipes();
	}
	
	private static void RegisterRecipes()
	{
		ItemStack cystal = new ItemStack(xpCrystal);
		RandomUtils.getNBT(cystal).setFloat(ExperienceCrystal.TAG_EXPERIENCE, 0.0f);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(xpCrystal), new Object[]
		{
				" g ", "geg", " g ", 'g', "blockGlass", 'e', Items.ender_pearl
		}));
	}
}
