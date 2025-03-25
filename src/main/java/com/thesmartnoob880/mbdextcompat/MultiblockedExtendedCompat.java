package com.thesmartnoob880.mbdextcompat;

import com.lowdragmc.multiblocked.api.registry.MbdCapabilities;
import com.mojang.logging.LogUtils;
import com.thesmartnoob880.mbdextcompat.arsnouveau.capability.SourceMultiblockCapability;
import com.thesmartnoob880.mbdextcompat.elementalcraft.capability.ElementMultiblockCapability;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(MultiblockedExtendedCompat.MODID)
public class MultiblockedExtendedCompat
{
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "mbdextcompat";

    public static final boolean isANLoaded = ModList.get().isLoaded("ars_nouveau");

    public static final boolean isECLoaded = ModList.get().isLoaded("elementalcraft");
    public MultiblockedExtendedCompat()
    {
        //Check for if mods to integrate are present
        //Ars Nouveau
        if (isANLoaded)
            MbdCapabilities.registerCapability(SourceMultiblockCapability.CAP);
        //Elementalcraft
        if (isECLoaded)
            MbdCapabilities.registerCapability(ElementMultiblockCapability.CAP);
    }

    public static RuntimeException throwMissingRequiredModException(String modid) {
       return new RuntimeException("Cannot invoke features that require Mod '"+ modid +"' without it being present!");
    }
}

