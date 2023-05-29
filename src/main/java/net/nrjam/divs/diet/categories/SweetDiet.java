package net.nrjam.divs.diet.categories;

import net.nrjam.divs.diet.PlayerDiet;

public class SweetDiet extends PlayerDiet {
    private int sweetNeed = 50;

    @Override
    public int getNeed(){
        return sweetNeed;
    }

    @Override
    public String getNeedName() {
        return "sweetNeed";
    }

    @Override
    protected void setNeed(int need) {
        this.sweetNeed = need;
    }
}