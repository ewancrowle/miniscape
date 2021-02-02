package net.miniscape.islandescape.util.phase;

import lombok.Data;

@Data
public abstract class Phase {
    private boolean started = false;
    private boolean ended = false;

    public abstract void start();

    public abstract void end();
}
