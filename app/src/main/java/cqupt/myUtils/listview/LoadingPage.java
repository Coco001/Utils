package cqupt.myUtils.listview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import cqupt.myUtils.R;

/**
 * 加载页面 - 正在加载 - 加载失败 - 数据为空 - 访问成功
 */
public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_UNLOAD = 0;// 未加载
    private static final int STATE_LOADING = 1;// 正在加载
    private static final int STATE_LOAD_EMPTY = 2;// 数据为空
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_SUCCESS = 4;// 访问成功

    private int mCurrentState = STATE_UNLOAD;// 当前状态

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化布局
    private void initView() {
        if (mLoadingView == null) {// 正在加载
            mLoadingView = onCreateLoadingView();
            addView(mLoadingView);
        }

        if (mErrorView == null) {// 加载失败
            mErrorView = onCreateErrorView();
            mErrorView.findViewById(R.id.btn_retry).setOnClickListener(// 点击重试
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            loadData();
                        }
                    });
            addView(mErrorView);
        }

        if (mEmptyView == null) {// 数据为空
            mEmptyView = onCreateEmptyView();
            addView(mEmptyView);
        }

        showRightPage();
    }

    //根据当前状态,展示正确页面
    private void showRightPage() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility((mCurrentState == STATE_LOADING || mCurrentState == STATE_UNLOAD) ? View.VISIBLE : View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }

        if (mErrorView != null) {
            mErrorView.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }

        // 访问成功
        if (mSuccessView == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessView = onCreateSuccessView();
            if (mSuccessView != null) {// 防止子类返回null
                addView(mSuccessView);
            }
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //初始化访问成功布局, 子类必须实现
    public abstract View onCreateSuccessView();
    //加载网络数据，返回加载状态
    public abstract ResultState onLoad();

    //加载数据
    public void loadData() {
        // 状态归零
        if (mCurrentState == STATE_LOAD_EMPTY || mCurrentState == STATE_LOAD_ERROR || mCurrentState == STATE_LOAD_SUCCESS) {
            mCurrentState = STATE_UNLOAD;
        }

        if (mCurrentState == STATE_UNLOAD) {
            // 异步加载网络数据
            ThreadManager.getThreadPool().execute(new Runnable() {

                @Override
                public void run() {
                    final ResultState state = onLoad();// 开始加载网络数据
                    UIUtils.runOnUiThread(new Runnable() {// 必须在主线程更新界面

                        @Override
                        public void run() {
                            if (state != null) {
                                mCurrentState = state.getState();// 更新当前状态
                                showRightPage();// 更新当前页面
                            }
                        }
                    });
                }
            });
        }
    }

    //使用枚举表示访问网络的几种状态
    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS), // 访问成功
        STATE_EMPTY(STATE_LOAD_EMPTY), // 数据为空
        STATE_ERROR(STATE_LOAD_ERROR);// 访问失败
        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    //初始化正在加载布局
    private View onCreateLoadingView() {
        return UIUtils.inflate(R.layout.layout_loading);
    }

    //初始化加载失败布局
    private View onCreateErrorView() {
        return UIUtils.inflate(R.layout.layout_error);
    }

    //初始化数据为空布局
    private View onCreateEmptyView() {
        return UIUtils.inflate(R.layout.layout_empty);
    }

}
