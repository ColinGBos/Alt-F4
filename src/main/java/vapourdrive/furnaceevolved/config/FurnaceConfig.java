package vapourdrive.furnaceevolved.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class FurnaceConfig
{
	public static Configuration config;
	public static float maxXP;
	
	public static void preInit(File file)
	{
		config = new Configuration(file);
		config.load();
		
		maxXP = config.getFloat("CrystalMaxExperience", "Settings", 200f, 1f, 1000f, "Max Experience Capacity in the experience Crystal");
		
		config.save();
		
	}

}
