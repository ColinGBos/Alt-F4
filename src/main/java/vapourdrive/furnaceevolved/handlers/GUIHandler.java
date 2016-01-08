package vapourdrive.furnaceevolved.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import vapourdrive.furnaceevolved.blocks.ContainerEvolvedFurnace;
import vapourdrive.furnaceevolved.blocks.GuiFurnaceEvolved;
import vapourdrive.furnaceevolved.blocks.TileEntityEvolvedFurnace;

public class GUIHandler implements IGuiHandler
{
	public enum GUIIDs
	{
		EVOLVED_FURNACE;
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(GUIIDs.values()[ID])
		{
			case EVOLVED_FURNACE:
			{
				return new ContainerEvolvedFurnace(player.inventory, (TileEntityEvolvedFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			}
		}
		throw new IllegalArgumentException("NO GUI WITH ID: " + ID);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(GUIIDs.values()[ID])
		{
			case EVOLVED_FURNACE:
			{
				return new GuiFurnaceEvolved(player.inventory, (TileEntityEvolvedFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			}
		}
		throw new IllegalArgumentException("NO GUI WITH ID: " + ID);
	}

}
