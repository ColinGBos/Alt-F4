package vapourdrive.furnaceevolved.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import vapourdrive.furnaceevolved.FurnaceEvolved;
import cpw.mods.fml.common.registry.GameRegistry;

public class RandomUtils
{
    private static final Random RANDOM = new Random();

	
	public static ItemStack getItemStackFromString(String ModId, String ItemStackName, int meta, int StackSize, String NBT)
	{
		ItemStack stack = GameRegistry.makeItemStack(ModId + ":" + ItemStackName, meta, StackSize, NBT);
		if (stack == null)
		{
			FurnaceEvolved.log.log(Level.INFO, "Attempt to find: " + ModId + ", " + ItemStackName + " Failed");
		}
		return stack;

	}

	public static NBTTagCompound getNBT(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
	}

	public static ArrayList<ItemStack> getSubtypes(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (item == null)
		{
			return new ArrayList();
		}

		if (itemStack.getItemDamage() != OreDictionary.WILDCARD_VALUE)
		{
			return (ArrayList<ItemStack>) Collections.singletonList(itemStack);
		}

		return getSubtypes(item);
	}

	@Nonnull
	public static ArrayList<ItemStack> getSubtypes(Item item)
	{
		ArrayList<ItemStack> itemStacks = new ArrayList<ItemStack>();

		ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
		for (CreativeTabs itemTab : item.getCreativeTabs())
		{
			subItems.clear();
			item.getSubItems(item, itemTab, subItems);
			itemStacks.addAll(subItems);
		}

		if (itemStacks.isEmpty())
		{
			ItemStack stack = new ItemStack(item);
			itemStacks.add(stack);
		}

		return itemStacks;
	}

	public static void dropInventoryItems(World worldIn, int x, int y, int z, IInventory tileentity)
	{
		for (int i = 0; i < tileentity.getSizeInventory(); ++i)
        {
            ItemStack itemstack = tileentity.getStackInSlot(i);

            if (itemstack != null)
            {
            	spawnItemStack(worldIn, x, y, z, itemstack);
            }
        }
	}
	
	private static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
    {
        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (stack.stackSize > 0)
        {
            int i = RANDOM.nextInt(21) + 10;

            if (i > stack.stackSize)
            {
                i = stack.stackSize;
            }

            stack.stackSize -= i;
            EntityItem entityitem = new EntityItem(worldIn, x + (double)f, y + (double)f1, z + (double)f2, new ItemStack(stack.getItem(), i, stack.getItemDamage()));

            if (stack.hasTagCompound())
            {
                entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
            }

            float f3 = 0.05F;
            entityitem.motionX = RANDOM.nextGaussian() * (double)f3;
            entityitem.motionY = RANDOM.nextGaussian() * (double)f3 + 0.20000000298023224D;
            entityitem.motionZ = RANDOM.nextGaussian() * (double)f3;
            worldIn.spawnEntityInWorld(entityitem);
        }
    }
}
