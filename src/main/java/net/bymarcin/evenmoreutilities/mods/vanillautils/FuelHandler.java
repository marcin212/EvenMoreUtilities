package net.bymarcin.evenmoreutilities.mods.vanillautils;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler{

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.itemID == BlockCharcoal.instance.blockID){
			return 16000;
		}
		return 0;
	}
}
