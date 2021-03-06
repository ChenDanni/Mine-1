/*
 *
 * TODO the view of MVC structure. to create user interface and show result
 * automatically when model data are changed
 */
package edu.nju.view;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
 






































import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.vo.GameVO;
import edu.nju.view.listener.CoreListener;
import edu.nju.view.listener.MenuListener;

import java.util.Observer;

public class MainFrame implements Observer {
	
	//Variables declaration
	private JFrame mainFrame; 
	public JPanel head;
	private JMenuBar aJMenuBar;
	private JMenu game;
	private HashMap<String, JMenuItem> menuItemMap;
	private JMenuItem startItem;
	private JSeparator jSeparator;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private ButtonGroup group;
	private JCheckBoxMenuItem easy;
	private JCheckBoxMenuItem hard;
	private JCheckBoxMenuItem hell;
	private JCheckBoxMenuItem custom;
	private JMenuItem record;
	private JMenuItem exit;
	private JMenu online;
	private JMenuItem host;
	private JMenuItem client;
	//下线
	private JMenuItem offline;
	private MineNumberLabel mineNumberLabel;
	private JButton startButton;
	private JLabel time;
	private MineBoardPanel body;
	private final int buttonSize = 16;
	private final int bodyMarginNorth = 20;
	private final int bodyMarginOther = 12;
	private int defaultWidth = 9;
	private int defaultHeight = 9;
	private CoreListener coreListener;
	private MenuListener menuListener;
	//End of variables declaration
	
	//计时线程
	private int num;
	private boolean isRunning;
	private Thread t;
	
	//显示已标雷数
	public MineNumAlreadyLabel mineNum;
	
	//记录对话框
	public RecordDialogs recordDialog;
	//endpanel
	public JDialog endDialog;
	public JLabel endLabel;
	public JButton endButton;
	public JPanel endPanel;

