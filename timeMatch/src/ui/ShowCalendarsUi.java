package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import timeMatch.Controller;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JScrollBar;
import java.awt.Insets;
import java.awt.Scrollbar;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import java.awt.List;
import java.awt.MouseInfo;
import java.awt.Font;
import java.awt.Color;

public class ShowCalendarsUi extends JFrame {

	private JPanel contentPane;
	final Controller controller;
	JFrame showFrame = new JFrame();
	JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<>();

	public ShowCalendarsUi(Controller _controller) {
		controller = _controller;
		showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel calendarListLabel = new JLabel("Liste der Kalender");
		calendarListLabel.setForeground(new Color(255, 182, 193));
		calendarListLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		GridBagConstraints gbc_calendarListLabel = new GridBagConstraints();
		gbc_calendarListLabel.gridheight = 2;
		gbc_calendarListLabel.gridwidth = 14;
		gbc_calendarListLabel.insets = new Insets(0, 0, 5, 5);
		gbc_calendarListLabel.gridx = 1;
		gbc_calendarListLabel.gridy = 2;
		contentPane.add(calendarListLabel, gbc_calendarListLabel);
		
		list = new JList<String>(model);
		list.setVisibleRowCount(8);
		
		initList(list);
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1 || e.getClickCount() == 2) {
		           String selectedItem = (String) list.getSelectedValue();
		           showPopUpMenu(selectedItem);
		         }
		    }
		};
		
		list.addMouseListener(mouseListener);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.ipady = 100;
		gbc_list.ipadx = 100;
		gbc_list.insets = new Insets(5, 5, 5, 5);
		gbc_list.gridwidth = 11;
		gbc_list.gridheight = 11;
		gbc_list.gridx = 2;
		gbc_list.gridy = 2;
		contentPane.add(list, gbc_list);
		contentPane.setVisible(true);
		showFrame.add(contentPane);
		showFrame.setVisible(true);
	}
	
	private void initList(JList<String> list) {
		if(controller.getCalendarNameList().size() <= 0) {
			model.addElement("Keine Kalender gefunden.");
			JOptionPane.showMessageDialog(showFrame, "Keine Kalender gefunden.");
			showFrame.setVisible(false); //you can't see me!
			showFrame.dispose(); //Destroy the JFrame object
			return;
		}
			
		for(String elementString : controller.getCalendarNameList()) {
			model.addElement(elementString);
			
			//list.add(elementString, null);
			
		}
	}
	
	private void showPopUpMenu(String selectedItem) {
		
		System.out.println("popUp aufgerufen");
		final String calendarNameString = selectedItem;
		
		final JPopupMenu menu = new JPopupMenu(calendarNameString);
		JMenuItem deleteItem = new JMenuItem("Vernichten");
		JMenuItem editItem = new JMenuItem("Bearbeiten");
		
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich vernichten?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
					JOptionPane.showMessageDialog(showFrame, controller.delete(calendarNameString));
				}
				
				if(!controller.isNameTaken(calendarNameString)) {
					menu.setVisible(false);
				showFrame.setVisible(false); //you can't see me!
				showFrame.dispose(); //Destroy the JFrame object
		}
			
			}
		});
		
		editItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EditCalendarUi editUi = new EditCalendarUi(calendarNameString, controller);
				menu.setVisible(false);
				showFrame.setVisible(false); //you can't see me!
				showFrame.dispose(); //Destroy the JFrame object
			}
		});
		menu.add(deleteItem);
		menu.add(editItem);
		
		menu.setLocation(MouseInfo.getPointerInfo().getLocation() );
		menu.setVisible(true);
	}
	
	

}
