package vapourdrive.furnaceevolved.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import vapourdrive.furnaceevolved.items.IExperienceStorage;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipeHandler;

public class TileEntityEvolvedFurnace extends TileEntity implements ISidedInventory
{
	private ItemStack[] furnaceItemStacks = new ItemStack[10];
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int timeBurning;
	private int itemCookTime;
	private String furnaceCustomName;
	private float experienceStored;
	public final int maxExperienceStored = 1600;

	@Override
	public int getSizeInventory()
	{
		return furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return furnaceItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.furnaceItemStacks[index] != null)
		{
			if (this.furnaceItemStacks[index].stackSize <= count)
			{
				ItemStack itemstack1 = this.furnaceItemStacks[index];
				this.furnaceItemStacks[index] = null;
				return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.furnaceItemStacks[index].splitStack(count);

				if (this.furnaceItemStacks[index].stackSize == 0)
				{
					this.furnaceItemStacks[index] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (this.furnaceItemStacks[index] != null)
		{
			ItemStack itemstack = this.furnaceItemStacks[index];
			this.furnaceItemStacks[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		boolean flag = stack != null && this.furnaceItemStacks[index] != null && stack.isItemEqual(this.furnaceItemStacks[index])
				&& ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]);
		this.furnaceItemStacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		if ((index == 0) && !flag)
		{
			this.itemCookTime = this.getCookTime(stack);
			this.timeBurning = 0;
			this.markDirty();
		}
	}

	public int getCookTime(ItemStack stack)
	{
		return FurnaceRecipeHandler.getCookTime(stack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 0 || index == 1)
		{
			return true;
		}
		if (index == 2 || index == 3)
		{
			if (TileEntityFurnace.getItemBurnTime(stack) > 0)
			{
				return true;
			}
		}
		if (index == 8 && stack.getItem() instanceof IExperienceStorage)
		{
			return true;
		}
		return false;
	}

	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.furnaceBurnTime;
			case 1:
				return this.currentItemBurnTime;
			case 2:
				return this.timeBurning;
			case 3:
				return this.itemCookTime;
			case 4:
				return (int) this.experienceStored;
			default:
				return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.furnaceBurnTime = value;
				break;
			case 1:
				this.currentItemBurnTime = value;
				break;
			case 2:
				this.timeBurning = value;
				break;
			case 3:
				this.itemCookTime = value;
				break;
			case 4:
				this.experienceStored = value;
		}
	}

	public int getFieldCount()
	{
		return 5;
	}

	public void clear()
	{
		for (int i = 0; i < this.furnaceItemStacks.length; ++i)
		{
			this.furnaceItemStacks[i] = null;
		}
	}

	public String getName()
	{
		if (this.hasCustomName())
		{
			return this.furnaceCustomName;
		}
		else
		{
			return this.getGuiID();
		}
	}

