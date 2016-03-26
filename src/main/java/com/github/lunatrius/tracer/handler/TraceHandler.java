package com.github.lunatrius.tracer.handler;

import com.github.lunatrius.tracer.trace.Trace;
import com.github.lunatrius.tracer.trace.registry.TraceRegistry;
import com.github.lunatrius.tracer.trace.registry.TraceRenderInformation;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TraceHandler {
    public static final TraceHandler INSTANCE = new TraceHandler();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final ArrayList<Trace> traces = new ArrayList<Trace>();

    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        // NOOP
    }

    @SubscribeEvent
    public void onWorldUnload(final WorldEvent.Unload event) {
        final EntityPlayerSP player = this.minecraft.thePlayer;
        if (player == null) {
            return;
        }

        if (player.worldObj == event.getWorld()) {
            clearTraces();
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) {
            return;
        }

        final TraceRenderInformation renderInfo = TraceRegistry.INSTANCE.get(event.getEntity());
        if (renderInfo == null) {
            return;
        }

        this.traces.add(new Trace(event.getEntity(), renderInfo));
    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (this.minecraft.thePlayer == null || this.minecraft.theWorld == null) {
            clearTraces();
            return;
        }

        if (this.minecraft.isGamePaused()) {
            return;
        }

        this.minecraft.mcProfiler.startSection("tracer");
        final Iterator<Trace> iterator = this.traces.iterator();
        while (iterator.hasNext()) {
            final Trace trace = iterator.next();
            if (trace.isDead()) {
                iterator.remove();
                continue;
            }

            trace.update();
        }
        this.minecraft.mcProfiler.endSection();
    }

    public int clearTraces() {
        final int size = this.traces.size();
        if (size > 0) {
            this.traces.clear();
        }

        return size;
    }

    public List<Trace> getTraces() {
        return ImmutableList.copyOf(this.traces);
    }
}
