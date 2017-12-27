import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonListMain implements ActionListener {

	MainFrameGUI frame;
	TerminalGUI t;
	AdminGUI a;
	
	public ButtonListMain(MainFrameGUI f) {
		frame = f;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == frame.MainButtons[0]) {
			JFrame Frame1 = new AdminGUI(frame);
			Frame1.setVisible(true);
		}
		else if(arg0.getSource() == frame.MainButtons[1]) {
			   new TerminalGUI(frame.ret_TerminalCnt(), frame).setVisible(true);
			//frame.increase_TerminalCnt();
			
		}
		else if(arg0.getSource() == frame.MainButtons[2]) {
			System.exit(0);
		}
		else if(arg0.getSource() == a.button_add) {
			//
		}
		else if(arg0.getSource() == a.button_del) {
			a.del_element();
		}

	}

}