	public boolean hasCustomName()
	{
		return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if (side == 0)
		{
			return new int[]
			{
					4, 5, 6, 7, 9
			};
		}
		else if (side == 1)
		{
			return new int[]
			{
					0, 1, 8
			};
		}
		else
		{
			return new int[]
			{
					0, 1, 2, 3, 8
			};
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		if (side == 0)
		{
			return false;
		}
		if (TileEntityFurnace.isItemFuel(stack))
		{
			if (index == 2 || index == 3)
			{
				return true;
			}
		}
		else if (index == 8 && stack.getItem() instanceof IExperienceStorage)
		{
			return true;
		}
		else if (index == 0 || index == 1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		if (side == 0)
		{
			if (index >= 4)
			{
				return true;
			}
		}
		return false;
	}

	public void setCustomInventoryName(String displayName)
	{
		this.furnaceCustomName = displayName;
	}

	public boolean isBurning(IInventory tileFurnace)
	{
		return this.getField(0) > 0;
	}

	public boolean isBurning()
	{
		return this.furnaceBurnTime > 0;
	}

	@Override
	public void updateEntity()
	{
		boolean isBurning = this.furnaceBurnTime > 0;
		boolean requiresUpdate = false;

		if (isBurning())
		{
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.furnaceItemStacks[9] == null && this.furnaceItemStacks[8] != null
					&& this.furnaceItemStacks[8].getItem() instanceof IExperienceStorage)
			{
				if (this.getField(4) >= 10f)
				{
					ItemStack crystal = this.furnaceItemStacks[8].copy();
					IExperienceStorage xpStorage = (IExperienceStorage) crystal.getItem();
					float toExtract = xpStorage.receiveExperience(crystal, this.experienceStored, false);
					this.experienceStored -= toExtract;
					this.setInventorySlotContents(9, crystal);
					this.setInventorySlotContents(8, null);
				}
			}
			if (this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] == null)
			{
				this.setInventorySlotContents(0, this.furnaceItemStacks[1]);
				this.setInventorySlotContents(1, null);
			}
			else if (this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null
					&& (areStackCompatible(this.furnaceItemStacks[1], this.furnaceItemStacks[0])))
			{
				int tomove = Math.min(this.furnaceItemStacks[1].stackSize, this.furnaceItemStacks[0].getMaxStackSize()
						- this.furnaceItemStacks[0].stackSize);
				this.furnaceItemStacks[0].stackSize += tomove;
				this.furnaceItemStacks[1].stackSize -= tomove;
				if (furnaceItemStacks[1].stackSize == 0)
				{
					this.setInventorySlotContents(1, null);
				}
			}
			if (this.furnaceItemStacks[3] != null && this.furnaceItemStacks[2] == null)
			{
				this.setInventorySlotContents(2, this.furnaceItemStacks[3]);
				this.setInventorySlotContents(3, null);
			}
			else if (this.furnaceItemStacks[3] != null && this.furnaceItemStacks[2] != null
					&& (areStackCompatible(this.furnaceItemStacks[3], this.furnaceItemStacks[2])))
			{
				int tomove = Math.min(this.furnaceItemStacks[3].stackSize, this.furnaceItemStacks[2].getMaxStackSize()
						- this.furnaceItemStacks[2].stackSize);
				this.furnaceItemStacks[2].stackSize += tomove;
				this.furnaceItemStacks[3].stackSize -= tomove;
				if (furnaceItemStacks[3].stackSize == 0)
				{
					this.setInventorySlotContents(3, null);
				}
			}
			if (isBurning() || (this.furnaceItemStacks[2] != null && this.furnaceItemStacks[0] != null))
			{
				requiresUpdate = handleBurningUpdate();
			}
			else if (!this.isBurning() && this.timeBurning > 0)
			{
				this.timeBurning = MathHelper.clamp_int(this.timeBurning - 2, 0, this.itemCookTime);
			}

			if (isBurning != this.isBurning())
			{
				requiresUpdate = true;
			}
		}
		if (requiresUpdate)
		{
			this.markDirty();
			int meta = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(this.isBurning() && meta < 6)
			{
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 6, 3);
			}
			else if(!this.isBurning() && meta >= 6)
			{
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 6, 3);
			}
		}
	}

	public boolean handleBurningUpdate()
	{
		boolean requiresUpdate = false;
		if (!this.isBurning() && canSmelt())
		{
			this.currentItemBurnTime = this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime((this.furnaceItemStacks[2]));

			if (this.isBurning())
			{
				requiresUpdate = true;

				if (this.furnaceItemStacks[2] != null)
				{
					--this.furnaceItemStacks[2].stackSize;

					if (this.furnaceItemStacks[2].stackSize == 0)
					{
						this.furnaceItemStacks[2] = furnaceItemStacks[2].getItem().getContainerItem(furnaceItemStacks[2]);
					}
				}
			}
		}
		if (this.isBurning() && this.canSmelt())
		{
			++this.timeBurning;

			if (this.timeBurning == this.itemCookTime)
			{
				this.timeBurning = 0;
				this.itemCookTime = this.getCookTime(this.furnaceItemStacks[0]);
				this.smeltItem();

				requiresUpdate = true;
			}
		}
		else
		{
			this.timeBurning = 0;
		}
		return requiresUpdate;
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipeHandler.getSmeltingResult(this.furnaceItemStacks[0]);
			Float experience = FurnaceRecipeHandler.getExperience(this.furnaceItemStacks[0]);
			this.experienceStored += Math.min(experience, this.maxExperienceStored - this.experienceStored);

			if (this.furnaceItemStacks[4] == null)
			{
				this.furnaceItemStacks[4] = itemstack.copy();
			}
			else if (areStackCompatible(this.furnaceItemStacks[4], itemstack)
					&& this.furnaceItemStacks[4].stackSize + itemstack.stackSize <= this.furnaceItemStacks[4].getMaxStackSize())
			{
				this.furnaceItemStacks[4].stackSize += itemstack.stackSize;
			}
			else if (this.furnaceItemStacks[5] == null)
			{
				this.furnaceItemStacks[5] = itemstack.copy();
			}
			else if (areStackCompatible(this.furnaceItemStacks[5], itemstack)
					&& this.furnaceItemStacks[5].stackSize + itemstack.stackSize <= this.furnaceItemStacks[5].getMaxStackSize())
			{
				this.furnaceItemStacks[5].stackSize += itemstack.stackSize;
			}
			else if (this.furnaceItemStacks[6] == null)
			{
				this.furnaceItemStacks[6] = itemstack.copy();
			}
			else if (areStackCompatible(this.furnaceItemStacks[6], itemstack)
					&& this.furnaceItemStacks[6].stackSize + itemstack.stackSize <= this.furnaceItemStacks[6].getMaxStackSize())
			{
				this.furnaceItemStacks[6].stackSize += itemstack.stackSize;
			}
			else if (this.furnaceItemStacks[7] == null)
			{
				this.furnaceItemStacks[7] = itemstack.copy();
			}
			else if (areStackCompatible(this.furnaceItemStacks[7], itemstack)
					&& this.furnaceItemStacks[7].stackSize + itemstack.stackSize <= this.furnaceItemStacks[7].getMaxStackSize())
			{
				this.furnaceItemStacks[7].stackSize += itemstack.stackSize;
			}

			if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge)
					&& this.furnaceItemStacks[0].getItemDamage() == 1 && this.furnaceItemStacks[2] != null
					&& this.furnaceItemStacks[2].getItem() == Items.bucket)
			{
				this.furnaceItemStacks[2] = new ItemStack(Items.water_bucket);
			}

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize <= 0)
			{
				this.furnaceItemStacks[0] = null;
			}
		}
	}

	private boolean canSmelt()
	{
		if (this.furnaceItemStacks[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipeHandler.getSmeltingResult(this.furnaceItemStacks[0]);
			if (itemstack == null)
			{
				return false;
			}
			if (this.furnaceItemStacks[4] == null || this.furnaceItemStacks[5] == null || this.furnaceItemStacks[6] == null
					|| this.furnaceItemStacks[7] == null)
			{
				return true;
			}
			if (areStackCompatible(this.furnaceItemStacks[4], itemstack)
					&& furnaceItemStacks[4].stackSize < furnaceItemStacks[4].getMaxStackSize())
			{
				int result = furnaceItemStacks[4].stackSize + itemstack.stackSize;
				return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[4].getMaxStackSize();
			}
			else if (areStackCompatible(this.furnaceItemStacks[5], itemstack)
					&& furnaceItemStacks[5].stackSize < furnaceItemStacks[5].getMaxStackSize())
			{
				int result = furnaceItemStacks[5].stackSize + itemstack.stackSize;
				return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[5].getMaxStackSize();
			}
			else if (areStackCompatible(this.furnaceItemStacks[6], itemstack)
					&& furnaceItemStacks[6].stackSize < furnaceItemStacks[6].getMaxStackSize())
			{
				int result = furnaceItemStacks[6].stackSize + itemstack.stackSize;
				return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[6].getMaxStackSize();
			}
			else if (areStackCompatible(this.furnaceItemStacks[7], itemstack)
					&& furnaceItemStacks[7].stackSize < furnaceItemStacks[7].getMaxStackSize())
			{
				int result = furnaceItemStacks[7].stackSize + itemstack.stackSize;
				return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[7].getMaxStackSize();
			}
		}
		return false;
	}

	public String getGuiID()
	{
		return "Evolved Furnace";
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.furnaceItemStacks.length)
			{
				this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		this.experienceStored = compound.getFloat("ExperienceStored");
		this.furnaceBurnTime = compound.getShort("BurnTime");
		this.timeBurning = compound.getShort("CookTime");
		this.itemCookTime = compound.getShort("CookTimeTotal");
		this.currentItemBurnTime = compound.getShort("CurrentFuelBurnTime");

		if (compound.hasKey("CustomName", 8))
		{
			this.furnaceCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setFloat("ExperienceStored", this.experienceStored);
		compound.setShort("BurnTime", (short) this.furnaceBurnTime);
		compound.setShort("CookTime", (short) this.timeBurning);
		compound.setShort("CookTimeTotal", (short) this.itemCookTime);
		compound.setShort("CurrentFuelBurnTime", (short) this.currentItemBurnTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i)
		{
			if (this.furnaceItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.furnaceCustomName);
		}
	}

	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.furnaceCustomName : this.getGuiID();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
	}
	
	public boolean areStackCompatible(ItemStack stack1, ItemStack stack2)
	{
		if(stack1 != null && stack2 != null)
		{
			if(stack1.getItem() != null && stack2.getItem() != null)
			{
				if(stack1.getItem() == stack2.getItem())
				{
					if (!stack1.getHasSubtypes() || stack1.getItemDamage() == stack2.getItemDamage())
					{
						if(ItemStack.areItemStackTagsEqual(stack1, stack2))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
