package com.pahimar.ee3.util;

import net.minecraft.entity.player.EntityPlayer;

import com.pahimar.ee3.network.PacketHandler;
import com.pahimar.ee3.network.message.MessageSoundEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonSoundHelper {

    public static void playSoundAtLocation(int dimensionId, float xCoord, float yCoord, float zCoord, String soundName,
            float volume, float pitch) {
        playSoundAtLocation(dimensionId, xCoord, yCoord, zCoord, soundName, volume, pitch, 32d);
    }

    public static void playSoundAtLocation(int dimensionId, float xCoord, float yCoord, float zCoord, String soundName,
            float volume, float pitch, double range) {
        PacketHandler.INSTANCE.sendToAllAround(
                new MessageSoundEvent(soundName, xCoord, yCoord, zCoord, volume, pitch),
                new NetworkRegistry.TargetPoint(dimensionId, xCoord, yCoord, zCoord, range));
    }

    public static void playSoundAtPlayer(EntityPlayer entityPlayer, String soundName, float volume, float pitch) {
        playSoundAtPlayer(entityPlayer, soundName, volume, pitch, 32d);
    }

    public static void playSoundAtPlayer(EntityPlayer entityPlayer, String soundName, float volume, float pitch,
            double range) {
        PacketHandler.INSTANCE.sendToAllAround(
                new MessageSoundEvent(entityPlayer, soundName, volume, pitch),
                new NetworkRegistry.TargetPoint(
                        entityPlayer.worldObj.provider.dimensionId,
                        entityPlayer.posX,
                        entityPlayer.posY,
                        entityPlayer.posZ,
                        range));
    }
}
