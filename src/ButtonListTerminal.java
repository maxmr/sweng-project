import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ButtonListTerminal implements ActionListener {

	TerminalGUI frame;
	TerminalGUI.Language lang = TerminalGUI.Language.eng;
	QRScanner scan;
	TerminalGUI.State state;
	TerminalGUI.State nextstate;
	MainFrameGUI mainframe;
	
	public ButtonListTerminal(TerminalGUI f) {
		frame = f;
	}
	
	public ButtonListTerminal(TerminalGUI f, QRScanner s) {
		frame = f;
		scan = s;
	}
	
	public ButtonListTerminal(TerminalGUI f, MainFrameGUI m) {
		frame = f;
		mainframe = m;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == frame.exit_button) {
			frame.dispose();
			mainframe.del_terminalref(frame);
			new TerminalGUI(mainframe.ret_TerminalCnt(), mainframe).setVisible(true);

		}
		else if(arg0.getSource() == frame.next_button) {
			System.out.println(frame.scan.getQRCode());
			this.state = frame.state;
			switch(state) {
			case WELCOME:
				if(!frame.check_QRcode()) {
					nextstate = TerminalGUI.State.UNKOWN;
					System.out.println("ich bin unbekannt");
				}
				else if(frame.check_late()) {
					nextstate = TerminalGUI.State.LATE;					
				}
				else if (frame.check_early()) {
					nextstate = TerminalGUI.State.EARLY;
				}
				else
				{
					nextstate = TerminalGUI.State.ACCEPTED;
				}
				
				break;
			case UNKOWN:

				break;
			case EARLY:

				break;
			case LATE:

				break;
			case ACCEPTED:

				break;
			case LUGGAGE:

				break;
			case END:

				break;
				
			}
			frame.setState(nextstate);
			frame.update_panels();

		}
		else if(arg0.getSource() == frame.lang_buttons[0]) {
			lang = TerminalGUI.Language.eng;
			frame.update_language(lang);

		}
		else if(arg0.getSource() == frame.lang_buttons[1]) {
			lang = TerminalGUI.Language.ger;
			frame.update_language(lang);
		}
		else if(arg0.getSource() == frame.scanqr) {
			scan.ScanQRCode();
			frame.update_panels();
		}
		else if (arg0.getSource() == frame.Exit_unkown) {
			//mainframe.decrease_TerminalCnt();
			frame.dispose();
			new TerminalGUI(mainframe.ret_TerminalCnt(), mainframe).setVisible(true);
		}



	}

}