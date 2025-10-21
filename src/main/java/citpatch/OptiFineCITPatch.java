package citpatch;

import citpatch.mixin.optifine.CustomItemsAccessor;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.registries.GameData;
import net.optifine.CustomItemProperties;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Mod(
        modid = OptiFineCITPatch.MODID,
        version = OptiFineCITPatch.VERSION,
        name = OptiFineCITPatch.NAME,
        dependencies = "required-after:fermiumbooter@[1.3.0,)",
        acceptableRemoteVersions = "*"
)
public class OptiFineCITPatch {
    public static final String MODID = "optifinecitpatch";
    public static final String VERSION = "1.0.4";
    public static final String NAME = "OptiFine CIT Patch";
    public static final Logger LOGGER = LogManager.getLogger();

    private static final Map<Integer/*new*/, Integer /*old*/> enchIdOriginalMap = new HashMap<>();

    @Mod.EventHandler
    public void onEnchantmentIdRemapping(FMLModIdMappingEvent event){
        //This only remaps the CustomItems.itemProperties, not the enchantmentProperties (because im too lazy and this took way too long)

        Map<Integer, Integer> enchIdMap;
        if(!event.isFrozen){
            //client mappings to world/server mappings

            ImmutableList<FMLModIdMappingEvent.ModRemapping> enchIdRemappings = event.getRemaps(GameData.ENCHANTMENTS);
            if(enchIdRemappings == null || enchIdRemappings.isEmpty()) return;

            enchIdMap = enchIdRemappings.stream().collect(Collectors.toMap(remap -> remap.oldId, remap -> remap.newId));
            enchIdOriginalMap.clear();
            enchIdMap.forEach((oldId, newId) -> enchIdOriginalMap.put(newId, oldId));
        } else {
            //world/server mappings back to client mappings

            enchIdMap = enchIdOriginalMap;
            if(enchIdOriginalMap.isEmpty()) return;
        }

        CustomItemProperties[][] allItemCITs = CustomItemsAccessor.getItemProperties();
        if(allItemCITs == null || allItemCITs.length == 0) return;

        for(CustomItemProperties[] itemCITs : allItemCITs){
            if(itemCITs == null || itemCITs.length == 0) continue;

            for (CustomItemProperties cit : itemCITs) {
                if (cit.enchantmentIds == null || cit.enchantmentIds.getCountRanges() == 0) continue;

                //Go through all the RangeInt's in cit.enchantmentIds.ranges, translate them to new ids and store them in the set
                Set<Integer> newEnchIds = new HashSet<>();
                for (int oldIntRangeIndex = 0; oldIntRangeIndex < cit.enchantmentIds.getCountRanges(); oldIntRangeIndex++) {
                    RangeInt oldRange = cit.enchantmentIds.getRange(oldIntRangeIndex);
                    for (int oldEnchIdIndex = oldRange.getMin(); oldEnchIdIndex <= oldRange.getMax(); oldEnchIdIndex++) {
                        newEnchIds.add(enchIdMap.getOrDefault(oldEnchIdIndex, oldEnchIdIndex));
                    }
                }

                if(newEnchIds.size() == 1) {
                    int enchId = newEnchIds.stream().findFirst().orElse(-1);
                    if(enchId != -1)
                        cit.enchantmentIds = new RangeListInt(new RangeInt(enchId, enchId));
                } else if(newEnchIds.size() > 1) {
                    //make new ranges by sorting the set and then cutting it into strips of consecutive ints which will be added as RangeInt
                    List<Integer> sortedNewIds = newEnchIds.stream().sorted().collect(Collectors.toList());
                    RangeListInt newRangeList = new RangeListInt();
                    int currEnchIdRangeStart = -1;
                    for (int i = 0; i < sortedNewIds.size(); i++) {
                        int currEnchId = sortedNewIds.get(i);
                        if (currEnchIdRangeStart == -1) currEnchIdRangeStart = currEnchId;

                        if (i == sortedNewIds.size() - 1 || sortedNewIds.get(i + 1) != currEnchId + 1) { //at end of list or range is cut off
                            newRangeList.addRange(new RangeInt(currEnchIdRangeStart, currEnchId));
                            currEnchIdRangeStart = -1;
                        }
                    }
                    cit.enchantmentIds = newRangeList;
                }
            }
        }
    }
}