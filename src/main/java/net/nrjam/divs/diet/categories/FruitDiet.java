package net.nrjam.divs.diet.categories;

import net.nrjam.divs.diet.PlayerDiet;

public class FruitDiet extends PlayerDiet {
    private int fruitNeed = 50;

    @Override
    public int getNeed(){
        return fruitNeed;
    }

    @Override
    public String getNeedName() {
        return "fruitNeed";
    }

    @Override
    protected void setNeed(int need) {
        this.fruitNeed = need;
    }
}