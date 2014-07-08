package net.bymarcin.evenmoreutilities.mods.energysiphon;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import cpw.mods.fml.common.registry.GameRegistry;

public class EnergySiphonMod implements IMod{
	public static int EnergySiphonID;
	
	@Override
	public void init() {
		EnergySiphonID = EvenMoreUtilities.instance.config.getBlock("BlocksId","EnergiSiphonID", 2050).getInt();
        GameRegistry.registerBlock(BlockEnergySiphon.instance,StaticValues.modId+":energySiphon");
        GameRegistry.registerTileEntity(TileEntitySiphon.class, StaticValues.modId+".energySiphon");
	}

	@Override
	public void postInit() {
		
	}

}
