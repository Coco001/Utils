package cqupt.myUtils.listview;

import android.view.View;
import android.widget.TextView;

import cqupt.myUtils.R;

/**
 * 主页界面的holder.
 */

public class HomeHolder extends BaseHolder<String> {

    private TextView mTextView;

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.item_recyclerview);
        mTextView = (TextView) view.findViewById(R.id.item_tv);
        return view;
    }

    @Override
    public void refreshView(String data) {
        mTextView.setText(data);
    }
}
