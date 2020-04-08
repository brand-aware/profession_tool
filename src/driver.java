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
		if(args.length != 1){
			System.out.println("required format: java driver <rootDir>");
			System.exit(0);
		}
		Properties properties = new Properties(args[0]);
		MainScreen screen = new MainScreen(properties);
		screen.init();
	}

}