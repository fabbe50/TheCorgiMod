package com.fabbe50.corgis;

import com.fabbe50.corgis.entities.registry.EntityRegistry;
import com.fabbe50.corgis.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Corgis {
    @Mod.Instance(Reference.MOD_ID)
    public static Corgis instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    private static IProxy proxy;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Corgis.getLogger().info("Running preInit on Common");

        EntityRegistry.init();

        Corgis.getLogger().info("Finished preInit on Common");

        proxy.preInit(event);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
