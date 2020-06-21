package timeMatch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;

public class Gui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(204, 255, 153));
		panel.setForeground(new Color(204, 255, 153));
		panel.setBounds(36, 0, 398, 37);
		frame.getContentPane().add(panel);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
		
		Panel panel_2 = new Panel();
		panel_2.setBackground(new Color(0, 51, 0));
		panel_2.setBounds(0, 0, 37, 261);
		frame.getContentPane().add(panel_2);
		
		Panel panel_1 = new Panel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(36, 35, 398, 226);
		frame.getContentPane().add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton);
	}
}
