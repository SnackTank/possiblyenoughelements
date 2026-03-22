package net.snacktank.pee;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.snacktank.pee.command.CommandGasum;
import net.snacktank.pee.command.CommandSetGasum;
import net.snacktank.pee.recipes.ModRecipes;

@Mod(modid = PossiblyEnoughElements.MODID, name = PossiblyEnoughElements.NAME, version = PossiblyEnoughElements.VERSION, acceptedMinecraftVersions = PossiblyEnoughElements.MC_VERSION)
public class PossiblyEnoughElements {

	public static final String MODID = "possiblyenoughelements";
	public static final String NAME = "Possibly Enough Elements";
	public static final String VERSION = "0.2.0";
	public static final String MC_VERSION = "[1.12.2]";
	
	public static final Logger LOGGER = LogManager.getLogger(PossiblyEnoughElements.MODID);
	public static final CreativeTabs PEE_DRAWER = new PossiblyEnoughElementsTab();

	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.info(PossiblyEnoughElements.NAME + " v" + PossiblyEnoughElements.VERSION + " loaded.");
		ModRecipes.initSmelting();
		
	}
    @EventHandler
    public void init(FMLServerStartingEvent event) {
      event.registerServerCommand(new CommandGasum());
      event.registerServerCommand(new CommandSetGasum());
    }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
        
	}
}