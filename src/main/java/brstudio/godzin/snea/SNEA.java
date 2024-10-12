package brstudio.godzin.snea;

import brstudio.godzin.snea.proxy.CommonProxy;
import brstudio.godzin.snea.util.handlers.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import brstudio.godzin.snea.util.References;

@Mod(modid = References.MODID, name = References.NAME, version = "0.0.1")
public class SNEA {

    @Mod.Instance
    public static SNEA instance;

    @SidedProxy(clientSide = References.CLIENT, serverSide = References.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){
    }

}
