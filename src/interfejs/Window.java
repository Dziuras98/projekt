package interfejs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileSystemView;



public class Window extends JFrame
{
	int ang=0;
	int dark=0;
	Symulacja symulacja;
	WyborFali oknofali;
	Pryzmat oknopryzmatu;
	GradientToggleButton start, ciemny;
	GradientButton fala, pryzmat, zwierciadlo;
	JMenuBar menuBar;
	JMenu menubutton;
	JMenuItem nowa, otworz, zapisz;
	JRadioButtonMenuItem jezyk;

	public Window() throws HeadlessException 
	{
	  this.setSize(1100,700);
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  this.setTitle("Odbicie i za³amanie œwiat³a");
	  this.setLayout(new BorderLayout());
	    
		JPanel menu = new JPanel(new GridLayout(1,8));
		this.add(menu, BorderLayout.PAGE_START);
	
			menuBar =  new JMenuBar();
			menubutton = new JMenu("Menu");
			menubutton.setMnemonic(KeyEvent.VK_A);
			menuBar.add(menubutton);
			
			jezyk = new JRadioButtonMenuItem("English");
			menubutton.add(jezyk);
			ActionListener zmienjezyk = new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					
					if(ang==0)
					{
						nowa.setText("New simulation");
						otworz.setText("Open");
						zapisz.setText("Save");
						fala.setText("choose wavelength");
						pryzmat.setText("add prism");
						zwierciadlo.setText("add mirror");
						ciemny.setText("dark mode");
						ang=1;
					}
					else
					{
						nowa.setText("Nowa symulacja");
						otworz.setText("Otwórz");
						zapisz.setText("Zapisz");
						fala.setText("wybór fali");
						pryzmat.setText("dodaj pryzmat");
						zwierciadlo.setText("dodaj zwierciad³o");
						ciemny.setText("tryb ciemny");
						ang=0;
					}
				}	
			};
		   	jezyk.addActionListener(zmienjezyk);
		   	
			nowa = new JMenuItem("Nowa symulacja");
			menubutton.add(nowa);
			ActionListener wyczysc = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					symulacja.wyczysc();
					start.setSelected(false);
				}
			};
			nowa.addActionListener(wyczysc);
			
			otworz = new JMenuItem("Otwórz");
			menubutton.add(otworz);
			ActionListener otwieranie = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

					int returnValue = jfc.showOpenDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) 
					{
						File in = jfc.getSelectedFile();
						symulacja.wczytaj(in);
					}
				}
			};
			otworz.addActionListener(otwieranie);
			
			zapisz = new JMenuItem("Zapisz");
			menubutton.add(zapisz);
			ActionListener zapis = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

					int returnValue = jfc.showSaveDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) 
					{
						File out = jfc.getSelectedFile();
						symulacja.zapisz(out);
					}
				}
			};
			zapisz.addActionListener(zapis);
			menuBar.add(menubutton);
			this.setJMenuBar(menuBar);
	
			JLabel pusta1 = new JLabel();
			menu.add(pusta1);
			
			fala = new GradientButton("wybór fali", Color.white, Color.lightGray);
			menu.add(fala);
			ActionListener lambda = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					oknofali = new WyborFali(dark, ang);
					oknofali.setVisible(true);
					
					oknofali.addWindowListener(new java.awt.event.WindowAdapter()
					{
						@Override
						public void windowClosed(java.awt.event.WindowEvent windowEvent)
						{
							if(oknofali.check())
							{
								symulacja.zapisLambda(oknofali.lambda);
								symulacja.zapiskolor(oknofali.chkolor());
							}
						}
					});
				}
			};
			fala.addActionListener(lambda);
			
			pryzmat = new GradientButton("dodaj pryzmat", Color.white, Color.lightGray);
			menu.add(pryzmat);
			ActionListener prism = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					oknopryzmatu = new Pryzmat(dark, ang);
					oknopryzmatu.setVisible(true);
					
					oknopryzmatu.addWindowListener(new java.awt.event.WindowAdapter()
					{
						@Override
						public void windowClosed(java.awt.event.WindowEvent windowEvent)
						{
							if(oknopryzmatu.check())
							{
								symulacja.dodajZwierciadlo(oknopryzmatu.getn());
							}
						}
					});
				}
			};
			pryzmat.addActionListener(prism);
			
			zwierciadlo = new GradientButton("dodaj zwierciadlo", Color.white, Color.lightGray);
			menu.add(zwierciadlo);
			ActionListener mirror = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					Zwierciadlo oknozwierciadla = new Zwierciadlo(dark, ang);
					oknozwierciadla.setVisible(true);
					
					oknozwierciadla.addWindowListener(new java.awt.event.WindowAdapter()
					{
						@Override
						public void windowClosed(java.awt.event.WindowEvent windowEvent)
						{
							if(oknozwierciadla.check())
							{
								symulacja.dodajZwierciadlo(oknozwierciadla.typ());
							}
						}
					});
				}
			};
			zwierciadlo.addActionListener(mirror);
			
			start = new GradientToggleButton("START/STOP", Color.white, Color.lightGray);
			menu.add(start);
			ActionListener startstop = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(symulacja.czysymulacja())
						symulacja.symulacja(false);
					else
						symulacja.symulacja(true);
				}
			};
			start.addActionListener(startstop);
			
    		JLabel pusta2 = new JLabel();
    		menu.add(pusta2);
    		
    		ciemny = new GradientToggleButton("tryb ciemny", Color.white, Color.lightGray);
    		menu.add(ciemny);
    	   	ActionListener darkmode = new ActionListener() 
    		{
    			@Override
    			public void actionPerformed(ActionEvent arg0) 
    			{
    				
    				if(dark==0)
    				{
    					menu.setBackground(Color.black);
    					menu.setForeground(Color.white);
    					symulacja.setBackground(Color.black);
    					symulacja.setForeground(Color.white);
    					fala.setBackground(Color.black);
    					fala.setForeground(Color.white);
    					pryzmat.setBackground(Color.black);
    					pryzmat.setForeground(Color.white);
    					zwierciadlo.setBackground(Color.black);
    					zwierciadlo.setForeground(Color.white);
    					start.setBackground(Color.black);
    					start.setForeground(Color.white);
    					ciemny.setBackground(Color.black);
    					ciemny.setForeground(Color.white);
    					fala.setMode(1);
    					pryzmat.setMode(1);
    					zwierciadlo.setMode(1);
    					start.setMode(1);
    					ciemny.setMode(1);
    					symulacja.setdark(true);
    					dark=1;
    				}
    				else
    				{
    					menu.setBackground(Color.lightGray);
    					menu.setForeground(Color.black);
    					symulacja.setBackground(Color.lightGray);
    					symulacja.setForeground(Color.black);
    					fala.setBackground(Color.white);
    					fala.setForeground(Color.black);
    					pryzmat.setBackground(Color.white);
    					pryzmat.setForeground(Color.black);
    					zwierciadlo.setBackground(Color.white);
    					zwierciadlo.setForeground(Color.black);
    					start.setBackground(Color.white);
    					start.setForeground(Color.black);
    					ciemny.setBackground(Color.white);
    					ciemny.setForeground(Color.black);
    					fala.setMode(0);
    					pryzmat.setMode(0);
    					zwierciadlo.setMode(0);
    					start.setMode(0);
    					ciemny.setMode(0);
    					symulacja.setdark(false);
    					dark=0;
    				}
    			}
    		};
    		ciemny.addActionListener(darkmode);
	
		symulacja = new Symulacja();
		this.add(symulacja, BorderLayout.CENTER);
	   						
		menu.setBackground(Color.lightGray);
		menu.setForeground(Color.black);
		symulacja.setBackground(Color.lightGray);
		symulacja.setForeground(Color.black);
		fala.setBackground(Color.white);
		fala.setForeground(Color.black);
		pryzmat.setBackground(Color.white);
		pryzmat.setForeground(Color.black);
		zwierciadlo.setBackground(Color.white);
		zwierciadlo.setForeground(Color.black);
		start.setBackground(Color.white);
		start.setForeground(Color.black);
		ciemny.setBackground(Color.white);
		ciemny.setForeground(Color.black);
		fala.setMode(0);
		pryzmat.setMode(0);
		zwierciadlo.setMode(0);
		start.setMode(0);
		ciemny.setMode(0);
    } //koniec konstruktora
	
    public static void main(String[] args) 
    {
    	Window frame = new Window();
    	frame.setVisible(true);
    }
}
