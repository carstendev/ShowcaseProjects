package controller;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import observer.DownloadObserver;
import Model.MainViewModel;
import tasks.DownloadTask;
import view.MainView;

/**
 * Class thats acts as a controller for the MainView class and handles its events.
 * 
 * @author c.timpert
 * 
 */
public class MainViewController {

	private MainView view;
	private MainViewModel model;

	public MainViewController(MainView view, MainViewModel model) {
		this.model = model;
		this.view = view;
		model.addObserver(view);
	}

	/**
	 * Opens a JFileChooser and saves the chosen directory to the model if one was chosen
	 */
	public void showFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Choose Location: ");
		fileChooser.setPreferredSize(new Dimension(450, 300));
		fileChooser.setVisible(true);
		int option = fileChooser.showOpenDialog(view);
		if (option == JFileChooser.APPROVE_OPTION) {
			File directory = fileChooser.getSelectedFile();
			model.setSaveLocation(directory.getAbsolutePath().replaceAll("\\\\", "/"));
		}
	}

	/**
	 * Saves the downloadlink into the model
	 * 
	 * @param link the link to save
	 */
	public void setDonwloadLink(String link) {
		model.setDownloadLink(link);
	}

	/**
	 * Start the download process
	 */
	public void startDownload() {
		String downloadLink = model.getDownloadLink();
		String saveLocation = model.getSaveLocation();
		try {
			DownloadTask task = new DownloadTask(new URL(downloadLink), saveLocation);
			DownloadObserver observer = new DownloadObserver(view);
			task.addObserver(observer);
			task.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
