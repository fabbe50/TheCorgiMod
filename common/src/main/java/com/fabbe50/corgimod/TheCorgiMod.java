package com.fabbe50.corgimod;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;

import java.util.function.Supplier;

public final class TheCorgiMod {
    public static final String MOD_ID = "thecorgimod";

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static void init() {
        // Write common init code here.
    }
}
