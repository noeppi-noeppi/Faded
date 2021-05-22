package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;

public class ExplodableRender extends EntityRenderer<PrimedExplodable> {
    
    public ExplodableRender(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.5f;
   }

   @Override
   public void render(PrimedExplodable entity, float yaw, float partialTicks, MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light) {
      matrixStack.push();
      matrixStack.translate(0, 0.5, 0);
      if (entity.getFuse() - partialTicks + 1.0F < 10) {
         float baseScale = MathHelper.clamp(1 - ((entity.getFuse() - partialTicks + 1) / 10f), 0, 1);
         float scale = 1 + (baseScale * baseScale * 0.3F);
         matrixStack.scale(scale, scale, scale);
      }

      matrixStack.rotate(Vector3f.YP.rotationDegrees(-90));
      matrixStack.translate(-0.5, -0.5, 0.5);
      matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
      int overlay = (entity.getFuse() / 5 % 2 == 0) ? OverlayTexture.getPackedUV(OverlayTexture.getU(1), 10) : OverlayTexture.NO_OVERLAY;
      Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(entity.getExplodable() == null ? Blocks.TNT.getDefaultState() : entity.getExplodable(), matrixStack, buffer, light, overlay, EmptyModelData.INSTANCE);

      matrixStack.pop();
      super.render(entity, yaw, partialTicks, matrixStack, buffer, light);
   }
   
   @Override
   @Nonnull
   public ResourceLocation getEntityTexture(@Nonnull PrimedExplodable entity) {
      return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
   }
}
