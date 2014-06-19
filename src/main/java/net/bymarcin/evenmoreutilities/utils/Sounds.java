package net.bymarcin.evenmoreutilities.utils;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class Sounds {
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
		event.manager.addSound(StaticValues.modId+":"+"alarm_main.ogg");
		event.manager.addSound(StaticValues.modId+":"+"alarm_offworld.ogg");
		event.manager.addSound(StaticValues.modId+":"+"alarm.ogg");
	}
}
