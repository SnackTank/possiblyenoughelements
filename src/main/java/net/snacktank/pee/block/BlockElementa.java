package net.snacktank.pee.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.snacktank.pee.PossiblyEnoughElements;
import net.snacktank.pee.init.ModItems;
import net.snacktank.pee.tileentity.TileEntityElementa;

public class BlockElementa extends Block{
	
	private String name = "cubus_elementorum";

	public BlockElementa() {
		super(Material.IRON);
		setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		setCreativeTab(PossiblyEnoughElements.PEE_DRAWER);
		setHardness(12);
	}
	
	//Random tick boilerplate
	@Override
    public boolean getTickRandomly() {
        return true;
    }
	
	@Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		this.updateTick(worldIn, pos, state, random);
    }
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		if (worldIn.isRemote) return;
		int[] allergies = getAllergies(worldIn, pos);
		if(allergies == null) return;
		int[] neighbors = getNeighbors(worldIn, pos);
		if(!allergicToNeighbor(neighbors, allergies)) return;
		explode(worldIn, pos);
	}
	
	//Check neighbors when placed!
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int[] allergies = getAllergies(worldIn, pos);
		if(allergies == null) return;
		int[] neighbors = getNeighbors(worldIn, pos);
		if(!allergicToNeighbor(neighbors, allergies)) return;
		explode(worldIn, pos);
	}
	private boolean allergicToNeighbor(int[] neighbors, int[] allergies) {
		for(int i = 0; i < allergies.length; i++) {
			if(neighbors[0] == allergies[i]) {
				return true;
			}
			if(neighbors[1] == allergies[i]) {
				return true;
			}
			if(neighbors[2] == allergies[i]) {
				return true;
			}
			if(neighbors[3] == allergies[i]) {
				return true;
			}
			if(neighbors[4] == allergies[i]) {
				return true;
			}
			if(neighbors[5] == allergies[i]) {
				return true;
			}
		}
		return false;
	}
	
	//Returns block ID of Neighbors.
	private int[] getNeighbors(World world, BlockPos pos) {
		int idNorth = Block.getIdFromBlock(world.getBlockState(pos.north()).getBlock());
		int idEast = Block.getIdFromBlock(world.getBlockState(pos.east()).getBlock());
		int idSouth = Block.getIdFromBlock(world.getBlockState(pos.south()).getBlock());
		int idWest = Block.getIdFromBlock(world.getBlockState(pos.west()).getBlock());
		int idUp = Block.getIdFromBlock(world.getBlockState(pos.up()).getBlock());
		int idDown = Block.getIdFromBlock(world.getBlockState(pos.down()).getBlock());
		int[] neighbors = {idNorth, idEast, idSouth, idWest, idUp, idDown};
		return neighbors;
	}
	
	private void explode(World world, BlockPos pos) {
		if (world.isRemote) return;
		world.destroyBlock(pos, false);
		world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5, true);
	}
	
	private int[] getAllergies(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityElementa) {
			NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
			return nbt.getIntArray("allergies");
		}
		return null;
	}
	
	//Return block NBT for debugging purposes. 
	//So nice that these blocks mostly share code, which probably means they could be one.
	//But we'll have different blocks for each state of matter.
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
	
	//Required for making a tileEntity
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
		
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityElementa();
	}

}
