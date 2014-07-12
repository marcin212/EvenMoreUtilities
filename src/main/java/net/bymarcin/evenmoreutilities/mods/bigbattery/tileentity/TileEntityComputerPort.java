package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class TileEntityComputerPort extends RectangularMultiblockTileEntityBase implements SimpleComponent{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery side", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));			
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {		
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery side", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery side", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery side", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BigBattery(worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BigBattery.class;
	}
	
	@Override
	public String getComponentName() {
		return "big_battery";
	}
	
	private BigBattery getControler(){
		return (BigBattery) getMultiblockController();
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIn(Context c, Arguments args){
		int id = args.checkInteger(0);
		if(getControler()!=null)
			try{
				((TileEntityPowerTap)getControler().getPowerTaps().toArray()[id]).setIn();
				return null;
			}catch(IndexOutOfBoundsException e){
				return new Object[]{null,"Electrode not found"};
			}
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setOut(Context c, Arguments args){
		int id = args.checkInteger(0);
		if(getControler()!=null)
			try{
				((TileEntityPowerTap)getControler().getPowerTaps().toArray()[id]).setOut();
				return null;
			}catch(IndexOutOfBoundsException e){
				return new Object[]{null,"Electrode not found"};
			}
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyStored(Context c, Arguments args){
		if(getControler()!=null)
			return new Object[]{getControler().getStorage().getRealEnergyStored()};
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] getMaxEnergyStored(Context c, Arguments args){
		if(getControler()!=null)
			return new Object[]{getControler().getStorage().getRealMaxEnergyStored()};
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] setElectrodeTransfer(Context c, Arguments args){
		int id = args.checkInteger(0);
		int transfer = args.checkInteger(1);
		if(getControler()!=null){	
			try{
				((TileEntityPowerTap)getControler().getPowerTaps().toArray()[id]).setTransfer(transfer);
				return null;
			}catch(IndexOutOfBoundsException e){
				return new Object[]{null,"Electrode not found"};
			}
		}
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] setAllElectrodeTransfer(Context c, Arguments args){
		int transfer = args.checkInteger(0);
		if(getControler()!=null){	
				for(TileEntityPowerTap tap: getControler().getPowerTaps())
						tap.setTransfer(transfer);
				return null;
		}
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] getMaxElectrodeTransfer(Context c, Arguments args){
		if(getControler()!=null)
			return new Object[]{getControler().getStorage().getMaxExtract()};
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
	
    @Callback
    @Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyBalanceLastTick(Context c, Arguments args){
		if(getControler()!=null)
			return new Object[]{getControler().getLastTickBalance()};
		return new Object[]{null,"Controler block not found. Rebuild your battery."};
	}
}
