package GUI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.MessageBox;

//import EMG_Partitioning.FirstInputWindow;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.List;
import javax.swing.JSeparator;
import java.awt.Color;
import Pkg.JavaCode.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Button;
import javax.swing.JSpinner;
import java.awt.TextArea;
import java.awt.ScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import java.awt.Panel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.SystemColor;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class FirstInputWindow extends JFrame {
	
	private JTextField ecorefield;
	int k = 0;
	String emgAdr;
	 Vector<Object> data = new Vector<Object>();
	String combo_Def = "Random";
	String addr=new String();
	public FirstInputWindow() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Model Generation Tool");
		setSize(678,311);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select Ecore file:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 14, 90, 14);
		getContentPane().add(lblNewLabel);
		
		ClassDetection CD = new ClassDetection();
		
		ecorefield = new JTextField();
		ecorefield.setBounds(110, 11, 460, 20);
		getContentPane().add(ecorefield);
		ecorefield.setColumns(10);
		
		JButton Browsebtn = new JButton("Browse...");
		Browsebtn.setBackground(new Color(240, 240, 240));
		Browsebtn.setForeground(new Color(0, 0, 0));
		Browsebtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Browsebtn.setBounds(580, 10, 84, 23);
		getContentPane().add(Browsebtn);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 236, 654, 1);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(78, 47, 524, 1);
		getContentPane().add(separator_1);
		
		List list_Classes = new List();
		list_Classes.setBounds(10, 110, 95, 120);
		getContentPane().add(list_Classes);
		list_Classes.setEnabled(false);
		
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setBounds(121, 122, 53, 20);
		getContentPane().add(spinner);
		spinner.setValue(1);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.LIGHT_GRAY);
		panel.setBounds(187, 105, 477, 120);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
		table.setBackground(new Color(255, 255, 255));
		table.setForeground(new Color(0, 0, 0));
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBounds(93, 34, 614, 160);
		//String header[] = new String[] {"Class Name", "Instances","Attributes","Value" };
		DefaultTableModel dm = new DefaultTableModel(new String[] {"Class Name", "Instances","Attributes","Value" },0);
		
	    //dm.setColumnIdentifiers(header);
	    table.setModel(dm);
		panel.add(table);
		panel.add(new JScrollPane(table));
		panel.setVisible(false);
	    TableColumnModel cm = table.getColumnModel();
	    JComboBox cmb = new JComboBox<String>();
	    
	    cm.getColumn(3).setCellEditor(new DefaultCellEditor(cmb));
	    cmb.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    cmb.addItem("Random");
	    cmb.addItem("Select");
	    cmb.addItem("none");
	    cmb.setSelectedItem(2);
	   //new JComboBox(new DefaultComboBoxModel(new String[] {"Random","Select"}))
	    
	    /* Table Search shavad, shady baraye bazyabi b dard bkhore*/
	    cmb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //
                // Get the source of the component, which is our combo
                // box.
                //
                JComboBox comboBox = (JComboBox) event.getSource();

                Object selected = comboBox.getSelectedItem();
                               
                if(selected.toString().equals("Select"))
                {
                	file_chooser.resetChoosableFileFilters();
				//	file_chooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)"));
					try {
						OpenFile();
						JOptionPane.showMessageDialog(null, ChooseFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(ChooseFile.equals(null))
						dm.addRow(data);
                }
                
            }
        });
	    //panel.setBorder(new LineBorder(Color.BLUE));
	    Button button = new Button("Add >>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{   				
				if(list_Classes.getSelectedItem() != null){				
				   
					data = new Vector<Object>();
					panel.setVisible(true);
					//System.out.println("test :- " + list_Classes.getSelectedItem() + spinner.getValue());
				    data.add(list_Classes.getSelectedItem() );
				    data.add(spinner.getValue());
				    if (!CD.getAttributes().isEmpty())
				    {
				    	System.out.println(CD.getAttributes());
				    	data.add(CD.getAttributes().get(k));
				    	data.add("Random");
				    }
				    dm.addRow(data);
				   // System.out.println("test :- " + list_Classes.getSelectedItem() + spinner.getValue());
				    list_Classes.remove(list_Classes.getSelectedItem());
				    list_Classes.select(0);
				    k++;
			}					
			    }
		});
		
		button.setBounds(121, 148, 53, 22);
		getContentPane().add(button);
		
		Button btn_back = new Button("<< Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					list_Classes.addItem(dm.getValueAt(table.getSelectedRowCount(), 0).toString());
					//table.remove(0);
					}
		});
		
		btn_back.setBounds(121, 177, 53, 22);
		getContentPane().add(btn_back);
		JRadioButton createrdbtn = new JRadioButton("Create Emg file");
		ArrayList<String> className = new ArrayList<String>();
		Browsebtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					file_chooser.resetChoosableFileFilters();
					file_chooser.setFileFilter(new FileNameExtensionFilter("Metamodel files (*.ecore),(*.Emf)","ecore","emf"));
					OpenFile();
					ecorefield.setText(ChooseFile);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				CD.Setaddr(ChooseFile.replaceAll("\\\\", "/"));
				CD.main();
				
				list_Classes.setEnabled(false);
				list_Classes.select(0);
				
			}
		});
		
		
		
		JButton Next = new JButton("Next");
		Next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (ecorefield.getText().isEmpty())
					JOptionPane.showMessageDialog( null,"Enter Ecorefile adress");
				else if (emgfield.getText().isEmpty() && ! panel.isVisible())
					JOptionPane.showMessageDialog( null,"Enter EMG file adress");
				
				else if (createrdbtn.isSelected()){
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Set a file name");   
				 
				 int userSelection = fileChooser.showSaveDialog(new JFrame());
				  
				 if (userSelection == JFileChooser.APPROVE_OPTION) {
				     File fileToSave = fileChooser.getSelectedFile();
				     addr = fileToSave.getAbsolutePath() + ".emg";
				     System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				     emgAdr = addr;
				 }
				
				 
				PrintWriter writer = null;
				try {
					
					writer = new PrintWriter(addr, "UTF-8");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int count = 0; count < dm.getRowCount(); count++){
					  System.out.println(dm.getValueAt(count, 0).toString());
					}
				
				int num_Create = 0;
				while(num_Create < table.getRowCount()){
				
					System.out.println("$instances " + dm.getValueAt(num_Create, 1).toString());
					writer.println("$instances " + dm.getValueAt(num_Create, 1).toString());
					System.out.println("operation " + dm.getValueAt(num_Create, 0).toString() + " create(){}");
					writer.println("operation " + 
							dm.getValueAt(num_Create, 0).toString() + " create(){");
					
					
					try {
						if (dm.getValueAt(num_Create, 3).toString().equals("Random"))
						writer.println("self." +dm.getValueAt(num_Create, 2).toString()+"="+
								"nextString(\"LETTER_LOWER\",6" + ");");
					} catch (Exception e) {
						// TODO: handle exception
						writer.println("}");
					}
					//writer.println("}");
					num_Create++;
				}
				writer.close();	
				setVisible(false);
				SecondWindow window = new SecondWindow(ecorefield.getText(),emgAdr);
				}
				else
				{
					setVisible(false);
					SecondWindow window = new SecondWindow(ecorefield.getText(),emgAdr);
				}
			}
		});
		
		Next.setForeground(new Color(0, 0, 0));
		Next.setBackground(new Color(240, 240, 240));
		Next.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Next.setBounds(11, 248, 122, 23);
		getContentPane().add(Next);
		
		JButton Cancel = new JButton("Finish");
		Cancel.setBackground(new Color(240, 240, 240));
		Cancel.setForeground(new Color(0, 0, 0));
		Cancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				if (createrdbtn.isSelected()){
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Specify a file to save");   
				 
				 int userSelection = fileChooser.showSaveDialog( new JFrame());
				  
				 if (userSelection == JFileChooser.APPROVE_OPTION) {
				     File fileToSave = fileChooser.getSelectedFile();
				     addr = fileToSave.getAbsolutePath() + ".emg";
				     System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				     emgAdr = addr;
				 }
				
				 
				PrintWriter writer = null;
				try {
					
					writer = new PrintWriter(addr, "UTF-8");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int count = 0; count < dm.getRowCount(); count++){
					  System.out.println(dm.getValueAt(count, 0).toString());
					}
				
				int num_Create = 0;
				while(num_Create < table.getRowCount()){
				
					System.out.println("$instances " + dm.getValueAt(num_Create, 1).toString());
					writer.println("$instances " + dm.getValueAt(num_Create, 1).toString());
					System.out.println("operation " + dm.getValueAt(num_Create, 0).toString() + " create(){}");
					writer.println("operation " + 
							dm.getValueAt(num_Create, 0).toString() + " create(){");
					if (dm.getValueAt(num_Create, 3).toString().equals("Random") )
					writer.println("self." +dm.getValueAt(num_Create, 2).toString()+"="+
							"nextString(\"LETTER_LOWER\",6" + ");");
					
					writer.println("}");
					num_Create++;
				}
				writer.close();	
				}
				System.exit(0);
			}
		});
		Cancel.setBounds(143, 248, 112, 23);
		getContentPane().add(Cancel);
		
		JButton button_2 = new JButton("Cancel");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button_2.setForeground(Color.BLACK);
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_2.setBackground(SystemColor.menu);
		button_2.setBounds(496, 248, 140, 23);
		getContentPane().add(button_2);
		
		JButton emgbrowse = new JButton("Browse...");
		emgbrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try 
				{
					file_chooser.resetChoosableFileFilters();
					file_chooser.setFileFilter(new FileNameExtensionFilter("EMG files (*.emg)","emg"));
					OpenFile();
					emgfield.setText(ChooseFile);
					emgAdr = ChooseFile;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		emgbrowse.setForeground(new Color(0, 0, 0));
		emgbrowse.setFont(new Font("Tahoma", Font.PLAIN, 11));
		emgbrowse.setBackground(SystemColor.menu);
		emgbrowse.setBounds(580, 57, 84, 23);
		
		getContentPane().add(emgbrowse);
		
		
		emgfield = new JTextField();
		emgfield.setForeground(new Color(0, 0, 0));
		emgfield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		emgfield.setColumns(10);
		emgfield.setBounds(110, 58, 460, 20);
		getContentPane().add(emgfield);
		
		list_Classes.setEnabled(false);
		
		JRadioButton Selectrdbtn = new JRadioButton("Select Emg file:");
		Selectrdbtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Selectrdbtn.setBackground(new Color(240, 240, 240));
		Selectrdbtn.setSelected(true);
		Selectrdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emgbrowse.setEnabled(true);
				emgfield.setEnabled(true);
				list_Classes.setEnabled(false);
				btn_back.setEnabled(false);
				button.setEnabled(false);
				panel.setEnabled(false);
			}
		});
		buttonGroup.add(Selectrdbtn);
		Selectrdbtn.setBounds(6, 55, 99, 23);
		getContentPane().add(Selectrdbtn);
				
		
		
		createrdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emgbrowse.setEnabled(false);
				emgfield.setEnabled(false);
				list_Classes.setEnabled(true);
				btn_back.setEnabled(true);
				button.setEnabled(true);
				panel.setEnabled(true);
				spinner.setEnabled(true);
				for (int i = 0  ; i<CD.getClasses().size() ; i++){
					className.add(i, CD.getClasses().get(i));
					System.out.println(i + "-" + className.get(i));
					list_Classes.add(className.get(i));
						
				}
				
				
			}
		});
		createrdbtn.setBackground(new Color(240, 240, 240));
		createrdbtn.setForeground(new Color(0, 0, 0));
		createrdbtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		buttonGroup.add(createrdbtn);
		createrdbtn.setBounds(6, 81, 109, 23);
		getContentPane().add(createrdbtn);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBounds(110, 93, 514, 1);
		getContentPane().add(separator_2);
		setVisible(true);
	}
	
	JFileChooser file_chooser = new JFileChooser();
	String ChooseFile;
	private JTable table;
	private JTextField emgfield;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public void OpenFile() throws IOException
	{
				file_chooser.setMultiSelectionEnabled(true);
				file_chooser.setCurrentDirectory(new File("/"));
		        file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        	        
		   if(file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
		
				File file = file_chooser.getSelectedFile();
				ChooseFile = file.getPath();
			}
		   else
			   ChooseFile = null;
	}
}