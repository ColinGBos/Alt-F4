package vapourdrive.furnaceevolved.proxies;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import vapourdrive.furnaceevolved.blocks.FE_Blocks;
import vapourdrive.furnaceevolved.items.FE_Items;

public class ClientProxy extends CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		FE_Blocks.preInitModel();
		FE_Items.clientPreInit();
	}
	
	public void Init(FMLInitializationEvent event)
	{
		super.Init(event);
	}
}
