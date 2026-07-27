package net.snacktank.pee.machine;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.snacktank.pee.PossiblyEnoughElements;
import net.snacktank.pee.tileentity.TileEntityGasum;

public class MachineElectrolysis {

	private ICommandSender sender;
	private EntityPlayer player;
	private World world;
	private BlockPos pos;
	private Chunk chunk;
	
	private LinkedList<BlockPos> machineBlockPos;
	private LinkedList<BlockPos> blackListBlockPos;
	private LinkedList<BlockPos[]> validMachine;
	private LinkedList<BlockPos[]> electrodes;
	
	public MachineElectrolysis(MinecraftServer server, ICommandSender sender) {
		this.sender = sender;
		
		world = server.getEntityWorld();		
		player = world.getPlayerEntityByUUID(sender.getCommandSenderEntity().getUniqueID());
		pos = player.getPosition();
		
		chunk = world.getChunkFromBlockCoords(pos);
		
		update();
		
		sendMessage("Valid machines: " + validMachine.size());
		for(int i = 0; i < validMachine.size(); i++) {
			BlockPos[] pos = validMachine.get(i);
			sendMessage(i + ": " + pos[0] + " " + pos[1] + " " + pos[2]);
		}
		
	}
	
	public void update() {
		getMachine();
		getElectrode();
		for(int i = 0; i < electrodes.size(); i++) {
			if(electrodes.get(i)[0] == null) continue;
			if(electrodes.get(i)[1] == null) continue;
			//Get Block state
			BlockPos anodePos = electrodes.get(i)[0];
			BlockPos cathodePos = electrodes.get(i)[1];
			Block hydrogen = Block.getBlockFromName(PossiblyEnoughElements.MODID + ":cubus_gasi");
			Block oxygen = Block.getBlockFromName(PossiblyEnoughElements.MODID + ":cubus_gasi");
			IBlockState anodeState = oxygen.getDefaultState();
			IBlockState cathodeState = hydrogen.getDefaultState();
			BlockPos newAnodePos = anodePos.up();
			BlockPos newCathodePos = cathodePos.up();
			
			//Place block
			world.setBlockState(newAnodePos, anodeState);
			world.setBlockState(newCathodePos, cathodeState);
			
			//Change the NBT data to change the output gas.
			TileEntity ote = sender.getEntityWorld().getTileEntity(newAnodePos);
			TileEntity hte = sender.getEntityWorld().getTileEntity(newCathodePos);
			((TileEntityGasum) ote).name = "oxygenium";
			((TileEntityGasum) ote).canFloat = true;
			((TileEntityGasum) ote).canExplode = false;
			((TileEntityGasum) ote).markDirty();
			
			((TileEntityGasum) hte).name = "hydrogenium";
			((TileEntityGasum) hte).canFloat = true;
			((TileEntityGasum) hte).canExplode = true;
			((TileEntityGasum) hte).markDirty();
			
		}
	}
	
	private void getElectrode() {
		electrodes = new LinkedList<BlockPos[]>();
		for(int i = 0; i < validMachine.size(); i++) {
			//0 Anode, 1 Cathode
			BlockPos[] electrodeBlocks = new BlockPos[2];
			BlockPos[] blockPos = new BlockPos[3];
			blockPos[0] = validMachine.get(i)[0];
			blockPos[1] = validMachine.get(i)[1];
			blockPos[2] = validMachine.get(i)[2];
			boolean isBlockApowered = world.isBlockPowered(blockPos[0]);
			boolean isBlockBpowered = world.isBlockPowered(blockPos[2]);
			
			if(isBlockApowered && isBlockBpowered) {
				electrodeBlocks[0] = null;
				electrodeBlocks[1] = null;
				electrodes.add(electrodeBlocks);
				continue;
			}
			if(isBlockApowered) {
				electrodeBlocks[0] = blockPos[0];
				electrodeBlocks[1] = blockPos[2];
				electrodes.add(electrodeBlocks);
				continue;
			}
			if(isBlockBpowered) {
				electrodeBlocks[0] = blockPos[2];
				electrodeBlocks[1] = blockPos[0];
				electrodes.add(electrodeBlocks);
				continue;
			}
			//If no block is powered
			electrodeBlocks[0] = null;
			electrodeBlocks[1] = null;
			electrodes.add(electrodeBlocks);
		}
	}
	
