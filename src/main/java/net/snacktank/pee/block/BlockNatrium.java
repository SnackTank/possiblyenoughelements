package net.snacktank.pee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.snacktank.pee.PossiblyEnoughElements;

public class BlockNatrium extends Block{

	private String name = "cubus_natrii";
	
	public BlockNatrium() {
		super(Material.CAKE);
		setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		setCreativeTab(PossiblyEnoughElements.PEE_DRAWER);
		setHardness(12);
	}

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		//PossiblyEnoughElements.LOGGER.info(worldIn.getBlockState(fromPos));
		Block neighborBlock = worldIn.getBlockState(fromPos).getBlock();
		if(neighborBlock instanceof BlockLiquid) {
	    	explode(worldIn, pos);
		}
    }
		
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		//PossiblyEnoughElements.LOGGER.info(worldIn.getBlockState(pos));
		Block nNorth = worldIn.getBlockState(pos.north()).getBlock();
		Block nEast = worldIn.getBlockState(pos.east()).getBlock();
		Block nSouth = worldIn.getBlockState(pos.south()).getBlock();
		Block nWest = worldIn.getBlockState(pos.west()).getBlock();
		
		if(nNorth instanceof BlockLiquid) {
			explode(worldIn, pos);
			return;
		}
		if(nEast instanceof BlockLiquid) {
			explode(worldIn, pos);
			return;
		}
		if(nSouth instanceof BlockLiquid) {
			explode(worldIn, pos);
			return;
		}
		if(nWest instanceof BlockLiquid) {
			explode(worldIn, pos);
			return;
		}
    }
	
	public void explode(World worldIn, BlockPos pos) {
		if (worldIn.isRemote) return;
    	worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4, true);
		worldIn.destroyBlock(pos, false);
	}
}
