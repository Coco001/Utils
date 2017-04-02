package cqupt.myUtils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				Log.d("错误",e.getMessage());
			}
		}
		return true;
	}
}
