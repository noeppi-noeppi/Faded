package io.github.noeppi_noeppi.mods.faded.mods.turtlecraft;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class TurtleModel extends EntityModel<Turtle> {
    
    public ModelRenderer body;
	public ModelRenderer head;
	public ModelRenderer shell1;
	public ModelRenderer shell2;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	
	public static final int textureWidth = 64;
    public static final int textureHeight = 32;
    
    public TurtleModel() {
        
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(0f, 0f, 0f, 8, 3, 6);
        this.body.setRotationPoint(-4f, 1f, -3f);
        this.body.setTextureSize(textureWidth, textureHeight);

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(0f, 0f, 0f, 2, 2, 2);
        this.head.setRotationPoint(0f, 2f, 2f);
        this.head.setTextureSize(textureWidth, textureHeight);

        this.shell1 = new ModelRenderer(this, 0, 0);
        this.shell1.addBox(0f, 0f, 0f, 6, 1, 4);
        this.shell1.setRotationPoint(1f, 3f, 1f);
        this.shell1.setTextureSize(textureWidth, textureHeight);

        this.shell2 = new ModelRenderer(this, 0, 0);
        this.shell2.addBox(0f, -1f, 0f, 4, 1, 2);
        this.shell2.setRotationPoint(1f, 2f, 1f);
        this.shell2.setTextureSize(textureWidth, textureHeight);

        this.leg1 = new ModelRenderer(this, 0, 0);
        this.leg1.addBox(0f, -1f, 0f, 1, 1, 1);
        this.leg1.setRotationPoint(1f, 0f, 1f);
        this.leg1.setTextureSize(textureWidth, textureHeight);

        this.leg2 = new ModelRenderer(this, 0, 0);
        this.leg2.addBox(0f, -1f, 0f, 1, 1, 1);
        this.leg2.setRotationPoint(1f, 0f, 4f);
        this.leg2.setTextureSize(textureWidth, textureHeight);

        this.leg3 = new ModelRenderer(this, 0, 0);
        this.leg3.addBox(0f, -1f, 0f, 1, 1, 1);
        this.leg3.setRotationPoint(6f, 0f, 1f);
        this.leg3.setTextureSize(textureWidth, textureHeight);

        this.leg4 = new ModelRenderer(this, 0, 0);
        this.leg4.addBox(0f, -1f, 0f, 1, 1, 1);
        this.leg4.setRotationPoint(6f, 0f, 4f);
        this.leg4.setTextureSize(textureWidth, textureHeight);

        this.body.addChild(this.head);
        this.body.addChild(this.shell1);
        this.body.addChild(this.leg1);
        this.body.addChild(this.leg2);
        this.body.addChild(this.leg3);
        this.body.addChild(this.leg4);

        this.shell1.addChild(this.shell2);
    }
    
    @Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setRotationAngles(@Nonnull Turtle entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngle(this.body, 0, ((float) Math.PI / -2), 0);
		this.setRotationAngle(this.head, MathHelper.clamp(netHeadYaw, -35, 35) * ((float) Math.PI / 180f), 0, (headPitch + (entity.getAttackTarget() != null ? 90 : 75)) * ((float) Math.PI / 180f));
		this.setRotationAngle(this.leg1, 0, 0, MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.leg2, 0, 0, MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.leg3, 0, 0, MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.leg4, 0, 0, MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
