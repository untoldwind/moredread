package net.untoldwind.moredread.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Just a marker for classes that are supposed to be singletons.
 * 
 * Singletons must not contain any non-final fields.
 * 
 * @author junglas
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Singleton {

}
