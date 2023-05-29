package net.nrjam.divs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.nrjam.divs.client.ClientDietData;

import java.util.function.Supplier;

public class SweetDietDataSyncPacket {
    private final int sweetNeed;

    public SweetDietDataSyncPacket(int sweetNeed) {
        this.sweetNeed = sweetNeed;
    }

    public SweetDietDataSyncPacket(FriendlyByteBuf buf) {
        this.sweetNeed = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(sweetNeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDietData.setPlayerSweetNeed(sweetNeed);
        });
        return true;
    }
}
