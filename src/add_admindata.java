
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class add_admindata extends JFrame {
	private Container c;
	public JButton button_cancel;
	public JButton button_add;
	private JTextField[] textfield;
	private JLabel[] labels;

	MainFrameGUI mainframe;
	AdminGUI admingui;
		
	public add_admindata(MainFrameGUI m, AdminGUI a) {
		
	mainframe = m;
	admingui = a;
		
	c = getContentPane();
		
	//panel für oberen Bereich bzw. ersten 3 Elemente im Gridlayout
	JPanel upper_panel = new JPanel(new GridLayout(3,1));
	upper_panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	
	//Label
	upper_panel.add(new JLabel("Add Data"));
		
	//Panel für Liste, Boarderlayout für automatische Breite UND Höhe
	JPanel middle_panel = new JPanel(new GridLayout(12, 2));
	
	labels = new JLabel[12];
	textfield = new JTextField[12];
	
	for (int i=0; i < 12; i++) {
		labels[i] = new JLabel();
		textfield[i] = new JTextField();
		middle_panel.add(labels[i]);
		middle_panel.add(textfield[i]);
	}
	middle_panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		
	labels[0].setText("QR-Code: ");
	labels[1].setText("Vorname: ");
	labels[2].setText("Name: ");
	labels[3].setText("FamilienID: ");
	labels[4].setText("FamilienBonus in kg: ");
	labels[5].setText("Fluggesellschaft: ");
	labels[6].setText("Abflugzeit(HH:mm): ");
	labels[7].setText("Datum(dd.mm.yyyy): ");
	labels[8].setText("CheckIn mögl. in std: ");
	labels[9].setText("Maximalgewicht: in kg ");
	labels[10].setText("Anzahl Koffer: ");
	labels[11].setText("FlugNr: ");	
	
	//buttons
	JPanel pa[];		// eigenes panel pro button
	JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	pa = new JPanel[2];	

	button_add = new JButton("add");
	button_add.addActionListener(new ButtonListadmin());
	button_add.setEnabled(true);
	button_cancel = new JButton("del");		
	button_cancel.addActionListener(new ButtonListadmin());
	button_cancel.setEnabled(true);

		pa[0] = new JPanel();
		pa[0].add(button_add);
		pa[0].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		bottom_panel.add(pa[0]);
		pa[1] = new JPanel();
		pa[1].add(button_cancel);
		pa[1].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		bottom_panel.add(pa[1]);
	
	c.add(upper_panel,BorderLayout.NORTH);
	c.add(middle_panel, BorderLayout.CENTER);
	c.add(bottom_panel,BorderLayout.SOUTH);
	
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBackground(Color.LIGHT_GRAY);
	//pack();
	setSize(600, 400);
	setPreferredSize(new Dimension(600, 400));
	setMinimumSize(new Dimension(450, 200));
		
}

public class ButtonListadmin implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 if(arg0.getSource() == button_add) {
			 String temp[] = new String[12];
			 
			 for (int i=0;i<12;i++) {
				 temp[i] = textfield[i].getText().trim();
			 }
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
				//List 11: Flugnr			 
			if(temp[4].equals("")) temp[4] = "0.0";
			if(temp[6].equals("")) temp[6] = "00:00";
			if(temp[7].equals("")) temp[7] = "01.01.2000";
			if(temp[8].equals("")) temp[8] = "2";
			if(temp[9].equals("")) temp[9] = "0.0";
			if(temp[10].equals("")) temp[10] = "0";
			
			 
			mainframe.data.add(temp);
			admingui.sync_list_data();
			dispose();
			 			 
			//
		}
		else if(arg0.getSource() == button_cancel) {
			dispose();
		}
	}

}	
}
