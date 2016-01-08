package vapourdrive.furnaceevolved.recipes;

import net.minecraft.item.ItemStack;
import vapourdrive.furnaceevolved.utils.RandomUtils;

public class DumpedRecipe
{
	private final String modNameInput;
	private final String stackNameInput;
	private final int metaDataInput;
	private final boolean ignoreInputMeta;
	private final int stackSizeInput;
	private final String nbtStringInput;
	private final String modNameOutput;
	private final String stackNameOutput;
	private final int metaDataOutput;
	private final int stackSizeOutput;
	private final String nbtStringOutput;
	private final float experience;
	private final int burnTime;
	private final boolean toRegister;

	public DumpedRecipe(String ModNameInput, String StackNameInput, int MetaDataInput, boolean IgnoreInputMeta, int StackSizeInput, String nbtStringInput,
			String ModNameOutput, String StackNameOutput, int MetaDataOutput, int StackSizeOutput, String nbtStringOutput,
			float Experience, int Burntime, boolean toRegister)
	{
		this.modNameInput = ModNameInput;
		this.stackNameInput = StackNameInput;
		this.metaDataInput = MetaDataInput;
		this.ignoreInputMeta = IgnoreInputMeta;
		this.stackSizeInput = StackSizeInput;
		this.modNameOutput = ModNameOutput;
		this.stackNameOutput = StackNameOutput;
		this.metaDataOutput = MetaDataOutput;
		this.stackSizeOutput = StackSizeOutput;
		this.experience = Experience;
		this.burnTime = Burntime;
		this.toRegister = toRegister;
		this.nbtStringInput = nbtStringInput;
		this.nbtStringOutput = nbtStringOutput;
	}

	public DumpedRecipe(String ModNameInput, String StackNameInput, int MetaDataInput, boolean IgnoreInputMeta, int StackSizeInput, String nbtStringInput,
			String ModNameOutput, String StackNameOutput, int MetaDataOutput, int StackSizeOutput, String nbtStringOutput,
			float Experience, boolean toRegister)
	{
		this.modNameInput = ModNameInput;
		this.stackNameInput = StackNameInput;
		this.metaDataInput = MetaDataInput;
		this.ignoreInputMeta = IgnoreInputMeta;
		this.stackSizeInput = StackSizeInput;
		this.modNameOutput = ModNameOutput;
		this.stackNameOutput = StackNameOutput;
		this.metaDataOutput = MetaDataOutput;
		this.stackSizeOutput = StackSizeOutput;
		this.experience = Experience;
		this.burnTime = 200;
		this.toRegister = toRegister;
		this.nbtStringInput = nbtStringInput;
		this.nbtStringOutput = nbtStringOutput;
	}

	public String getInputStackMod()
	{
		return modNameInput;
	}

	public String getInputStackName()
	{
		return stackNameInput;
	}

	public int getInputStackMeta()
	{
		return metaDataInput;
	}

	public ItemStack getInputStack()
	{
		ItemStack stack;

		if(this.ignoreInputMeta)
		{
			stack = RandomUtils.getItemStackFromString(modNameInput, stackNameInput, 0, stackSizeInput, nbtStringInput);
		}
		else
		{
			stack = RandomUtils.getItemStackFromString(modNameInput, stackNameInput, metaDataInput, stackSizeInput, nbtStringInput);
		}
		return stack;
	}

	public String getOutputStackMod()
	{
		return modNameOutput;
	}

	public String getOutputStackName()
	{
		return stackNameOutput;
	}

	public int getOutputStackMeta()
	{
		return metaDataOutput;
	}

	public ItemStack getOutputStack()
	{
		ItemStack stack = RandomUtils.getItemStackFromString(modNameOutput, stackNameOutput, metaDataOutput, stackSizeOutput, nbtStringOutput);
		return stack;
	}

	public float getExperience()
	{
		return experience;
	}

	public int getBurnTime()
	{
		return burnTime;
	}
	
	public boolean getInputIgnoresMeta()
	{
		return ignoreInputMeta;
	}
	
	public boolean getToRegister()
	{
		return toRegister;
	}
}
