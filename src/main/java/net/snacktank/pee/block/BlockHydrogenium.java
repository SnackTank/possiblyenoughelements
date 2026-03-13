package net.snacktank.pee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.snacktank.pee.PossiblyEnoughElements;
import net.snacktank.pee.tileentity.TileEntityHydrogenium;

public class BlockHydrogenium extends Block{

	private String name = "cubus_hydrogenii";

	public BlockHydrogenium() {
		super(Material.GLASS);
		setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		setCreativeTab(PossiblyEnoughElements.PEE_DRAWER);
		setLightOpacity(0);
		setHardness(1);
		setTickRandomly(false);
	}	
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		//PossiblyEnoughElements.LOGGER.info(worldIn.getBlockState(fromPos));
		Block neighborBlock = worldIn.getBlockState(fromPos).getBlock();
		if(neighborBlock instanceof BlockFire) {
	    	explode(worldIn, pos);
		}
    }
	
	public void explode(World worldIn, BlockPos pos) {
		if (worldIn.isRemote) return;
    	worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5, true);
		worldIn.destroyBlock(pos, false);
	}
	
	public static void move(World world, BlockPos pos, IBlockState state) {
		if(world.isRemote) return;
		BlockPos newPos = pos.add(0, 1, 0);
		if(world.isAirBlock(newPos)) {
			world.setBlockState(newPos, state);
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityHydrogenium();
	}
	
	/*
	@Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		this.updateTick(worldIn, pos, state, random);
    }
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		if (worldIn.isRemote) return;
		
		PossiblyEnoughElements.LOGGER.info(rand);
		BlockPos newPos = pos.add(0, 1, 0);
		if(worldIn.isAirBlock(newPos)) {
			worldIn.setBlockState(newPos, state);
			worldIn.setBlockToAir(pos);
		}
	

	}
	*/
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state) {
        return true;
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
    	explode(world, pos);
    }
}
