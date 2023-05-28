package net.nrjam.divs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.nrjam.divs.DietaryVariations;

public class DietHudOverlay {
    private static final int LOWER_LIMIT = 25;
    private static final int LOWER_THRESHOLD = 40;
    private static final int UPPER_THRESHOLD = 60;
    private static final int UPPER_LIMIT = 75;

    private static final ResourceLocation AVERAGE_FRUIT = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/average_fruit.png");
    private static final ResourceLocation LOWER_FRUIT = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/lower_fruit.png");
    private static final ResourceLocation UPPER_FRUIT = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/upper_fruit.png");

    private static final ResourceLocation AVERAGE_VEGETABLE = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/average_vegetable.png");
    private static final ResourceLocation LOWER_VEGETABLE = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/lower_vegetable.png");
    private static final ResourceLocation UPPER_VEGETABLE = new ResourceLocation(DietaryVariations.MOD_ID,
            "textures/gui/upper_vegetable.png");

    static void CreateHUDOverlay(int data, ResourceLocation upper, ResourceLocation lower, ResourceLocation average, PoseStack poseStack, int pos1, int pos2) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (data < LOWER_THRESHOLD) {
            RenderSystem.setShaderTexture(0, lower);
        } else if (data > UPPER_THRESHOLD) {
            RenderSystem.setShaderTexture(0, upper);
        } else {
            RenderSystem.setShaderTexture(0, average);
        }

        GuiComponent.blit(poseStack, pos1, pos2, 0, 0, 9, 9, 9, 9);
    }

    public static final IGuiOverlay HUD_FRUIT_NEED = ((gui, poseStack, partialTick, width, height) -> {
        CreateHUDOverlay(ClientDietData.getPlayerFruitNeed(), UPPER_FRUIT, LOWER_FRUIT, AVERAGE_FRUIT, poseStack, width / 2 + 9, height - 49);
    });

    public static final IGuiOverlay HUD_VEGETABLE_NEED = ((gui, poseStack, partialTick, width, height) -> {
        CreateHUDOverlay(ClientDietData.getPlayerVegetableNeed(), UPPER_VEGETABLE, LOWER_VEGETABLE, AVERAGE_VEGETABLE, poseStack, width / 2 + 18, height - 49);
    });


}
