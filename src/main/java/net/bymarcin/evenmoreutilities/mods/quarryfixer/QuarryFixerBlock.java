package net.bymarcin.evenmoreutilities.mods.quarryfixer;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.BuildCraftAPI;
import buildcraft.core.Box;
import buildcraft.core.utils.BlockUtil;
import buildcraft.factory.TileQuarry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class QuarryFixerBlock extends Block {
	public static QuarryFixerBlock instance = new QuarryFixerBlock(QuarryFixerMod.quarryFixerBlockID);
	private Icon furnaceTopIcon;

	private QuarryFixerBlock(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.quarryFixer");
	}

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("iron_block");
        this.furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
    }
    
	
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
    	
    	switch (par1) {
		case 0:
		case 1:
			return this.furnaceTopIcon;
		default:
			return this.blockIcon;
			
		}
        
    }
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		int sx, sy, sz;
		sx = par2;
		sy = par3;
		sz = par4;
		TileQuarry tq = null;
		
		
		
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (par1World.getBlockTileEntity(sx + dir.offsetX, sy + dir.offsetY, sz + dir.offsetZ) instanceof TileQuarry) {
				tq = (TileQuarry) par1World.getBlockTileEntity(sx + dir.offsetX, sy + dir.offsetY, sz + dir.offsetZ);
				break;
			}
		}

		if (tq == null)
			return false;

		Box box = tq.box;

		for (int x = box.xMin; x <= box.xMax; x++) {
			for (int y = box.yMin -1 ; y >= 1; y--) {
				for (int z = box.zMin; z <= box.zMax; z++) {
					
					if (x == box.xMin || x == box.xMax || z == box.zMin
							|| z == box.zMax) {
						if (BlockUtil.isSoftBlock(par1World, x, y, z))
							par1World.setBlock(x, y, z, Block.stone.blockID);
						continue;
					}
					
					if(BuildCraftAPI.softBlocks[par1World.getBlockId(x, y, z)]){
						par1World.setBlockToAir(x, y, z);
					}
				}
			}
		}

		return true;
	}
}
