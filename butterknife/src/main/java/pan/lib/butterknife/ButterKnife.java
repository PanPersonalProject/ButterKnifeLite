package pan.lib.butterknife;

import android.app.Activity;


/**
 * Author:         pan qi
 * CreateDate:     2020/4/23 18:00
 */
public class ButterKnife {
    private static final String ACTIVITY_VIEW_BINDING_SUFFIX = "$ViewBinding";

    public static void bind(Activity activity) {
        String companyClassName = activity.getClass().getName() + ACTIVITY_VIEW_BINDING_SUFFIX;
        try {
            Class<?> companyClass = Class.forName(companyClassName);
            companyClass.getDeclaredConstructor(activity.getClass()).newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
