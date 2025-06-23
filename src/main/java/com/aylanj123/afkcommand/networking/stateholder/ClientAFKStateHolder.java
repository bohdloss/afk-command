package com.aylanj123.afkcommand.networking.stateholder;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientAFKStateHolder {

    public static boolean afk = false;
    public static int timeIdle = Integer.MAX_VALUE;
    public static int currentIdleTime = 0;

}
