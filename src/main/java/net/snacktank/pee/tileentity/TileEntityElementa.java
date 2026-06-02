package net.snacktank.pee.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityElementa extends TileEntity{

	private String name = "natrium";
	private int[] allergies = {1, 2, 3};
	
	public TileEntityElementa() {

	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("name", name);
		compound.setIntArray("allergies", allergies);
		//compound.setBoolean("can_explode", canExplode);
		//compound.setBoolean("can_float", canFloat);
        return compound;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		name = compound.getString("name");
		allergies = compound.getIntArray("allergies");
		//canExplode = compound.getBoolean("can_explode");
		//canFloat = compound.getBoolean("can_float");
	}
	
}
