package pan.lib.butterknife;

import android.app.Activity;


/**
 * Author:         pan qi
 * CreateDate:     2020/4/23 18:00
 */
public class ButterKnife {
    private static final String ACTIVITY_VIEW_BINDING_SUFFIX = "$ViewBinding";

    public static void bind(Activity activity) {
        Class activityClass = activity.getClass();

        /*
         * 循环查找当前Activity和它父类Activity的所有$ViewBinding文件
         * Activity.class.isAssignableFrom(activityClass)  判断当前类是Activity或者Activity的子类
         * */
        while (activityClass != null && Activity.class.isAssignableFrom(activityClass)) {
            String companyClassName = activityClass.getName() + ACTIVITY_VIEW_BINDING_SUFFIX;
            try {
                Class<?> companyClass = Class.forName(companyClassName);
                companyClass.getDeclaredConstructor(activityClass).newInstance(activity);
                activityClass = activityClass.getSuperclass();
            } catch (ClassNotFoundException e) {
                activityClass = activityClass.getSuperclass();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
