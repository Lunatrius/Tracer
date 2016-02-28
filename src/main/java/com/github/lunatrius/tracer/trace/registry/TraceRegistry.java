package com.github.lunatrius.tracer.trace.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class TraceRegistry {
    public static final TraceRegistry INSTANCE = new TraceRegistry();

    private Map<String, TraceRenderInformation> map = new HashMap<String, TraceRenderInformation>();

    public void clear() {
        this.map.clear();
    }

    public void register(final String entityName, final int red, final int green, final int blue, final int alpha, final double thickness, final int ttl, final double offsetY) {
        this.map.put(entityName, new TraceRenderInformation(red, green, blue, alpha, ttl, thickness, offsetY));
    }

    public TraceRenderInformation get(final Entity entity) {
        if (entity == null) {
            return null;
        }

        // prevent abuse (vanilla returns null for EntityList.getEntityString anyway)
        if (entity instanceof EntityPlayer) {
            return null;
        }

        final String entityName = EntityList.getEntityString(entity);
        return this.map.get(entityName);
    }
}
