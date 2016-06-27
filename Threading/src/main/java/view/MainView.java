package view;

import info.clearthought.layout.TableLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Model.MainViewModel;
import controller.MainViewController;

/**
 * Class that represents the main view (frame). It is not designed to be pretty atm and still needs alot of work.
 * 
 * @author c.timpert
 * 
 */
public class MainView extends JFrame implements Observer, ActionListener, DocumentListener {

	private MainViewController controller;
	private JTextField saveLocationTextField = new JTextField();
	private JTextField downloadLinkTextField = new JTextField();
	private JLabel linkLabel = new JLabel("Link: ");
	private JLabel locationLabel = new JLabel("Save Location: ");
	private final DownloaderProgressBar progressBar;


	public MainView() {
		super("Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(620, 170);
		setResizable(false);
		TableLayout layout = new TableLayout();
		double[] rows = { 0.05, 0.2, 0.05, 0.15, 0.15, 0.35 };
		double[] columns = { 5, 100, 100, 190, 150, 50, 5 };
		layout.setRow(rows);
		layout.setColumn(columns);
		setLocationRelativeTo(null);
		progressBar = new DownloaderProgressBar();
		Container contentPane = getContentPane();
		contentPane.setLayout(layout);

		saveLocationTextField.setEditable(false);
		contentPane.add(saveLocationTextField, "2, 1, 4, 1");
		contentPane.add(locationLabel, "1, 1");
		contentPane.add(initButton("Chooser", null), "5, 1");

		contentPane.add(linkLabel, "1, 3");
		downloadLinkTextField.getDocument().addDocumentListener(this);
		contentPane.add(downloadLinkTextField, "2, 3, 5, 3");

		contentPane.add(progressBar, "1, 5, 5, 5");
		contentPane.add(initButton("Start", null), "5, 4");

	}

	/**
	 * Initializes a JButton.
	 * The given name is also used as the action command.
	 * 
	 * @param name the text and action command
	 * @param icon the icon
	 * @return the initialized JButton
	 */
	private JButton initButton(String name, Icon icon) {
		JButton button = new JButton(name, icon);
		button.addActionListener(this);
		button.setActionCommand(name);
		return button;
	}

	/**
	 * Called when the model notifies its observers
	 */
	@Override
	public void update(Observable o, Object arg) {
		saveLocationTextField.setText(((MainViewModel) o).getSaveLocation());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			controller.startDownload();
		} else if (e.getActionCommand().equals("Chooser")) {
			controller.showFileChooser();
		}
	}

	/**
	 * Adds a Controller to the View
	 */
	public void addController(MainViewController controller) {
		this.controller = controller;
	}

	/**
	 * Return a reference to the Progressbar
	 * 
	 * @return
	 */
	public DownloaderProgressBar getProgressBar() {
		return progressBar;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		controller.setDonwloadLink(downloadLinkTextField.getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		controller.setDonwloadLink(downloadLinkTextField.getText());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		controller.setDonwloadLink(downloadLinkTextField.getText());
	}
}
