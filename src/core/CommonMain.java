/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class CommonMain extends ConfigMain{
	
	protected Properties properties;
	protected JFrame mainPage;
	protected JLayeredPane layeredPane;
	protected DisplayData displayData;
	
	protected JMenuBar menuBar;
	protected JMenu fileMenu, options, help;
	protected JMenuItem loadUser, newuser, exit, deleteUser, deleteAccomp,
		workflow, about;
		
	protected JScrollPane viewerPane, notePane;
	protected ListSelectionModel viewerLSM;
	protected JList<String> itemList;
	protected JButton objectives, accomplishments, add, action;
	protected JTextArea sideNote;
	
	protected ArrayList<String> currentList;
	protected int selection = -1;
	
	protected boolean created = false;
	protected boolean objectiveSelected = true;
	protected boolean accomplishSelected = false;
	protected boolean objectiveFlag = false;
	protected boolean accomplishFlag = false;
	protected boolean objectiveEmpty = true;
	protected boolean accomplishEmpty = true;
	protected boolean itemSelected = false;
}
