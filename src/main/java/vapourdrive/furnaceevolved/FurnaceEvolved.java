package vapourdrive.furnaceevolved;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vapourdrive.furnaceevolved.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.ModID, version = Reference.Version, name = Reference.Name, dependencies = "after:Thaumcraft;after:GardenContainers;after:ThermalFoundation;after:ThermalExpansion;after:RedstoneArsenal;after:GardenTrees;after:GardenStuff;after:GardenCore")
public class FurnaceEvolved
{
	@Instance(Reference.ModID)
	public static FurnaceEvolved Instance;

	@SidedProxy(clientSide = "vapourdrive.furnaceevolved.proxies.ClientProxy", serverSide = "vapourdrive.furnaceevolved.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static final Logger log = LogManager.getLogger(Reference.ModID);
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiation preInit");
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiation Init");
		proxy.Init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiation postInit");
		proxy.postInit(event);
	}
}
