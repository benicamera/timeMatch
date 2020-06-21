package timeMatch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;

public class Gui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	final Controller controller;

	/**
	 * Create the application.
	 */
	public Gui() {
		controller = new Controller();
		initialize();
		toTest();
	}
		
		public void toTest(/* insert Parameters*/) {
			controller.toTest(/*insert Parameters*/);
		}
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 436, 287);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
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
		
		ImageIcon createButtonIcon = new ImageIcon(Gui.class.getResource("/resources/createButtonIcon.png"));
		Image createButtonImage = createButtonIcon.getImage();
		Image image = createButtonImage.getScaledInstance(10, 10, Image.SCALE_SMOOTH);

		
		JButton createButton = new JButton(createButtonIcon);
		createButton.setToolTipText("Erstellt neuen Kalender");
		//createButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/createButtonIcon.png")));
		panel_1.add(createButton);
	}
	
}
