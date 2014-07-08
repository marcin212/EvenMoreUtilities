package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IGUI;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class RedstoneEmitterMod implements IMod, IGUI{
	public static int RedstoneEmitterID;
	@Override
	public void init() {
		EMURegistry.registerPacket(0,PacketRedstoneChange.class);
		RedstoneEmitterID = EvenMoreUtilities.instance.config.getBlock("BlocksId","redstoneEmitterID", 2049).getInt();
		GameRegistry.registerBlock(BlockRedstoneEmitter.instance,StaticValues.modId+":RedstoneEmitter");
	    GameRegistry.registerTileEntity(TileEntityRedstoneEmitter.class, StaticValues.modId+".RedstoneEmitter");
	    GameRegistry.addRecipe(new ItemStack(BlockRedstoneEmitter.instance), "   ", "rzr", " x ",
	            'x', Item.redstoneRepeater, 'r', Item.redstone, 'z', Block.torchRedstoneActive);
	    EMURegistry.registerGUI(this);
	}

	@Override
	public void load() {
		
	}
	
	@Override
	public Object getServerGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
		
        if(blockEntity instanceof TileEntityRedstoneEmitter){
            return new GuiTiny((TileEntityRedstoneEmitter) blockEntity);
        }
		return null;
	}

}
