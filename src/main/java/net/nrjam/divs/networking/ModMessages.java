package net.nrjam.divs.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.nrjam.divs.DietaryVariations;
import net.nrjam.divs.networking.packet.FruitDietDataSyncPacket;
import net.nrjam.divs.networking.packet.VegetableDietDataSyncPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(DietaryVariations.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FruitDietDataSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FruitDietDataSyncPacket::new)
                .encoder(FruitDietDataSyncPacket::toBytes)
                .consumerMainThread(FruitDietDataSyncPacket::handle)
                .add();

        net.messageBuilder(VegetableDietDataSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(VegetableDietDataSyncPacket::new)
                .encoder(VegetableDietDataSyncPacket::toBytes)
                .consumerMainThread(VegetableDietDataSyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}