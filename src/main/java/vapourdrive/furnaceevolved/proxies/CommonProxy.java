package vapourdrive.furnaceevolved.proxies;

import java.io.File;

import org.apache.logging.log4j.Level;

import vapourdrive.furnaceevolved.FurnaceEvolved;
import vapourdrive.furnaceevolved.blocks.FE_Blocks;
import vapourdrive.furnaceevolved.config.ConfigHandler;
import vapourdrive.furnaceevolved.handlers.FurnaceRecipeRegistryHandler;
import vapourdrive.furnaceevolved.handlers.GUIHandler;
import vapourdrive.furnaceevolved.items.FE_Items;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	public File ConfigPath;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigPath = event.getModConfigurationDirectory();
		ConfigHandler.preInit(ConfigPath);
		NetworkRegistry.INSTANCE.registerGuiHandler(FurnaceEvolved.Instance, new GUIHandler());
		FE_Blocks.preInit();
		FE_Items.preInit();
	}
	
	public void Init(FMLInitializationEvent event)
	{
		
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiation CommonProxy postInit");
		FurnaceRecipeRegistryHandler.initialWrite(ConfigPath);
		FurnaceRecipeRegistryHandler.postInit(ConfigPath);
	}

}
