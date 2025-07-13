package optifinecitpatch.mixin.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.optifine.CustomItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CustomItems.class)
public abstract class CustomItemsMixin_OnlyFirstEnchant {
    @WrapOperation(
            method = "getEnchantmentIdLevels",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagList;tagCount()I")
    )
    private static int optifinecitpatch_onlyCheckFirstEnchant(NBTTagList instance, Operation<Integer> original, @Local(argsOnly = true) ItemStack stack){
        int actualCount = original.call(instance);
        if(stack.getItem() == Items.ENCHANTED_BOOK && actualCount > 0) return 1; //Only check first enchant on book
        return actualCount;
    }
}