	public MainFrame() {
		/*try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		componentsInstantiation();
		initComponents();
		mainFrame.setVisible(true);

	}

	//Instantiation of components
	private void componentsInstantiation() {
		mainFrame = new JFrame();
		head = new JPanel();
		mineNumberLabel = new MineNumberLabel();
		startButton = new JButton();
		time = new JLabel();
		mineNum = new MineNumAlreadyLabel();

		aJMenuBar = new JMenuBar();
		game = new JMenu();
		startItem = new JMenuItem();
		jSeparator = new JSeparator();
		jSeparator1 = new JSeparator();
		jSeparator2 = new JSeparator();
		easy = new JCheckBoxMenuItem();
		hard = new JCheckBoxMenuItem();
		hell = new JCheckBoxMenuItem();
		custom = new JCheckBoxMenuItem();
 		record = new JMenuItem();
		exit = new JMenuItem();
		online = new JMenu();
		host = new JMenuItem();
		client = new JMenuItem();
		offline = new JMenuItem();
		
		menuItemMap = new HashMap<String,JMenuItem>();
		group = new ButtonGroup();
		
		body = new MineBoardPanel(defaultHeight,defaultWidth);
		coreListener = new CoreListener(this);
		menuListener = new MenuListener(this);
		
		//结束对话框
		endDialog = new JDialog(mainFrame, "游戏结束", true);
		endLabel = new JLabel();
		endButton = new JButton("ok");
		endPanel = new JPanel();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		mainFrame
				.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		game.setText("Game");

		//build menu bar
		group.add(easy);
		group.add(hard);
		group.add(hell);
		group.add(custom);

		startItem.setText("Start");
		game.add(startItem);
		startItem.addActionListener(menuListener);
		menuItemMap.put("start", startItem);

		game.add(jSeparator1);

		easy.setText("Easy");
		game.add(easy);
		easy.addActionListener(menuListener);
		menuItemMap.put("easy", easy);

		hard.setText("Hard");
		hard.addActionListener(menuListener);
		game.add(hard);
		menuItemMap.put("hard", hard);

		hell.setText("Hell");
		hell.addActionListener(menuListener);
		game.add(hell);
		menuItemMap.put("hell", hell);

		custom.setText("Custom");
		custom.addActionListener(menuListener);
		game.add(custom);
		menuItemMap.put("custom", custom);

		game.add(jSeparator2);

		record.setText("Record");
		game.add(record);
		menuItemMap.put("record", record);
		record.addActionListener(menuListener);

		game.add(jSeparator);

		exit.setText("Exit");
		exit.addActionListener(menuListener);
		game.add(exit);
		menuItemMap.put("exit", exit);

		aJMenuBar.add(game);
		
		online.setText("online");
		host.setText("registe as host");
		host.addActionListener(menuListener);
		online.add(host);
		menuItemMap.put("host", host);
		
		client.setText("registe as client");
		client.addActionListener(menuListener);
		online.add(client);
		menuItemMap.put("client", client);
		
		offline.setText("log out");
		offline.addActionListener(menuListener);
		online.add(offline);
		menuItemMap.put("offline", offline);
		
		aJMenuBar.add(online);
		mainFrame.setJMenuBar(aJMenuBar);
		//build menu bar end

		mainFrame.getContentPane().setLayout(null);

		//build head panel
		head.setBorder(new javax.swing.border.TitledBorder(null, "",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		head.setLayout(null);

		startButton.setIcon(Images.START_BEGIN);
        startButton.addActionListener(coreListener);
		Font font = new Font("Serif", Font.BOLD, 12);
		mineNumberLabel.setFont(font);
		time.setFont(font);
		mineNum.setFont(font);

		mineNumberLabel.setHorizontalAlignment(JLabel.CENTER);
        time.setHorizontalAlignment(JLabel.CENTER);
        mineNum.setHorizontalAlignment(JLabel.CENTER);
		
        mineNumberLabel.setText("剩余雷数");
        mineNum.setText("已标雷数");
		time.setText("0");

		head.add(mineNumberLabel);
		head.add(mineNum);
		head.add(startButton);
		head.add(time);
		mainFrame.getContentPane().add(head);	
		//build head panel end

		//build body panel
		mainFrame.getContentPane().add(body);
		//build body panel end
		
		//build end panel
		endPanel.add(endLabel);
		endPanel.add(endButton);
		endDialog.setContentPane(endPanel);
		endButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				endDialog.setVisible(false);
			}
		});
		//build end panel end
		
		mainFrame.setTitle("JMineSweeper");
		mainFrame.setIconImage(Images.FRAME_IMAGE);		
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		
		//build head, and body in detail  
		head.setBounds(4, 5, body.getColumns() * buttonSize + bodyMarginOther * 2 - 4, 65);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight()/2);
		mineNum.setBounds(0, head.getHeight()/2, head.getHeight(), head.getHeight()/2);
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());

		body.setBounds(2, head.getHeight(), body.getColumns() * buttonSize + 2
				* bodyMarginOther, body.getRows() * buttonSize + bodyMarginNorth
				+ bodyMarginOther);
		body.setBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.TitledBorder(""), "Challenge Yourself!",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);
		mainFrame.validate();
		mainFrame.repaint();
		easy.setSelected(true);
		mainFrame
				.setLocation((screenSize.width - head.getWidth()) / 2,
						(screenSize.height - aJMenuBar.getHeight()
								- head.getHeight() - body.getHeight()) / 2);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});
//		
//		//set endpanel location,size
//		endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public JMenuItem getMenuItem(String name) {
		if (menuItemMap == null)
			return null;
		return (JMenuItem) menuItemMap.get(name);
	}
	
	public MineBoardPanel getMineBoard(){
		return this.body;
	}
	public MineNumberLabel getMineNumberLabel(){
		return this.mineNumberLabel;
	}

	public JButton getStartButton(){
		return this.startButton;
	}
	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 游戏gameModel发生变化体现在这里
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		UpdateMessage notifingObject = (UpdateMessage)arg;
		if(notifingObject.getKey().equals("start")){
			GameVO newGame = (GameVO) notifingObject.getValue();
			int gameWidth = newGame.getWidth();
			int gameHeight = newGame.getHeight();
			String level = newGame.getLevel();
			String key = "Challenge Yourself!";
			if (OperationQueue.operationState == OperationState.CLIENT) {
				key = "you are client!";
			}else if (OperationQueue.operationState == OperationState.HOST) {
				key = "you are host!";
			}
			restart(gameHeight,gameWidth,level, key);
			startButton.setIcon(Images.START_RUN);
			
			if(t != null) {
				t.stop();
				t = null;
			}
			num = 0;
			isRunning = true;
			t = new Thread(new CountTime(time));
			t.start();
		}else if(notifingObject.getKey().equals("end_Fail")){
			isRunning = false;
			startButton.setIcon(Images.START_END);
			//set endpanel location,size
			endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
			endLabel.setText("you lose!");
			endDialog.setVisible(true);
			recordDialog.show();
		}else if (notifingObject.getKey().equals("end_Win")) {
			isRunning = false;
			startButton.setIcon(Images.START_BEGIN);
			//set endpanel location,size
			endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
			endLabel.setText("you win!");
			endDialog.setVisible(true);
			recordDialog.show();
		}else if (notifingObject.getKey().equals("end_Client_Fail") ||
				notifingObject.getKey().equals("end_Host_Win")) {
			isRunning = false;
			//set endpanel location,size
			endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
			if (OperationQueue.operationState == OperationState.CLIENT) {
				startButton.setIcon(Images.START_END);
				endLabel.setText("you lose!");
			}else {
				startButton.setIcon(Images.START_BEGIN);
				endLabel.setText("you win!");
			}
			endDialog.setVisible(true);
		}else if (notifingObject.getKey().equals("end_Client_Win") ||
				notifingObject.getKey().equals("end_Host_Fail")) {
			isRunning = false;
			//set endpanel location,size
			endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
			if (OperationQueue.operationState == OperationState.CLIENT) {
				startButton.setIcon(Images.START_BEGIN);
				endLabel.setText("you win!");
			}else {
				startButton.setIcon(Images.START_END);
				endLabel.setText("you lose!");
			}
			endDialog.setVisible(true);
		}else if (notifingObject.getKey().equals("tie")) {
			isRunning = false;
			//set endpanel location,size
			endDialog.setBounds(mainFrame.getLocation().x+50, mainFrame.getLocation().y+50, 150, 90);
			endLabel.setText("Tie!");
			startButton.setIcon(Images.START_BEGIN);
			endDialog.setVisible(true);
		}
	}

	private void restart(int mineBoardHeight,int mineBoardWidth,String type,String key) {

		mainFrame.getContentPane().remove(body);
		body = new MineBoardPanel(mineBoardHeight,mineBoardWidth);
		head.setBounds(4, 5, mineBoardWidth * buttonSize + bodyMarginOther * 2 - 4, 65);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight()/2);
		mineNum.setBounds(0, head.getHeight()/2, head.getHeight(), head.getHeight()/2);
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());

		body.setBounds(2, head.getHeight(), mineBoardWidth * buttonSize + 2
				* bodyMarginOther, mineBoardHeight * buttonSize + bodyMarginNorth
				+ bodyMarginOther);
		body.setBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.TitledBorder(""), key,
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);
		time.setText("0");
		
		if(type == null){
//			custom.setSelected(true);
			easy.setSelected(true);
		}
		else if(type.equals("小")){
			easy.setSelected(true);
		}
		else if(type.equals("中")){
			hard.setSelected(true);
		}
		else if(type.equals("大")){
			hell.setSelected(true);
		}
		else{
			custom.setSelected(true);
		}
//		switch (type) {
//		case "小":
//			easy.setSelected(true);
//			break;
//		case "中":
//			hard.setSelected(true);
//			break;
//		case "大":
//			hell.setSelected(true);
//			break;
//		default:
//			custom.setSelected(true);
//			break;
//		}
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	class CountTime implements Runnable{
		private JLabel time;
		
		public CountTime(JLabel time){
			this.time = time;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning){
				time.setText(num+"");
				num++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}

