package net.bymarcin.evenmoreutilities.mods.scanner;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import li.cil.oc.api.driver.Converter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import com.google.common.collect.Maps;

public class EntityConventer implements Converter {

	@Override
	public void convert(Object entity, Map<Object, Object> map) {
		if (entity instanceof Entity) {
			Entity ent = (Entity)entity;
		
			Map<Object, Object> position = Maps.newHashMap();
			position.put("x", ent.posX);
			position.put("y", ent.posY);
			position.put("z", ent.posZ);
			map.put("position", position);
			
			map.put("type", ent.getEntityName());

			if (ent.riddenByEntity != null) {
				map.put("riddenBy", ent.riddenByEntity);
			}

			if (ent.ridingEntity != null) {
				map.put("ridingEntity", ent.ridingEntity.entityId);
			}
		}
		

		if (entity instanceof EntityHorse) {
			EntityHorse horse = (EntityHorse) entity;
			IInventory invent = null;
			try {
				Field f = horse.getClass().getField("field_110296_bG");
				f.setAccessible(true);
				invent = (IInventory) f.get(horse);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
			map.put("eatingHaystack", horse.isEatingHaystack());
			map.put("chestedHorse", horse.isChested());
			map.put("hasReproduced", horse.getHasReproduced());
			map.put("bred", horse.func_110205_ce());
			map.put("horseType", horse.getHorseType());
			map.put("horseVariant", horse.getHorseVariant());
			map.put("horseTemper", horse.getTemper());
			map.put("horseTame", horse.isTame());
			map.put("ownerName", horse.getOwnerName());
			map.put("horseInventory",invent);
		}

		if (entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) entity;
			map.put("profession", villager.getProfession());
			map.put("isMating", villager.isMating());
			map.put("isPlaying", villager.isPlaying());
			boolean isTrading = villager.isTrading();
			map.put("isTrading", isTrading);
			if (isTrading) {
				map.put("customer", villager.getCustomer().username);
			}
		}

		if (entity instanceof EntitySheep) {
			EntitySheep sheep = (EntitySheep) entity;
			map.put("sheepColor", sheep.getFleeceColor());
			map.put("isSheared", sheep.getSheared());
		}

		if (entity instanceof EntityZombie) {
			EntityZombie zombie = (EntityZombie) entity;
			map.put("isVillagerZombie", zombie.isVillager());
			map.put("convertingToVillager", zombie.isConverting());
		}

		if (entity instanceof EntityBat) {
			EntityBat bat = (EntityBat) entity;
			map.put("isHanging", bat.getIsBatHanging());
		}

		if (entity instanceof EntityPig) {
			EntityPig pig = (EntityPig) entity;
			map.put("isSaddled", pig.getSaddled());
		}

		if (entity instanceof EntityWolf) {
			EntityWolf wolf = (EntityWolf) entity;
			map.put("isShaking", wolf.getWolfShaking());
			map.put("isAngry", wolf.isAngry());
			map.put("collarColor", Math.pow(2, wolf.getCollarColor()));
		}

		if (entity instanceof EntityTameable) {
			EntityTameable mob = (EntityTameable) entity;
			boolean isTamed = mob.isTamed();
			map.put("isTamed", isTamed);
			if (isTamed) {
				map.put("isSitting", mob.isSitting());
				map.put("owner", mob.getOwnerName());
			}
		}

		if (entity instanceof EntityCreeper) {
			EntityCreeper creeper = (EntityCreeper) entity;
			map.put("isCharged", creeper.getPowered());
		}

		if (entity instanceof EntityWitch) {
			EntityWitch witch = (EntityWitch) entity;
			map.put("isAggressive", witch.getAggressive());
		}

		if (entity instanceof EntityLivingBase) {

			EntityLivingBase living = (EntityLivingBase) entity;

			HashMap<String, Object> armor = new HashMap<String,Object>();
			map.put("armor", armor);
			HashMap<Object, String> potionEffects = new HashMap<Object, String>();
			map.put("potionEffects", potionEffects);

			armor.put("boots", living.getCurrentItemOrArmor(1));
			armor.put("leggings",living.getCurrentItemOrArmor(2));
			armor.put("chestplate", living.getCurrentItemOrArmor(3));
			armor.put("helmet", living.getCurrentItemOrArmor(4));
			map.put("health", living.getHealth());
			map.put("maxHealth", living.getMaxHealth());
			map.put("isAirborne", living.isAirBorne);
			map.put("isBurning", living.isBurning());
			map.put("isAlive", living.isEntityAlive());
			map.put("isInWater", living.isInWater());
			map.put("isOnLadder", living.isOnLadder());
			map.put("isSleeping", living.isPlayerSleeping());
			map.put("isRiding", living.isRiding());
			map.put("isSneaking", living.isSneaking());
			map.put("isSprinting", living.isSprinting());
			map.put("isWet", living.isWet());
			map.put("isChild", living.isChild());
			map.put("isDead", living.isDead);
			map.put("yaw", living.rotationYaw);
			map.put("pitch", living.rotationPitch);
			map.put("yawHead", living.rotationYawHead);
			map.put("heldItem", living.getHeldItem());

			@SuppressWarnings("unchecked")
			Collection<PotionEffect> effects = living.getActivePotionEffects();

			int count = 1;
			for (PotionEffect effect : effects) {
				potionEffects.put(count++, effect.getEffectName());
			}

			Vec3 posVec = living.worldObj.getWorldVec3Pool().getVecFromPool(
					living.posX, living.posY + 1.62F, living.posZ);
			Vec3 lookVec = living.getLook(1.0f);
			Vec3 targetVec = posVec.addVector(lookVec.xCoord * 10f,
					lookVec.yCoord * 10f, lookVec.zCoord * 10f);
			MovingObjectPosition mop = living.worldObj.clip(posVec, targetVec);
			map.put("IsLookingAtBlock", false);
			if (mop != null) {
				map.put("IsLookingAtBlock",
						mop.typeOfHit == EnumMovingObjectType.TILE);
				if (mop.typeOfHit == EnumMovingObjectType.TILE) {
					HashMap<String, Object> lookingAt = new HashMap<String, Object>();
						lookingAt.put("x", mop.blockX);
						lookingAt.put("y", mop.blockY);
						lookingAt.put("z", mop.blockZ);
					map.put("lookingAt", lookingAt);
				} else {
					map.put("lookingAt", null);
				}
			}
		}

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			map.put("type", "Player");
			map.put("inventory",player.inventory);
			map.put("isAirBorne", player.isAirBorne);
			map.put("isBlocking", player.isBlocking());
			map.put("username", player.username);
			map.put("foodLevel", player.getFoodStats().getFoodLevel());
			map.put("isCreativeMode", player.capabilities.isCreativeMode);
		}
	}

}
