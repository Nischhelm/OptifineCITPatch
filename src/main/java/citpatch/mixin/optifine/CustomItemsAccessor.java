package citpatch.mixin.optifine;

import citpatch.OptiFineCITPatch;
import net.optifine.CustomItemProperties;
import net.optifine.CustomItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CustomItems.class)
public interface CustomItemsAccessor {
    @Accessor(value = "itemProperties", remap = false)
    static CustomItemProperties[][] getItemProperties() {
        OptiFineCITPatch.LOGGER.warn("Accessing OptiFine CustomItems.itemProperties failed!");
        return null;
    }
}