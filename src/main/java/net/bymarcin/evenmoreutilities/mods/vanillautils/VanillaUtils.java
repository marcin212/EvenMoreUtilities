package net.bymarcin.evenmoreutilities.mods.vanillautils;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaUtils implements IMod{
	static Integer kinderSurpriseID;
	static Integer blockCharcoalID;
	static ItemStack coal = new ItemStack(Item.coal, 1);
	static ItemStack coal9 = new ItemStack(Item.coal, 9,1);
	
	@Override
	public void init() {
	  //  kinderSurpriseID = EvenMoreUtilities.instance.config.getBlock("ItemsId","KinderSurpriseID", 1011).getInt();
	  //  GameRegistry.registerItem(KinderSurprise.instance, StaticValues.modId+":KinderSurprise");
	  //  GameRegistry.addShapedRecipe(new ItemStack(KinderSurprise.instance,1),
	  //  		"BSB",
	  //  		"SES",
	  //  		"BMB",
	  //   		'M', Item.bucketMilk, 'E', Item.egg, 'S', Item.sugar, 'B', Item.arrow);
	    
	    blockCharcoalID = EvenMoreUtilities.instance.config.getBlock("ItemsId","BlockCharcoalID", 1012).getInt();
	    GameRegistry.registerBlock(BlockCharcoal.instance,StaticValues.modId+":BlockCharcoal");
	    coal.setItemDamage(1);
	    GameRegistry.addShapelessRecipe(new ItemStack(BlockCharcoal.instance),
	    		coal,coal,coal,
	    		coal,coal,coal,
	    		coal,coal,coal);
	    GameRegistry.addShapelessRecipe(coal9, BlockCharcoal.instance);
	    
	    
	    
	    GameRegistry.registerFuelHandler(new FuelHandler());
	    
		//KinderSurprise.addDrop(5F, Item.appleRed);
		//KinderSurprise.addDrop(3F, EntityPig.class);
		/*TODO Drop List
			KinderSurprise.addDrop(2F, Item.appleGold);
			KinderSurprise.addDrop(2F, EntityZombie.class);	
		*/
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}
}
