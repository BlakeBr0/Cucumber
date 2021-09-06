package com.blakebr0.cucumber.helper;

import net.minecraft.world.phys.Vec3;

public final class VecHelper {
    public static Vec3 rotate(Vec3 vec, float yaw, float pitch, float roll) {
        double yawRadians = Math.toRadians(yaw);
        double pitchRadians = Math.toRadians(pitch);
        double rollRadians = Math.toRadians(roll);

        double xPos = vec.x * Math.cos(yawRadians) * Math.cos(pitchRadians) + vec.z * (Math.cos(yawRadians) * Math.sin(pitchRadians) * Math.sin(rollRadians) - Math.sin(yawRadians) * Math.cos(rollRadians)) + vec.y * (Math.cos(yawRadians) * Math.sin(pitchRadians) * Math.cos(rollRadians) + Math.sin(yawRadians) * Math.sin(rollRadians));
        double zPos = vec.x * Math.sin(yawRadians) * Math.cos(pitchRadians) + vec.z * (Math.sin(yawRadians) * Math.sin(pitchRadians) * Math.sin(rollRadians) + Math.cos(yawRadians) * Math.cos(rollRadians)) + vec.y * (Math.sin(yawRadians) * Math.sin(pitchRadians) * Math.cos(rollRadians) - Math.cos(yawRadians) * Math.sin(rollRadians));
        double yPos = -vec.x * Math.sin(pitchRadians) + vec.z * Math.cos(pitchRadians) * Math.sin(rollRadians) + vec.y * Math.cos(pitchRadians) * Math.cos(rollRadians);

        return new Vec3(xPos, yPos, zPos);
    }
}
