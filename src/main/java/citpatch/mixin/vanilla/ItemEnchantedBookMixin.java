package citpatch.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemEnchantedBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEnchantedBook.class)
public class ItemEnchantedBookMixin {
    @ModifyReturnValue(
            method = "hasEffect",
            at = @At(value = "RETURN")
    )
    private boolean optifinecitpatch_dontRenderEnchGlint(boolean original){
        return false;
    }
}
