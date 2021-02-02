package net.miniscape.util.guice;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ScopeAnnotation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Manager {
    String name();

    boolean enabled() default true;
}
