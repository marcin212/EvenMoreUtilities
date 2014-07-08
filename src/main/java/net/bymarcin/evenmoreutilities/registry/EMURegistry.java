package net.bymarcin.evenmoreutilities.registry;

import java.util.HashSet;
import java.util.Set;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.handler.GuiHandler;
import net.bymarcin.evenmoreutilities.handler.PacketHandler;
import net.bymarcin.evenmoreutilities.utils.AbstractPacket;
import net.bymarcin.evenmoreutilities.utils.StaticValues;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import cpw.mods.fml.common.network.NetworkRegistry;

public class EMURegistry {
	protected static Set<IProxy> proxy = new HashSet<IProxy>();
	protected static Set<IGUI> gui = new HashSet<IGUI>();
	protected static BiMap<Integer, Class<? extends AbstractPacket>> packet;
	protected static ImmutableBiMap.Builder<Integer, Class<? extends AbstractPacket>> builder = ImmutableBiMap.builder();
	protected static int lastPacketId= 0;
	
	public static void registerProxy(IProxy proxy){
		EMURegistry.proxy.add(Preconditions.checkNotNull(proxy));
	}
	
	public static void registerGUI(IGUI gui){
		EMURegistry.gui.add(Preconditions.checkNotNull(gui));
	}	
    
	public static void registerPacket(int id, Class<? extends AbstractPacket> packet){
		EMURegistry.builder.put(Integer.valueOf(id), Preconditions.checkNotNull(packet));
	}
	
	public static void init(){
        NetworkRegistry.instance().registerGuiHandler(EvenMoreUtilities.instance, new GuiHandler());
        NetworkRegistry.instance().registerChannel(new PacketHandler(), StaticValues.modId);	
	}
	
	public static void postInit(){
		packet = builder.build();
	}
}
