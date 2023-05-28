package net.nrjam.divs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.nrjam.divs.client.ClientDietData;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DietDataSyncPacket {
    private final int fruitNeed;

    public DietDataSyncPacket(int fruitNeed) {
        this.fruitNeed = fruitNeed;
    }

    public DietDataSyncPacket(FriendlyByteBuf buf) {
        this.fruitNeed = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(fruitNeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDietData.set(fruitNeed);
        });
        return true;
    }
}
