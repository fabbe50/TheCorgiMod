package com.fabbe50.corgimod.world.entity.interfaces.model;

public interface ICorgiTail extends ITail {
    @Override
    default float getTailAngle() {
        return 0.62831855F;
    }
}
