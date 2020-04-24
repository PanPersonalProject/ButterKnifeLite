package pan.lib.butterknife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import pan.lib.butterknife_annotation.BindView;


/**
 * Author:         panqi
 * CreateDate:     2020/4/23 18:00
 */
public class ButterKnife {


    public static void bind(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();

        // getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
        // getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields) {
            bindingView(activity, field);
        }


    }

    private static void bindingView(Activity activity, Field field) {
        BindView bindView = field.getAnnotation(BindView.class);
        if (bindView != null) {
            int resId = bindView.value();
            View view = activity.findViewById(resId);
            try {
                //这里因为field.set()方法是私有的，不能直接赋值，需要先设置该属性的访问状态
                field.setAccessible(true);
                field.set(activity,view);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
