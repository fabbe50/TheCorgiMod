package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.PirateCorgi;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class BoatMixin extends Entity {
    @Shadow private boolean inputLeft;

    @Shadow private float deltaRotation;

    @Shadow private boolean inputRight;

    @Shadow private boolean inputUp;

    @Shadow private boolean inputDown;

    @Shadow public abstract void setPaddleState(boolean p_38340_, boolean p_38341_);

    public BoatMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(at = @At("HEAD"), method = "controlBoat", cancellable = true)
    private void injectControlBoat(CallbackInfo ci) {
        if (this.isVehicle()) {
            float f = 0.0f;
            if (this.inputLeft) {
                this.deltaRotation--;
            }

            if (this.inputRight) {
                this.deltaRotation++;
            }

            if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                f += 0.005f;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);
            if (this.inputUp) {
                f += 0.04f;
            }

            if (this.inputDown) {
                f -= 0.005f;
            }

            float s = 1f;
            if (this.hasPassenger(entity -> {
                return entity instanceof PirateCorgi && ((PirateCorgi) entity).isTame();
            })) {
                s = CorgiMod.config.corgiAbilities.pirateCorgiBoatSpeed;
            }

            this.setDeltaMovement(this.getDeltaMovement().add((Mth.sin(-this.getYRot() * ((float)Math.PI / 180f)) * f * s), 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f * s));
            this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            ci.cancel();
        }
    }
}
