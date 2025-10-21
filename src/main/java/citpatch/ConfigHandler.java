package citpatch;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
    @MixinConfig.CompatHandling(modid = "optifine", desired = true, reason = "Mod not needed without optifine, auto-disabling", warnIngame = false)
    public static boolean removeLogSpam = true;

    @Config.Comment("Will only render a CIT for the first enchant on a book with multiple enchantments.")
    @Config.Name("Only First Enchant")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.firstenchant.json", defaultValue = true)
    @MixinConfig.CompatHandling(modid = "optifine", desired = true, reason = "Mod not needed without optifine, auto-disabling", warnIngame = false)
    public static boolean onlyFirstEnchant = true;

    @Config.Comment("Fixes CITs breaking completely if an enchantment couldn't be found.")
    @Config.Name("Fix Missing Enchantment")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.fixmissing.json", defaultValue = true)
    @MixinConfig.CompatHandling(modid = "optifine", desired = true, reason = "Mod not needed without optifine, auto-disabling", warnIngame = false)
    public static boolean fixMissingEnchantment = true;

    @Config.Comment("Some ids can change when players load into worlds that have different id mappings. This will reload CITs on login to show them correctly.")
    @Config.Name("Reload CITs on Login")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(lateMixin = "mixins.optifinecitpatch.reloadonlogin.json", defaultValue = true)
    @MixinConfig.CompatHandling(modid = "optifine", desired = true, reason = "Mod not needed without optifine, auto-disabling", warnIngame = false)
    public static boolean reloadOnLogin = true;

    @Mod.EventBusSubscriber(modid = OptiFineCITPatch.MODID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(OptiFineCITPatch.MODID)) {
                ConfigManager.sync(OptiFineCITPatch.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
