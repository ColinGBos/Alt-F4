package vapourdrive.furnaceevolved.handlers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Level;

import vapourdrive.furnaceevolved.FurnaceEvolved;
import vapourdrive.furnaceevolved.recipes.DumpedRecipe;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipe;
import vapourdrive.furnaceevolved.recipes.FurnaceRecipeHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FurnaceRecipeRegistryHandler
{
	public static ArrayList<DumpedRecipe> dumpArrayList;
	public static ArrayList<DumpedRecipe> userDumpArrayList;
	public static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

	public static void initialWrite(File configPath)
	{
		try
		{
			File jsonFurnaceReference = new File(configPath, "/furnaceevolved/FurnaceRecipesReference.json");
			File jsonFurnaceConfig = new File(configPath, "/furnaceevolved/FurnaceRecipes.json");
			jsonFurnaceReference.createNewFile();
			jsonFurnaceConfig.createNewFile();
			FurnaceEvolved.log.log(Level.INFO, "Created File");
			dumpArrayList = buildDumpRecipeArrayList(FurnaceRecipes.instance().getSmeltingList());
			String stream = gson.toJson(dumpArrayList);

			FileWriter writer = new FileWriter(jsonFurnaceReference, false);

			writer.write(stream);
			writer.close();
		}
		catch (IOException error)
		{
		}
	}

	public static void postInit(File configPath)
	{
		try
		{
			File jsonFurnaceConfig = new File(configPath, "/furnaceevolved/FurnaceRecipes.json");
			if (jsonFurnaceConfig.exists())
			{
				userDumpArrayList = gson.fromJson(new FileReader(jsonFurnaceConfig), new TypeToken<ArrayList<DumpedRecipe>>()
				{
				}.getType());
				FurnaceRecipeHandler.furnaceRecipe = buildFurnaceRecipeArrayList(userDumpArrayList, dumpArrayList);
			}
		}
		catch (IOException error)
		{
		}
	}

	public static ArrayList<FurnaceRecipe> buildFurnaceRecipeArrayList(ArrayList<DumpedRecipe> userList,
			ArrayList<DumpedRecipe> dumpArrayList)
	{
		ArrayList<String> registeredRecipes = new ArrayList<String>();
		ArrayList<FurnaceRecipe> recipeList = new ArrayList<FurnaceRecipe>();
		if (userList != null && !userList.isEmpty())
		{
			Iterator iterator = userList.iterator();
			while (iterator.hasNext())
			{
				DumpedRecipe dumpRecipe = (DumpedRecipe) iterator.next();
				if (dumpRecipe.getToRegister())
				{
					String name = dumpRecipe.getOutputStackName();
					ItemStack input = dumpRecipe.getInputStack();
					ItemStack output = dumpRecipe.getOutputStack();
					FurnaceRecipe furnaceRecipe = new FurnaceRecipe(name, input, dumpRecipe.getInputIgnoresMeta(), output,
							dumpRecipe.getExperience(), dumpRecipe.getBurnTime());
					recipeList.add(furnaceRecipe);
					registeredRecipes.add(dumpRecipe.getInputStackMod() + ":" + dumpRecipe.getInputStackName());
				}
			}
		}
		Iterator iterator2 = dumpArrayList.iterator();
		while (iterator2.hasNext())
		{
			DumpedRecipe dumpRecipe = (DumpedRecipe) iterator2.next();
			if (dumpRecipe.getToRegister())
			{
				String name = dumpRecipe.getOutputStackName();
				ItemStack input = dumpRecipe.getInputStack();
				ItemStack output = dumpRecipe.getOutputStack();
				FurnaceRecipe furnaceRecipe = new FurnaceRecipe(name, input, dumpRecipe.getInputIgnoresMeta(), output,
						dumpRecipe.getExperience(), dumpRecipe.getBurnTime());
				if (!registeredRecipes.contains(dumpRecipe.getInputStackMod() + ":" + dumpRecipe.getInputStackName()))
				{
					recipeList.add(furnaceRecipe);
				}
			}
		}

		return recipeList;
	}

	public static ArrayList<DumpedRecipe> buildDumpRecipeArrayList(Map<ItemStack, ItemStack> map)
	{
		ArrayList<DumpedRecipe> returnArray = new ArrayList<DumpedRecipe>();
		HashMap<String, DumpedRecipe> recipes = new HashMap<String, DumpedRecipe>();
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			ItemStack input = (ItemStack) entry.getKey();
			ItemStack output = (ItemStack) entry.getValue();
			if (recipes.containsKey(getMod(output) + ":" + geIdentifier(output)))
			{
				FurnaceEvolved.log.log(Level.WARN, "There are multiple furnace recipes for: " + getMod(input) + ":" + geIdentifier(input));
			}
			float experience = FurnaceRecipes.instance().getSmeltingExperience(output);
			boolean ignoreInputMeta = input.getMetadata() == 32767;
			String inputTag = null;
			String outputTag = null;
			if (input.hasTagCompound())
			{
				inputTag = input.getTagCompound().toString();
			}
			if (output.hasTagCompound())
			{
				outputTag = output.getTagCompound().toString();
			}
			DumpedRecipe recipe = new DumpedRecipe(getMod(input), geIdentifier(input), input.getMetadata(), ignoreInputMeta,
					input.stackSize, inputTag, getMod(output), geIdentifier(output), output.getMetadata(), output.stackSize, outputTag,
					experience, true);
			returnArray.add(recipe);
			recipes.put(getMod(input) + ":" + geIdentifier(input), recipe);
		}
		return returnArray;
	}

	@SuppressWarnings("deprecation")
	private static String geIdentifier(ItemStack stack)
	{
		Item item = stack.getItem();
		return GameRegistry.findUniqueIdentifierFor(item).name;
	}

	public static String getMod(ItemStack stack)
	{
		ResourceLocation fullname = GameData.getItemRegistry().getNameForObject(stack.getItem());
		String name = fullname.toString();
		return (name).substring(0, (name).indexOf(':'));
	}

}
