package com.calci;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
public class Calculator implements ActionListener{
	JFrame frame;
	JTextField text;
	JButton[] button =new JButton[10];
	JButton[] arthemetic=new JButton[9];
	JButton add,sub,mul,div;
	JButton decimal,equals,delete,clear,negative;
	JPanel panel;
	Font myfont = new Font("TimesNewRoman",Font.BOLD,25);
	
	double num1=0,num2=0,result=0;
	char operator;
	
	Calculator(){
		
		frame=new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLayout(null);
		
		//text field
		text=new JTextField();
		text.setBounds(150, 50, 300, 50);
		text.setFont(myfont);
		text.setEditable(false);
		
		add= new JButton("+");
		sub= new JButton("-");
		mul= new JButton("*");
		div= new JButton("/");
		delete= new JButton("DEL");
		decimal= new JButton(".");
		equals= new JButton("=");
		clear= new JButton("AC");
		negative= new JButton("(-)");
		
		arthemetic[0]=add;
		arthemetic[1]=sub;
		arthemetic[2]=mul;
		arthemetic[3]=div;
		arthemetic[4]=decimal;
		arthemetic[5]=equals;
		arthemetic[6]=delete;
		arthemetic[7]=clear;
		arthemetic[8]=negative;
		
		for(int i=0;i<9;i++) {
			arthemetic[i].addActionListener(this);
			arthemetic[i].setFont(myfont);
			arthemetic[i].setFocusable(false);
		}
		
		for(int i=0;i<10;i++) {
			button[i]=new JButton(String.valueOf(i));
			button[i].addActionListener(this);
			button[i].setFont(myfont);
			button[i].setFocusable(false);
		}
		delete.setBounds(150,465 , 90,50);
		clear.setBounds(255,465 , 90,50);
		negative.setBounds(360, 465, 90, 50);
		
		panel=new JPanel();
		panel.setBounds(150, 150, 300, 300);
		panel.setLayout(new GridLayout(4,4,10,10));
//		panel.setBackground(Color.pink);
		frame.add(panel);
		panel.add(button[1]);
		panel.add(button[2]);
		panel.add(button[3]);
		panel.add(add);
		panel.add(button[4]);
		panel.add(button[5]);
		panel.add(button[6]);
		panel.add(sub);
		panel.add(button[7]);
		panel.add(button[8]);
		panel.add(button[9]);
		panel.add(mul);
		panel.add(decimal);
		panel.add(button[0]);
		panel.add(div);
		panel.add(equals);
		
		frame.add(text);
		frame.add(delete);
		frame.add(clear);
		frame.add(negative);
		
			
		
		frame.setVisible(true);
		
		
		
	}

	public static void main(String[] args) {
		Calculator c=new Calculator();
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++) {
			if(e.getSource()==button[i]) {
				text.setText(text.getText().concat(String.valueOf(i)));
			}
			
		}
		if(e.getSource()==decimal) {
			text.setText(text.getText().concat("."));
		}
		if(e.getSource()==add) {
			num1=Double.parseDouble(text.getText());
			operator='+';
			text.setText("");
		}
		if(e.getSource()==mul) {
			num1=Double.parseDouble(text.getText());
			operator='*';
			text.setText("");
		}
		if(e.getSource()==sub) {
			num1=Double.parseDouble(text.getText());
			operator='-';
			text.setText("");
		}
		if(e.getSource()==div) {
			num1=Double.parseDouble(text.getText());
			operator='/';
			text.setText("");
		}
		
		if(e.getSource()==equals) {
			num2=Double.parseDouble(text.getText());
			
			switch(operator) {
			case '+':
				result=num1+num2;
				
				break;
			case '-':result=num1-num2;
//			text.setText(String.valueOf(result));
			break;
			case '*':result=num1*num2;
//			text.setText(String.valueOf(result));
			break;
			case '/':result=num1/num2;
//			text.setText(String.valueOf(result));
			break;
			}
			text.setText(String.valueOf(result));
			num1=result;
		}
		if(e.getSource()==clear) {
			text.setText("");
		}
		if(e.getSource()==delete) {
			String string=text.getText();
			text.setText("");
			for(int i=0;i<string.length()-1;i++) {
				text.setText(text.getText()+string.charAt(i));
			
			}
		}
		
		if(e.getSource()==negative) {
			double temp=Double.parseDouble(text.getText());
			temp*=-1;
			text.setText(String.valueOf(temp));
		}
		
	}

}
