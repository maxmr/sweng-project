import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListMain implements ActionListener {

	MainFrameGUI frame;
	
	public ButtonListMain(MainFrameGUI f) {
		frame = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == frame.MainButtons[0]) {
			System.out.println("Button Setting pressed");
		}
		else if(arg0.getSource() == frame.MainButtons[1]) {
			new TerminalGUI(frame.ret_TerminalCnt(), frame).setVisible(true);
			frame.increase_TerminalCnt();
		}
		else if(arg0.getSource() == frame.MainButtons[2]) {
			System.exit(0);
		}

	}

}