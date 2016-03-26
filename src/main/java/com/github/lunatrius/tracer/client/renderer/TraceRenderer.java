package com.github.lunatrius.tracer.client.renderer;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.tracer.handler.TraceHandler;
import com.github.lunatrius.tracer.trace.TickingVec3;
import com.github.lunatrius.tracer.trace.Trace;
import com.github.lunatrius.tracer.trace.registry.TraceRenderInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TraceRenderer {
    public static final TraceRenderer INSTANCE = new TraceRenderer();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private Vector3f playerPosition = new Vector3f();


    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        final List<Trace> traces = TraceHandler.INSTANCE.getTraces();
        if (traces.size() <= 0) {
            return;
        }

        final EntityPlayerSP player = this.minecraft.thePlayer;
        if (player == null) {
            return;
        }

        this.playerPosition.x = (float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks());
        this.playerPosition.y = (float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks());
        this.playerPosition.z = (float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks());

        this.minecraft.mcProfiler.startSection("tracer");
        render(traces);
        this.minecraft.mcProfiler.endSection();
    }

    private void render(final List<Trace> traces) {
        GlStateManager.pushMatrix();

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.translate(-this.playerPosition.x, -this.playerPosition.y, -this.playerPosition.z);

        final Tessellator tessellator = Tessellator.getInstance();
        final VertexBuffer buffer = tessellator.getBuffer();

        for (final Trace trace : traces) {
            final TraceRenderInformation renderInfo = trace.getRenderInformation();
            final int r = renderInfo.getRed();
            final int g = renderInfo.getGreen();
            final int b = renderInfo.getBlue();
            final int a = renderInfo.getAlpha();
            final float thickness = renderInfo.getThickness();
            final double offsetY = renderInfo.getOffsetY();

            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(thickness);

            buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);

            for (final TickingVec3 pos : trace.getPositions()) {
                buffer.pos(pos.xCoord, pos.yCoord + offsetY, pos.zCoord).color(r, g, b, a).endVertex();
            }

            tessellator.draw();
        }

        GL11.glLineWidth(1.0f);

        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();

        GlStateManager.popMatrix();
    }
}
