package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
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

    @Shadow public abstract void setPaddleState(boolean bl, boolean bl2);

    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "controlBoat", cancellable = true)
    private void injectControlBoat(CallbackInfo ci) {
        if (this.isVehicle() && this.hasPassenger(entity -> entity instanceof Corgi corgi && corgi.getVariant().is(CorgiVariants.PIRATE) && corgi.isTame())) {
            float s = ModConfig.<Float>getValue("pirateCorgiBoatSpeedModifier").getValue();
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

            this.setDeltaMovement(this.getDeltaMovement().add((Mth.sin(-this.getYRot() * ((float)Math.PI / 180f)) * f * s), 0.0D, Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f * s));
            this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
            ci.cancel();
        }
    }
}
