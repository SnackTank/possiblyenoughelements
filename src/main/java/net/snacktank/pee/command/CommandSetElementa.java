package net.snacktank.pee.command;


import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSetElementa extends CommandBase{

	/*setelement <id>
	 * id 0: read NBT			| /setelement 0
	 * id 1: set allergies		| /setelement 1 <name> <allergy-length> <allergies> <display-name-length> <display-name>
	 *                  0 1    2 3 4 5 6     7  8
	 * exp: /setelement 1 test 2 1 2 3 Block of Test
	 *      /setelement 1 natrium 2 8 9 3 Block of Sodium
	*/
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		EntityPlayer player = sender.getCommandSenderEntity().getEntityWorld().getPlayerEntityByUUID(sender.getCommandSenderEntity().getUniqueID());
		int id = Integer.parseInt(params[0]);
		if(id == 0) {
			String message = getNBT(player);
			if(message == null) return;
			sender.sendMessage(new TextComponentString(message));
			return;
		}
		
		//We're assuming the ID is 1 or above
		String name = params[1];
		int allergyLength = Integer.parseInt(params[2]);
		int displayNameLength = Integer.parseInt(params[3+allergyLength]);
		String[] displayName = new String[displayNameLength];
		int[] allergies = new int[allergyLength];
		for(int i = 0; i < allergyLength; i++) {
			allergies[i] = Integer.parseInt(params[i+3]);
		}
		for(int i = 0; i < displayNameLength; i++) {
			displayName[i] = params[i+(4+allergyLength)];
		}
		String nameDisplay = arrayToSingleString(displayName);
		
		//We're just going to override the NBT data, even if it already exists!
		NBTTagCompound blockEntityNBT = new NBTTagCompound();
		NBTTagCompound displayNBT = new NBTTagCompound();
		NBTTagCompound finalCompound = new NBTTagCompound();
		
		blockEntityNBT.setIntArray("allergies", allergies);
		blockEntityNBT.setString("name", name);
		
		displayNBT.setString("Name", nameDisplay);
		
		finalCompound.setTag("BlockEntityTag", blockEntityNBT);
		finalCompound.setTag("display", displayNBT);
		
		player.getHeldItemMainhand().setTagCompound(finalCompound);
	}

	private String getNBT(EntityPlayer player) {
		Item item = player.getHeldItemMainhand().getItem();
		NBTTagCompound nbt = item.getNBTShareTag(player.getHeldItemMainhand());
		if(nbt == null) return null;
		return nbt.toString();
	}
	
	private String arrayToSingleString(String[] stringIn) {
		String stringOut = "";
		
		for(int i = 0; i < stringIn.length; i++) {
			stringOut += stringIn[i] + " ";
		}
		stringOut.trim();
		
		return stringOut;
	}
	
	@Override
	public String getName() {
		return "setelement";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "TODO FIX THIS";
	}

}
