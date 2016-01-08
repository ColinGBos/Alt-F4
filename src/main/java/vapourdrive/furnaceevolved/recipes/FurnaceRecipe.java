package vapourdrive.furnaceevolved.recipes;

import java.util.ArrayList;

import vapourdrive.furnaceevolved.utils.RandomUtils;
import net.minecraft.item.ItemStack;

public class FurnaceRecipe
{
	private final String identifier;
	private final ItemStack input;
	private final boolean inputIgnoresMeta;
	private final ItemStack output;
	private final float experience;
	private final int cookTime;
	
	public FurnaceRecipe(String Identifier, ItemStack Input, boolean inputIgnoresMeta, ItemStack Output, float Experience, int BurnTime)
	{
		this.identifier = Identifier;
		this.input = Input;
		this.output = Output;
		this.experience = Experience;
		this.cookTime = BurnTime;
		this.inputIgnoresMeta = inputIgnoresMeta;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public ItemStack getInputStack()
	{
		return input;
	}
	
	public ArrayList<ItemStack> getInputStackList()
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		if(getIgnoresMeta())
		{
			list = (ArrayList<ItemStack>) RandomUtils.getSubtypes(input.getItem());
			return list;
		}
		else
		{
			list.add(input);
			return list;
		}
	}
	
	public ItemStack getOutputStack()
	{
		return output;
	}
	
	public float getExperience()
	{
		return experience;
	}
	
	public int getCookTime()
	{
		return cookTime;
	}
	
	public boolean getIgnoresMeta()
	{
		return inputIgnoresMeta;
	}

}
