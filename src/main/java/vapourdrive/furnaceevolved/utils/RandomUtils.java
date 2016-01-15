package vapourdrive.furnaceevolved.utils;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.Nonnull;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import vapourdrive.furnaceevolved.FurnaceEvolved;

public class RandomUtils
{
	public static ItemStack getItemStackFromString(String ModId, String ItemStackName, int meta, int StackSize, String NBT)
	{
		ItemStack stack = GameRegistry.makeItemStack(ModId + ":" + ItemStackName, meta, StackSize, NBT);
		if (stack == null)
		{
			FurnaceEvolved.log.log(Level.INFO, "Attempt to find: " + ModId + ", " + ItemStackName + " Failed");
		}
		stack.stackSize = StackSize;

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

		if (item.getMetadata(itemStack) != OreDictionary.WILDCARD_VALUE)
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
}
