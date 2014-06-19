package net.bymarcin.evenmoreutilities.mods.yelloriumenhancement;
import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.bymarcin.evenmoreutilities.IMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import binnie.extrabees.core.ExtraBeeItems;
import binnie.extrabees.products.EnumHoneyComb;
public class YE implements IMod{

	@Override
	public void init() {
		if(!EnumHoneyComb.URANIUM.isActive()) return;
		ArrayList<ItemStack> dustYellorium = OreDictionary.getOres("dustYellorium");
		ArrayList<ItemStack> dustBlutonimu = OreDictionary.getOres("dustBlutonium");
		if(dustYellorium.size() > 0  && dustBlutonimu.size() > 0){
			EnumHoneyComb.URANIUM.tryAddProduct(dustYellorium.get(0), 25);
			if(ExtraBeeItems.UraniumDust.isActive()){
				EnumHoneyComb.URANIUM.tryAddProduct(ExtraBeeItems.UraniumDust, 100);
				GameRegistry.addShapelessRecipe(dustBlutonimu.get(0),
					ExtraBeeItems.UraniumDust.get(1),
					ExtraBeeItems.UraniumDust.get(1),
					ExtraBeeItems.UraniumDust.get(1),
					ExtraBeeItems.UraniumDust.get(1));
			}
		}
	}

	@Override
	public void load() {
		
	}

}
