package vapourdrive.furnaceevolved.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vapourdrive.furnaceevolved.FurnaceEvolved;

public class BlockEvolvedFurnace extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	private static boolean keepInventory;

	public BlockEvolvedFurnace()
	{
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
		this.setHardness(3.5F);
		this.setStepSound(soundTypePiston);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setUnlocalizedName("evolvedFurnace");
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			Block block = worldIn.getBlockState(pos.north()).getBlock();
			Block block1 = worldIn.getBlockState(pos.south()).getBlock();
			Block block2 = worldIn.getBlockState(pos.west()).getBlock();
			Block block3 = worldIn.getBlockState(pos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
			{
				enumfacing = EnumFacing.SOUTH;
			}
			else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
			{
				enumfacing = EnumFacing.NORTH;
			}
			else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
			{
				enumfacing = EnumFacing.EAST;
			}
			else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
			{
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(BURNING, false), 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (state.getValue(BURNING) && worldIn.rand.nextFloat() > 0.4f)
		{
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + (rand.nextDouble() * 6.0D / 16.0) + 0.1D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.54D;
			double d4 = rand.nextDouble() * 0.4D - 0.2D;
			double outVelocity = rand.nextDouble() * 0.01D;
			double downVelocity = rand.nextDouble() * 0.05D;

			switch (enumfacing)
			{
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, -d1, d2 + d4, -outVelocity, downVelocity, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, -outVelocity, downVelocity, 0.0D, new int[0]);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, outVelocity, downVelocity, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, outVelocity, downVelocity, 0.0D, new int[0]);
					break;
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, downVelocity, -outVelocity, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, downVelocity, -outVelocity, new int[0]);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, downVelocity, outVelocity, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, downVelocity, outVelocity, new int[0]);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else if (!player.isSneaking())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityEvolvedFurnace)
			{
				player.openGui(FurnaceEvolved.Instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}

			return true;
		}
		return false;
	}

	public static void setState(boolean isBurning, World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		keepInventory = true;

		worldIn.setBlockState(pos, FE_Blocks.EvolvedFurnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING))
				.withProperty(BURNING, isBurning), 3);

		keepInventory = false;

		if (tileentity != null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
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

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(BURNING, false);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(BURNING, false), 2);

		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityEvolvedFurnace)
			{
				((TileEntityEvolvedFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!keepInventory)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityEvolvedFurnace)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityEvolvedFurnace) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos)
	{
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(BURNING, state.getValue(BURNING));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		int side = meta;
		if (meta > 5)
		{
			side = meta - 6;
		}
		EnumFacing enumfacing = EnumFacing.getFront(side);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(BURNING, meta > 5);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int side = ((EnumFacing) state.getValue(FACING)).getIndex();
		if (state.getValue(BURNING))
		{
			side = side + 6;
		}
		return side;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, FACING, BURNING);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
    {
        int lightValue = 0;
        if(world.getBlockState(pos).getValue(BURNING))
        {
        	lightValue = 13;
        }
        return lightValue;
    }
}
