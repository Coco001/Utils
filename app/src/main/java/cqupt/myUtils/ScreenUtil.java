package cqupt.myUtils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtil {

    // 获取旧分辨率
    public static String getOldLcd(Activity activity) {
        StringBuffer buffer = new StringBuffer();
        int density = 0;
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            density = dm.densityDpi;
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        buffer.append(heightPixels);
        buffer.append("*");
        buffer.append(widthPixels);
        buffer.append(",");
        buffer.append(density);
        buffer.append("dpi");
        return buffer.toString();
    }

    // 获取新分辨率
    public static String getNewLcd(Activity activity) {
        StringBuffer buffer = new StringBuffer();
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        buffer.append(heightPixels);
        buffer.append("*");
        buffer.append(widthPixels);
        return buffer.toString();
    }

    // 获取手机屏幕宽高乘积
    public static int getScreenSize(Activity activity) {
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        return widthPixels * heightPixels;
    }

    /**
     * 获取屏幕相关参数
     * @param context 上下文
     * @return 屏幕的宽高
     */
    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static float getDeviceDensity(Context context) {
        return getScreenSize(context).density;
    }

}
