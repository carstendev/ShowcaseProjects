/**
 * 
 */
package tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import javax.swing.SwingWorker;
import application.State;
import util.DownloaderUtils;


/**
 * Observable Class that represents the download Task
 * 
 * @author c.timpert
 * 
 */
public class DownloadTask extends Observable {

	// Max size of download buffer.
	private static final int MAX_BUFFER_SIZE = 1024;

	private final URL url;
	private final String saveDirectory;
	private double percentage = 0;
	private double downloadSize = 0;
	private double downloaded = 0;
	private Task workerThread;
	private State state;

	public DownloadTask(URL url, String saveDirectory) throws IOException {
		this.url = url;
		this.saveDirectory = saveDirectory;
		this.downloadSize = DownloaderUtils.getFileSize(url);
	}

	/**
	 * Updates the percentage already downloaded and notifies all observers its state has changed
	 * 
	 * @param newPercentage
	 */
	private void updatePercentage(double newPercentage) {
		percentage = newPercentage;
		setChanged();
		notifyObservers(Double.valueOf(percentage));
	}

	/**
	 * Starts the download process
	 * 
	 * @throws Exception
	 */
	public synchronized void start() throws Exception {
		if (workerThread == null) {
			workerThread = new Task();
		}
		workerThread.execute();
	}

	private synchronized void changeState(State state) {
		this.state = state;
	}

	public synchronized State getState() {
		return state;
	}

	private class Task extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() {
			changeState(State.RUNNING);

			try (InputStream stream = DownloaderUtils.downloadFile(url)) {

				while (state == State.RUNNING) {
					byte buffer[];
					if (downloadSize - downloaded > MAX_BUFFER_SIZE) {
						buffer = new byte[MAX_BUFFER_SIZE];
					} else {
						buffer = new byte[(int) (downloadSize - downloaded)];
					}

					// Read from server into buffer.
					int read = stream.read(buffer);
					if (read == -1) {
						break;
					}
					// Write buffer to file.
					Files.write(Paths.get(DownloaderUtils.getFileName(saveDirectory, url)), buffer, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					downloaded += read;

					updatePercentage((downloaded / downloadSize) * 100);
				}

				if (state == State.RUNNING) {
					updatePercentage(downloaded / downloadSize * 100);
				}
			} catch (Exception e) {
				e.printStackTrace();
				changeState(State.ERROR);
			}
			return null;
		}

		@Override
		protected void done() {
			changeState(State.FINISHED);
		}
	}
}
