
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.border.*;

public class TerminalGUI extends JDialog {

	private Container C;
	private String TerminalTitle;
	int ID;
	MainFrameGUI Mainframe;
	QRScanner scan;
	Scale scale;
	
	String[] userdata = {"","","","","","","","","","","",""};
	
	//Main Items
	private JPanel MainPanel;
	private JPanel upper_panel;
	private JPanel bottom_panel;
	private JPanel[] Actionpanel;
	private JPanel center_panel;
	
	//welcome Panel
	private JLabel welcome;
	private JLabel scantext;
	private JLabel ID_str;
	public JButton scanqr;
	
	//Unkown Panel
	private JLabel unkowntext;
	private JLabel unkowntext2;
	
	//Unkown Panel
	private JLabel endtext;
	private JLabel endtext2;
	
	//accepted Panel	
	private JLabel lname;
	private JLabel flugnr;
	private JLabel Abflug;
	private JLabel fdate;
	private JLabel Lluggage;
	private JLabel lweight;
	private JLabel lbonusweight;
	private JLabel lairline;
	private JLabel Vflugnr;
	private JLabel VAbflug;
	private JLabel Vfdate;
	private JLabel Vluggage;
	private JLabel Vweight;
	private JLabel Vbonusweight;
	private JLabel Vairline;
	
	public JButton Print_button;
	public JButton Scale_button;
	
	public String printlabel;
	public String printlabel_err;
	public String printlabel_err2;
		
	//early Panel
	private JLabel earlytext;
	private JLabel earlytext2;

	//early Panel
	private JLabel latetext;
	private JLabel latetext2;
	public JButton Exit_late;
	
	//public for keylistener reference
	public JButton[] lang_buttons;
	public JButton next_button;
	public JButton exit_button;
	public enum State {WELCOME,UNKOWN,EARLY,LATE,ACCEPTED,LUGGAGE,END};
	public enum Language {eng,ger};
	private Language lang;
	public State state;
		
	public TerminalGUI(int TerminalID, MainFrameGUI f) {
		Mainframe = f;
		Mainframe.add_terminalref(this);
		ID = TerminalID;
		C = getContentPane();
		TerminalTitle = "Terminal " + ID;
		state = State.WELCOME;
		scan = new QRScanner();
		scale = new Scale();
		
		lang = Language.eng;

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
		
		center_panel.setBorder(BorderFactory.createEtchedBorder());
		center_panel.setPreferredSize(new Dimension(1200, 500));
		
		// ------- Bottom Panel ---------- //
		bottom_panel = new JPanel(new BorderLayout(20, 10));

		next_button = new JButton("Next");
		next_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		next_button.setPreferredSize(new Dimension(300, 100));
		next_button.addActionListener(new ButtonListTerminal(this,Mainframe));
		next_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		
		exit_button = new JButton("Exit");
		exit_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit_button.setPreferredSize(new Dimension(300, 100));
		exit_button.addActionListener(new ButtonListTerminal(this,Mainframe));
		exit_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		
		bottom_panel.add(exit_button, BorderLayout.WEST);
		bottom_panel.add(next_button, BorderLayout.EAST);
		bottom_panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
		MainPanel = new JPanel(new BorderLayout());
		MainPanel.add(upper_panel, BorderLayout.NORTH);
		MainPanel.add(center_panel,BorderLayout.CENTER);
		MainPanel.add(bottom_panel, BorderLayout.SOUTH);
		
		C.add(MainPanel);
		addWindowListener(new ClosingListener(this));
		setSize(1200,800);
		setTitle(TerminalTitle);
		setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		next_button.setEnabled(false);
		update_panels();
	}
	
	public void update_language(Language l) {
		lang = l;
		switch_lang();
	}
	
	public void update_from_global() {
		update_panels();
	}
	
