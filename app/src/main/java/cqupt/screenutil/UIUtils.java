package cqupt.screenutil;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/**
 * 操控UI界面的工具类.
 */

public class UIUtils {
    public static Context getContext(){
        return MyApplication.context;
    }

    public static Handler getHandler(){
        return MyApplication.handler;
    }

    //返回指定colorId对应的颜色值
    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    //加载指定viewId的视图对象，并返回
    public static View getView(int viewId){
        View view = View.inflate(getContext(), viewId, null);
        return view;
    }

    public static String[] getStringArr(int strArrId){
        String[] stringArray = getContext().getResources().getStringArray(strArrId);
        return stringArray;
    }

    //将dp转化为px
    public static int dp2px(int dp){
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);//实现四舍五入
    }

    public static int px2dp(int px){
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);//实现四舍五入
    }

    public static void runOnUiThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            UIUtils.getHandler().post(runnable);
        }
    }

    private static boolean isInMainThread() {
        int currentThreadId = android.os.Process.myTid();
        return MyApplication.mainThreadId == currentThreadId;
    }

    public static void toast(String message, Boolean isLong) {
        Toast.makeText(UIUtils.getContext(), message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
    }

}
