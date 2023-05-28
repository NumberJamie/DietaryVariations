package net.nrjam.divs.client;

public class ClientDietData {
    private static int playerFruitNeed;
    private static int playerVegetableNeed;

    public static void setPlayerFruitNeed(int fruitNeed) {
        ClientDietData.playerFruitNeed = fruitNeed;
    }

    public static void setPlayerVegetableNeed(int vegetableNeed) {
        ClientDietData.playerVegetableNeed = vegetableNeed;
    }

    public static int getPlayerFruitNeed() {
        return playerFruitNeed;
    }

    public static int getPlayerVegetableNeed() {
        return playerVegetableNeed;
    }
}
