package application;


import Model.MainViewModel;
import controller.MainViewController;
import view.MainView;

/**
 * A testapplication that showcases the use of a progress bar that can keep track of a download.
 * The application loosely uses the mvc pattern
 * 
 * @author c.timpert
 * 
 */
public class TestApplication {

	public static void main(String[] args) throws Exception {
		MainViewModel model = new MainViewModel();
		MainView view = new MainView();
		MainViewController controller = new MainViewController(view, model);
		view.addController(controller);
		view.setVisible(true);
	}
}
