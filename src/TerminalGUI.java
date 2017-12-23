
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.border.*;

public class TerminalGUI extends JFrame {

	private Container C;
	private String TerminalTitle;
	int ID;
	MainFrameGUI Mainframe;
	QRScanner scan;
	
	String[] userdata;
	
	//Main Items
	private JPanel MainPanel;
	private JPanel upper_panel;
	private JPanel bottom_panel;
	private JPanel[] Actionpanel;
	private JPanel center_panel;
	
	//welcome Panel
	private JLabel welcome;
	private JLabel scantext;
	public JButton scanqr;
	
	//Unkown Panel
	private JLabel unkowntext;
	private JLabel unkowntext2;
	public JButton Exit_unkown;
	
	//public for keylistener reference
	public JButton[] lang_buttons;
	public JButton next_button;
	public JButton back_button;
	public enum State {WELCOME,UNKOWN,EARLY,LATE,ACCEPTED,LUGGAGE,END};
	public enum Language {eng,ger};
	private Language lang;
	public State state;
	
	
	public TerminalGUI(int TerminalID, MainFrameGUI f) {
		Mainframe = f;
		ID = TerminalID;
		C = getContentPane();
		TerminalTitle = "Terminal " + ID;
		state = State.WELCOME;
		scan = new QRScanner();

		// ------- upper Panel ---------- //
		lang_buttons = new JButton[2];		
		ImageIcon icon_eng = new ImageIcon(new ImageIcon("src/eng.jpg").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));
		lang_buttons[0] = new JButton(icon_eng);		
		ImageIcon icon_ger = new ImageIcon(new ImageIcon("src/german.jpg").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));
		lang_buttons[1] = new JButton(icon_ger);
		

		
		upper_panel = new JPanel();
		upper_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		
		for (int i=0; i < lang_buttons.length; i++) {
			lang_buttons[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
			lang_buttons[i].setPreferredSize(new Dimension(150, 100));
			lang_buttons[i].setMaximumSize(new Dimension(150, 100));
			lang_buttons[i].setMinimumSize(new Dimension(150, 100));
			lang_buttons[i].addActionListener(new ButtonListTerminal(this));
			
			upper_panel.add(lang_buttons[i]);
		}
		
		upper_panel.setPreferredSize(new Dimension(1200, 120));
		upper_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		Actionpanel = new JPanel[7];
		Actionpanel[0] = create_WelcomeScreen();
		Actionpanel[1] = create_UnkownScreen();
		Actionpanel[2] = create_toearlyScreen();
		Actionpanel[3] = create_tolateScreen();
		Actionpanel[4] = create_AcceptedScreen();
		Actionpanel[5] = create_LuggageScreen();
		Actionpanel[6] = create_EndScreen();

		
		
		// ------- Center Panel ---------- //
		center_panel = new JPanel();
		for (int i = 0; i < Actionpanel.length; i++) {
		center_panel.add(Actionpanel[i],BorderLayout.CENTER);	
		}
		update_panels();
		
	
		center_panel.setBorder(BorderFactory.createEtchedBorder());
		center_panel.setPreferredSize(new Dimension(1200, 500));
		
		// ------- Bottom Panel ---------- //
		bottom_panel = new JPanel(new BorderLayout(20, 10));

		next_button = new JButton("Next");
		next_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		next_button.setPreferredSize(new Dimension(300, 100));
		next_button.addActionListener(new ButtonListTerminal(this));
		next_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		
		back_button = new JButton("Back");
		back_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		back_button.setPreferredSize(new Dimension(300, 100));
		back_button.addActionListener(new ButtonListTerminal(this));
		back_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		
		bottom_panel.add(back_button, BorderLayout.WEST);
		bottom_panel.add(next_button, BorderLayout.EAST);
		bottom_panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
		MainPanel = new JPanel(new BorderLayout());
		MainPanel.add(upper_panel, BorderLayout.NORTH);
		MainPanel.add(center_panel,BorderLayout.CENTER);
		MainPanel.add(bottom_panel, BorderLayout.SOUTH);
		
		C.add(MainPanel);
		addWindowListener(new ClosingListener());
		setSize(1200,800);
		setTitle(TerminalTitle);
		setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
	
	public void update_language(Language l) {
		lang = l;
		switch_lang();
	}
	
	private void switch_lang() {
		switch(lang) {
			case eng:
				next_button.setText("next");
				back_button.setText("back");
				welcome.setText("Welcome");
				
				break;
		
			case ger:
				next_button.setText("weiter");
				back_button.setText("zurück");
				welcome.setText("Willkommen");
				break;
		}
		
	}
	
	//Welcome Panel
	private JPanel create_WelcomeScreen() {
		JPanel pan = new JPanel(new GridLayout(3, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		JPanel upper = new JPanel();
		welcome = new JLabel("Welcome");
		welcome.setFont(new Font("Serif", Font.BOLD, 40));
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(welcome);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		scantext = new JLabel("Bitte scannen sie das Flugticket");
		scantext.setFont(new Font("Serif", Font.PLAIN, 20));
		scantext.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(scantext);
		middle.setPreferredSize(new Dimension(1200,300));
		pan.add(middle);

		JPanel lower = new JPanel();
		scanqr = new JButton("QR-Code");
		scanqr.setAlignmentX(Component.CENTER_ALIGNMENT);
		scanqr.setPreferredSize(new Dimension(300, 100));
		scanqr.setMaximumSize(new Dimension(300,100));
		//scanqr.addActionListener(new ButtonListTerminal(this));
		scanqr.setFont((new Font("Serif", Font.PLAIN, 40)));
		scanqr.addActionListener(new ButtonListTerminal(this, scan));
		lower.add(scanqr);
		lower.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);
		
		return pan;
	}
	//Unkown Panel
	private JPanel create_UnkownScreen() {
		JPanel pan = new JPanel(new GridLayout(3, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		JPanel upper = new JPanel();
		unkowntext = new JLabel("ID not found");
		unkowntext.setFont(new Font("Serif", Font.BOLD, 40));
		unkowntext.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(unkowntext);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		unkowntext2 = new JLabel("Please contact the service");
		unkowntext2.setFont(new Font("Serif", Font.PLAIN, 20));
		unkowntext2.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(unkowntext2);
		middle.setPreferredSize(new Dimension(1200,300));
		pan.add(middle);

		JPanel lower = new JPanel();
		Exit_unkown = new JButton("Exit");
		Exit_unkown.setAlignmentX(Component.CENTER_ALIGNMENT);
		Exit_unkown.setPreferredSize(new Dimension(300, 100));
		Exit_unkown.setMaximumSize(new Dimension(300,100));
		//scanqr.addActionListener(new ButtonListTerminal(this));
		Exit_unkown.setFont((new Font("Serif", Font.PLAIN, 40)));
		Exit_unkown.addActionListener(new ButtonListTerminal(this, Mainframe));
		lower.add(Exit_unkown);
		lower.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);
		
		return pan;
	}
	//Accepted Panel
	private JPanel create_AcceptedScreen() {
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(1200,500));
		JLabel label1 = new JLabel("passt");
		pan.add(label1);
		
		return pan;
	}
	//to early Panel
	private JPanel create_toearlyScreen() {
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(1200,500));
		JLabel label1 = new JLabel("zu früh");
		pan.add(label1);
		
		return pan;
	}
	//to early Panel
	private JPanel create_tolateScreen() {
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(1200,500));
		JLabel label1 = new JLabel("zu spät");
		pan.add(label1);
		
		return pan;
	}
	//Luggage Panel
	private JPanel create_LuggageScreen() {
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(1200,500));
		JLabel label1 = new JLabel("Welcome");
		pan.add(label1);
		
		return pan;
	}
	//end Panel
	private JPanel create_EndScreen() {
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(1200,500));
		JLabel label1 = new JLabel("Welcome");
		pan.add(label1);
		
		return pan;
	}
	
	public void setState(State s) {
		state = s;
		System.out.println(state);
	}
	
	
	public void update_panels() {
		for (int i =0; i < Actionpanel.length; i++) {
			Actionpanel[i].setVisible(false);		
		}
		switch(state) {
			case WELCOME:
				Actionpanel[0].setVisible(true);
				break;
			case UNKOWN:
				Actionpanel[1].setVisible(true);
				System.out.println("hier bin ich");
				break;
			case EARLY:
				Actionpanel[2].setVisible(true);
				break;
			case LATE:
				Actionpanel[3].setVisible(true);
				break;
			case ACCEPTED:
				Actionpanel[4].setVisible(true);
				break;
			case LUGGAGE:
				Actionpanel[5].setVisible(true);
				break;
			case END:
				Actionpanel[6].setVisible(true);
				break;
		}
		
	}
	
	public boolean check_QRcode() {
		boolean ret = false;
		//read database
		String[] temp;
		
		for (int i = 0; i < Mainframe.data.size(); i++) {
			temp = Mainframe.data.get(i);
			if (temp[0].equals(scan.getQRCode())) {
				userdata = temp;
				ret = true;
				break;
			}
		}
		
		return ret;
	}
	
	public boolean check_late() {
		boolean ret = false;
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime flight;
		
		String temp = userdata[7] + " " + userdata[6];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		flight = LocalDateTime.parse(temp, formatter);
		
		if(now.isAfter(flight)) {
			ret = true;
		}
		
		System.out.println(flight);
		System.out.println(now);
		
		return ret;
	}
	
	public boolean check_early() {
		boolean ret = false;
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime flight;
		
		String temp = userdata[7] + " " + userdata[6];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		flight = LocalDateTime.parse(temp, formatter);
		
		LocalDateTime flight_CheckIn = flight.minusHours(Long.parseLong(userdata[8]));
		
		if(now.isBefore(flight_CheckIn)) {
			ret = true;
		}
		
		return ret;
	}

	// WindowListener-Klasse als innere Klasse
	public class ClosingListener extends WindowAdapter	{
		
		public void windowClosing(WindowEvent e){ 
			Mainframe.decrease_TerminalCnt();
			e.getWindow().dispose();
		}
	}
	
	
	public static void main(String[] args) {
	//	new TerminalGUI(0).setVisible(true);

	}


	
}
