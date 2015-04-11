package com.team;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = KalStuff.MODID, version = KalStuff.VERSION)
public class KalStuff {
public static final String MODID = "kalstuff";
public static final String VERSION = "1.0";
//The instance of your mod that Forge uses. Optional.
@Mod.Instance(KalStuff.MODID)
public static KalStuff instance;
//Says where the client and server 'proxy' code is loaded.
@SidedProxy(clientSide="com.team.ClientOnlyProxy", serverSide="com.team.DedicatedServerProxy")
public static CommonProxy proxy;
@EventHandler
public void preInit(FMLPreInitializationEvent event)
{
proxy.preInit();
}
@EventHandler
public void init(FMLInitializationEvent event)
{
proxy.init();
}
@EventHandler
public void postInit(FMLPostInitializationEvent event)
{
proxy.postInit();
}
}


