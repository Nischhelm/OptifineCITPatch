package optifinecitpatch;

import fermiumbooter.FermiumRegistryAPI;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.lang.reflect.Method;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class OptiFineCITPatchPlugin implements IFMLLoadingPlugin {

	public OptiFineCITPatchPlugin() {
		FermiumRegistryAPI.enqueueMixin(false, "mixins.optifinecitpatch.vanilla.json");
		FermiumRegistryAPI.enqueueMixin(true, "mixins.optifinecitpatch.json", OptiFineCITPatchPlugin::isOptiFineLoaded);
	}

	public static boolean isOptiFineLoaded(){
		try {
			Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
			method.setAccessible(true);
			boolean isPresent = method.invoke(Launch.classLoader, "optifine.OptiFineClassTransformer") != null;
			if(isPresent) OptiFineCITPatch.LOGGER.info("OptiFine CIT Patch: The following PACKAGE_CLASSLOADER_EXCLUSION error is fine, it actually shows that it works.");
			return isPresent;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) { }

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}