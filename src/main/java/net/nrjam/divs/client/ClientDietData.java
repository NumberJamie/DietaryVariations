package net.nrjam.divs.client;

public class ClientDietData {
    private static int playerFruitNeed;
    private static int playerVegetableNeed;
    private static int playerSweetNeed;

    public static void setPlayerFruitNeed(int fruitNeed) {
        ClientDietData.playerFruitNeed = fruitNeed;
    }

    public static void setPlayerVegetableNeed(int vegetableNeed) {
        ClientDietData.playerVegetableNeed = vegetableNeed;
    }

    public static void setPlayerSweetNeed(int sweetNeed) {
        ClientDietData.playerSweetNeed = sweetNeed;
    }

    public static int getPlayerFruitNeed() {
        return playerFruitNeed;
    }

    public static int getPlayerVegetableNeed() {
        return playerVegetableNeed;
    }

    public static int getPlayerSweetNeed() {
        return playerSweetNeed;
    }
}
