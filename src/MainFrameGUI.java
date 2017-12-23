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
	
	private Container C;
	public JButton[] MainButtons;
	private static String MainTitle;
	private static String version = "v0.1";
	private JPanel MainPanel;
	private int TerminalCnt = 0;
	
	public MainFrameGUI(){
		C = getContentPane();
		data = new ArrayList<String[]>();
		
		for (int i=1; i<4;i++) {
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
		
		
		//MainContainer.setLayout(new GridLayout(MainButtons.length, 0));
		MainPanel.add(MainButtons[0]);
		MainPanel.add(MainButtons[1]);
		MainPanel.add(MainButtons[2]);		
		
		C.add(MainPanel);
		
		setSize(800, 600);
		setTitle(MainTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
	}
	
	public void  increase_TerminalCnt() {
		TerminalCnt ++;
	}
	
	private void create_example_data(int nr) {
		switch(nr){
			case 1:
				String[] temp = {"12345678abcdefg","Max", "Mustermann","F517","0.0","Muster-air","17:30","30.12.2017","2","20.0","3"};
				data.add(temp);
				break;
			case 2:
				String[] temp2 = {"23456789bcdefgh","Anna", "Mustermann","F517","0.0","Muster-air","17:30","30.12.2017","2","20.0","3"};
				data.add(temp2);
				break;
			case 3:		
				String[] temp3 = {"abcd1234fghc","Tom", "Musterstudent","None","0.0","Muster-air","14:30","31.12.2017","4","15.0","2"};
				data.add(temp3);
				break;
		}
		
		
		
	}
	
	
	public void decrease_TerminalCnt() {
		if (TerminalCnt > 0)
			TerminalCnt --;
	}
	
	public int ret_TerminalCnt() {
		return TerminalCnt;
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
