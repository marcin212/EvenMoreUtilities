package net.bymarcin.evenmoreutilities.mods.scanner;

import li.cil.oc.api.Driver;
import li.cil.oc.api.Items;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ScannerMod implements IMod{
	public static int scannerID;
	public static int scannerRange;
	ItemStack wifi= Items.get("wlanCard").createItemStack(1);
	ItemStack accessPoint= Items.get("accessPoint").createItemStack(1);
	@Override
	public void init() {
		scannerID = EvenMoreUtilities.instance.config.getBlock("BlocksId","scanner", 3721).getInt();
		scannerRange = EvenMoreUtilities.instance.config.get("Options","scannerRange", 32).getInt(32);
		
        GameRegistry.registerBlock(BlockScanner.instance,StaticValues.modId+":scanner");
        GameRegistry.addRecipe(new ItemStack(BlockScanner.instance,1), 
                " x ","xdx"," x ", 'x', wifi, 'd', accessPoint);
        GameRegistry.registerTileEntity(TileEntityBlockScanner.class, StaticValues.modId+".scanner");
        Driver.add(new EntityConventer());
        Driver.add(new ConventerIInventory());
	}

	@Override
	public void load() {
		
	}

}
