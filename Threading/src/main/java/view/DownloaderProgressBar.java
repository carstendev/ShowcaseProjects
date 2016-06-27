/**
 * 
 */
package view;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


/**
 * Class that represents the progress bar
 * 
 * @author c.timpert
 * 
 */
public class DownloaderProgressBar extends JProgressBar {

	private static final int MINIMUM = 0;
	private static final int MAXIMUM = 100;


	public DownloaderProgressBar() {
		super(MINIMUM, MAXIMUM);
		setStringPainted(true);
		setBorder(BorderFactory.createTitledBorder("Download file"));
	}

	/**
	 * Updates the progess bar on the edt after all pending edt processes are done
	 * 
	 * @param percentage the new percentage
	 */
	public void updateBar(final int percentage) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setValue(percentage);
			}
		});
	}

}
