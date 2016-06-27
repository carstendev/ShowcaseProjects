/**
 * 
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Class that provides util methods for downloading
 * 
 * @author c.timpert
 * 
 */
public final class DownloaderUtils {

	public static int getFileSize(URL url) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.getInputStream();
			return connection.getContentLength();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String getFileName(String saveDirectory, URL url) {
		String fileName = url.getFile();
		return saveDirectory + "/"+ fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	public static InputStream downloadFile(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("Server returned error response code " + connection.getResponseCode());
		}
		return connection.getInputStream();
	}
}
