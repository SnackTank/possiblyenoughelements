package net.snacktank.pee;

import net.snacktank.pee.util.RegistryUtil;
import net.snacktank.pee.init.ModBlocks;
import net.snacktank.pee.item.ItemNatrium;
import net.snacktank.pee.tileentity.TileEntityGasum;
import net.snacktank.pee.tileentity.TileEntityHydrogenium;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.snacktank.pee.block.BlockGasum;
import net.snacktank.pee.block.BlockHydrogenium;
import net.snacktank.pee.block.BlockNatrium;

@EventBusSubscriber(modid = PossiblyEnoughElements.MODID)
public class RegistrationHandler {
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		final Item[] items = {
				new Item().setRegistryName(PossiblyEnoughElements.MODID, "first_item").setUnlocalizedName(PossiblyEnoughElements.MODID + "." + "first_item").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER),              
				new ItemNatrium(0, 0, false)
				//new Item().setRegistryName(PossiblyEnoughElements.MODID, "natrium").setUnlocalizedName(PossiblyEnoughElements.MODID + "." + "natrium").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER)
		};
		final Item[] itemBlocks = {
				new ItemBlock(ModBlocks.FIRST_BLOCK).setRegistryName(ModBlocks.FIRST_BLOCK.getRegistryName()),
				new ItemBlock(ModBlocks.CUBUS_NATRII).setRegistryName(ModBlocks.CUBUS_NATRII.getRegistryName()),
				new ItemBlock(ModBlocks.CUBUS_HYDROGENII).setRegistryName(ModBlocks.CUBUS_HYDROGENII.getRegistryName()),
				new ItemBlock(ModBlocks.CUBUS_GASI).setRegistryName(ModBlocks.CUBUS_GASI.getRegistryName())
		};

		event.getRegistry().registerAll(items);
		event.getRegistry().registerAll(itemBlocks);
	}
	

	private static void registerTileEntity(@Nonnull final Class<? extends TileEntity> clazz, @Nonnull final String name) {
		GameRegistry.registerTileEntity(clazz, new ResourceLocation(PossiblyEnoughElements.MODID, name));
	}
	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		final Block[] blocks = {
				RegistryUtil.setBlockName(new Block(Material.ROCK), "first_block").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER),
				//RegistryUtil.setBlockName(new Block(Material.CAKE), "cubus_natrii").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER),
				//RegistryUtil.setBlockName(new Block(Material.AIR), "cubus_hydrogenii").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER)
				new BlockNatrium(),
				new BlockHydrogenium(),
				new BlockGasum(),
				
		};
		//Tile Entities
		registerTileEntity(TileEntityHydrogenium.class, "cubus_hydrogenii");
		registerTileEntity(TileEntityGasum.class, "cubus_gasi");
		
		event.getRegistry().registerAll(blocks);
	}
}
