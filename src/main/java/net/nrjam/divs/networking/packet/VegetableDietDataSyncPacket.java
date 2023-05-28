package net.nrjam.divs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.nrjam.divs.client.ClientDietData;

import java.util.function.Supplier;

public class VegetableDietDataSyncPacket {
    private final int vegetableNeed;

    public VegetableDietDataSyncPacket(int vegetableNeed) {
        this.vegetableNeed = vegetableNeed;
    }

    public VegetableDietDataSyncPacket(FriendlyByteBuf buf) {
        this.vegetableNeed = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(vegetableNeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDietData.setPlayerVegetableNeed(vegetableNeed);
        });
        return true;
    }
}
