package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import java.util.Random;

public class ExplodableProperties {
    
    public static final ExplodableProperties DEFAULT = new ExplodableProperties(4);
    
    public final float power;
    public final int fuse;
    public final float initialMotion;
    
    public ExplodableProperties(float power) {
        this(power, 80);
    }
    
    public ExplodableProperties(float power, int fuse) {
        this(power, fuse, 0.02f);
    }
    
            
    public ExplodableProperties(float power, int fuse, float initialMotion) {
        this.power = power;
        this.fuse = fuse;
        this.initialMotion = initialMotion;
    }
    
    public int reducedFuse(Random rand) {
        return Math.max(20, (this.fuse / 8) + rand.nextInt(this.fuse / 4));
    }
}
