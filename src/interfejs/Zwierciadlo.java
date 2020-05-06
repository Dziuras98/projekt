package interfejs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Zwierciadlo extends JFrame implements ActionListener
{
	int dark, ang, a=0;
	GradientButton dodaj;
	Boolean add;
	public Zwierciadlo(int a, int b) throws HeadlessException 
	{
		add=false;
		ang = b;
		dark = a;
		this.setSize(300,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4,1));
		
		JPanel panel1 = new JPanel();
		this.add(panel1);
		JRadioButton plaskie = new JRadioButton("zwierciad³o p³askie");
		panel1.add(plaskie);
		plaskie.setActionCommand("1");
		plaskie.addActionListener(this);
		
		JPanel panel2 = new JPanel();
		this.add(panel2);
		JRadioButton wklesle = new JRadioButton("zwierciad³o wklês³e");
		panel2.add(wklesle);
		wklesle.setActionCommand("2");
		wklesle.addActionListener(this);
		
		JPanel panel3 = new JPanel();
		this.add(panel3);
		JRadioButton wypukle = new JRadioButton("zwierciad³o wypuk³e");
		panel3.add(wypukle);
		wypukle.setActionCommand("3");
		wypukle.addActionListener(this);
		
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(plaskie);
		grupa.add(wklesle);
		grupa.add(wypukle);
		
		JPanel panel4 = new JPanel();
		this.add(panel4);
		dodaj = new GradientButton("dodaj", Color.white, Color.lightGray);
	  	panel4.add(dodaj);
	  	dodaj.addActionListener(new DodajZwierciadlo());
		
		if(dark==0)
	  	{
	  		this.setBackground(Color.white);
	  		this.setForeground(Color.black);
	  		panel1.setBackground(Color.white);
	  		panel2.setBackground(Color.white);
	  		panel3.setBackground(Color.white);
	  		panel4.setBackground(Color.white);
	  		plaskie.setBackground(Color.white);
	  		plaskie.setForeground(Color.black);
	  		wypukle.setBackground(Color.white);
	  		wypukle.setForeground(Color.black);
	  		wklesle.setBackground(Color.white);
	  		wklesle.setForeground(Color.black);
	  		dodaj.setBackground(Color.white);
	  		dodaj.setForeground(Color.black);
	  		dodaj.setMode(0);
	  	}
	  	else
	  	{
	  		this.setBackground(Color.black);
	  		this.setForeground(Color.white);
	  		panel1.setBackground(Color.black);
	  		panel2.setBackground(Color.black);
	  		panel3.setBackground(Color.black);
	  		panel4.setBackground(Color.black);
	  		plaskie.setBackground(Color.black);
	  		plaskie.setForeground(Color.white);
	  		wypukle.setBackground(Color.black);
	  		wypukle.setForeground(Color.white);
	  		wklesle.setBackground(Color.black);
	  		wklesle.setForeground(Color.white);
	  		dodaj.setForeground(Color.white);
	  		dodaj.setBackground(Color.black);
	  		dodaj.setMode(1);
	  	}
	  	
	  	if(ang==1)
	  	{
	  		plaskie.setText("plane mirror");
	  		wypukle.setText("convex mirror");
	  		wklesle.setText("concave mirror");
	  		dodaj.setText("add");
	  	}
	}//koniec konstruktora
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		a = Integer.parseInt(arg0.getActionCommand());
	}
	
	public class DodajZwierciadlo implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(a!=0)
			{
				add=true;
				setVisible(false);
				dispose();
			}
		}
	}
	
	public int typ()
	{
		return a;
	}
	
	public Boolean check()
	{
		return add;
	}
}