	private void getMachine() {
		validMachine = new LinkedList<BlockPos[]>();
		blackListBlockPos = new LinkedList<BlockPos>();
		machineBlockPos = new LinkedList<BlockPos>();
		
		int x = 0;
		int y = 0;
		int z = 0;
		boolean keepGoing = true;
		
		while(keepGoing) {
			Block block = chunk.getBlockState(x, y, z).getBlock();
			BlockPos pos = chunk.getPos().getBlock(x, y, z);
			int blockId = Block.getIdFromBlock(block);
			
			//Block Scanning Logic
			x++;
			if(x > 15) {
				x = 0;
				z++;
			}
			if(z > 15) {
				z = 0;
				x = 0;
				y++;
			}
			if(y > 255) {
				keepGoing = false;
			}
			
			if(blockId == 41) {
				if(blackListBlockPos.contains(pos)) {
					continue;
				}
				machineBlockPos.add(pos);
				
				//Check for the structure
				//[G] [W] [G]
				
				Block northBlock = world.getBlockState(pos.north()).getBlock();
				Block eastBlock = world.getBlockState(pos.east()).getBlock();
				Block southBlock = world.getBlockState(pos.south()).getBlock();
				Block westBlock = world.getBlockState(pos.west()).getBlock();
				
				Block northNorthBlock = world.getBlockState(pos.add(0, 0, -2)).getBlock();
				Block eastEastBlock = world.getBlockState(pos.add(2, 0, 0)).getBlock();
				Block southSouthBlock = world.getBlockState(pos.add(0, 0, 2)).getBlock();
				Block westWestBlock = world.getBlockState(pos.add(-2, 0, 0)).getBlock();
				
				int idNorth = Block.getIdFromBlock(northBlock);
				int idEast = Block.getIdFromBlock(eastBlock);
				int idSouth = Block.getIdFromBlock(southBlock);
				int idWest = Block.getIdFromBlock(westBlock);
				
				int idNorthNorth = Block.getIdFromBlock(northNorthBlock);
				int idEastEast = Block.getIdFromBlock(eastEastBlock);
				int idSouthSouth = Block.getIdFromBlock(southSouthBlock);
				int idWestWest = Block.getIdFromBlock(westWestBlock);
				
				if(idNorth == 9 && idNorthNorth == 41) {
					BlockPos[] machinePos = new BlockPos[3];
					machinePos[0] = pos;
					machinePos[1] = pos.north();
					machinePos[2] = pos.north().north();
					validMachine.add(machinePos);
					blackListBlockPos.add(machinePos[0]);
					blackListBlockPos.add(machinePos[2]);
					continue;
				}
				
				if(idEast == 9 && idEastEast == 41) {
					BlockPos[] machinePos = new BlockPos[3];
					machinePos[0] = pos;
					machinePos[1] = pos.east();
					machinePos[2] = pos.east().east();
					validMachine.add(machinePos);
					blackListBlockPos.add(machinePos[0]);
					blackListBlockPos.add(machinePos[2]);
					continue;
				}
				
				if(idSouth == 9 && idSouthSouth == 41) {
					BlockPos[] machinePos = new BlockPos[3];
					machinePos[0] = pos;
					machinePos[1] = pos.south();
					machinePos[2] = pos.south().south();
					validMachine.add(machinePos);
					blackListBlockPos.add(machinePos[0]);
					blackListBlockPos.add(machinePos[2]);
					continue;
				}
				
				if(idWest == 9 && idWestWest == 41) {
					BlockPos[] machinePos = new BlockPos[3];
					machinePos[0] = pos;
					machinePos[1] = pos.west();
					machinePos[2] = pos.west().west();
					validMachine.add(machinePos);
					blackListBlockPos.add(machinePos[0]);
					blackListBlockPos.add(machinePos[2]);
					continue;
				}
				
			}
		}
		

	}

	private void sendMessage(String message) {
		sender.sendMessage(new TextComponentString(message));
	}
	
}
