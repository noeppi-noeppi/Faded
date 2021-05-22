package io.github.noeppi_noeppi.mods.faded.mods.turtlecraft;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TurtleRender extends MobRenderer<Turtle, TurtleModel> {

    private static final ResourceLocation REINDEER_TEXTURE = new ResourceLocation(Faded.getInstance().modid, "textures/entity/turtlecraft/turtle.png");

    public TurtleRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TurtleModel(), 0.7F);
    }

    @Override
    protected void preRenderCallback(@Nonnull Turtle entity, @Nonnull MatrixStack matrixStack, float partialTickTime) {
        super.preRenderCallback(entity, matrixStack, partialTickTime);
        matrixStack.translate(0.9, -3, -0.1);
        matrixStack.scale(2, -2, 2);
    }

    @Override
    @Nonnull
    public ResourceLocation getEntityTexture(@Nonnull Turtle entity) {
        return REINDEER_TEXTURE;
    }
}