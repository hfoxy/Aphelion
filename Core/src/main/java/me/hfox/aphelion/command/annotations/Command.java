package me.hfox.aphelion.command.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] aliases();

    String description();

    String usage() default "";

    int min() default -1;

    int max() default -1;

    String valueFlags() default "";

    String flags() default "";

}
