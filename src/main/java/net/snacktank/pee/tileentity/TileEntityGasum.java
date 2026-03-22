package net.snacktank.pee.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.snacktank.pee.block.BlockGasum;

public class TileEntityGasum extends TileEntity implements ITickable{

	public String name = "hydrogenium";
	public boolean canExplode = true;
	public boolean canFloat = true;
	int j;
	
	public TileEntityGasum() {
		j = 0;
	}
		
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("name", name);
		compound.setBoolean("can_explode", canExplode);
		compound.setBoolean("can_float", canFloat);
        return compound;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		name = compound.getString("name");
		canExplode = compound.getBoolean("can_explode");
		canFloat = compound.getBoolean("can_float");
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