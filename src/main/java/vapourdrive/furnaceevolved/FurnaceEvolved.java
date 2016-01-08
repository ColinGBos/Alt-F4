package vapourdrive.furnaceevolved;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vapourdrive.furnaceevolved.proxies.CommonProxy;

@Mod(modid = Reference.ModID, version = Reference.Version, name = Reference.Name)
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
