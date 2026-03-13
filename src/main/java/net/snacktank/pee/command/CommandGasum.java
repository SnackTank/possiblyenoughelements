package net.snacktank.pee.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.snacktank.pee.tileentity.TileEntityGasum;

public class CommandGasum extends CommandBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		double x, y, z;
		x = Double.parseDouble(params[0]);
		y = Double.parseDouble(params[1]);
		z = Double.parseDouble(params[2]);
		
			TileEntity te = sender.getEntityWorld().getTileEntity(new BlockPos(x,y,z));
			
			if(te instanceof TileEntityGasum) {				
				NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
	            sender.sendMessage(new TextComponentString("Gas NBT: " + nbt.toString()));
			} else {
				sender.sendMessage(new TextComponentString("No gas here :("));
			}
	}

	@Override
	public String getName() {
		return "getgas";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "command.possiblyenoughelements.getgas.usage";
	}

}
