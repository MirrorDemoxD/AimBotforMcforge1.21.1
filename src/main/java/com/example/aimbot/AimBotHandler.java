package com.example.aimbot;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AimBotMod.MODID, value = Dist.CLIENT)
public class AimBotHandler {
    private static boolean enabled = false;
    private static double smoothing = ClientConfig.INSTANCE.smoothing.get();
    private static double maxRange = ClientConfig.INSTANCE.maxRange.get();

    public static boolean isEnabled() { return enabled; }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.toggleKey.consumeClick()) enabled = !enabled;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        Player nearest = null;
        double nearestDistSq = Double.MAX_VALUE;
        AABB searchBox = mc.player.getBoundingBox().inflate(maxRange);

        for (Player player : mc.level.players()) {
            if (player == mc.player || player.isCreative() || player.isSpectator()) continue;
            if (!searchBox.intersects(player.getBoundingBox())) continue;
            double distSq = mc.player.distanceToSqr(player);
            if (distSq < nearestDistSq) {
                nearestDistSq = distSq;
                nearest = player;
            }
        }
        if (nearest == null) return;

        Vec3 targetPos = nearest.getEyePosition(1.0f);
        Vec3 playerPos = mc.player.getEyePosition(1.0f);
        Vec3 delta = targetPos.subtract(playerPos);
        double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        float targetYaw = (float) Math.toDegrees(Math.atan2(-delta.x, delta.z));
        float targetPitch = (float) Math.toDegrees(Math.atan2(-delta.y, horizontal));

        if (smoothing > 0) {
            float currentYaw = mc.player.getYRot();
            float currentPitch = mc.player.getXRot();
            float diffYaw = targetYaw - currentYaw;
            float diffPitch = targetPitch - currentPitch;
            while (diffYaw > 180) diffYaw -= 360;
            while (diffYaw < -180) diffYaw += 360;
            float factor = (float) (1.0 - smoothing);
            targetYaw = currentYaw + diffYaw * factor;
            targetPitch = currentPitch + diffPitch * factor;
        }
        mc.player.setYRot(targetYaw);
        mc.player.setXRot(targetPitch);
    }
}
