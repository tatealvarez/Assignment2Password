/*
 * TATIANA ALVAREZ
 * 9.16.24
 * CS 341
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PasswordStrengthApp {

	private JFrame frame;
	private JTextField passwordText;
	private JTextArea outputText;
	private JButton submitButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordStrengthApp window = new PasswordStrengthApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PasswordStrengthApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(153, 153, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter a password to check it's strength");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(75, 15, 300, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(20, 48, 74, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		passwordText = new JTextField();
		passwordText.setBackground(new Color(204, 204, 255));
		passwordText.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		passwordText.setBounds(100, 40, 250, 30);
		frame.getContentPane().add(passwordText);
		passwordText.setColumns(10);
		
		submitButton = new JButton("Submit");
		submitButton.setToolTipText("");
		submitButton.setBackground(new Color(204, 204, 255));
		submitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		submitButton.setBounds(160, 75, 117, 29);
		frame.getContentPane().add(submitButton);
		
		JLabel lblNewLabel_2 = new JLabel("Output");
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(190, 115, 61, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		outputText = new JTextArea();
		outputText.setBackground(new Color(204, 204, 255));
		outputText.setWrapStyleWord(true);
		outputText.setEditable(false);
		outputText.setLineWrap(true);
		outputText.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		outputText.setBounds(75, 145, 300, 100);
		frame.getContentPane().add(outputText);
		
		registerSubmitButtonEvent();
	}
	
	//button event for submit button
	private void registerSubmitButtonEvent() {
		submitButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				
				//store the password as a string
				String password = passwordText.getText();
				
				//check strength of password and print result
				outputText.setText(checkPassword(password));
			}
		});
	}
	
	private String checkPassword(String password) {
		
		//turn password into a char array
		char a [] = password.toCharArray();
		
		//check if password is correct length
		if (a.length < 8) {
			return "Password must be at least 8 characters.";
		} else if (a.length > 12) {
			return "Password must be no more than 12 characters.";
		}
		
		//create a hashmap to store the times a character appears
		HashMap<Character, Integer> count = new HashMap<Character, Integer>();
		
		
		//nested for loop to check if there are repeating characters
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				
				//if char is in map, increment count
				if (count.containsKey(a[i]) && a[i] != ' ') {
					count.put(a[i], count.get(a[i]) + 1);
				} 
				//return if password has a space
				else if (a[i] == ' ') {
					return "Password must not include any spaces.";
				}
				//if not, add it to the map
				else {
					count.put(a[i], 1);
				}
				
				//prevent chars being counted multiple times due to nested loop
				a[i] = '0';
			}
		}
		
		//removes the 0s that were counted
		count.remove('0');
		
		//value to store largest block
		int largestValue = 0;
		
		//loop through map to find largest block
		for (HashMap.Entry<Character, Integer> entry: count.entrySet()) {
			Character key = entry.getKey();
			if (largestValue < count.get(key)) {
				largestValue = count.get(key);
			}
		}
		
		//returns weak response if block is larger than 2
		if (largestValue > 2) {
			return "The largest block in the password is " + largestValue + ". "
					+ "This password can be made stronger by reducing this block by " +
					+ (largestValue - 2) + "." ;
		}
		
		//returns decent response otherwise
		return "The largest block in the password is " + largestValue + ". This"
				+ " is a decent password.";
	}
}

