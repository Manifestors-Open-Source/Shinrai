package com.manifestors.shinrai.client.module.annotations;

import com.manifestors.shinrai.client.module.ModuleCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleData {

    String name();
    String description() default "";
    ModuleCategory category();
    int keyCode() default 0;

}
