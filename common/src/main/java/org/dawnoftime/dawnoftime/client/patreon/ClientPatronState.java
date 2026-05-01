package org.dawnoftime.dawnoftime.client.patreon;

public class ClientPatronState {
    // Written by S2C packet handler (netty thread), read by render thread
    public static volatile int playerTier = 0;
}
