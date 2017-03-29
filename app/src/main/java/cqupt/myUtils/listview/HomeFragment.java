package cqupt.myUtils.listview;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * 测试
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<String> data;
	@Override
	public View onCreateSuccessView() {
		ListView view = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter(data);
		view.setAdapter(adapter);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		data = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			data.add("测试数据" + i);
		}
		return LoadingPage.ResultState.STATE_SUCCESS;
	}

	class HomeAdapter extends MyBaseAdapter<String> {
		public HomeAdapter(ArrayList<String> data) {
			super(data);
		}

		@Override
		protected ArrayList<String> onLoadMore() {
			ArrayList<String> moredata = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				moredata.add("测试更多" + i);
			}
			SystemClock.sleep(2000);

			return moredata;
		}

		@Override
		protected BaseHolder getHolder(int position) {
			return new HomeHolder();
		}

	}

}
