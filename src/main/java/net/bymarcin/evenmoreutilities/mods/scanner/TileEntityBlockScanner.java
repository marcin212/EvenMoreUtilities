package net.bymarcin.evenmoreutilities.mods.scanner;

import java.util.List;
import java.util.Map;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class TileEntityBlockScanner extends TileEntity implements SimpleComponent{

	@Override
	public String getComponentName() {
		return "OpenSensor";
	}
	public int getScannerRange() {
		return ScannerMod.scannerRange;
	}

	public ChunkCoordinates getLocation() {
		return new ChunkCoordinates(xCoord, yCoord, zCoord);
	}

	private static AxisAlignedBB getBoundingBox(ChunkCoordinates location,
			double range) {
		return AxisAlignedBB
				.getAABBPool()
				.getAABB(location.posX, location.posY, location.posZ,
						location.posX + 1, location.posY + 1, location.posZ + 1)
				.expand(range, range, range);
	}


	@SuppressWarnings("unchecked")
	public static <T extends Entity> List<T> getEntitiesWithinAABB(World world,
			Class<? extends T> cls, AxisAlignedBB aabb) {
		return world.getEntitiesWithinAABB(cls, aabb);
	}

	private List<Integer> listEntityIds(Class<? extends Entity> entityClass) {
		List<Integer> ids = Lists.newArrayList();
		final AxisAlignedBB aabb = getBoundingBox(getLocation(),
				getScannerRange());
		for (Entity entity : getEntitiesWithinAABB(getWorldObj(), entityClass,
				aabb))
			ids.add(entity.entityId);
		return ids;
	}

	private Entity getEntityInfo(int mobId) {
		return getWorldObj().getEntityByID(mobId);
	}

	@Callback
	public Object[] getPlayerNames(Context context, Arguments args) {
		@SuppressWarnings("unchecked")
		List<EntityPlayer> players = getWorldObj().getEntitiesWithinAABB(
				EntityPlayer.class,
				getBoundingBox(getLocation(), getScannerRange()));

		List<String> names = Lists.newArrayList();
		for (EntityPlayer player : players)
			names.add(player.username);

		return names.toArray();
	}

	@Callback
	public Object[] getMobIds(Context context, Arguments args) {
		return new Object[]{listEntityIds(EntityLiving.class).toArray()};
	}

	@Callback
	public Object[] getMinecartIds(Context context, Arguments args) {
		return new Object[]{listEntityIds(EntityMinecart.class).toArray()};
	}

	@Callback
	public Object[] getPlayerData(Context context, Arguments args) {
		EntityPlayer player = getWorldObj().getPlayerEntityByName(args.checkString(0));
		return  new Object[]{player};
	}

	@Callback
	public Object[] getMobData(Context context, Arguments args) {
		Entity e= getEntityInfo(args.checkInteger(0));
		return  new Object[]{e};
	}

	@Callback
	public Object[] getMinecartData(Context context, Arguments args) {
		Entity e= getEntityInfo(args.checkInteger(0));
		return  new Object[]{e};
	}

	@Callback
	public Object[] sonicScan(Context context, Arguments args) {

		int range = 1 + getScannerRange() / 2;
		World world = getWorldObj();
		Map<Integer, Map<String, Object>> results = Maps.newHashMap();
		ChunkCoordinates sensorPos = getLocation();
		int sx = sensorPos.posX;
		int sy = sensorPos.posY;
		int sz = sensorPos.posZ;

		final int rangeSq = range * range;
		int i = 0;
		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {
					final int bx = sx + x;
					final int by = sy + y;
					final int bz = sz + z;
					if (!world.blockExists(bx, by, bz))
						continue;

					final int distSq = x * x + y * y + z * z;
					if (distSq == 0 || distSq > rangeSq)
						continue;
					int id = world.getBlockId(bx, by, bz);
					Block block = Block.blocksList[id];

					String type;
					if (block == null || world.isAirBlock(bx, by, bz))
						type = "AIR";
					else if (block.blockMaterial.isLiquid())
						type = "LIQUID";
					else if (block.blockMaterial.isSolid())
						type = "SOLID";
					else
						type = "UNKNOWN";

					Map<String, Object> tmp = Maps.newHashMap();
					tmp.put("x", x);
					tmp.put("y", y);
					tmp.put("z", z);
					tmp.put("type", type);
					results.put(++i, tmp);

				}
			}
		}
		return new Object[]{results};
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}
	

}
