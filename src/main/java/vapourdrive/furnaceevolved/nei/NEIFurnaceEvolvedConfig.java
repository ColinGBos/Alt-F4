package vapourdrive.furnaceevolved.nei;

import org.apache.logging.log4j.Level;

import vapourdrive.furnaceevolved.FurnaceEvolved;
import vapourdrive.furnaceevolved.Reference;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIFurnaceEvolvedConfig implements IConfigureNEI
{

	@Override
	public String getName()
	{
		return Reference.Name;
	}

	@Override
	public String getVersion()
	{
		return Reference.Version;
	}

	@Override
	public void loadConfig()
	{
		API.registerRecipeHandler(new NEIFurnaceRecipeHandler());
		API.registerUsageHandler(new NEIFurnaceRecipeHandler());
		FurnaceEvolved.log.log(Level.INFO, "Loading NEI Integration");
	}

}