	private void switch_lang() {
		switch(lang) {
			case eng:
				next_button.setText("next");
				Scale_button.setText("scale");
				Print_button.setText("print");
				welcome.setText("Welcome");
				scantext.setText("Please scan your boarding pass");
				unkowntext.setText("ID not found");
				unkowntext2.setText("Please contact the service");
				endtext.setText("Check-In succeed");
				endtext2.setText("Please enter the boarding zone");
				earlytext.setText("Check-In not possible");
				earlytext2.setText("Try again "+ userdata[8] + " hours before boarding");
				latetext.setText("Check-In not possible");
				latetext2.setText("Your flight has already taken off");
				ID_str.setText("ID: " + scan.getQRCode());		
				flugnr.setText("Flight Nr.: ");
				Abflug.setText("Take off: ");
				fdate.setText("Date: ");
				Lluggage.setText("available Lugagges: ");
				lweight.setText("available Weight: ");
				lbonusweight.setText("extra Weight(family): ");
				lairline.setText("Airline: ");
				lname.setText("Welcome " + userdata[1] + " " + userdata[2]);
				printlabel = "Please add the printed Label to your Luggage and then put it on the conveyor belt";
				printlabel_err = "Too much weight. Please exit the Check-In and contact the service personal to complete the procedure";
				printlabel_err2 = "Too much luggages. Please exit the Check-In and contact the service personal to complete the procedure";			
				break;
		
			case ger:
				next_button.setText("weiter");
				Scale_button.setText("Waage");
				Print_button.setText("drucken");
				welcome.setText("Willkommen");
				scantext.setText("Bitte scannen Sie Ihr Flugticket");
				unkowntext.setText("ID nicht gefunden");
				unkowntext2.setText("Bitte wenden Sie sich an das Service Personal");
				endtext.setText("Check-In erfolgreich");
				endtext2.setText("Bitte begeben Sie sich in den Abflugbereich");
				earlytext.setText("Check-In nicht möglich");
				earlytext2.setText("Versuchen Sie es noch einmal "+ userdata[8] + " Stunden vor Abflug");
				latetext.setText("Check-In nicht möglich");
				latetext2.setText("Der Flug ist bereits gestartet");
				ID_str.setText("ID: " + scan.getQRCode());			
				flugnr.setText("Flug Nr.: ");
				Abflug.setText("Abflug: ");
				fdate.setText("Datum: ");
				Lluggage.setText("mögl. Gepäckstücke: ");
				lweight.setText("verfügbares Gewicht: ");
				lbonusweight.setText("extra Gewicht(Familie): ");
				lairline.setText("Fluggesellschaft: ");
				lname.setText("Willkommen " + userdata[1] + " " + userdata[2]);
				printlabel = "Bitte bringen Sie das Label an Ihrem Koffer an und platzieren Ihn auf dem Förderband";
				printlabel_err = "Zu viel Gewicht. Bitte beenden Sie den Check-In und wenden Sie sich an das Service Personald um den Vorgang abzuschließen";
				printlabel_err2 = "Zu viele Gepäckstücke. Bitte beenden Sie den Check-In und wenden Sie sich an das Service Personald um den Vorgang abzuschließen";
				break;
		}
		
		Vflugnr.setText(userdata[11]);
		VAbflug.setText(userdata[6]);
		Vfdate.setText(userdata[7]);
		Vluggage.setText(userdata[10]);
		Vweight.setText(userdata[9] + " kg");
		Vbonusweight.setText(userdata[4] + " kg");
		Vairline.setText(userdata[5]);	
	}
	
