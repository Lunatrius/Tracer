package com.github.lunatrius.tracer.trace;

import net.minecraft.util.Vec3;

public class TickingVec3 extends Vec3 {
    private int ttl;

    public TickingVec3(final Vec3 vec, final int ttl) {
        this(vec.xCoord, vec.yCoord, vec.zCoord, ttl);
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
