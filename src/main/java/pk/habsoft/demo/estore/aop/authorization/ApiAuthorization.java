package pk.habsoft.demo.estore.aop.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pk.habsoft.demo.estore.enums.PermissionType;

/**
 * Annotation to check users's permission
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiAuthorization {
	PermissionType[] permissions() default {};
}
