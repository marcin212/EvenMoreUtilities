package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.bymarcin.evenmoreutilities.utils.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class PacketRedstoneChange extends AbstractPacket{
	int delta;
	int x;
	int y;
	int z;
	public PacketRedstoneChange() {
		
	}
	
	public PacketRedstoneChange(int x, int y, int z , int delta) {
		this.delta = delta;
		this.x = x;
		this.y = y;
		this.z = z;
	}	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(delta);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);	
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		delta = in.readInt();
		x=in.readInt();
		y=in.readInt();
		z=in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		
		if(side.isServer()){
			TileEntityRedstoneEmitter te = (TileEntityRedstoneEmitter)player.worldObj.getBlockTileEntity(x, y, z);
			te.change(delta);
			PacketDispatcher.sendPacketToAllPlayers(te.getDescriptionPacket());
		}
	}
}
