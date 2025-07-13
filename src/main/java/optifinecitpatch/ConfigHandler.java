package optifinecitpatch;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

@Config(modid = OptiFineCITPatch.MODID)
@MixinConfig(name = OptiFineCITPatch.MODID)
@SuppressWarnings("unused")
public class ConfigHandler {
    @Config.Comment("Enchanted Books will not have Enchantment Glint if set to true.")
    @Config.Name("Remove Glint")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(earlyMixin = "mixins.optifinecitpatch.glint.json", defaultValue = false)
    public static boolean removeGlint = false;

    @Config.Comment("Will not spam the startup log with one line per registered CIT if enabled.")
    @Config.Name("Remove Log Spam")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.logspam.json", defaultValue = true)
    public static boolean removeLogSpam = true;

    @Config.Comment("Will only render a CIT for the first enchant on a book with multiple enchantments.")
    @Config.Name("Only First Enchant")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.firstenchant.json", defaultValue = true)
    public static boolean onlyFirstEnchant = true;

    @Config.Comment("Fixes CITs breaking completely if an enchantment couldn't be found.")
    @Config.Name("Fix Missing Enchantment")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.fixmissing.json", defaultValue = true)
    public static boolean fixMissingEnchantment = true;
}
