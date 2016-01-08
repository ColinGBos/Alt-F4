package vapourdrive.furnaceevolved.config;

import java.io.File;

public class ConfigHandler
{
	public static File FurnaceCFG;
	public static void preInit(File configPath)
	{
		FurnaceConfig.preInit(new File(configPath + "/furnaceevolved/FurnaceEvolved.cfg"));
		
	}

}
