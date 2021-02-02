package net.miniscape.command;

import net.miniscape.player.Rank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] names();

    Rank rank() default Rank.NONE;

    boolean enabled() default true;
}