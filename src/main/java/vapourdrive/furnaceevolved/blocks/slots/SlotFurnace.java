package vapourdrive.furnaceevolved.blocks.slots;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class SlotFurnace extends Slot
{
	private EntityPlayer thePlayer;
	public SlotFurnace(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	{
		super(inventoryIn, slotIndex, xPosition, yPosition);
        this.thePlayer = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
	@Override
    public ItemStack decrStackSize(int amount)
    {
        return super.decrStackSize(amount);
    }

	@Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        this.onCrafting(stack);
        super.onPickupFromSlot(playerIn, stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
	@Override
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
	@Override
    protected void onCrafting(ItemStack stack)
    {
        stack.getItem().onCreated(stack, thePlayer.worldObj, thePlayer);

        FMLCommonHandler.instance().firePlayerSmeltedEvent(thePlayer, stack);

        if (stack.getItem() == Items.iron_ingot)
        {
            this.thePlayer.triggerAchievement(AchievementList.acquireIron);
        }
    }

}
