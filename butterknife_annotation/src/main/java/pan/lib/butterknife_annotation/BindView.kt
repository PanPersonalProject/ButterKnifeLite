package pan.lib.butterknife_annotation

import androidx.annotation.IdRes

/**
 *
 * Author:         panqi
 * CreateDate:     2020/4/23 17:15
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class BindView(
    /** View ID to which the field will be bound.  */
    @IdRes val value: Int
)
