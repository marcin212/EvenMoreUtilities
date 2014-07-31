package net.bymarcin.evenmoreutilities.mods.superconductor.gui;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductor;
import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.utils.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidStack;

public class PacketUpdateFluidAmount extends AbstractPacket{
	int x;
	int y;
	int z;
	int fluidAmount;
	int fluidID;
	
	public PacketUpdateFluidAmount() {
	}
	
	public PacketUpdateFluidAmount(int x, int y, int z, int fluidAmount, int fluidID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.fluidAmount = fluidAmount;
		this.fluidID = fluidID;
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeInt(fluidAmount);
		out.writeInt(fluidID);
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		fluidAmount = in.readInt();
		fluidID = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if(side.isClient()){
			TileEntityControler tile= (TileEntityControler) player.worldObj.getBlockTileEntity(x, y, z);
			FluidStack fluid = new FluidStack(fluidID, fluidAmount);
			if(tile!=null && ((SuperConductor)tile.getMultiblockController()) !=null){
				((SuperConductor)tile.getMultiblockController()).getTank().setFluid(fluid);
			}
		}
	}

}
