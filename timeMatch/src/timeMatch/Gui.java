package timeMatch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class Gui {

	private JFrame frame;

	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton createButton = new JButton("");
		createButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/createButtonIcon.png")));
		createButton.setToolTipText("Kalender erstellen");
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 0, 5);
		gbc_createButton.gridx = 0;
		gbc_createButton.gridy = 3;
		frame.getContentPane().add(createButton, gbc_createButton);
		
		JButton editButton = new JButton("");
		editButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/editButtonIcon.png")));
		editButton.setToolTipText("Kalender bearbeiten");
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.insets = new Insets(0, 0, 0, 5);
		gbc_editButton.gridx = 4;
		gbc_editButton.gridy = 3;
		frame.getContentPane().add(editButton, gbc_editButton);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/matchButtonIcon.png")));
		btnNewButton.setToolTipText("suche Match");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 8;
		gbc_btnNewButton.gridy = 3;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
	}

}
