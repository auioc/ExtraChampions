package org.auioc.mcmod.extrachampions.compat.pehkui;

import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public class PehkuiCompat {

    public static void setSizeScale(Entity entity, float scale) {
        setScale(entity, ScaleTypes.HEIGHT, scale);
        setScale(entity, ScaleTypes.WIDTH, scale);
    }

    public static void setScale(Entity entity, ScaleType scaleType, float scale) {
        var scaleData = scaleType.getScaleData(entity);
        scaleData.setTargetScale(scale);
        scaleData.setScaleTickDelay(0);
    }

}
