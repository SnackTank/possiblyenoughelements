package net.snacktank.pee.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.snacktank.pee.block.BlockGasum;

public class TileEntityGasum extends TileEntity implements ITickable{

	public String name = "hydrogenium";
	int j;
	
	public TileEntityGasum() {
		//name = "hydrogenium";
		j = 0;
	}
		
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("name", name);
        return compound;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		name = compound.getString("name");
	}
	
	@Override
	public void update() {
		if(world.isRemote) return;
		j++;
		if(j == 10) {
			IBlockState state = world.getBlockState(pos);
			BlockGasum.move(world, pos, state);
			j = 0;
		}
	}
}
