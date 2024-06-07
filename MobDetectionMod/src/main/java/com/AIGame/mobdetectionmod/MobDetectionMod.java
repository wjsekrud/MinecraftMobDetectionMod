package com.AIGame.mobdetectionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mobdetectionmod")
public class MobDetectionMod
{
    public static final String MOD_ID = "mobdetectionmod";
    
    public MobDetectionMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RendererCaptureHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        
    }

    public static void handleDetectionResponse(String response) {

        StringBuilder message = new StringBuilder(response);
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(message.toString()), Minecraft.getInstance().player.getUUID());
        }
    }

}
