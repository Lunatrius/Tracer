package com.github.lunatrius.tracer.trace;

import net.minecraft.util.math.Vec3d;

public class TickingVec3 extends Vec3d {
    private int ttl;

    public TickingVec3(final Vec3d vec, final int ttl) {
        this(vec.x, vec.y, vec.z, ttl);
    }

    public TickingVec3(final double x, final double y, final double z, final int ttl) {
        super(x, y, z);
        this.ttl = ttl;
    }

    public void update() {
        this.ttl--;
    }

    public boolean isDead() {
        return this.ttl < 0;
    }
}
