package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.ArrayList;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class DragAndDropUi extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DragAndDropUi() {
	setSize(new Dimension(691, 363));
    setPreferredSize(new Dimension(300, 200));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Import");
	SpringLayout springLayout = new SpringLayout();
	getContentPane().setLayout(springLayout);
 
	JLabel lblDraganddrop = new JLabel("Drag And Drop");
	springLayout.putConstraint(SpringLayout.NORTH, lblDraganddrop, 10, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, lblDraganddrop, 10, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, lblDraganddrop, 315, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, lblDraganddrop, -10, SpringLayout.EAST, getContentPane());
	lblDraganddrop.setHorizontalTextPosition(SwingConstants.CENTER);
	lblDraganddrop.setHorizontalAlignment(SwingConstants.CENTER);
	lblDraganddrop.setBorder(new LineBorder(new Color(0, 0, 0)));
	lblDraganddrop.setAlignmentX(Component.CENTER_ALIGNMENT);
	lblDraganddrop.setVerticalAlignment(SwingConstants.CENTER);
	lblDraganddrop.setVerticalTextPosition(SwingConstants.CENTER);
	lblDraganddrop.setFont(new Font("Tahoma", Font.PLAIN, 48));
	getContentPane().add(lblDraganddrop);
	
	setVisible(true);
	
	new DropTarget(lblDraganddrop, new DropTargetListener()
	{
	    @Override
	    public void drop(DropTargetDropEvent dtde)
	    {
		try //wenn etwas gedropt wurde
		{
		    Transferable tr = dtde.getTransferable();
		    DataFlavor[] flavors = tr.getTransferDataFlavors(); //Welche Daten sind es
		    ArrayList<File> fileNames = new ArrayList<File>();
		    for (int i = 0; i < flavors.length; i++)
		    {
			if (flavors[i].isFlavorJavaFileListType())
			{
			    dtde.acceptDrop(dtde.getDropAction());
			    @SuppressWarnings("unchecked")
			    java.util.List<File> files = (java.util.List<File>) tr.getTransferData(flavors[i]);
			    for (int k = 0; k < files.size(); k++)
			    {
				fileNames.add(files.get(k)); 
				
				// Der Dateiname wird in der Comandozeile ausgegeben.
				System.out.println(files.get(k));
			    }
 
			    dtde.dropComplete(true);
			}
		    }
		    return;
		}
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
		dtde.rejectDrop();
		
	    }
 
	    @Override
	    public void dragEnter(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dragOver(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dropActionChanged(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dragExit(DropTargetEvent dte)
	    {}
 
	});
    }
	}


