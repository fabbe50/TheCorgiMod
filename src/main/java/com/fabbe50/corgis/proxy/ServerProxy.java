package com.fabbe50.corgis.proxy;

import com.fabbe50.corgis.Corgis;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Corgis.getLogger().info("Running preInit on Server");

        Corgis.getLogger().info("Finished preInit on Server");
    }
}
