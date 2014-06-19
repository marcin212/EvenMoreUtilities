package net.bymarcin.evenmoreutilities.mods.additionalconverters;

import java.util.Map;
import java.util.Set;

import li.cil.oc.api.driver.Converter;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.google.common.collect.Sets;

public class AdditionalItemStackInfo implements Converter {

	@Override
	public void convert(Object value, Map<Object, Object> output) {
		if (value instanceof ItemStack) {
			output.put("rawName", getRawNameForStack((ItemStack)value));
			
			//Aspect info from thaumcraft
			Set<Object> outAspectList = Sets.newLinkedHashSet();
			AspectList aspectList = getAspects((ItemStack)value);
			if(aspectList!=null){
				for(Aspect a:aspectList.getAspects()){
					outAspectList.add(a.getName());
				}		
			}
			if(outAspectList.size()>0)
				output.put("aspects", outAspectList.toArray(new String[]{}));
			//
	
		}
	}

	public static String getRawNameForStack(ItemStack is) {

		String rawName = "unknown";

		try {
			rawName = is.getUnlocalizedName().toLowerCase();
		} catch (Exception e) {
		}
		try {
			if (rawName.length() - rawName.replaceAll("\\.", "").length() == 0) {
				String packageName = is.getItem().getClass().getName()
						.toLowerCase();
				String[] packageLevels = packageName.split("\\.");
				if (!rawName.startsWith(packageLevels[0])
						&& packageLevels.length > 1) {
					rawName = packageLevels[0] + "." + rawName;
				}
			}
		} catch (Exception e) {

		}

		return rawName.trim();
	}
	
	  public AspectList getAspects(ItemStack itemstack)
	  {
	    if (itemstack.hasTagCompound()) {
	      AspectList aspects = new AspectList();
	      aspects.readFromNBT(itemstack.getTagCompound());
	      return aspects.size() > 0 ? aspects : null;
	    }
	    return null;
	  }
	  
	
}
