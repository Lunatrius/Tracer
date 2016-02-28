package com.github.lunatrius.tracer.proxy;

import com.github.lunatrius.tracer.handler.ConfigurationHandler;
import com.github.lunatrius.tracer.reference.Reference;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class CommonProxy {
    public void preInit(final FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        FMLInterModComms.sendMessage("LunatriusCore", "checkUpdate", Reference.FORGE);
    }

    public void init(final FMLInitializationEvent event) {

    }

    public void postInit(final FMLPostInitializationEvent event) {

    }

    public void serverStarting(final FMLServerStartingEvent event) {

    }

    public void serverStopping(final FMLServerStoppingEvent event) {

    }

    public void setConfigEntryClassSlider(final Property... properties) {

    }
}
