package vapourdrive.furnaceevolved;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vapourdrive.furnaceevolved.proxies.CommonProxy;

@Mod(modid = Reference.ModID, version = Reference.Version, name = Reference.Name, dependencies = "before:JustEnoughItems;after:MineTweaker")
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
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.Init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverStarted(FMLServerAboutToStartEvent event)
	{
		proxy.serverStarted(event);
	}
}
