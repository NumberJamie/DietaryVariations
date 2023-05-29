package net.nrjam.divs.datagen.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.nrjam.divs.DietaryVariations;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> FRUITS = tag("fruits");
        public static final TagKey<Item> VEGETABLES = tag("vegetables");
        public static final TagKey<Item> SWEETS = tag("sweets");

        public static final TagKey<Item> BAD_FOOD = tag("bad_food");
        public static final TagKey<Item> OKAY_FOOD = tag("okay_food");
        public static final TagKey<Item> GOOD_FOOD = tag("good_food");
        public static final TagKey<Item> BEST_FOOD = tag("best_food");
        public static final TagKey<Item> PERFECT_FOOD = tag("perfect_food");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(DietaryVariations.MOD_ID, name));
        }
    }
}