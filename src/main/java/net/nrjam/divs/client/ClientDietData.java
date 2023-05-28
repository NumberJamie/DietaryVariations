package net.nrjam.divs.client;

public class ClientDietData {
    private static int playerFruitNeed;

    public static void set(int fruitNeed) {
        ClientDietData.playerFruitNeed = fruitNeed;
    }

    public static int getPlayerFruitNeed() {
        return playerFruitNeed;
    }
}
