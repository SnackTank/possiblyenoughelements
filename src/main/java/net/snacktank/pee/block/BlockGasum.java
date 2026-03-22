package net.snacktank.pee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.snacktank.pee.PossiblyEnoughElements;
import net.snacktank.pee.init.ModItems;
import net.snacktank.pee.tileentity.TileEntityGasum;

public class BlockGasum extends Block{

	public String name = "cubus_gasi";
	
	public BlockGasum() {
		super(Material.GLASS);
		setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		setCreativeTab(PossiblyEnoughElements.PEE_DRAWER);
		setLightOpacity(0);
		setHardness(1);
	}
	
	//Return block NBT for debugging purposes. 
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) return true;
		if(playerIn.getHeldItemMainhand().getItem() == ModItems.FIRST_ITEM) {
			ICommandSender sender = playerIn;
			TileEntity te = sender.getEntityWorld().getTileEntity(new BlockPos(pos.getX(),pos.getY(),pos.getZ()));
			NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
			sender.sendMessage(new TextComponentString(nbt.toString()));
		}
		return true;
    }
	
	//Chain explosions. 
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosionIn) {
		if(!getCanExplode(world, pos)) return; 
		explode(world, pos);
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {				
		//Check if it can explode, if so well explode
		if(!getCanExplode(worldIn, pos)) return; 
		
		Block neighborBlock = worldIn.getBlockState(fromPos).getBlock();
		if(neighborBlock instanceof BlockFire) {
	    	explode(worldIn, pos);
		}
    }
	
	public static void move(World world, BlockPos pos, IBlockState state) {
		if(world.isRemote) return;
		
		//Check if gas is lighter than air
		BlockGasum bg = new BlockGasum();			//Call a new instance of the block because dumb static
		if (!bg.getCanFloat(world, pos)) return;	
		TileEntity te = world.getTileEntity(pos);

		
		BlockPos newPos = pos.up();
		if(world.isAirBlock(newPos)) {
			world.setBlockState(newPos, state);
			world.setBlockToAir(pos);
			
			if(te != null) {
				TileEntity newTE = world.getTileEntity(newPos);
				if(newTE != null) {
					NBTTagCompound tag = te.writeToNBT(new NBTTagCompound());
	            	tag.setInteger("x", newPos.getX());	//Write new X
	            	tag.setInteger("y", newPos.getY());	//Write new Y
	            	tag.setInteger("z", newPos.getZ());	//Write new Z
	            	newTE.readFromNBT(tag);			
				}
			}
			world.removeTileEntity(pos);
		}
		
		
	}
	
	public void explode(World worldIn, BlockPos pos) {
		if (worldIn.isRemote) return;
		worldIn.destroyBlock(pos, false);
    	worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5, true);
	}
	
	//Required boilerplate 
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityGasum();
	}
	
	//I think the names are self explanatory. 
	public boolean getCanExplode(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityGasum) {
			//Read the NBT
			NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
			return nbt.getBoolean("can_explode");
		}
		return true;
	}
	
	public boolean getCanFloat(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityGasum) {
			NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
			return nbt.getBoolean("can_float");
		}
		return true;
	}
	
}