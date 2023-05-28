package net.nrjam.divs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.nrjam.divs.client.ClientDietData;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FruitDietDataSyncPacket {
    private final int fruitNeed;

    public FruitDietDataSyncPacket(int fruitNeed) {
        this.fruitNeed = fruitNeed;
    }

    public FruitDietDataSyncPacket(FriendlyByteBuf buf) {
        this.fruitNeed = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(fruitNeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDietData.setPlayerFruitNeed(fruitNeed);
        });
        return true;
    }
}
