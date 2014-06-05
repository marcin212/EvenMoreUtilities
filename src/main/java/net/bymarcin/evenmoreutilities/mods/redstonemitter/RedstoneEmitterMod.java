package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class RedstoneEmitterMod implements IMod{
	public static int RedstoneEmitterID;
	@Override
	public void init() {
		RedstoneEmitterID = EvenMoreUtilities.instance.config.getBlock("BlocksId","redstoneEmitterID", 2049).getInt();
		GameRegistry.registerBlock(BlockRedstoneEmitter.instance,StaticValues.modId+":RedstoneEmitter");
	    GameRegistry.registerTileEntity(TileEntityRedstoneEmitter.class, StaticValues.modId+".RedstoneEmitter");
	    GameRegistry.addRecipe(new ItemStack(BlockRedstoneEmitter.instance), "   ", "rzr", " x ",
	            'x', Item.redstoneRepeater, 'r', Item.redstone, 'z', Block.torchRedstoneActive);
	}

	@Override
	public void load() {
		
	}

}
