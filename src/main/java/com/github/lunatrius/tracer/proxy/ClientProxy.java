package com.github.lunatrius.tracer.proxy;

import com.github.lunatrius.tracer.client.renderer.TraceRenderer;
import com.github.lunatrius.tracer.command.client.CommandTracer;
import com.github.lunatrius.tracer.handler.ConfigurationHandler;
import com.github.lunatrius.tracer.handler.TraceHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(ConfigurationHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(TraceHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(TraceRenderer.INSTANCE);
        ClientCommandHandler.instance.registerCommand(new CommandTracer());
    }

    @Override
    public void setConfigEntryClassSlider(final Property... properties) {
        for (final Property property : properties) {
            property.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);
        }
    }
}
