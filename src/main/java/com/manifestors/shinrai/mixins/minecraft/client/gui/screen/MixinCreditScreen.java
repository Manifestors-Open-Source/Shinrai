package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreditsScreen.class)
public class MixinCreditScreen {
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;",ordinal = 0))
    private static Identifier creditsOfManifestor(String path){

        return Identifier.of("shinrai","texts/credits.json");
    }
}
