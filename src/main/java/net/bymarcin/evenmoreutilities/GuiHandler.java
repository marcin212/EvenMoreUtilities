package net.bymarcin.evenmoreutilities;

import net.bymarcin.evenmoreutilities.mods.redstonemitter.GuiTiny;
import net.bymarcin.evenmoreutilities.mods.redstonemitter.TileEntityRedstoneEmitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
    	return null;
            
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityRedstoneEmitter){
                    return new GuiTiny((TileEntityRedstoneEmitter) tileEntity);
            }
            return null;

    }
    
}
