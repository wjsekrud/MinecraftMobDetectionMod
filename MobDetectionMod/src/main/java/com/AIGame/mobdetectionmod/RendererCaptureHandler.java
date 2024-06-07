package com.AIGame.mobdetectionmod;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.EffectiveSide;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




@Mod.EventBusSubscriber(modid = MobDetectionMod.MOD_ID, value = Dist.CLIENT)
public class RendererCaptureHandler {

    private static final int FRAME_INTERVAL = 120;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final HttpClient httpClient = new HttpClient();


    private static final Minecraft mc = Minecraft.getInstance();
    private static final Framebuffer framebuffer = mc.getMainRenderTarget();
    private static int tickCount = 0;


    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (EffectiveSide.get().isClient()) {
            tickCount++;
            if (tickCount >= FRAME_INTERVAL) {
                tickCount = 0;
                Minecraft.getInstance().submit(RendererCaptureHandler::captureFrame);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void captureFrame() {
        try {

            if (mc.player != null) {
                ClientPlayerEntity player = mc.player;

                Vector3d originalPos = player.position();
                float originalYaw = player.yRot;
                float originalPitch = player.xRot;

                Vector3d offset = Vector3d.directionFromRotation(0, 180F).scale(5);
                Vector3d cameraPos = mc.player.position().add(offset);
                player.setPos(cameraPos.x, cameraPos.y, cameraPos.z);
                player.yRot = mc.player.yRot + 180F;
                player.xRot = 0;

                framebuffer.bindWrite(false);
                mc.gameRenderer.render(mc.getFrameTime(), System.nanoTime(), true);

                player.setPos(originalPos.x, originalPos.y, originalPos.z);
                player.yRot = originalYaw;
                player.xRot = originalPitch;


                framebuffer.bindRead();
                NativeImage nativeImage = new NativeImage(framebuffer.width, framebuffer.height, true);
                nativeImage.downloadTexture(0, true);

                executorService.submit(() -> sendFrame(nativeImage));


            }
            else{
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void sendFrame(NativeImage nativeImage) {
        try {
            int width = nativeImage.getWidth();
            int height = nativeImage.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = nativeImage.getPixelRGBA(x, y);
                    int a = (pixel >> 24) & 0xFF;
                    int r = (pixel >> 16) & 0xFF;
                    int g = (pixel >> 8) & 0xFF;
                    int b = pixel & 0xFF;

                    int argb = (a << 24) | (b << 16) | (g << 8) | r;
                    bufferedImage.setRGB(x, height - 1 - y, argb);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            httpClient.sendImage(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            nativeImage.close();
        }
    }
}
