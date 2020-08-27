package GUI;


import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.MessageBox;

import Pkg.JavaCode.OutputModel;

import java.awt.Font;
import java.awt.List;

import javax.swing.JTable;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;

public class Pattern_Set extends JFrame {
	private JTextField ModelnameField;

	private JTable table;
	private OutputModel model = new OutputModel();
	
	public Pattern_Set(List list,ArrayList<OutputModel> models) {
		setType(Type.POPUP);
		setResizable(false);
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(455,192);
		JLabel lblName = new JLabel("Model Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblName.setBounds(16, 11, 79, 14);
		getContentPane().add(lblName);
		
		ModelnameField = new JTextField();
		ModelnameField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ModelnameField.setBounds(91, 8, 345, 20);
		getContentPane().add(ModelnameField);
		ModelnameField.setColumns(10);
		
		JLabel lblPatternFiles = new JLabel("Pattern Files : ");
		lblPatternFiles.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPatternFiles.setBounds(16, 36, 79, 14);
		getContentPane().add(lblPatternFiles);
		
		table = new JTable();
		
		table.setCellSelectionEnabled(true);
		//table.setDefaultRenderer(String.class, centerRenderer);
		table.setBackground(new Color(255, 255, 255));
		table.setForeground(new Color(0, 0, 0));
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		DefaultTableModel dm = new DefaultTableModel(new String[] {"Pattern", "Number"},0);
	    table.setModel(dm);
	       
		JScrollPane scrollPane = new JScrollPane(table);
		
	    scrollPane.setBounds(91, 36, 262, 87);
	    getContentPane().add(scrollPane);
	    table.getColumnModel().getColumn(1).setPreferredWidth(1);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
		
	    JButton btnRemove = new JButton("Remove");
	    btnRemove.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		 dm.removeRow(table.getSelectedRow());
	    	}
	    });
	    
	    btnRemove.setToolTipText("");
	    btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    btnRemove.setBackground(SystemColor.menu);
	    btnRemove.setBounds(360, 65, 76, 23);
	    getContentPane().add(btnRemove);
	 
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(240, 240, 240));
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					file_chooser.resetChoosableFileFilters();
					file_chooser.setFileFilter(new FileNameExtensionFilter("Pattern files (*.epl)","epl"));
					OpenFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Vector<Object> data = new Vector<Object>();
			    data.add(ChooseFile);
			    data.add("1");
			    dm.addRow(data);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setBounds(360, 39, 76, 23);
		getContentPane().add(btnAdd);
		
		JButton btnOk = new JButton("Ok");
		
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!ModelnameField.getText().isEmpty())
				{
					ChooseFile = ChooseFile.replaceAll("\\\\", "/");
					int j  = ChooseFile.lastIndexOf("/");
					System.out.println("CHoose File = " + ChooseFile);
					model.adress = ChooseFile.substring(0, j+1) + ModelnameField.getText() + ".model";
					
					for (int count = 0; count < dm.getRowCount(); count++){
					
						model.patterns.add(dm.getValueAt(count, 0).toString().replaceAll("\\\\", "/"));
						model.numbers.add(Integer.parseInt(dm.getValueAt(count, 1).toString()));
						System.out.println(count +"\n"+ model.adress +"\n"+ model.patterns.get(count) +"\n"+model.numbers);
					}
					
					list.addItem(model.adress);
					
				/*	RandomAccessFile writer = null;
					
					int num_Create = 0;
					while(num_Create < table.getRowCount()){
						
						String addr = dm.getValueAt(num_Create, 0).toString();
						System.out.println("Address" + addr);
						try {
							writer = new RandomAccessFile(new File(addr), "rw");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("$number " + dm.getValueAt(num_Create, 1).toString());
						try {
							writer.seek(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							writer.write(("$number " + dm.getValueAt(num_Create, 1).toString()).getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						num_Create++;
					}
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	*/
					models.add(model);
					setVisible(false);
				}
					else
					JOptionPane.showMessageDialog( null,"Enter Model Name");
			}
		});
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOk.setBackground(SystemColor.menu);
		btnOk.setBounds(91, 129, 133, 23);
		getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBackground(SystemColor.menu);
		btnCancel.setBounds(234, 129, 119, 23);
		getContentPane().add(btnCancel);
		setVisible(true);
	}
	
	JFileChooser file_chooser = new JFileChooser();
	String ChooseFile;
	public void OpenFile() throws IOException
	{
				file_chooser.setMultiSelectionEnabled(true);
				file_chooser.setCurrentDirectory(new File("/"));
		        file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        	        
		   if(file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				File file = file_chooser.getSelectedFile();
				ChooseFile = file.getAbsolutePath();
			}
		   
		   else
			   ChooseFile = null;
	}
}
