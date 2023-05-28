package net.nrjam.divs.diet.categories;

import net.nrjam.divs.diet.PlayerDiet;

public class VegetableDiet extends PlayerDiet {
    private int vegetableNeed = 50;

    @Override
    public int getNeed(){
        return vegetableNeed;
    }

    @Override
    public String getNeedName() {
        return "vegetableDiet";
    }

    @Override
    protected void setNeed(int need) {
        this.vegetableNeed = need;
    }
}