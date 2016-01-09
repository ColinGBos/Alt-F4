package vapourdrive.furnaceevolved.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vapourdrive.furnaceevolved.FurnaceEvolved;
import vapourdrive.furnaceevolved.Reference;
import vapourdrive.furnaceevolved.utils.RandomUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEvolvedFurnace extends BlockContainer
{
	private static boolean keepInventory;
	private IIcon blockTop;
	private IIcon blockFrontOn;
	private IIcon blockFrontOff;
	private IIcon blockBottom;

	public BlockEvolvedFurnace()
	{
		super(Material.rock);
		this.setHardness(3.5F);
		this.setStepSound(soundTypePiston);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockName("evolvedFurnace");
	}

	@Override
	public void onBlockAdded(World worldIn, int x, int y, int z)
	{
		super.onBlockAdded(worldIn, x, y, z);
		if (!worldIn.isRemote)
		{
			Block block = worldIn.getBlock(x, y, z - 1);
			Block block1 = worldIn.getBlock(x, y, z + 1);
			Block block2 = worldIn.getBlock(x - 1, y, z);
			Block block3 = worldIn.getBlock(x + 1, y, z);
			byte b0 = 3;

			if (block.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 3;
			}

			if (block1.func_149730_j() && !block.func_149730_j())
			{
				b0 = 2;
			}

			if (block2.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 5;
			}

			if (block3.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 4;
			}

			worldIn.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		if (stack.hasDisplayName())
		{
			((TileEntityFurnace) world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand)
	{
		if (worldIn.getBlockMetadata(x, y, z) >= 6 && worldIn.rand.nextFloat() > 0.4f)
		{
			int l = worldIn.getBlockMetadata(x, y, z) - 6;
			double d0 = (double) x + 0.5D;
			double d1 = (double) y + (rand.nextDouble() * 6.0D / 16.0) + 0.1D;
			double d2 = (double) z + 0.5D;
			double d3 = 0.54D;
			double d4 = rand.nextDouble() * 0.4D - 0.2D;
			double outVelocity = rand.nextDouble() * 0.01D;
			double downVelocity = rand.nextDouble() * 0.05D;

			switch (l)
			{
				case 4:
					worldIn.spawnParticle("smoke", d0 - d3, -d1, d2 + d4, -outVelocity, downVelocity, 0.0D);
					worldIn.spawnParticle("flame", d0 - d3, d1, d2 + d4, -outVelocity, downVelocity, 0.0D);
					break;
				case 5:
					worldIn.spawnParticle("smoke", d0 + d3, d1, d2 + d4, outVelocity, downVelocity, 0.0D);
					worldIn.spawnParticle("flame", d0 + d3, d1, d2 + d4, outVelocity, downVelocity, 0.0D);
					break;
				case 2:
					worldIn.spawnParticle("smoke", d0 + d4, d1, d2 - d3, 0.0D, downVelocity, -outVelocity);
					worldIn.spawnParticle("flame", d0 + d4, d1, d2 - d3, 0.0D, downVelocity, -outVelocity);
					break;
				case 3:
					worldIn.spawnParticle("smoke", d0 + d4, d1, d2 + d3, 0.0D, downVelocity, outVelocity);
					worldIn.spawnParticle("flame", d0 + d4, d1, d2 + d3, 0.0D, downVelocity, outVelocity);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else if (!player.isSneaking())
		{
			TileEntity tileentity = worldIn.getTileEntity(x, y, z);

			if (tileentity instanceof TileEntityEvolvedFurnace)
			{
				player.openGui(FurnaceEvolved.Instance, 0, worldIn, x, y, z);
			}

			return true;
		}
		return false;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEvolvedFurnace();
	}

	@Override
	public void breakBlock(World worldIn, int x, int y, int z, Block block, int meta)
	{
		if (!keepInventory)
		{
			TileEntity tileentity = worldIn.getTileEntity(x, y, z);

			if (tileentity instanceof TileEntityEvolvedFurnace)
			{
				RandomUtils.dropInventoryItems(worldIn, x, y, z, (TileEntityEvolvedFurnace) tileentity);
			}
		}

		super.breakBlock(worldIn, x, y, z, block, meta);
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta)
	{
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		int turn;
		turn = meta;
		if (meta >= 6)
		{
			turn = meta - 6;
		}
		if (side == 0)
		{
			return this.blockBottom;
		}
		if (side == 1)
		{
			return this.blockTop;
		}
		if (side == turn)
		{
			if (meta >= 6)
			{
				return this.blockFrontOn;
			}
			return this.blockFrontOff;
		}

		return this.blockIcon;

	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon(Reference.ResourcePath + "furnace_side");
		this.blockTop = register.registerIcon(Reference.ResourcePath + "furnace_top");
		this.blockFrontOn = register.registerIcon(Reference.ResourcePath + "furnace_front_lit");
		this.blockFrontOff = register.registerIcon(Reference.ResourcePath + "furnace_front");
		this.blockBottom = register.registerIcon(Reference.ResourcePath + "furnace_bottom");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int lightValue = 0;
		if (world.getBlockMetadata(x, y, z) >= 6)
		{
			lightValue = 13;
		}
		return lightValue;
	}
}
