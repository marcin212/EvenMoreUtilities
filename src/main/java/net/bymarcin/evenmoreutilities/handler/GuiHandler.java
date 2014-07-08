package net.bymarcin.evenmoreutilities.handler;

import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler extends EMURegistry implements IGuiHandler {
	
	
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
    	 TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
    	 Object temp = null;
    	 for(IGUI g : gui){
    		temp = g.getServerGuiElement(id, tileEntity, player, world, x, y, z);
    		System.out.println("test");
    		System.out.println(temp);
    		if(temp != null) return temp;
    	 }	
    	return null;
            
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            Object temp = null;
	       	for(IGUI g :  gui){
	    		 temp = g.getClientGuiElement(id, tileEntity, player, world, x, y, z);
	     		System.out.println("test1");
	    		System.out.println(temp);
	    		 if(temp != null) return temp;
	       	}
            return null;
    }
    
}
