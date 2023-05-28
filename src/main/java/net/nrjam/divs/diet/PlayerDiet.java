package net.nrjam.divs.diet;

import net.minecraft.nbt.CompoundTag;

public abstract class PlayerDiet {
    public abstract int getNeed();
    public abstract String getNeedName();
    protected final int MIN_THRESHOLD = 0;
    protected final int LOWER_LIMIT = 25;
    protected final int LOWER_THRESHOLD = 40;
    protected final int UPPER_THRESHOLD = 60;
    protected final int UPPER_LIMIT = 75;
    protected final int MAX_THRESHOLD = 100;

    public void addNeed(int add) {
        int need = getNeed();
        need = Math.min(need + add, MAX_THRESHOLD);
        setNeed(need);
    }

    public void subNeed(int sub) {
        int need = getNeed();
        need = Math.max(need - sub, MIN_THRESHOLD);
        setNeed(need);
    }

    public void copyFrom(PlayerDiet source) {
        setNeed(source.getNeed());
    }

    public void saveNBTData(CompoundTag nbt) {
        int need = getNeed();
        String needName = getNeedName();
        nbt.putInt(needName, need);
    }

    public void loadNBTData(CompoundTag nbt) {
        String needName = getNeedName();
        nbt.getInt(needName);
    }

    protected abstract void setNeed(int need);
}
