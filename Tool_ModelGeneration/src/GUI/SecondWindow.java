package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;


import Pkg.JavaCode.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.SystemColor;

public class SecondWindow extends JFrame {
	
	ArrayList<OutputModel> models = new ArrayList<OutputModel>();
	
	public SecondWindow(String ecoreadr, String emgadr) {
		setResizable(false);
		
		System.out.println(ecoreadr + "-----------" + emgadr);
		setTitle("Model Generation Tool");
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EMG file");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel EmgLbl = new JLabel(emgadr);
		EmgLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		EmgLbl.setBounds(66, 11, 311, 14);
		getContentPane().add(EmgLbl);
		EmgStandaloneExample emg = new EmgStandaloneExample();
		
		
		List PattcomList = new List();
		PattcomList.setBounds(10, 179, 317, 87);
		getContentPane().add(PattcomList);
		
		List PatternList = new List();
		PatternList.setBounds(10, 55, 317, 98);
		getContentPane().add(PatternList);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(240, 240, 240));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setForeground(new Color(0, 0, 0));
		btnAdd.setBounds(333, 80, 80, 23);
		getContentPane().add(btnAdd);
		setVisible(true);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						
				Pattern_Set pt = new Pattern_Set(PatternList,models);		
				}
				
		});
		
		getContentPane().add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBackground(new Color(240, 240, 240));
		btnRemove.setForeground(new Color(0, 0, 0));
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setBounds(333, 113, 80, 23);
		getContentPane().add(btnRemove);
		setSize(new Dimension(441, 358));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PatternList.remove(PatternList.getSelectedIndex());
			}
		});
		
		getContentPane().add(btnRemove);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(110, 43, 305, 2);
		getContentPane().add(separator);
		
		JLabel lblSelectPatterns = new JLabel("equivalence Classes");
		lblSelectPatterns.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectPatterns.setBounds(5, 35, 147, 14);
		getContentPane().add(lblSelectPatterns);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(10, 272, 403, 2);
		getContentPane().add(separator_1);
		
		JButton btnGenerateModels = new JButton("Generate Models");
		btnGenerateModels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				ArrayList<String> patterns = new ArrayList<String>();
				//ArrayList<String> Models = new ArrayList<String>();
				
				/*for(int i =0 ;i < PatternList.getItemCount();i++)
				{			
					patterns.add(PatternList.getItem(i).replaceAll("\\\\", "/"));
				}
				*/
				emg.SetMMFilePath(ecoreadr);
				
				if (PattcomList.countItems() != 0 )
				{
					BufferedWriter writer = null;
					try {
						writer = new BufferedWriter(new FileWriter(emgadr,true));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					BufferedReader read = null;
					for(int i =0 ;i < PattcomList.getItemCount();i++)
					{			
						try {
								String str;
								read = new BufferedReader(new FileReader(PattcomList.getItem(i).replaceAll("\\\\", "/")));
								
								try {
									while ((str = read.readLine()) != null) {
											writer.write("\n" + str);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					        try {
								read.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					        try {
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				
				emg.SetEmgFilePath(EmgLbl.getText());
				emg.SetModels(models);
				
				try {
					EmgStandaloneExample.main();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setVisible(false);
				Message_window msg = new Message_window(emg.getOutputModels());
			}
		});
		
		btnGenerateModels.setForeground(Color.BLACK);
		btnGenerateModels.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGenerateModels.setBackground(SystemColor.menu);
		btnGenerateModels.setBounds(10, 285, 117, 23);
		getContentPane().add(btnGenerateModels);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBackground(SystemColor.menu);
		btnCancel.setBounds(140, 285, 97, 23);
		getContentPane().add(btnCancel);
		
	
		JLabel label_1 = new JLabel("Select Patterns Files");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(5, 159, 122, 14);
		getContentPane().add(label_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBounds(108, 166, 305, 2);
		getContentPane().add(separator_2);
		
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					file_chooser.resetChoosableFileFilters();
					file_chooser.setFileFilter(new FileNameExtensionFilter("Pattern files (*.epl)","epl"));
					OpenFile();
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				if (ChooseFile != null)
					PattcomList.addItem(ChooseFile);
			}
			
		});
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button.setBackground(SystemColor.menu);
		button.setBounds(333, 194, 80, 23);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("Remove");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PattcomList.remove(PattcomList.getSelectedIndex());
			}
		});
		button_1.setForeground(Color.BLACK);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBackground(SystemColor.menu);
		button_1.setBounds(333, 227, 80, 23);
		getContentPane().add(button_1);
		setVisible(true);
	}
	
	JFileChooser file_chooser = new JFileChooser();
	
	String ChooseFile;
	
	public void OpenFile() throws IOException
	{
		
				file_chooser.setMultiSelectionEnabled(true);
				file_chooser.setCurrentDirectory(new File("/"));
		        file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        file_chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));
		        file_chooser.setBackground(new Color(240, 240, 240));
		        file_chooser.setForeground(new Color(0, 0, 0));
		        file_chooser.setApproveButtonText("Add Pattern");
		        
		        if(file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
		
				File file = file_chooser.getSelectedFile();
				ChooseFile = file.getPath();
			}
		   
		   else
			   ChooseFile = null;
		}
}
