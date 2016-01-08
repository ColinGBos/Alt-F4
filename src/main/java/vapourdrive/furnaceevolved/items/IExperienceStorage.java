package vapourdrive.furnaceevolved.items;

import net.minecraft.item.ItemStack;

public interface IExperienceStorage
{
	float getMaxExperienceStored(ItemStack stack);
	float getCurrentExperienceStored(ItemStack stack);
	float extractExperience(ItemStack stack, float maxExtract, boolean simulate);
	float receiveExperience(ItemStack stack, float maxReceive, boolean simulate);
}
