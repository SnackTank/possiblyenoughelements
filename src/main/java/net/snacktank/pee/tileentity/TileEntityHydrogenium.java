package net.snacktank.pee.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.snacktank.pee.block.BlockHydrogenium;

public class TileEntityHydrogenium extends TileEntity implements ITickable{
	int i;

	public TileEntityHydrogenium() {
		i = 0;
	}

	@Override
	public void update() {
		if(world.isRemote) return;
		i++;
		if(i == 10) {
			IBlockState state = world.getBlockState(pos);
			BlockHydrogenium.move(world, pos, state);
			i = 0;
		}
	}

}
