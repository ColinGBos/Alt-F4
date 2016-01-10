package vapourdrive.furnaceevolved.items;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vapourdrive.furnaceevolved.Reference;
import vapourdrive.furnaceevolved.utils.RandomUtils;

public class FE_Items
{
	public static Item xpCrystal;

	public static void preInit()
	{
		xpCrystal = new ExperienceCrystal();
		RegisterRecipes();
	}

	@SideOnly(Side.CLIENT)
	public static void clientPreInit()
	{
		ResourceLocation[] resourceVariants = new ResourceLocation[16];
		for (int i = 0; i < 16; i++)
		{
			ResourceLocation location = new ModelResourceLocation(Reference.ResourcePath + "ExperienceCrystal_" + i);
			resourceVariants[i] = location;
		}
		ModelBakery.registerItemVariants(xpCrystal, resourceVariants);
		ModelLoader.setCustomMeshDefinition(xpCrystal, new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				ExperienceCrystal crystal = (ExperienceCrystal) stack.getItem();
				int level = (int) (15 * (crystal.getCurrentExperienceStored(stack)/crystal.getMaxExperienceStored(stack)));
				if (level > 15)
				{
					level = 15;
				}
				if (level < 0)
				{
					level = 0;
				}
				return new ModelResourceLocation(Reference.ResourcePath + "ExperienceCrystal_" + level);
			}

		});
	}
	
	private static void RegisterRecipes()
	{
		ItemStack cystal = new ItemStack(xpCrystal);
		RandomUtils.getNBT(cystal).setFloat(ExperienceCrystal.TAG_EXPERIENCE, 0.0f);
		GameRegistry.addRecipe(new ShapedOreRecipe(cystal, new Object[]
		{
				" g ", "geg", " g ", 'g', "blockGlass", 'e', Items.ender_pearl
		}));
	}
}
