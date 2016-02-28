package com.github.lunatrius.tracer.trace;

import com.github.lunatrius.tracer.trace.registry.TraceRenderInformation;
import net.minecraft.entity.Entity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Trace {
    private final WeakReference<Entity> entityWeakReference;
    private final ArrayList<TickingVec3> positions;
    private final TraceRenderInformation renderInformation;

    public Trace(final Entity entity, final TraceRenderInformation renderInformation) {
        this.entityWeakReference = new WeakReference<Entity>(entity);
        this.positions = new ArrayList<TickingVec3>();
        this.renderInformation = renderInformation;
    }

    public Entity getEntity() {
        return this.entityWeakReference.get();
    }

    public List<TickingVec3> getPositions() {
        return this.positions;
    }

    public TraceRenderInformation getRenderInformation() {
        return this.renderInformation;
    }

    public void update() {
        for (final TickingVec3 location : this.positions) {
            location.update();
        }

        final Entity entity = getEntity();
        if (entity != null) {
            this.positions.add(new TickingVec3(entity.getPositionVector(), this.renderInformation.getTTL()));
        }

        while (this.positions.size() > 0) {
            final TickingVec3 location = this.positions.get(0);
            if (!location.isDead()) {
                break;
            }

            this.positions.remove(0);
        }
    }

    public boolean isDead() {
        final Entity entity = getEntity();
        return (entity == null || entity.isDead) && this.positions.size() == 0;
    }
}
