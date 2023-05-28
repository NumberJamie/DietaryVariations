package net.nrjam.divs.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nrjam.divs.DietaryVariations;
import net.nrjam.divs.client.DietHudOverlay;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = DietaryVariations.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("fruit", DietHudOverlay.HUD_FRUIT_NEED);
            event.registerAboveAll("vegetable", DietHudOverlay.HUD_VEGETABLE_NEED);
        }
    }
}
