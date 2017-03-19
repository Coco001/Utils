package cqupt.myUtils;

/**
 * 网络请求回调的接口.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
