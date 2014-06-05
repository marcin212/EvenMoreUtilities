package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRedstoneEmitter extends TileEntity{
	int emittRedstone = 15;
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	@Override
	public void updateEntity() {
		
	}
	public int getRedstoneSignal(){
		return emittRedstone;
	}
	
	public void change(int i){
		if(emittRedstone+i<=15 && emittRedstone+i>=0)
			emittRedstone +=i;
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, this.getWorldObj().getBlockId(xCoord, yCoord, zCoord));
	}
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
           emittRedstone = tagCompound.getInteger("emitt");      
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);               
            tagCompound.setInteger("emitt", emittRedstone);
    }
    @Override
    public Packet getDescriptionPacket()
    {
     NBTTagCompound var1 = new NBTTagCompound();
     this.writeToNBT(var1);
     return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, var1);
    }
            
    @Override
    public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet)
    {
     readFromNBT(packet.data);
    }
}
