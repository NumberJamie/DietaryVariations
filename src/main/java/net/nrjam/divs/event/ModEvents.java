package net.nrjam.divs.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.nrjam.divs.DietaryVariations;
import net.nrjam.divs.datagen.tags.ModTags;
import net.nrjam.divs.diet.PlayerDiet;
import net.nrjam.divs.diet.PlayerDietProvider;
import net.nrjam.divs.networking.ModMessages;
import net.nrjam.divs.networking.packet.FruitDietDataSyncPacket;
import net.nrjam.divs.networking.packet.SweetDietDataSyncPacket;
import net.nrjam.divs.networking.packet.VegetableDietDataSyncPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DietaryVariations.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void replaceDietInfoPlayer(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player) || event.getEntity().level.isClientSide()) {
            return;
        }

        player.getCapability(PlayerDietProvider.PLAYER_DIET_NEED).ifPresent(provider -> {
            List<PlayerDiet> dietCategories = provider.getDietCategories();
            for (PlayerDiet category : dietCategories) {
                if (shouldHandleCategory(category, event)) {
                    Map<TagKey<Item>, Integer> tagToNeedMap = getCategoryTagToNeedMap(category);
                    tagToNeedMap.entrySet().stream()
                            .filter(entry -> event.getItem().is(entry.getKey()))
                            .findFirst()
                            .ifPresent(entry -> {
                                int needValue = entry.getValue();
                                category.addNeed(needValue);
                                syncCategoryNeed(category, player);
                            });
                }
            }
        });
    }

    private static boolean shouldHandleCategory(PlayerDiet category, LivingEntityUseItemEvent.Finish event) {
        return (category.getNeedName().equals("fruitNeed") && event.getItem().is(ModTags.Items.FRUITS))
                || (category.getNeedName().equals("vegetableNeed") && event.getItem().is(ModTags.Items.VEGETABLES))
                || (category.getNeedName().equals("sweetNeed") && event.getItem().is(ModTags.Items.SWEETS));
    }

    private static Map<TagKey<Item>, Integer> getCategoryTagToNeedMap(PlayerDiet category) {
        Map<TagKey<Item>, Integer> tagToNeedMap = new HashMap<>();
            tagToNeedMap.put(ModTags.Items.BAD_FOOD, 0);
            tagToNeedMap.put(ModTags.Items.OKAY_FOOD, 1);
            tagToNeedMap.put(ModTags.Items.GOOD_FOOD, 5);
            tagToNeedMap.put(ModTags.Items.BEST_FOOD, 10);
            tagToNeedMap.put(ModTags.Items.PERFECT_FOOD, 15);
        return tagToNeedMap;
    }

    private static void syncCategoryNeed(PlayerDiet category, Player player) {
        if (category.getNeedName().equals("fruitNeed")) {
            ModMessages.sendToPlayer(new FruitDietDataSyncPacket(category.getNeed()), (ServerPlayer) player);
        } else if (category.getNeedName().equals("vegetableNeed")) {
            ModMessages.sendToPlayer(new VegetableDietDataSyncPacket(category.getNeed()), (ServerPlayer) player);
        } else if (category.getNeedName().equals("sweetNeed")) {
            ModMessages.sendToPlayer(new SweetDietDataSyncPacket(category.getNeed()), (ServerPlayer) player);
        }
        // player.sendSystemMessage(Component.literal("to " + category.getNeedName() + " added: " +  category.getNeed()));
    }

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
                    if (category.getNeed() > 0 && event.player.getRandom().nextFloat() < 0.0005f) {
                        category.subNeed(1);
                        // event.player.sendSystemMessage(Component.literal("Subtracted " + category.getNeedName() + " from: " +  category.getNeed()));
                        if (category.getNeedName().equals("fruitNeed")) {
                            ModMessages.sendToPlayer(new FruitDietDataSyncPacket(category.getNeed()), ((ServerPlayer) event.player));
                        } else if (category.getNeedName().equals("vegetableNeed")) {
                            ModMessages.sendToPlayer(new VegetableDietDataSyncPacket(category.getNeed()), ((ServerPlayer) event.player));
                        } else if (category.getNeedName().equals("sweetNeed")) {
                            ModMessages.sendToPlayer(new SweetDietDataSyncPacket(category.getNeed()), ((ServerPlayer) event.player));
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerDietProvider.PLAYER_DIET_NEED).ifPresent(provider -> {
                    List<PlayerDiet> dietCategories = provider.getDietCategories();
                    for (PlayerDiet category : dietCategories) {
                        if (category.getNeedName().equals("fruitNeed")) {
                            ModMessages.sendToPlayer(new FruitDietDataSyncPacket(category.getNeed()), player);
                        } else if (category.getNeedName().equals("vegetableNeed")) {
                            ModMessages.sendToPlayer(new VegetableDietDataSyncPacket(category.getNeed()), player);
                        } else if (category.getNeedName().equals("sweetNeed")) {
                            ModMessages.sendToPlayer(new SweetDietDataSyncPacket(category.getNeed()), player);
                        }
                    }
                });
            }
        }
    }
}
