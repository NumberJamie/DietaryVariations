package net.nrjam.divs.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.nrjam.divs.DietaryVariations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, DietaryVariations.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.Items.BAD_FOOD)
                .add(Items.GLISTERING_MELON_SLICE)
                .add(Items.CHORUS_FRUIT)
                .add(Items.POISONOUS_POTATO);
        this.tag(ModTags.Items.OKAY_FOOD)
                .add(Items.MELON_SLICE)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
                .add(Items.CARROT)
                .add(Items.POTATO)
                .add(Items.BEETROOT)
                .add(Items.DRIED_KELP);
        this.tag(ModTags.Items.GOOD_FOOD)
                .add(Items.APPLE)
                .add(Items.COOKIE)
                .add(Items.CAKE)
                .add(Items.BAKED_POTATO)
                .add(Items.BEETROOT_SOUP);
        this.tag(ModTags.Items.BEST_FOOD)
                .add(Items.MUSHROOM_STEW)
                .add(Items.PUMPKIN_PIE)
                .add(Items.HONEY_BOTTLE);
        this.tag(ModTags.Items.PERFECT_FOOD)
                .add(Items.GOLDEN_CARROT)
                .add(Items.GOLDEN_APPLE)
                .add(Items.ENCHANTED_GOLDEN_APPLE);

        this.tag(ModTags.Items.FRUITS)
                .add(Items.APPLE)
                .add(Items.GOLDEN_APPLE)
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .add(Items.MELON_SLICE)
                .add(Items.GLISTERING_MELON_SLICE)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
                .add(Items.CHORUS_FRUIT);
        this.tag(ModTags.Items.VEGETABLES)
                .add(Items.CARROT)
                .add(Items.GOLDEN_CARROT)
                .add(Items.POTATO)
                .add(Items.BAKED_POTATO)
                .add(Items.POISONOUS_POTATO)
                .add(Items.BEETROOT)
                .add(Items.DRIED_KELP)
                .add(Items.PUMPKIN_PIE)
                .add(Items.BEETROOT_SOUP)
                .add(Items.MUSHROOM_STEW);
        this.tag(ModTags.Items.SWEETS)
                .add(Items.SWEET_BERRIES)
                .add(Items.COOKIE)
                .add(Items.CAKE)
                .add(Items.PUMPKIN_PIE)
                .add(Items.HONEY_BOTTLE);
    }
}