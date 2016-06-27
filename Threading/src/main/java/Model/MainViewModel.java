package Model;

import java.util.Observable;

/**
 * Oberservable class that acts as the model for the MainView and MainViewController classes
 * 
 * @author c.timpert
 * 
 */
public class MainViewModel extends Observable {

	private String downloadLink;
	private String saveLocation;

	/**
	 * Returns the download link
	 */
	public String getDownloadLink() {
		return downloadLink;
	}

	/**
	 * Sets the download link and notifies all observers that its state has changed
	 * 
	 * @param downloadLink
	 */
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
		setChanged();
		notifyObservers();
	}

	/**
	 * Retuns the save location
	 * 
	 * @return
	 */
	public String getSaveLocation() {
		return saveLocation;
	}

	/**
	 * Sets the save location and notifies all observers that its state has changed
	 * 
	 * @param saveLocation
	 */
	public void setSaveLocation(String saveLocation) {
		this.saveLocation = saveLocation;
		setChanged();
		notifyObservers();
	}
}