	//Welcome Panel
	private JPanel create_WelcomeScreen() {
		JPanel pan = new JPanel(new GridLayout(4, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		
		JPanel upper = new JPanel();
		welcome = new JLabel("Welcome");
		welcome.setFont(new Font("Serif", Font.BOLD, 40));
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(welcome);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		scantext = new JLabel("Please scan your boarding pass");
		scantext.setFont(new Font("Serif", Font.PLAIN, 20));
		scantext.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(scantext);
		middle.setPreferredSize(new Dimension(1200,200));
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
		
		JPanel lower2 = new JPanel();
		ID_str = new JLabel("ID: " + userdata[0]);
		ID_str.setFont(new Font("Serif", Font.PLAIN, 15));
		ID_str.setAlignmentX(Component.CENTER_ALIGNMENT);
		lower2.add(ID_str);
		lower2.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);	
		pan.add(lower2);
		
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
	
		return pan;
	}
	//Accepted Panel
	private JPanel create_AcceptedScreen() {
		JPanel pan = new JPanel(new GridLayout(3, 1, 0, 0));
		pan.setPreferredSize(new Dimension(1200,500));
		
		JPanel upper = new JPanel();
		lname = new JLabel("Welcome " + userdata[1] + " " + userdata[2]);
		lname.setFont(new Font("Serif", Font.BOLD, 30));
		lname.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(lname);
		upper.setPreferredSize(new Dimension(1200,50));
		pan.add(upper);
		
		JPanel middle = new JPanel(new GridLayout(7,2,0,0));
		
		flugnr = new JLabel();
		Abflug = new JLabel();
		fdate = new JLabel();
		Lluggage = new JLabel();
		lweight = new JLabel();
		lbonusweight = new JLabel();
		Vflugnr = new JLabel();
		VAbflug = new JLabel();
		Vfdate = new JLabel();
		Vluggage = new JLabel();
		Vweight = new JLabel();
		Vbonusweight = new JLabel();
		lairline = new JLabel();
		Vairline = new JLabel();
			
		middle.add(flugnr);
		middle.add(Vflugnr);
		middle.add(lairline);
		middle.add(Vairline);
		middle.add(Abflug);
		middle.add(VAbflug);	
		middle.add(fdate);
		middle.add(Vfdate);		
		middle.add(Lluggage);
		middle.add(Vluggage);		
		middle.add(lweight);
		middle.add(Vweight);		
		middle.add(lbonusweight);
		middle.add(Vbonusweight);
		middle.setPreferredSize(new Dimension(600,350));
		middle.setBorder(BorderFactory.createEmptyBorder(0, 430, 30, 170));
		pan.add(middle);

		JPanel lower = new JPanel();
		Scale_button = new JButton("scale");
		Scale_button.setAlignmentX(Component.LEFT_ALIGNMENT);
		Scale_button.setPreferredSize(new Dimension(300, 100));
		Scale_button.setMaximumSize(new Dimension(300,100));
		Scale_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		Scale_button.addActionListener(new ButtonListTerminal(this, scale,Mainframe));
		lower.add(Scale_button);
		
		Print_button = new JButton("print");
		Print_button.setAlignmentX(Component.RIGHT_ALIGNMENT);
		Print_button.setPreferredSize(new Dimension(300, 100));
		Print_button.setMaximumSize(new Dimension(300,100));
		Print_button.setFont((new Font("Serif", Font.PLAIN, 40)));
		Print_button.addActionListener(new ButtonListTerminal(this, scale, Mainframe));
		Print_button.setEnabled(false);
		lower.add(Print_button);		
		lower.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);
		
		return pan;
	}
	//to early Panel
	private JPanel create_toearlyScreen() {
		JPanel pan = new JPanel(new GridLayout(3, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		JPanel upper = new JPanel();
		earlytext = new JLabel("Check-In not possible.");
		earlytext.setFont(new Font("Serif", Font.BOLD, 40));
		earlytext.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(earlytext);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		earlytext2 = new JLabel("Try again " + userdata[8] + " hours before boarding");
		earlytext2.setFont(new Font("Serif", Font.PLAIN, 20));
		earlytext2.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(earlytext2);
		middle.setPreferredSize(new Dimension(1200,300));
		pan.add(middle);
		JPanel lower = new JPanel();
		lower.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);
		
		return pan;
	}
	//to late Panel
	private JPanel create_tolateScreen() {
		JPanel pan = new JPanel(new GridLayout(3, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		JPanel upper = new JPanel();
		latetext = new JLabel("Check-In not possible.");
		latetext.setFont(new Font("Serif", Font.BOLD, 40));
		latetext.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(latetext);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		latetext2 = new JLabel("Your flight has already taken off");
		latetext2.setFont(new Font("Serif", Font.PLAIN, 20));
		latetext2.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(latetext2);
		middle.setPreferredSize(new Dimension(1200,300));
		pan.add(middle);

		JPanel lower = new JPanel();
		Exit_late = new JButton("Exit");
		Exit_late.setAlignmentX(Component.CENTER_ALIGNMENT);
		Exit_late.setPreferredSize(new Dimension(300, 100));
		Exit_late.setMaximumSize(new Dimension(300,100));
		//scanqr.addActionListener(new ButtonListTerminal(this));
		Exit_late.setFont((new Font("Serif", Font.PLAIN, 40)));
		Exit_late.addActionListener(new ButtonListTerminal(this, Mainframe));
		//lower.add(Exit_late);
		lower.setPreferredSize(new Dimension(1200,100));
		pan.add(lower);
		
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
		
		JPanel pan = new JPanel(new GridLayout(3, 1, 20, 20));
		pan.setPreferredSize(new Dimension(1200,500));
		JPanel upper = new JPanel();
		endtext = new JLabel();
		endtext.setFont(new Font("Serif", Font.BOLD, 40));
		endtext.setAlignmentX(Component.CENTER_ALIGNMENT);
		upper.add(endtext);
		upper.setPreferredSize(new Dimension(1200,100));
		pan.add(upper);
		
		JPanel middle = new JPanel();
		endtext2 = new JLabel();
		endtext2.setFont(new Font("Serif", Font.PLAIN, 20));
		endtext2.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(endtext2);
		middle.setPreferredSize(new Dimension(1200,300));
		pan.add(middle);
			return pan;
	}
	
	public void setState(State s) {
		state = s;
		//System.out.println(state);
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
				next_button.setEnabled(false);
				break;
			case EARLY:
				Actionpanel[2].setVisible(true);
				next_button.setEnabled(false);
				break;
			case LATE:
				Actionpanel[3].setVisible(true);
				next_button.setEnabled(false);
				break;
			case ACCEPTED:
				Actionpanel[4].setVisible(true);
				exit_button.setEnabled(false);
				break;
			case LUGGAGE:
				Actionpanel[5].setVisible(true);
				break;
			case END:
				Actionpanel[6].setVisible(true);
				exit_button.setEnabled(true);
				next_button.setEnabled(false);
				break;
		}
		TerminalTitle = "Terminal " + ID;
		setTitle(TerminalTitle);
		switch_lang();		
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
		
		////System.out.println(flight);
		////System.out.println(now);		
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
		 TerminalGUI terminalref;
		public ClosingListener(TerminalGUI t) {
			super();
			terminalref = t;
		}
		
		public void windowClosing(WindowEvent e){ 
			Mainframe.del_terminalref(terminalref);
			e.getWindow().dispose();
		}
	}	
}
