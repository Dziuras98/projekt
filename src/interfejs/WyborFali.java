package interfejs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class WyborFali extends JFrame implements ActionListener
{
	static final int SLIDER_MIN = 380;
	static final int SLIDER_MAX = 780;
	static final int SLIDER_INIT = 380;
	JSlider slider;
	JLabel label;
	int przycisk=0, wartosc, dark, ang, lambda;
	GradientButton ustaw;
	Boolean add;
	public WyborFali(int a, int b) throws HeadlessException 
	{
		add=false;
		ang = b;
		dark = a;
		this.setSize(300,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
	  
	  	JPanel panel1 = new JPanel();
	  	this.add(panel1, BorderLayout.PAGE_START);
	  		JRadioButton swiatlobiale = new JRadioButton("œwiat³o bia³e");
	  		panel1.add(swiatlobiale);
	  		swiatlobiale.setActionCommand("1");
	  		swiatlobiale.addActionListener(this);
	  	JPanel panel2 = new JPanel(new GridLayout(4,1));
	  	this.add(panel2, BorderLayout.CENTER);
	  		JPanel panel3 = new JPanel();
	  		panel2.add(panel3);
	  		JRadioButton lambda = new JRadioButton("wybór d³ugoœci fali");
	  		lambda.setActionCommand("2");
	  		lambda.addActionListener(this);
	  		panel3.add(lambda);
	  		slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	  		panel2.add(slider);
	  		slider.addChangeListener(new Slider());
	  		JPanel panel4 = new JPanel();
	  		panel2.add(panel4);
	  		label = new JLabel();
	  		panel4.add(label);
	  		ButtonGroup grupa = new ButtonGroup();
	  		grupa.add(swiatlobiale);
	  		grupa.add(lambda);
	  	JPanel panel5 = new JPanel();
	  	this.add(panel5, BorderLayout.PAGE_END);
	  		ustaw = new GradientButton("ustaw",  Color.white, Color.lightGray);
	  		panel5.add(ustaw);
	  		ustaw.addActionListener(new Ustaw());
	  		
	  	if(dark==0)
	  	{
	  		this.setBackground(Color.white);
	  		this.setForeground(Color.black);
	  		panel1.setBackground(Color.white);
	  		panel2.setBackground(Color.white);
	  		panel3.setBackground(Color.white);
	  		panel4.setBackground(Color.white);
	  		panel5.setBackground(Color.white);
	  		lambda.setBackground(Color.white);
	  		lambda.setForeground(Color.black);
	  		swiatlobiale.setBackground(Color.white);
	  		swiatlobiale.setForeground(Color.black);
	  		slider.setBackground(Color.white);
	  		slider.setForeground(Color.black);
	  		label.setBackground(Color.white);
	  		label.setForeground(Color.black);
	  		ustaw.setBackground(Color.white);
	  		ustaw.setForeground(Color.black);
	  		ustaw.setMode(0);
	  	}
	  	else
	  	{
	  		this.setBackground(Color.black);
	  		this.setForeground(Color.white);
	  		panel1.setBackground(Color.black);
	  		panel2.setBackground(Color.black);
	  		panel3.setBackground(Color.black);
	  		panel4.setBackground(Color.black);
	  		panel5.setBackground(Color.black);
	  		lambda.setBackground(Color.black);
	  		lambda.setForeground(Color.white);
	  		swiatlobiale.setBackground(Color.black);
	  		swiatlobiale.setForeground(Color.white);
	  		slider.setBackground(Color.black);
	  		slider.setForeground(Color.white);
	  		label.setBackground(Color.black);
	  		label.setForeground(Color.white);
	  		ustaw.setBackground(Color.black);
	  		ustaw.setForeground(Color.white);
	  		ustaw.setMode(1);
	  	}
	  	
	  	if(ang==1)
	  	{
	  		swiatlobiale.setText("white light");
	  		lambda.setText("wavelength");
	  		ustaw.setText("set");
	  	}
	}//koniec konstruktora
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		przycisk = Integer.parseInt(arg0.getActionCommand());
		if(przycisk==1)
		{
			wartosc = slider.getValue();
		}
	}
	
	public class Slider implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent arg0) 
		{
			if(przycisk==2)
			{
			String wartosc = String.format("%d", slider.getValue());
			label.setText(wartosc + "nm");
			lambda = slider.getValue();
			
			}
			else
			{
				slider.setValue(wartosc);
			}
		}
	}
	
	public class Ustaw implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(przycisk!=0)
			{
				add=true;
				setVisible(false);
				dispose();
			}
		}
	}
	public Boolean check()
	{
		return add;
	}
	public boolean chkolor()
	{
		if(przycisk==2)
			return true;
		else
			return false;
	}
}
