package observer;

import java.util.Observable;
import java.util.Observer;
import view.MainView;

/**
 * Acts as an observer and mediator for the {@link tasks.DownloadTask} and the {@link view.DownloaderProgressBar}
 * 
 * @author c.timpert
 * 
 */
public class DownloadObserver implements Observer {

	private MainView view;

	public DownloadObserver(MainView view) {
		this.view = view;
	}

	/**
	 * Called when the download task notifies its observers
	 */
	@Override
	public void update(Observable o, Object arg) {
		Double percentage = (Double) arg;
		view.getProgressBar().updateBar(percentage.intValue());
	}
}
