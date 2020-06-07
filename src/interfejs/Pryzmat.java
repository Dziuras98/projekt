package interfejs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import interfejs.WyborFali.Slider;

public class Pryzmat extends JFrame
{
	static final int SLIDER_MIN = 1;
	static final int SLIDER_MAX = 30;
	static final int SLIDER_INIT = 10;
	JSlider slider;
	JLabel label;
	int dark, ang;
	float n;
	GradientButton dodaj;
	Boolean add;
	public Pryzmat(int a, int b) throws HeadlessException 
	{
		add = false;
		ang = b;
		dark = a;
		this.setSize(300,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4,1));
	  	JPanel panel1 = new JPanel();
	  	this.add(panel1);
	  	JLabel wspolczynnik = new JLabel("wspó³czynnik za³amania");
	  	panel1.add(wspolczynnik);
	  	
	  	JPanel panel2 = new JPanel();
	  	this.add(panel2);
	  	slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
  		panel2.add(slider);
  		slider.addChangeListener(new Slider());
  		
	  	JPanel panel3 = new JPanel();
	  	this.add(panel3);
	  	label = new JLabel();
	  	panel3.add(label);
	  	
	  	JPanel panel4 = new JPanel();
	  	this.add(panel4);
	  	dodaj = new GradientButton("dodaj", Color.white, Color.lightGray);
	  	panel4.add(dodaj);
	  	dodaj.addActionListener(new DodajPryzmat());
	  	
	  	if(dark==0)
	  	{
	  		this.setBackground(Color.white);
	  		this.setForeground(Color.black);
	  		panel1.setBackground(Color.white);
	  		panel2.setBackground(Color.white);
	  		panel3.setBackground(Color.white);
	  		panel4.setBackground(Color.white);
	  		wspolczynnik.setBackground(Color.white);
	  		wspolczynnik.setForeground(Color.black);
	  		slider.setBackground(Color.white);
	  		slider.setForeground(Color.black);
	  		label.setBackground(Color.white);
	  		label.setForeground(Color.black);
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
	  		wspolczynnik.setBackground(Color.black);
	  		wspolczynnik.setForeground(Color.white);
	  		slider.setBackground(Color.black);
	  		slider.setForeground(Color.white);
	  		label.setBackground(Color.black);
	  		label.setForeground(Color.white);
	  		dodaj.setForeground(Color.white);
	  		dodaj.setBackground(Color.black);
	  		dodaj.setMode(1);
	  	}
	  	
	  	if(ang==1)
	  	{
	  		wspolczynnik.setText("refractive index");
	  		dodaj.setText("add");
	  	}
	}//koniec konstruktora
	
	public class Slider implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent arg0) 
		{
			String wartosc = String.format("%.1f", (float)slider.getValue()/10);
			label.setText(wartosc);
		}
	}
	
	public class DodajPryzmat implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			add=true;
			n = (float)slider.getValue()/10;
			setVisible(false);
			dispose();
		}
	}
	public Boolean check() 
	{
		return add;
	}
	public float getn() {
		return n;
	}
	
}
