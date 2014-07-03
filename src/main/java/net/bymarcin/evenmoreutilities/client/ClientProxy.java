package net.bymarcin.evenmoreutilities.client;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import net.bymarcin.evenmoreutilities.CommonProxy;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBatteryMod;
import net.bymarcin.evenmoreutilities.utils.Sounds;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

    
    @Override
    public void registerRenderers() {
		MinecraftForge.EVENT_BUS.register(new Sounds());
		BigBatteryMod.client();
    }
}
