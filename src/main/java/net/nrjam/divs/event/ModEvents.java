package net.nrjam.divs.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.nrjam.divs.DietaryVariations;
import net.nrjam.divs.diet.PlayerDiet;
import net.nrjam.divs.diet.PlayerDietProvider;

import java.util.List;

@Mod.EventBusSubscriber(modid = DietaryVariations.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerDietProvider.PLAYER_DIET_NEED).isPresent()) {
                event.addCapability(new ResourceLocation(DietaryVariations.MOD_ID, "properties"), new PlayerDietProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerDietProvider.PLAYER_DIET_NEED).ifPresent(oldProvider -> {
                event.getEntity().getCapability(PlayerDietProvider.PLAYER_DIET_NEED).ifPresent(newProvider -> {
                    List<PlayerDiet> oldCategories = oldProvider.getDietCategories();
                    List<PlayerDiet> newCategories = newProvider.getDietCategories();
                    for (int i = 0; i < oldCategories.size() && i < newCategories.size(); i++) {
                        PlayerDiet oldCategory = oldCategories.get(i);
                        PlayerDiet newCategory = newCategories.get(i);
                        newCategory.copyFrom(oldCategory);
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerDietProvider.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            event.player.getCapability(PlayerDietProvider.PLAYER_DIET_NEED).ifPresent(provider -> {
                List<PlayerDiet> dietCategories = provider.getDietCategories();
                for (PlayerDiet category : dietCategories) {
                    if (category.getNeed() > 0 && event.player.getRandom().nextFloat() < 0.005f) {
                        category.subNeed(1);
                        event.player.sendSystemMessage(Component.literal("Subtracted " + category.getNeedName() + " from: " +  category.getNeed()));
                    }
                }
            });
        }
    }
}
