package com.fabbe50.corgimod.client.model;
// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public abstract class AbstractCorgiModel<T extends Corgi> extends ColorableAgeableListModel<T> {
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart rb_leg;
	private final ModelPart rf_leg;
	private final ModelPart lb_leg;
	private final ModelPart lf_leg;

	public AbstractCorgiModel(ModelPart root) {
		this.body = root.getChild("body");
		this.tail = root.getChild("tail");
		this.head = root.getChild("head");
		this.rb_leg = root.getChild("rb_leg");
		this.rf_leg = root.getChild("rf_leg");
		this.lb_leg = root.getChild("lb_leg");
		this.lf_leg = root.getChild("lf_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 10).addBox(-3.0F, -7.9F, -8.0F, 6.0F, 4.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(83, 10).addBox(-1.0F, -1.9F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.0F, -5.9F, -3.1667F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(27, 3).addBox(-3.0F, -8.9F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 3).addBox(1.0F, -8.9F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.918F, -2.9028F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.232F, -3.2639F));

		PartDefinition bottom_mouth_r1 = mouth.addOrReplaceChild("bottom_mouth_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.1F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.018F, 0.0972F, 0.3927F, 0.0F, 0.0F));

		PartDefinition rb_leg = partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(72, 33).addBox(-0.9F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));

		PartDefinition rf_leg = partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-0.9F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));

		PartDefinition lb_leg = partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(61, 33).addBox(-1.1F, 0.0F, -1.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));

		PartDefinition lf_leg = partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(47, 33).addBox(-1.1F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
		if (entity.isAngry()) {
			this.tail.yRot = 0.0F;
		} else {
			this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		}
		this.head.zRot = entity.getHeadRollAngle(ageInTicks) + entity.getBodyRollAngle(ageInTicks, 0.0F);
		this.body.zRot = entity.getBodyRollAngle(ageInTicks, -0.16F);
		this.tail.zRot = entity.getBodyRollAngle(ageInTicks, -0.2F);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInSittingPose()) {
			this.body.setPos(0F, 26F, 0F);
			this.tail.setPos(0F, 19F, 7F);
			this.tail.xRot = -0.5f;
			this.rf_leg.setPos(-2F, 23F, -7F);
			this.rf_leg.xRot = ((float) Math.PI * 3F / 2F);
			this.rf_leg.yRot = 0.5f;
			this.lf_leg.setPos(2F, 23F, -7F);
			this.lf_leg.xRot = ((float) Math.PI * 3F / 2F);
			this.lf_leg.yRot = -0.5f;
			this.rb_leg.yRot = -2.5f;
			this.rb_leg.xRot = ((float) Math.PI * -3F / 2F);
			this.rb_leg.setPos(-2F, 23F, 7F);
			this.lb_leg.yRot = 2.5f;
			this.lb_leg.xRot = ((float) Math.PI * -3F / 2F);
			this.lb_leg.setPos(2F, 23F, 7F);
			if (entity.isBaby()) {
				this.head.xScale = 0.75F;
				this.head.yScale = 0.75F;
				this.head.zScale = 0.75F;
				this.head.setPos(0F, 18F, -5.75F);
			} else {
				this.head.xScale = 1F;
				this.head.yScale = 1F;
				this.head.zScale = 1F;
				this.head.setPos(0F, 21F, -6.75F);
			}

//			this.head..setRotation(0F, 18F, -7.5F);
//			this.EarRight.setRotation(0F, 18F, -7.5F);
//			this.MouthUpper.setRotation(0F, 18F, -7.5F);
//			this.MouthLower.setRotation(0F, 18F, -7.5F);
		} else {
			this.body.setPos(0F, 24F, 0F);
			this.tail.setPos(0F, 17F, 7F);
			this.tail.xRot = 0;
			this.rf_leg.setPos(-2F, 20F, -7F);
			this.lf_leg.setPos(2F, 20F, -7F);
			this.rb_leg.setPos(-2F, 20F, 7F);
			this.lb_leg.setPos(2F, 20F, 7F);
			this.rf_leg.xRot = (float)Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.rf_leg.yRot = 0;
			this.lf_leg.xRot = (float)Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
			this.lf_leg.yRot = 0;
			this.rb_leg.xRot = (float)Math.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
			this.rb_leg.yRot = 0;
			this.lb_leg.xRot = (float)Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.lb_leg.yRot = 0;
			if (entity.isBaby()) {
				this.head.xScale = 0.75F;
				this.head.yScale = 0.75F;
				this.head.zScale = 0.75F;
				this.head.setPos(0F, 16.5F, -5.75F);
			} else {
				this.head.xScale = 1F;
				this.head.yScale = 1F;
				this.head.zScale = 1F;
				this.head.setPos(0F, 18F, -6.75F);
			}

//			this.EarLeft.setRotation(0F, 15F, -9.5F);
//			this.EarRight.setRotation(0F, 15F, -9.5F);
//			this.MouthUpper.setRotation(0F, 15F, -9.5F);
//			this.MouthLower.setRotation(0F, 15F, -9.5F);
		}

		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.tail.xRot = ageInTicks / 10;
	}

	@Override
	protected @NotNull Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected @NotNull Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rf_leg, this.lf_leg, this.rb_leg, this.lb_leg, this.tail);
	}
}