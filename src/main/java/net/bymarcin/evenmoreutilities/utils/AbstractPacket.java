package net.bymarcin.evenmoreutilities.utils;

import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class AbstractPacket extends EMURegistry{
	public static final String CHANNEL = StaticValues.modId;

	public static AbstractPacket constructPacket(int packetId) throws ProtocolException, Throwable {
		Class<? extends AbstractPacket> clazz = packet.get(Integer.valueOf(packetId));
		if (clazz == null) {
			throw new ProtocolException("Unknown Packet Id!");
		} else {
			return clazz.newInstance();
		}
	}

	@SuppressWarnings("serial")
	public static class ProtocolException extends Exception {

		public ProtocolException() {
		}

		public ProtocolException(String message, Throwable cause) {
			super(message, cause);
		}

		public ProtocolException(String message) {
			super(message);
		}

		public ProtocolException(Throwable cause) {
			super(cause);
		}
	}

	public final int getPacketId() {
		if (packet.inverse().containsKey(getClass())) {
			return packet.inverse().get(getClass()).intValue();
		} else {
			throw new RuntimeException("Packet " + getClass().getSimpleName()
					+ " is missing a mapping!");
		}
	}

	public final Packet makePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeByte(getPacketId());
		write(out);
		return PacketDispatcher.getPacket(CHANNEL, out.toByteArray());
	}

	public abstract void write(ByteArrayDataOutput out);

	public abstract void read(ByteArrayDataInput in) throws ProtocolException;

	public abstract void execute(EntityPlayer player, Side side)
			throws ProtocolException;
}
