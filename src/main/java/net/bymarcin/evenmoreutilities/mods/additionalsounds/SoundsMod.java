package net.bymarcin.evenmoreutilities.mods.additionalsounds;

import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IProxy;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundsMod implements IProxy, IMod{
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
		event.manager.addSound(StaticValues.modId+":"+"alarm_main.ogg");
		event.manager.addSound(StaticValues.modId+":"+"alarm_offworld.ogg");
		event.manager.addSound(StaticValues.modId+":"+"alarm.ogg");
	}

	@Override
	public void clientSide() {
		MinecraftForge.EVENT_BUS.register(new SoundsMod());
	}

	@Override
	public void serverSide() {
		
	}

	@Override
	public void init() {
		EMURegistry.registerProxy(this);
	}

	@Override
	public void load() {
		
	}
}
