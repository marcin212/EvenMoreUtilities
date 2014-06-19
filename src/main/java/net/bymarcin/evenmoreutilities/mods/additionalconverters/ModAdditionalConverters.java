package net.bymarcin.evenmoreutilities.mods.additionalconverters;

import li.cil.oc.api.Driver;
import net.bymarcin.evenmoreutilities.IMod;

public class ModAdditionalConverters implements IMod{

	@Override
	public void init() {
		Driver.add(new AdditionalItemStackInfo());
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
