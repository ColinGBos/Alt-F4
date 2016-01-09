package vapourdrive.furnaceevolved.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFurnace extends ItemBlock
{

	public ItemBlockFurnace(Block block)
	{
		super(block);
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	list.add(new ItemStack(item, 1, 3));
    }

}
