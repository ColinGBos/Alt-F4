package vapourdrive.furnaceevolved.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vapourdrive.furnaceevolved.Reference;
import vapourdrive.furnaceevolved.utils.RandomUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExperienceCrystal extends Item implements IExperienceStorage
{
	public static final String TAG_EXPERIENCE = "FurnaceEvolved.Experience";
	public IIcon[] icons;

	public ExperienceCrystal()
	{
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("ExperienceCrystal");
		this.setMaxStackSize(1);
		GameRegistry.registerItem(this, "experience_crystal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		icons = new IIcon[16];
		for (int i = 0; i <=15; i++)
		{
			icons[i] = register.registerIcon(Reference.ResourcePath + "ExperienceCrystal_" + i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		ExperienceCrystal crystal = (ExperienceCrystal) stack.getItem();
		int level = (int) (15 * (crystal.getCurrentExperienceStored(stack) / crystal.getMaxExperienceStored(stack)));
		if(level > 15)
		{
			level = 15;
		}
		if (level < 0)
		{
			level = 0;
		}
		return icons[level];
	}

	@Override
	public float getMaxExperienceStored(ItemStack stack)
	{
		return 200f;
	}

	@Override
	public float getCurrentExperienceStored(ItemStack stack)
	{
		return RandomUtils.getNBT(stack).getFloat(TAG_EXPERIENCE);
	}

	@Override
	public float extractExperience(ItemStack stack, float maxExtract, boolean simulate)
	{
		float experience = getCurrentExperienceStored(stack);
		float experienceExtracted = Math.min(experience, maxExtract);
		if (!simulate)
		{
			experience -= experienceExtracted;
			RandomUtils.getNBT(stack).setFloat(TAG_EXPERIENCE, experience);
		}
		return experienceExtracted;
	}

	@Override
	public float receiveExperience(ItemStack stack, float maxReceive, boolean simulate)
	{
		float experience = getCurrentExperienceStored(stack);
		float experienceRecieved = Math.min(getMaxExperienceStored(stack) - experience, maxReceive);
		if (!simulate)
		{
			experience += experienceRecieved;
			RandomUtils.getNBT(stack).setFloat(TAG_EXPERIENCE, experience);
		}
		return experienceRecieved;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		float experience = this.extractExperience(stack, getMaxExperienceStored(stack), false);
		if (experience > 0f)
		{
			world.playSoundAtEntity(player, "random.orb", (world.rand.nextFloat() * 0.2F) + 0.8F, (world.rand.nextFloat() * 0.4F) + 0.6F);
		}
		player.addExperience((int) experience);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack FullCrystal = new ItemStack(FE_Items.xpCrystal);
		this.receiveExperience(FullCrystal, this.getMaxExperienceStored(FullCrystal), false);
		ItemStack EmptyCrystal = new ItemStack(FE_Items.xpCrystal);
		RandomUtils.getNBT(EmptyCrystal).setFloat(TAG_EXPERIENCE, 0.0f);
		list.add(FullCrystal);
		list.add(EmptyCrystal);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean useExtraInformation)
	{
		list.add(EnumChatFormatting.GREEN + "" + String.format("%.2f", this.getCurrentExperienceStored(stack)) + " "
				+ StatCollector.translateToLocal("phrase.evolvedfurnace.experiencestored"));
	}

}
