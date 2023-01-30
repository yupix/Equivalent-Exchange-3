package com.pahimar.ee3.handler;

import net.minecraftforge.event.world.WorldEvent;

import com.pahimar.ee3.exchange.DynamicEnergyValueInitThread;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

public class WorldEventHandler {

    public static boolean hasInitilialized = false;

    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event) {
        if (!hasInitilialized && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            DynamicEnergyValueInitThread.initEnergyValueRegistry();
            hasInitilialized = true;
        }
    }
}
