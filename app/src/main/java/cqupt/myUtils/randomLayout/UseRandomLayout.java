package cqupt.myUtils.randomLayout;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import cqupt.myUtils.R;
import cqupt.myUtils.UIUtils;

/**
 * 随机飞入布局的简单使用.
 */

public class UseRandomLayout {

    private ArrayList<String> mData;

    public void setData(ArrayList<String> data) {
        this.mData = data;
    }

    public View onCreateSuccessView() {
        final StellarMap view = (StellarMap) View.inflate(UIUtils.getContext(), R.layout.fragment_recommend, null);
        RecAdapter adapter = new RecAdapter();
        view.setAdapter(adapter);
        view.setRegularity(6, 9);//9行6列的格子
        int padding = UIUtils.dp2px(18);
        view.setInnerPadding(padding, padding, padding, padding);//设置内边界
        view.setGroup(0, true);//设置第一页显示
        //设置摇晃的监听事件
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                view.zoomIn();//跳转到下一页
            }
        });
        return view;
    }

    class RecAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {//返回分组的个数
            return 2;
        }

        @Override
        public int getCount(int group) {//返回每组显示的组数
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {//返回最终显示的view
            position += group * getCount(group - 1);
            String text = mData.get(position);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(text);
            Random random = new Random();
            int size = 18 + random.nextInt(10);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);//设置字体大小
            //设置颜色
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(222);
            int b = 30 + random.nextInt(200);
            textView.setTextColor(Color.rgb(r, g, b));
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {//返回下一组显示平移动画的组别
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {//返回下一组显示缩放动画的组别
            if (isZoomIn) {
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1;
                }
            } else {
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    group = 0;
                }
            }
            return group;
        }
    }
}
