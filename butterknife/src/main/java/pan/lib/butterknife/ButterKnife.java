package pan.lib.butterknife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import pan.lib.butterknife_annotation.BindView;


/**
 * Author:         panqi
 * CreateDate:     2020/4/23 18:00
 */
public class ButterKnife {


    public static void bind(Activity activity) {
        ArrayList<Field> fieldList = new ArrayList<>();
        Class currentClass = activity.getClass();
        while (currentClass != null) {
            // getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
            // getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
            Field[] declaredFields = currentClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            //获得子类和所有父类的declaredFields
            currentClass = currentClass.getSuperclass();
        }


        for (Field field : fieldList) {
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
                field.set(activity, view);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
