package vapourdrive.furnaceevolved.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vapourdrive.furnaceevolved.Reference;

public class FE_Blocks
{
	public static Block EvolvedFurnace;

	public static void preInit()
	{
		EvolvedFurnace = new BlockEvolvedFurnace();
		GameRegistry.registerBlock(EvolvedFurnace, "evolvedFurnace");
		GameRegistry.registerTileEntity(TileEntityEvolvedFurnace.class, "EvolvedFurnaceTile");
		RegisterRecipes();
	}

	public static void preInitModel()
	{
		ResourceLocation[] resourceVariants = new ResourceLocation[]
		{
				new ResourceLocation(Reference.ResourcePath + "evolvedFurnace_burning"),
				new ResourceLocation(Reference.ResourcePath + "evolvedFurnace")
		};
		ModelBakery.registerItemVariants(Item.getItemFromBlock(EvolvedFurnace), resourceVariants);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.ResourcePath + "evolvedFurnace", "inventory");
		ModelLoader.setCustomModelResourceLocation((Item.getItemFromBlock(EvolvedFurnace)), 0, itemModelResourceLocation);
	}

	private static void RegisterRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EvolvedFurnace), new Object[]
		{
				"SSS", "CHC", "CgC", 'S', new ItemStack(Blocks.stone_slab, 1, 0), 'H', Blocks.hopper, 'C', "cobblestone", 'g', "blockGlass"
		}));
	}
}
