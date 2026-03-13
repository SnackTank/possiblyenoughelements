package net.snacktank.pee.client;

import net.snacktank.pee.PossiblyEnoughElements;
import net.snacktank.pee.init.ModBlocks;
import net.snacktank.pee.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = PossiblyEnoughElements.MODID)
public class ModelRegistrationHandler {
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(ModItems.FIRST_ITEM, 0);
		registerModel(ModItems.NATRIUM, 0);
		registerModel(Item.getItemFromBlock(ModBlocks.FIRST_BLOCK), 0);
		registerModel(Item.getItemFromBlock(ModBlocks.CUBUS_NATRII), 0);
		registerModel(Item.getItemFromBlock(ModBlocks.CUBUS_HYDROGENII), 0);
		registerModel(Item.getItemFromBlock(ModBlocks.CUBUS_GASI), 0);
	}

	private static void registerModel(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, 
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
