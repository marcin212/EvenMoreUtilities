package net.bymarcin.evenmoreutilities.mods.superconductor.render;


import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public interface Glowing  {
	Icon getGlowIcon(IBlockAccess blockAccess, int x, int y, int z, int side);
}
