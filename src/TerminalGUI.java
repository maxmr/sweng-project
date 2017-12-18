
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class TerminalGUI extends JFrame {

	private Container C;
	private String TerminalTitle;
	int terminalnr = 0;
	
	private JPanel MainPanel;
	private JPanel upper_panel;
	private JPanel bottom_panel;
	private JPanel[] Actionpanel;
	private JPanel center_panel;
	
	private JButton[] lang_buttons;
	private JButton next_button;
	private JButton back_button;
	
	
	public TerminalGUI() {
		C = getContentPane();
		TerminalTitle = "Terminal " + terminalnr;
		
		lang_buttons = new JButton[2];
		
		ImageIcon icon_eng = new ImageIcon(new ImageIcon("src/eng.jpg").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));

		lang_buttons[0] = new JButton(icon_eng);
		
		ImageIcon icon_ger = new ImageIcon(new ImageIcon("src/german.jpg").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));
		lang_buttons[1] = new JButton(icon_ger);
		

		
		upper_panel = new JPanel();
		upper_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//upper_panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		for (int i=0; i < lang_buttons.length; i++) {
			lang_buttons[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
			lang_buttons[i].setPreferredSize(new Dimension(150, 100));
			lang_buttons[i].setMaximumSize(new Dimension(150, 100));
			lang_buttons[i].setMinimumSize(new Dimension(150, 100));
			
			upper_panel.add(lang_buttons[i]);
		}
		
		upper_panel.setPreferredSize(new Dimension(1200, 120));
		upper_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		 
		Actionpanel = new JPanel[1];
		Actionpanel[0] = new JPanel();
		JLabel label1 = new JLabel("Welcome");
		Actionpanel[0].add(label1);
		
		center_panel = new JPanel();
		center_panel.add(Actionpanel[0],BorderLayout.CENTER);
		center_panel.setBorder(BorderFactory.createEtchedBorder());
		center_panel.setPreferredSize(new Dimension(1200, 500));
		
		
		bottom_panel = new JPanel(new BorderLayout(20, 10));
		
		next_button = new JButton("Next");
		next_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		next_button.setPreferredSize(new Dimension(300, 100));
		
		back_button = new JButton("Back");
		back_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		back_button.setPreferredSize(new Dimension(300, 100));
		
		bottom_panel.add(back_button, BorderLayout.WEST);
		bottom_panel.add(next_button, BorderLayout.EAST);
		bottom_panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
		MainPanel = new JPanel(new BorderLayout());
		MainPanel.add(upper_panel, BorderLayout.NORTH);
		MainPanel.add(center_panel,BorderLayout.CENTER);
		MainPanel.add(bottom_panel, BorderLayout.SOUTH);
		
		C.add(MainPanel);
		setSize(1200,800);
		setTitle(TerminalTitle);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		new TerminalGUI().setVisible(true);

	}
	
}
