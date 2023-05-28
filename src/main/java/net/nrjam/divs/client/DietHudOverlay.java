package net.nrjam.divs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.nrjam.divs.DietaryVariations;

public class DietHudOverlay{
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

    public static final IGuiOverlay HUD_FRUIT_NEED = ((gui, poseStack, partialTick, width, height) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AVERAGE_FRUIT);
        GuiComponent.blit(poseStack, width / 2 + 9, height - 49,0,0,9,9, 9,9);
        if(ClientDietData.getPlayerFruitNeed() < LOWER_THRESHOLD) {
            RenderSystem.setShaderTexture(0, LOWER_FRUIT);
            GuiComponent.blit(poseStack, width / 2 + 9, height - 49,0,0,9,9, 9,9);
        }
        if(ClientDietData.getPlayerFruitNeed() > UPPER_THRESHOLD) {
            RenderSystem.setShaderTexture(0, UPPER_FRUIT);
            GuiComponent.blit(poseStack, width / 2 + 9, height - 49,0,0,9,9, 9,9);
        }
    });
}
