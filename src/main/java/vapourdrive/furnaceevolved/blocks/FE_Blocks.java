package vapourdrive.furnaceevolved.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class FE_Blocks
{
	public static Block EvolvedFurnace;

	public static void preInit()
	{
		EvolvedFurnace = new BlockEvolvedFurnace();
		GameRegistry.registerBlock(EvolvedFurnace, ItemBlockFurnace.class, "evolvedFurnace");
		GameRegistry.registerTileEntity(TileEntityEvolvedFurnace.class, "EvolvedFurnaceTile");
		RegisterRecipes();
	}

	private static void RegisterRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EvolvedFurnace, 1, 3), new Object[]
		{
				"SSS", "CHC", "CgC", 'S', new ItemStack(Blocks.stone_slab, 1, 0), 'H', Blocks.hopper, 'C', "cobblestone", 'g', "blockGlass"
		}));
	}
}
