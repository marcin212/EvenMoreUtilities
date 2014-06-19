package net.bymarcin.evenmoreutilities.mods.vanillautils;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaUtils implements IMod{
	static Integer kinderSurpriseID;
	@Override
	public void init() {
		kinderSurpriseID = EvenMoreUtilities.instance.config.getBlock("ItemsId","KinderSurpriseID", 1011).getInt();
	    GameRegistry.registerItem(KinderSurprise.instance, StaticValues.modId+":KinderSurprise");
		KinderSurprise.addDrop(5F, Item.appleRed);
		KinderSurprise.addDrop(2F, Item.appleGold);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
