package optifinecitpatch.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.optifine.config.ParserEnchantmentId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParserEnchantmentId.class)
public class ParserEnchantmentIdMixin {

    @ModifyReturnValue(method = "parse", at = @At("RETURN"), remap = false)
    private int optifinecitpatch_overrideReturnIfDefault(int original, @Local(argsOnly = true) int defValue) {
        return original == defValue ? -1 : original;
    }
}
