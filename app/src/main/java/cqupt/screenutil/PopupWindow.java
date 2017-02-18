package cqupt.screenutil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PopupWindow {
    Context mContext;

    public PopupWindow(Context context) {
        mContext = context;
    }

    private void popupShow(View view) {
        //设置popupView显示的View
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View mPopupView = mLayoutInflater.inflate(null, null);

        int density = (int) ScreenUtil.getDeviceDensity(mContext);
        android.widget.PopupWindow popupWindow =
                new android.widget.PopupWindow(mPopupView, 200 * density, 50 * density);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        Drawable transpent = new ColorDrawable(Color.TRANSPARENT);
        popupWindow.setBackgroundDrawable(transpent);

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(
                view,
                Gravity.NO_GRAVITY,
                location[0] - 40 * density,
                location[1] + 30 * density);
    }
}
