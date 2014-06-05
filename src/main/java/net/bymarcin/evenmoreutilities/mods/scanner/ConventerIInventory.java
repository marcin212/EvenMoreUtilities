package net.bymarcin.evenmoreutilities.mods.scanner;

import java.util.Map;

import li.cil.oc.api.driver.Converter;
import net.minecraft.inventory.IInventory;

public class ConventerIInventory implements Converter{

	@Override
	public void convert(Object value, Map<Object, Object> output) {
		if(value instanceof IInventory){
			IInventory inv = (IInventory) value;
			for(int i=0;i<inv.getSizeInventory();i++){
				output.put(i,inv.getStackInSlot(i));
			}
		}
		
	}

}
