import javax.swing.*;
import javax.swing.border.*;



import java.awt.*;
import java.util.ArrayList;

public class MainFrameGUI extends JFrame {
	
	ArrayList<String[]> data; 
	
	//List 0: QR-Code
	//List 1: Name
	//List 2: Vorname
	//List 3: Familien-ID
	//List 4: Familen Bonus-Gewicht
	//List 5: Fluggesellschaft
	//List 6: Abflugzeit
	//List 7: Abflugdatum
	//List 8: Frühester CheckIN t-time
	//List 9: Maximalgewicht pro Koffer
	//List 10: Maximale KofferAnzahl
	//1List 11: Flugnr
	
	private Container C;
	public JButton[] MainButtons;
	private static String MainTitle;
	private static String version = "v0.1";
	private JPanel MainPanel;
	private int TerminalCnt = 0;
	
	ArrayList<TerminalGUI> terminallist;
	
	public MainFrameGUI(){
		C = getContentPane();
		data = new ArrayList<String[]>();
		terminallist = new ArrayList<TerminalGUI>();
		
		for (int i=1; i<7;i++) {
			create_example_data(i);
		}
		
		MainTitle = "Flughafen CheckIn " + version;
		MainButtons = new JButton[3];
		
		MainButtons[0] = new JButton("Settings");
		MainButtons[1] = new JButton("create Terminal");
		MainButtons[2] = new JButton("Exit");
		
		for (int i=0; i < MainButtons.length; i++) {
			MainButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			MainButtons[i].addActionListener(new ButtonListMain(this));
		}
		
		MainPanel =  new JPanel(new GridLayout(MainButtons.length, 0));
		Border border_panel = BorderFactory.createEmptyBorder(40, 40, 40, 40);
		MainPanel.setBorder(border_panel);

		MainPanel.add(MainButtons[0]);
		MainPanel.add(MainButtons[1]);
		MainPanel.add(MainButtons[2]);		
		
		C.add(MainPanel);
		
		setSize(800, 600);
		setTitle(MainTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
	}
	
	public void add_terminalref( TerminalGUI t) {
		terminallist.add(t);	
	}
	
	public void del_terminalref( TerminalGUI t) {
		terminallist.remove(t);
		update_terminals();
	}
	
	public void update_terminals() {
		for (int i=0; i < terminallist.size(); i++) {
			TerminalGUI temp = terminallist.get(i);
			temp.ID = i;
			temp.update_from_global();
		}
	}
	
	public void update_bonus(TerminalGUI t) {
		String temp_bonus = t.userdata[4];
		String familycode = t.userdata[3];
		
		for (int i=0; i < data.size();i++) {
			String[] temp_data = data.get(i);
			if(temp_data[3].contentEquals(familycode)) {
				temp_data[4] = temp_bonus;
			}
		}
		
		for (int i=0; i < terminallist.size(); i++) {
			TerminalGUI temp = terminallist.get(i);
			if (temp.userdata[3].contentEquals(familycode)){
				temp.userdata[4] = temp_bonus;	
			}
			temp.update_from_global();
		}
	}
	
	private void create_example_data(int nr) {
		switch(nr){
			case 1:
				String[] temp = {"12345678abcdefg","Max", "Mustermann","F517","0.0","Muster-air","17:30","30.12.2017","2","20.0","3","F001"};
				data.add(temp);
				break;
			case 2:
				String[] temp2 = {"23456789bcdefgh","Anna", "Mustermann","F517","0.0","Muster-air","17:30","30.12.2017","2","20.0","3","F001"};
				data.add(temp2);
				break;
			case 3:
				//early
				String[] temp3 = {"early","Tom", "Musterstudent","None","0.0","Muster-air","14:30","31.12.2017","4","15.0","2","F003"};
				data.add(temp3);
				break; 
			case 4:
				//late
				String[] temp4 = {"late","Tom", "Musterstudent","None","0.0","Muster-air","14:30","23.12.2017","4","15.0","2","F978"};
				data.add(temp4);
				break;
			case 5:
				//in time
				String[] temp5 = {"intime","Max", "Mustermann","F9","0.0","Muster-air","14:30","31.12.2017","200","12.0","2","G907"};
				data.add(temp5);
				break;
			case 6:
				//in time
				String[] temp6 = {"intime2","Anna", "Mustermann","F9","0.0","Muster-air","14:30","31.12.2017","200","12.0","2","G907"};
				data.add(temp6);
				break;
		}	
	}
		
	public int ret_TerminalCnt() {
		return terminallist.size();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frame1 = new MainFrameGUI();
				frame1.setVisible(true);				
			}
		});
	}
}
