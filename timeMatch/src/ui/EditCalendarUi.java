package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import timeMatch.Controller;
import java.awt.GridBagLayout;
import java.util.Objects;

public class EditCalendarUi extends JFrame {

	private JPanel contentPane;
	final Controller controller;
	int year;
	int day;
	String month;
	int monthSort;
	
	Object[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
	Object[] days1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
	Object[] days2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
	Object[] days3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
	Object[] days4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
	/**
	 * Create the frame.
	 */
	public EditCalendarUi(String name, Controller _controller) {
		controller = _controller;
		year = yearInput();
		month = monthInput();
		day = dayInput(monthSort);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0};
		gbl_contentPane.rowHeights = new int[]{0};
		gbl_contentPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
	}
	
	private int yearInput() {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog("Welches Jahr?", "Jahr:"));
		} catch (Exception e) {
			return yearInput();
		}
	}
	
	private String monthInput() {
		String _month = (String)JOptionPane.showInputDialog( contentPane, "Wähle den Monat", "", JOptionPane.PLAIN_MESSAGE, null, months, "Monat");
		
		switch (_month) {
		case "Januar":
			monthSort = 4;
			break;
		case "Februar":
			if(controller.isSchaltjahr(year))
				monthSort = 2;
			else 
				monthSort = 1;
			break;
		case "März":
			monthSort = 4;
			break;
		case "April":
			monthSort = 3;
			break;
		case "Mai":
			monthSort = 4;
			break;
		case "Juni":
			monthSort = 3;
			break;
		case "Juli":
			monthSort = 4;
			break;
		case "August":
			monthSort = 4;
			break;
		case "September":
			monthSort = 3;
			break;
		case "Oktober":
			monthSort = 4;
			break;
		case "November":
			monthSort = 3;
			break;
		case "Dezember":
			monthSort = 4;
			break;
			
		default:
			break;
		}
		if(_month == null) {
			return monthInput();
		}else {
			
			return _month;
		}
	}

}
