package net.bymarcin.evenmoreutilities.mods.scanner;

import li.cil.oc.api.Driver;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ScannerMod implements IMod{
	public static int scannerID;
	public static int scannerRange;

	@Override
	public void init() {
		scannerID = EvenMoreUtilities.instance.config.getBlock("BlocksId","scanner", 3721).getInt();
		scannerRange = EvenMoreUtilities.instance.config.getBlock("Options","scannerRange", 32).getInt(32);
		
        GameRegistry.registerBlock(BlockScanner.instance,StaticValues.modId+":scanner");
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockScanner.instance, 
                " x ","xdx"," x ", Character.valueOf('x'), "gearIron", Character.valueOf('d'), new ItemStack(Block.dispenser)));
        GameRegistry.registerTileEntity(TileEntityBlockScanner.class, StaticValues.modId+".scanner");
        Driver.add(new EntityConventer());
        Driver.add(new ConventerIInventory());
	}

	@Override
	public void load() {
		
	}

}
