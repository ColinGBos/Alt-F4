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
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;

@Mod(modid = Reference.ModID, version = Reference.Version, name = Reference.Name, dependencies = "after:MineTweaker")
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
		FurnaceEvolved.log.log(Level.INFO, "Initiating preInit");
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiating Init");
		proxy.Init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiating postInit");
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverStarted(FMLServerAboutToStartEvent event)
	{
		FurnaceEvolved.log.log(Level.INFO, "Initiating serverStart");
		proxy.serverStarted(event);
	}
}
