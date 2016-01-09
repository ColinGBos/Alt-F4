package vapourdrive.furnaceevolved.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import vapourdrive.furnaceevolved.blocks.slots.SlotExperience;
import vapourdrive.furnaceevolved.blocks.slots.SlotFurnace;
import vapourdrive.furnaceevolved.blocks.slots.SlotFurnaceFuel;
import vapourdrive.furnaceevolved.items.IExperienceStorage;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipeHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerEvolvedFurnace extends Container
{
	private final TileEntityEvolvedFurnace tileFurnace;
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int timeBurning;
	private int itemCookTime;
	private int experience;

	public ContainerEvolvedFurnace(InventoryPlayer playerInventory, TileEntityEvolvedFurnace tileEntityEvolvedFurnace)
	{
		this.tileFurnace = tileEntityEvolvedFurnace;
		this.addSlotToContainer(new Slot(tileEntityEvolvedFurnace, 1, 17, 17));
		this.addSlotToContainer(new Slot(tileEntityEvolvedFurnace, 0, 35, 17));
		this.addSlotToContainer(new SlotFurnaceFuel(tileEntityEvolvedFurnace, 3, 17, 53));
		this.addSlotToContainer(new SlotFurnaceFuel(tileEntityEvolvedFurnace, 2, 35, 53));
		for (int i = 0; i < 4; i++)
		{
			this.addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntityEvolvedFurnace, 4 + i, 89 + (i * 18), 17));
		}
		this.addSlotToContainer(new Slot(tileEntityEvolvedFurnace, 8, 89, 53));
		this.addSlotToContainer(new SlotExperience(tileEntityEvolvedFurnace, 9, 143, 53));
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.timeBurning != this.tileFurnace.getField(2))
			{
				icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
			}

			if (this.furnaceBurnTime != this.tileFurnace.getField(0))
			{
				icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
			}

			if (this.currentItemBurnTime != this.tileFurnace.getField(1))
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
			}

			if (this.itemCookTime != this.tileFurnace.getField(3))
			{
				icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
			}

			if (this.experience != this.tileFurnace.getField(4))
			{
				icrafting.sendProgressBarUpdate(this, 4, this.tileFurnace.getField(4));
			}
		}

		this.timeBurning = this.tileFurnace.getField(2);
		this.furnaceBurnTime = this.tileFurnace.getField(0);
		this.currentItemBurnTime = this.tileFurnace.getField(1);
		this.itemCookTime = this.tileFurnace.getField(3);
		this.experience = this.tileFurnace.getField(4);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tileFurnace.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileFurnace.isUseableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index >= 4 && index <= 9)
			{
				if (!this.mergeItemStack(itemstack1, 10, 46, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 1 && index != 0 && index != 2 && index != 3)
			{
				if (FurnaceRecipeHandler.getSmeltingResult(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, 0, 2, true))
					{
						return null;
					}
				}
				else if (TileEntityFurnace.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 2, 4, true))
					{
						return null;
					}
				}
				else if (index >= 10 && index < 46)
				{
					if (itemstack.getItem() instanceof IExperienceStorage)
					{
						if (!this.mergeItemStack(itemstack1, 8, 9, false))
						{
							return null;
						}
					}
				}
				else if (index >= 10 && index < 37)
				{
					if (!this.mergeItemStack(itemstack1, 37, 46, false))
					{
						return null;
					}
				}
				else if (index >= 37 && index < 46 && !this.mergeItemStack(itemstack1, 10, 37, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 10, 46, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}

}
