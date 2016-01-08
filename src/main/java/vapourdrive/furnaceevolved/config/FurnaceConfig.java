package vapourdrive.furnaceevolved.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class FurnaceConfig
{
	public static Configuration config;
	public static void preInit(File file)
	{
		config = new Configuration(file);
		config.load();
		
		config.save();
		
	}

}
