/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainScreen extends IOMain{
	
	private MenuListener menuHandler;
	private ButtonListener handler;
	private ListSelectionHandler listHandler;
	
	public MainScreen(Properties p){
		properties = p;
	}
	
	private void create(){
		mainPage = new JFrame("profession_tool");
		mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String companyPath = properties.getCompany();
		Image company = Toolkit.getDefaultToolkit().getImage(companyPath);
		mainPage.setIconImage(company);
		mainPage.setPreferredSize(new Dimension(BACKGROUND_WIDTH, BACKGROUND_HEIGHT + LOGO_HEIGHT));
		mainPage.setResizable(false);
		menuHandler = new MenuListener();
		listHandler = new ListSelectionHandler();
		handler = new ButtonListener();
		
		menuBar = new JMenuBar();
		mainPage.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("file");
		options = new JMenu("options");
		help = new JMenu("help");
		
		loadUser = new JMenuItem("load user...");
		loadUser.addActionListener(menuHandler);
		newuser = new JMenuItem("new user...");
		newuser.addActionListener(menuHandler);
		exit = new JMenuItem("exit");
		exit.addActionListener(menuHandler);
		deleteUser = new JMenuItem("delete user");
		deleteUser.addActionListener(menuHandler);
		deleteAccomp = new JMenuItem("delete accomplishment");
		deleteAccomp.addActionListener(menuHandler);
		workflow = new JMenuItem("workflow");
		workflow.addActionListener(menuHandler);
		about = new JMenuItem("about");
		about.addActionListener(menuHandler);
		
		fileMenu.add(loadUser);
		fileMenu.add(newuser);
		fileMenu.add(exit);
		options.add(deleteUser);
		options.add(deleteAccomp);
		help.add(workflow);
		help.add(about);
		menuBar.add(fileMenu);
		menuBar.add(options);
		menuBar.add(help);		
		
		JLabel title = new JLabel();
		ImageIcon titleImage = new ImageIcon(properties.getLogo());
		title.setIcon(titleImage);
		title.setBounds(0, 0, LOGO_WIDTH, LOGO_HEIGHT);
		
		JLabel background = new JLabel();
		String backgroundPath = properties.getBackground();
		ImageIcon backgroundImage = new ImageIcon(backgroundPath);
		background.setIcon(backgroundImage);
		background.setBounds(0, LOGO_HEIGHT, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		objectives = new JButton("objectives");
		objectives.setBounds(BACKGROUND_WIDTH / 12, BACKGROUND_HEIGHT / 6 + LOGO_HEIGHT, 
				MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT);
		objectives.addActionListener(handler);
		
		accomplishments = new JButton("accomplishments");
		accomplishments.setBounds(BACKGROUND_WIDTH / 12, BACKGROUND_HEIGHT / 3 + LOGO_HEIGHT, 
				MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT);
		accomplishments.addActionListener(handler);
		
		loadDisplay();
		viewerPane.setBounds((BACKGROUND_WIDTH / 2) - 150, VIEWER_TOP + LOGO_HEIGHT, VIEWER_WIDTH, VIEWER_HEIGHT);
		itemList.setEnabled(false);
		displayData = new DisplayData(EMPTY_USER);
		displayData.addObjective(EMPTY_OBJECTIVE);
		displayData.addAccomplishment(EMPTY_ACCOMPLISH);
		
		add = new JButton("add");
		int helperX = ((BACKGROUND_WIDTH / 2) - 150 ) + VIEWER_WIDTH + (HELPER_BUTTON_WIDTH / 2);
		add.setBounds(helperX, VIEWER_TOP + LOGO_HEIGHT, HELPER_BUTTON_WIDTH, HELPER_BUTTON_HEIGHT);
		add.addActionListener(handler);
		add.setEnabled(false);
		
		action = new JButton("action");
		int actionY = VIEWER_TOP + HELPER_BUTTON_HEIGHT + (HELPER_BUTTON_HEIGHT / 2) + LOGO_HEIGHT;
		action.setBounds(helperX, actionY, HELPER_BUTTON_WIDTH, HELPER_BUTTON_HEIGHT);
		action.addActionListener(handler);
		action.setEnabled(false);
		
		sideNote = new JTextArea();
		sideNote.setLineWrap(true);
		notePane = new JScrollPane(sideNote);
		int noteY = VIEWER_TOP + LOGO_HEIGHT + VIEWER_HEIGHT - (HELPER_BUTTON_HEIGHT *2);
		notePane.setBounds(helperX, noteY, (HELPER_BUTTON_WIDTH * 2), (HELPER_BUTTON_HEIGHT * 2));
		sideNote.setEnabled(false);
		sideNote.setEditable(false);
		notePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(BACKGROUND_WIDTH, BACKGROUND_HEIGHT));
		layeredPane.add(background);
		layeredPane.add(objectives);
		layeredPane.add(accomplishments);
		layeredPane.add(viewerPane);
		layeredPane.add(add);
		layeredPane.add(action);
		layeredPane.add(notePane);
		
		layeredPane.moveToFront(objectives);
		layeredPane.moveToFront(accomplishments);
		layeredPane.moveToFront(viewerPane);
		layeredPane.moveToFront(add);
		layeredPane.moveToFront(action);
		layeredPane.moveToFront(notePane);
		
		mainPage.add(title);
		mainPage.add(layeredPane);
		mainPage.pack();
		mainPage.setVisible(true);		
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == loadUser){
				try {
					doLoadUser();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == newuser){
				try {
					doCreateUser();
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == exit){
				System.exit(0);
			}else if(event.getSource() == deleteUser){
				try {
					doDeleteUser();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == deleteAccomp){
				if(accomplishSelected){
					try {
						doDeleteAccomplishment();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(event.getSource() == workflow){
				doWorkflow();
			}else if(event.getSource() == about){
				doAbout();
			}
		}
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == objectives){
				loadObjectives();
				objectiveSelected = true;
				accomplishSelected = false;
				checkEnableList();
				sideNote.setText(null);
				sideNote.setEnabled(false);
			}else if(event.getSource() == accomplishments){
				loadAccomplishments();
				objectiveSelected = false;
				accomplishSelected = true;
				checkEnableList();
				sideNote.setText(null);
				sideNote.setEnabled(false);
			}else if(event.getSource() == add){
				doAdd();
			}else if(event.getSource() == action){
				try {
					doAction();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}
	}
	
	private class ListSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent event) {
			ListSelectionModel lsm = (ListSelectionModel)event.getSource();
			int index = lsm.getLeadSelectionIndex();
			String item = "";
			if(objectiveSelected){
				item = displayData.getObjective(index);
				action.setEnabled(true);
			}else{
				item = displayData.getAccomplishment(index);
				action.setEnabled(true);
			}
			String text = displayData.getNote(item);
			sideNote.setText(text);
			if(text != null){
				sideNote.setEnabled(true);
			}else{
				sideNote.setEnabled(false);
			}
		}
	}
	
	private void doLoadUser() throws Exception{
		String[] userlist = loadUserList();
		Object selection = null;
		if(userlist.length < 1) {
			String[] empty = {"EMPTY"};
			selection = JOptionPane.showInputDialog(null, 
					"please select a username: ", 
					"load username", 
					JOptionPane.INFORMATION_MESSAGE, 
					new ImageIcon(properties.getCompany()),
					empty, empty[0]);
		} else {
			selection = JOptionPane.showInputDialog(null, 
					"please select a username: ", 
					"load username", 
					JOptionPane.INFORMATION_MESSAGE, 
					new ImageIcon(properties.getCompany()),
					userlist, userlist[0]);
		}
		if(selection != null && selection.toString().compareTo("EMPTY") != 0){
			String username = selection.toString();
			if(loadSaveFile(username)){
				loadObjectives();
				checkEnableList();
				loadNotes();
				add.setEnabled(true);
				options.setEnabled(true);
			}
		}
	}
	
	private void doCreateUser() throws HeadlessException, IOException{
		Object input = JOptionPane.showInputDialog(null, 
				"enter new username", 
				"new user", 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(properties.getCompany()), 
				null, null);
		if(input != null){
		String username = input.toString();
			if(updateUserlist(username)){
				displayData = new DisplayData(username);
				displayData.addObjective(EMPTY_OBJECTIVE);
				displayData.addAccomplishment(EMPTY_ACCOMPLISH);
				saveData();
				loadObjectives();
				loadAccomplishments();
				checkEnableList();
				add.setEnabled(true);
				options.setEnabled(true);
			}else{
				JOptionPane.showMessageDialog(null, 
						"error!!! username already exists.  "
						+ "please create a unique username.", 
						"ERROR", 
						JOptionPane.ERROR_MESSAGE, 
						new ImageIcon(properties.getCompany()));
			}
		}
	}
	
	private void doDeleteUser() throws IOException{
		int choice = JOptionPane.showConfirmDialog(null, 
				"WARNING!  delete current user?  this will erase all "
				+ "data and cannot be undone...", 
				"WARNING!!!", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				new ImageIcon(properties.getCompany()));
		if(choice == 0){
			deleteUser();
			loadDisplay();
			checkEnableList();
			add.setEnabled(false);
			options.setEnabled(false);
		}
	}
	
	private void doDeleteAccomplishment() throws IOException{
		int choice = JOptionPane.showConfirmDialog(null, 
				"deleting an accomplishment is not typical behavior...!", 
				"!!!", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				new ImageIcon(properties.getCompany()));
		if(choice == 0){
			displayData.deleteNote(itemList.getSelectedValue());
			displayData.removeAccomplishment(itemList.getSelectedValue());
			loadAccomplishments();
			checkEnableList();
			saveData();
			saveNotes();
		}
	}
	
	private void doWorkflow(){
		String text = "typical professional {user}  >>>" +
				"\nhas >>> {objective}s  >>> >>> >>>\n\n" +
				"those tasks (win medal, trophy, title) get >>> {action}s" +
				"\nwe will either fail, or succeed >>> >>> >>>" +
				"\n\nour past successes let us display our {accomplishment}s" +
				"\nso our professional {user} has pictures of (the big deal, " +
				"our big event, the ceremony) for folks to admire";
		
		JOptionPane.showMessageDialog(null, 
				text,
				"workflow", 
				JOptionPane.OK_OPTION, 
				new ImageIcon(properties.getCompany()));
	}
	
	private void doAbout(){
		JOptionPane.showMessageDialog(null, 
				"product:\nprofession_tool\n\n" +
				"member of:\n???\n\ncontact:\n" +
				"mike.drummond.802@hotmail.com", 
				"about", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(properties.getCompany()));
	}
	
	private void doAdd(){
		if(objectiveSelected){
			Object input = JOptionPane.showInputDialog(null,
					"enter new objective: ",
					"add objective",
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon(properties.getCompany()),
					null, null);
			if(input != null){
				String newObjective = input.toString();
				boolean success = displayData.addObjective(newObjective);
				if(success){
					loadObjectives();
					checkEnableList();
					try {
						saveData();
						action.setEnabled(false);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, 
							"duplicate objective found...", 
							"!!!", 
							JOptionPane.WARNING_MESSAGE, 
							new ImageIcon(properties.getCompany()));
				}
			}
		}else if(accomplishSelected){
			Object input = JOptionPane.showInputDialog(null,
					"enter new accomplishment: ",
					"add accomplishment",
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon(properties.getCompany()),
					null, null);
			if(input != null){
				String newAccomplish = input.toString();
				int value = JOptionPane.showConfirmDialog(null, 
						"warning!!! accomplishments are a result of actions", 
						"WARNING", 
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						new ImageIcon(properties.getCompany()));
				if(value == 0){
					boolean success = displayData.addAccomplishment(newAccomplish);
					if(success){
						loadAccomplishments();
						checkEnableList();
						try {
							saveData();
							action.setEnabled(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(null, 
								"duplicate accomplishment found...", 
								"!!!", 
								JOptionPane.WARNING_MESSAGE, 
								new ImageIcon(properties.getCompany()));
					}
				}
			}
		}
	}
	
	private void doAction() throws IOException{
		if(objectiveSelected){
			String[] choices = new String[]{"remove", "accomplish", "notes"};
			int choice = JOptionPane.showOptionDialog(
					JOptionPane.getRootFrame(),
					"what action would you like to perform on this objective?", 
					"action on objective", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					new ImageIcon(properties.getCompany()),
					choices, 
					choices[0]);
			if(choice != -1){
				try {
					actionObjective(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if(accomplishSelected){
			String[] choices = new String[]{"notes"};
			int choice = JOptionPane.showOptionDialog(
					JOptionPane.getRootFrame(),
					"please enter how you would like to remember this accomplishment", 
					"action on acomplishment", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					new ImageIcon(properties.getCompany()),
					choices, 
					choices[0]);
			if(choice != -1){
				actionAccomplishment(choice);
			}
		}
	}
	
	private void actionObjective(int choice) throws IOException{
		if(choice == 0){
			String item = itemList.getSelectedValue();
			displayData.removeObjective(item);
			displayData.deleteNote(item);
			loadObjectives();
			checkEnableList();
			saveData();
			saveNotes();
			action.setEnabled(false);
		}else if(choice == 1){
			String item = itemList.getSelectedValue();
			displayData.removeObjective(item);
			displayData.addAccomplishment(item);
			displayData.deleteNote(item);
			checkEnableList();
			loadObjectives();
			saveData();
			saveNotes();
			action.setEnabled(false);
		}else if(choice == 2){
			Object input = JOptionPane.showInputDialog(null, 
					"enter note for this objective", 
					"add note", 
					JOptionPane.PLAIN_MESSAGE, 
					new ImageIcon(properties.getCompany()), null, null);
			if(input != null){
				String note = input.toString();
				String objective = itemList.getModel().getElementAt(itemList.getSelectedIndex());
				if(!objective.contains(SPLIT_PATTERN)){
					displayData.setNote(objective, note);
					saveNotes();
					sideNote.setText(note);
					if(note != null){
						sideNote.setEnabled(true);
					}else{
						sideNote.setEnabled(false);
					}
				}else{
					JOptionPane.showMessageDialog(null, 
							"invalid note, see faq", 
							"ERROR", 
							JOptionPane.WARNING_MESSAGE, 
							new ImageIcon(properties.getCompany()));
				}
			}
		}
	}
	
	private void actionAccomplishment(int choice) throws IOException{
		Object input = JOptionPane.showInputDialog(null, 
				"enter note", 
				"add note", 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(properties.getCompany()), null, null);
		if(input != null){
			String note = input.toString();
			String objective = itemList.getModel().getElementAt(itemList.getSelectedIndex());
			if(!objective.contains(SPLIT_PATTERN)){
				displayData.setNote(objective, note);
				saveNotes();
				sideNote.setText(note);
				if(note != null){
					sideNote.setEnabled(true);
				}else{
					sideNote.setEnabled(false);
				}
			}else{
				JOptionPane.showMessageDialog(null, 
						"invalid note, see faq", 
						"ERROR", 
						JOptionPane.WARNING_MESSAGE, 
						new ImageIcon(properties.getCompany()));
			}
		}
	}
	
	private void checkEnableList(){
		if(objectiveSelected){
			if(!displayData.objectiveEmpty()){
				itemList.setEnabled(true);
			}else{
				itemList.setEnabled(false);
				action.setEnabled(false);
			}
		}else if(accomplishSelected){
			if(!displayData.accomplishmentEmpty()){
				itemList.setEnabled(true);
			}else{
				itemList.setEnabled(false);
				action.setEnabled(false);
			}
		}
	}
	
	private void loadObjectives(){
		currentList = displayData.getObjectiveList();
		String[] objectiveList = new String[currentList.size()];
		for(int x = 0; x < objectiveList.length; x++){
			objectiveList[x] = currentList.get(x);
		}
		objectiveEmpty = false;
		loadDisplay(objectiveList);
	}
	
	private void loadAccomplishments(){
		currentList = displayData.getAccomplishmentList();
		String[] accomplishmentList = new String[currentList.size()];
		for(int x = 0; x < accomplishmentList.length; x++){
			accomplishmentList[x] = currentList.get(x);
		}
		accomplishEmpty = false;
		loadDisplay(accomplishmentList);
	}
	
	private void loadDisplay(){
		String[] displayList = new String[] {EMPTY_OBJECTIVE};
		objectiveSelected = true;
		accomplishSelected = false;
		objectiveEmpty = true;
		accomplishEmpty = true;
		loadDisplay(displayList);
	}
	
	private void loadDisplay(String[] displayList){
		if(itemList == null){
			itemList = new JList<String>(displayList);
			itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			viewerLSM = itemList.getSelectionModel();
			viewerLSM.addListSelectionListener(listHandler);
			viewerPane = new JScrollPane(itemList);
			viewerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			viewerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}else{
			if(displayList[0].compareTo(EMPTY_OBJECTIVE) == 0){
				objectiveEmpty = true;
			}else if(displayList[0].compareTo(EMPTY_ACCOMPLISH) == 0){
				accomplishEmpty = true;
			}
			JList<String> newList = new JList<String>(displayList);
			itemList.setModel(newList.getModel());
		}
	}
	
	public void init(){
		if(!created){
			create();
			created = true;
		}
	}
}
