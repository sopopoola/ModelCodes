package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JList;


import Pkg.JavaCode.OutputModel;

import java.awt.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Message_window extends JFrame {
	
	
	List Path_Model = new List();
	ArrayList<OutputModel> outputmodels;
	public Message_window(ArrayList<OutputModel> models) {
		
		outputmodels = models;
		setTitle("Output");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		setSize(516,206);
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(25, 11, 290, 14);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setText("Test models generated...");
		
		Path_Model.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		Path_Model.setForeground(Color.BLACK);
		Path_Model.setBackground(new Color(240, 240, 240));
		Path_Model.setBounds(10, 41, 442, 99);
		getContentPane().add(Path_Model);
		Getmodels();
		
		JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOk.setBackground(new Color(240, 240, 240));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				System.exit(0);
			}
		});
		btnOk.setBounds(196, 136, 89, 23);
		getContentPane().add(btnOk);
		
		setVisible(true);
			
	}
	
	public void Getmodels()
	{
		ArrayList<OutputModel> array = new ArrayList<OutputModel>();
		//EmgStandaloneExample emg = new EmgStandaloneExample();
		array = outputmodels;
		
		for (int i =0 ; i< array.size() ; i++)
		{
			Path_Model.add(array.get(i).adress);
		}
	}
	
}
