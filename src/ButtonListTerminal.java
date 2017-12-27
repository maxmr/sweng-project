import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;



public class ButtonListTerminal implements ActionListener {

	TerminalGUI frame;
	TerminalGUI.Language lang = TerminalGUI.Language.eng;
	QRScanner scan;
	TerminalGUI.State state;
	TerminalGUI.State nextstate;
	MainFrameGUI mainframe;
	Scale scale;
	
	public ButtonListTerminal(TerminalGUI f) {
		frame = f;
	}
	
	public ButtonListTerminal(TerminalGUI f, QRScanner s) {
		frame = f;
		scan = s;
	}
	
	public ButtonListTerminal(TerminalGUI f, Scale s, MainFrameGUI m) {
		frame = f;
		scale = s;
		mainframe = m;
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
			//System.out.println(frame.scan.getQRCode());
			this.state = frame.state;
			switch(state) {
			case WELCOME:
				if(!frame.check_QRcode()) {
					nextstate = TerminalGUI.State.UNKOWN;
					//System.out.println("ich bin unbekannt");
				}
				else if(frame.check_late()) {
					nextstate = TerminalGUI.State.LATE;					
				}
				else if (frame.check_early()) {
					nextstate = TerminalGUI.State.EARLY;
				}
				else{
					nextstate = TerminalGUI.State.ACCEPTED;
					if(Long.parseLong(frame.userdata[10]) > 0) {
						frame.Scale_button.setEnabled(true);
						}
						else	{
							frame.Scale_button.setEnabled(true);
						}
				}			
				break;
			case UNKOWN:
				break;
			case EARLY:
				break;
			case LATE:
				break;
			case ACCEPTED:
				nextstate = TerminalGUI.State.END;
				float bonus = Float.parseFloat(frame.userdata[4]);
				float weigth = Float.parseFloat(frame.userdata[9]);
				
				if(weigth>0) {
					bonus = bonus + (weigth/2);
					frame.userdata[4] = Float.toString(bonus);
					mainframe.update_bonus(frame);
				}
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
			if(scan.getQRCode().contentEquals("")==false) {
				frame.next_button.setEnabled(true);
				}
			
			frame.update_panels();
		}
		else if (arg0.getSource() == frame.Scale_button) {

			scale.scaleLuggage();
			
			if(scale.getWeight().contentEquals("")==false) {
				frame.Print_button.setEnabled(true);
			}
				
		}
		else if (arg0.getSource() == frame.Print_button) {	
			float weigth = Float.parseFloat(frame.userdata[9]);
			float bonus = Float.parseFloat(frame.userdata[4]);
			float temp = weigth;
			
			long lug_cnt = Long.parseLong(frame.userdata[10]);
			lug_cnt--;		
			
			float luggage = Float.parseFloat(scale.getWeight());
			
			weigth = weigth - luggage;
			
			if (weigth < 0) {
				weigth += bonus;
			}
			//break checkin
			if (lug_cnt < 0) {
				JOptionPane.showMessageDialog(frame, frame.printlabel_err2);
				frame.next_button.setEnabled(false);
				frame.exit_button.setEnabled(true);
				frame.Scale_button.setEnabled(false);
			}
			else if (weigth < 0) {
				JOptionPane.showMessageDialog(frame, frame.printlabel_err);
				frame.next_button.setEnabled(false);
				frame.exit_button.setEnabled(true);
				frame.Scale_button.setEnabled(false);
			}
			//continue checkin
			else {
				JOptionPane.showMessageDialog(frame, frame.printlabel);	
				//update new weight in data
				if((temp - luggage) < 0) {
					frame.userdata[4] = Float.toString(weigth);
					frame.userdata[9] = Float.toString(0);
				}
				else {
					frame.userdata[9] = Float.toString(weigth);
				}
				frame.userdata[10] = Long.toString(lug_cnt);
				mainframe.update_bonus(frame);			
				frame.Print_button.setEnabled(false);	
				//System.out.println(scale.getWeight());		
				}
		}

	}
}