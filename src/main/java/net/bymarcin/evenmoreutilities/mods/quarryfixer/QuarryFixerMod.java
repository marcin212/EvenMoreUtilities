package net.bymarcin.evenmoreutilities.mods.quarryfixer;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class QuarryFixerMod implements IMod{
	public static int quarryFixerBlockID;
	
	
	@Override
	public void init() {
		quarryFixerBlockID = EvenMoreUtilities.instance.config.getBlock("BlocksId","quarryFixerID", 3730).getInt();
        GameRegistry.registerBlock(QuarryFixerBlock.instance,StaticValues.modId+":quarryFixer");
        GameRegistry.addRecipe(new ShapedOreRecipe(QuarryFixerBlock.instance, 
                " x ","xdx"," x ", Character.valueOf('x'), "gearIron", Character.valueOf('d'), new ItemStack(Block.dispenser)));
	
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}

}
