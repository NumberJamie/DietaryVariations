package net.nrjam.divs.diet;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.nrjam.divs.diet.categories.FruitDiet;
import net.nrjam.divs.diet.categories.SweetDiet;
import net.nrjam.divs.diet.categories.VegetableDiet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerDietProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>, Iterable<PlayerDiet> {
    public static Capability<PlayerDietProvider> PLAYER_DIET_NEED = CapabilityManager.get(new CapabilityToken<>() {});

    private final List<PlayerDiet> dietCategories = new ArrayList<>();
    private final LazyOptional<PlayerDietProvider> optional = LazyOptional.of(this::createDietProvider);

    private PlayerDietProvider createDietProvider() {
        if (dietCategories.isEmpty()) {
            dietCategories.add(new FruitDiet());
            dietCategories.add(new VegetableDiet());
            dietCategories.add(new SweetDiet());
        }
        return this;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DIET_NEED){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        for (PlayerDiet dietCategory : dietCategories) {
            CompoundTag dietData = new CompoundTag();
            dietCategory.saveNBTData(dietData);
            nbt.put(dietCategory.getClass().getSimpleName(), dietData);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        dietCategories.clear();
        for (String dietCategoryName : nbt.getAllKeys()) {
            CompoundTag dietData = nbt.getCompound(dietCategoryName);
            PlayerDiet dietCategory = getDietCategoryByName(dietCategoryName);
            if (dietCategory != null) {
                dietCategory.loadNBTData(dietData);
                dietCategories.add(dietCategory);
            }
        }
    }

    private PlayerDiet getDietCategoryByName(String categoryName) {
        return switch (categoryName) {
            case "fruitNeed" -> new FruitDiet();
            case "vegetableNeed" -> new VegetableDiet();
            case "sweetNeed" -> new SweetDiet();
            default -> null;
        };
    }

    public List<PlayerDiet> getDietCategories() {
        return dietCategories;
    }

    @Override
    public Iterator<PlayerDiet> iterator() {
        return dietCategories.iterator();
    }
}
