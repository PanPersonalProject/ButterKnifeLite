package pan.lib.butterknife_annotation;

import androidx.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Author:         panqi
 * CreateDate:     2020/4/23 19:06
 */
@Retention(CLASS)
@Target(FIELD)
public @interface BindView {
    /** View ID to which the field will be bound. */
    @IdRes int value();
}
