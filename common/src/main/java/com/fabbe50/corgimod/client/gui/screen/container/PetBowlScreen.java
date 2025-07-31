package com.fabbe50.corgimod.client.gui.screen.container;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.world.inventory.PetBowlMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PetBowlScreen extends AbstractContainerScreen<PetBowlMenu> implements MenuAccess<PetBowlMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = TheCorgiMod.location("textures/gui/container/single_slot_screen.png");

    public PetBowlScreen(PetBowlMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderMenuBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_LOCATION, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
