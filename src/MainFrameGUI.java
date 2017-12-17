import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainFrameGUI extends JFrame {
	
	private Container C;
	private JButton[] MainButtons;
	private static String MainTitle;
	private static String version = "v0.1";
	private JPanel MainPanel;
	
	public MainFrameGUI(){
		C = getContentPane();
		MainTitle = "Flughafen CheckIn " + version;
		MainButtons = new JButton[3];
		
		MainButtons[0] = new JButton("Settings");
		MainButtons[1] = new JButton("create Terminal");
		MainButtons[2] = new JButton("Exit");
		
		for (int i=0; i < MainButtons.length; i++) {
			MainButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
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
	
	
	

	public static void main(String[] args) {
		new MainFrameGUI().setVisible(true);

	}

}
