package com.manifestors.shinrai.mixins.minecraft.entity;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface MixinGameOptionsAccessor {
    @Accessor("gamma")
    SimpleOption<Double> getGamma();
}