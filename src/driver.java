/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
import core.MainScreen;
import core.Properties;

public class driver {

	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir");
		String userDir = System.getProperty("user.home");
		Properties properties = new Properties(currentDir, userDir);
		MainScreen screen = new MainScreen(properties);
		screen.init();
	}
}