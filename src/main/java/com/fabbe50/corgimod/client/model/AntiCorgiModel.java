package com.fabbe50.corgimod.client.model;
// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.AntiCorgi;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AntiCorgiModel<T extends AntiCorgi> extends ColorableAgeableListModel<T> {
	private float lieDownAmount;
	private float lieDownAmountTail;
	private float relaxStateOneAmount;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart rb_leg;
	private final ModelPart rf_leg;
	private final ModelPart lb_leg;
	private final ModelPart lf_leg;

	public AntiCorgiModel(ModelPart root) {
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

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 10).addBox(-3.0F, -8.0F, -8.0F, 6.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(83, 10).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 7.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 10).addBox(-3.0F, -6.0F, -3.1667F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(27, 3).addBox(-3.0F, -9.0F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 3).addBox(1.0F, -9.0F, -1.1667F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.8333F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -2.018F, -2.9028F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.232F, -3.2639F));

		PartDefinition bottom_mouth_r1 = mouth.addOrReplaceChild("bottom_mouth_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.018F, 0.0972F, 0.3927F, 0.0F, 0.0F));

		PartDefinition rb_leg = partdefinition.addOrReplaceChild("rb_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 7.0F));

		PartDefinition rf_leg = partdefinition.addOrReplaceChild("rf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -7.0F));

		PartDefinition lb_leg = partdefinition.addOrReplaceChild("lb_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 7.0F));

		PartDefinition lf_leg = partdefinition.addOrReplaceChild("lf_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float z) {
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInSittingPose()) {
			this.body.setPos(0F, 25F, 0F);
			this.body.xRot = (0);
			this.tail.setPos(0F, 18F, 7F);
			this.tail.xRot = -0.5f;
			this.rf_leg.setPos(-2F, 22F, -7F);
			this.rf_leg.xRot = ((float) Math.PI * 3F / 2F);
			this.rf_leg.yRot = 0.5f;
			this.lf_leg.setPos(2F, 22F, -7F);
			this.lf_leg.xRot = ((float) Math.PI * 3F / 2F);
			this.lf_leg.yRot = -0.5f;
			this.rb_leg.yRot = -2.5f;
			this.rb_leg.xRot = ((float) Math.PI * -3F / 2F);
			this.rb_leg.setPos(-2F, 22F, 7F);
			this.lb_leg.yRot = 2.5f;
			this.lb_leg.xRot = ((float) Math.PI * -3F / 2F);
			this.lb_leg.setPos(2F, 22F, 7F);
			this.head.setPos(0F, 20F, -6.75F);
//			this.head..setRotation(0F, 18F, -7.5F);
//			this.EarRight.setRotation(0F, 18F, -7.5F);
//			this.MouthUpper.setRotation(0F, 18F, -7.5F);
//			this.MouthLower.setRotation(0F, 18F, -7.5F);
		} else {
			this.body.setPos(0F, 24F, 0F);
			this.body.xRot = (0);//(float)Math.PI / 22F);
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
			this.head.setPos(0F, 18F, -6.75F);
//			this.EarLeft.setRotation(0F, 15F, -9.5F);
//			this.EarRight.setRotation(0F, 15F, -9.5F);
//			this.MouthUpper.setRotation(0F, 15F, -9.5F);
//			this.MouthLower.setRotation(0F, 15F, -9.5F);
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rf_leg, this.lf_leg, this.rb_leg, this.lb_leg, this.tail);
	}
}