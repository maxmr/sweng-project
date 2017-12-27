import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





public class AdminGUI extends JFrame {


	private Container c;
	public JButton button_del;
	public JButton button_add;
	private JTextField textfield;
	private JList<String> list;
	private DefaultListModel<String> model;
	private JScrollPane sp;
	MainFrameGUI mainframe;
	
	
	public AdminGUI(MainFrameGUI m) {
		
	mainframe = m;
	
	c = getContentPane();
	
	
	//panel für oberen Bereich bzw. ersten 3 Elemente im Gridlayout
	JPanel upper_panel = new JPanel(new GridLayout(3,1));
	upper_panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	
	//Label
	upper_panel.add(new JLabel("Admin Panel"));
	

	

	
	//Panel für Liste, Boarderlayout für automatische Breite UND Höhe
	JPanel middle_panel = new JPanel(new BorderLayout());
	
	//Liste
	model = new DefaultListModel<String>();
	list = new JList<String>(model);
	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	list.addListSelectionListener(new Listlistener());
	get_data();
	sp = new JScrollPane(list);
	
	//middle_panel.add(list);
	middle_panel.add(sp);
	Border outer = BorderFactory.createEmptyBorder(0, 10, 0, 10);	//empty border für außen
	Border inner = BorderFactory.createEtchedBorder();			//für liste selbst
	middle_panel.setBorder(BorderFactory.createCompoundBorder(outer, inner));
	
	//buttons
	JPanel pa[];		// eigenes panel pro button
	JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	pa = new JPanel[2];
	
	

	button_add = new JButton("add");
	button_add.addActionListener(new ButtonListadmin(this));
	button_add.setEnabled(true);
	button_del = new JButton("del");		
	button_del.addActionListener(new ButtonListadmin(this));
	button_del.setEnabled(false);

	

		pa[0] = new JPanel();
		pa[0].add(button_add);
		pa[0].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		bottom_panel.add(pa[0]);
		pa[1] = new JPanel();
		pa[1].add(button_del);
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

public void sync_list_data() {
	model.clear();
	get_data();
	
}

public void del_element() {
	String model_temp = model.get(list.getSelectedIndex());
	
	String[] temp;
	
	for (int i = 0; i < mainframe.data.size(); i++) {
		temp = mainframe.data.get(i);
		if (temp[0].equals(model_temp)) {
			mainframe.data.remove(i);
			break;
		}
	}
	sync_list_data();
}

public void get_data() {
	
 for (int i = 0; i < mainframe.data.size();i++) {
	 String temp[] = mainframe.data.get(i);
	 model.add(i, temp[0]);
 }

}

		
public class Listlistener implements ListSelectionListener {
	
	
	


	@Override
	public void valueChanged(ListSelectionEvent arg0) {

		if(list.isSelectionEmpty() == true)
			//deaktivieren
			button_del.setEnabled(false);
			
		else
			//aktivieren
			button_del.setEnabled(true);

	}

}

public class ButtonListadmin implements ActionListener {
	
	AdminGUI admin;
	
	public ButtonListadmin(AdminGUI a) {
		admin = a;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 if(arg0.getSource() == button_add) {
			JFrame addframe = new add_admindata(mainframe, admin);
			addframe.setVisible(true);
		}
		else if(arg0.getSource() == button_del) {
			del_element();
		}

	}

}
	
}
