package xyz.eclipseisoffline.capecommand.mixin;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.PlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin {

    @Redirect(method = "createSkinLookup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/PlayerSkin;secure()Z"))
    private static boolean skinIsAlwaysSigned(PlayerSkin instance) {
        return true;
    }
}
