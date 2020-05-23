package interfejs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;



public class Symulacja extends JPanel implements MouseListener, MouseMotionListener
{
int lambda, obiekt, sobiekt, zalamany;
double myszxy;
boolean mode, obrotzrodla, dark, kolor, aktywny, wielekolorow, fincal;
Point2D punktroz;
float n;
Zrodlo zr;
Promien ray;
ArrayList<ZwierciadlaPryzmaty> zwierciadla = new ArrayList<ZwierciadlaPryzmaty>();
ArrayList<ArrayList<Point2D>> kolorowepromienie;
ArrayList<Promien> colorray;
ArrayList<Point2D> promien;
ArrayList<Thread> obliczenia;
int spowolnienie=0;
Przeciecia inter;
int odstep=0;
int currlambda;

	public Symulacja()
	{
		punktroz = new Point2D.Double(0,0);
		addMouseListener(this);
		addMouseMotionListener(this);
		obiekt=0;
		sobiekt=0;
		mode=false;
		zr = new Zrodlo();
		dark=false;
		ray = new Promien();
		obrotzrodla=false;
		promien = new ArrayList<Point2D>();
		ray.getpunkty().add(zr.getstart());
		ray.getkaty().add((double)zr.getkat());
		inter = new Przeciecia();
		colorray = new ArrayList<Promien>();
		kolorowepromienie = new ArrayList<ArrayList<Point2D>>();
		obliczenia = new ArrayList<Thread>();
		fincal=true;
		for(int i=380; i<781; i++)
		{
			colorray.add(new Promien());
			if(odstep==0)
			{
				kolorowepromienie.add(new ArrayList<Point2D>());
				colorray.get(i-380).setm(Math.pow(2.71828182846,(500-i)*0.0001));
				colorray.get(i-380).setlambda(i);
				colorray.get(i-380).setkolor(true);
			}
			odstep++;
			if(odstep==4)
				odstep=0;
		}
	}

	public void dodajZwierciadlo(int typ)
	{
		zwierciadla.add(new ZwierciadlaPryzmaty(typ));
		repaint();
	}
	public void dodajZwierciadlo(float n)
	{
		zwierciadla.add(new ZwierciadlaPryzmaty(n));
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.gray);
		Graphics2D g2d = (Graphics2D) g;
		BasicStroke bs = new BasicStroke(6);
		g2d.setStroke(bs);
		for(int i=0; i<zwierciadla.size(); i++)
		{
			g2d.setColor(Color.gray);
			if(dark)
				zwierciadla.get(i).setdark(true);
			else
				zwierciadla.get(i).setdark(false);
			zwierciadla.get(i).paint(g2d);
			g2d.setColor(Color.black);
		}
		if(aktywny)
		{
			wielekolorow=false;
			obliczpromien(ray, false);
			ray.paint(g2d);
			if(wielekolorow)
			{
				odstep=0;
				if(fincal)
				for(int i=380; i<781; i++)
				{
					fincal=false;
				/*	if(odstep==0) 
					{*/
						currlambda=i;
						obliczenia.add(new Thread(new Runnable() 
						{
							@Override
							public void run()
							{
								obliczpromien(colorray.get(currlambda-380), true);
								colorray.get(currlambda-380).paint(g2d);
								if(currlambda!=380) {
									obliczenia.get(currlambda-381).stop();
								}
							}
						}));
						if(!obliczenia.get(i-380).isAlive())
							obliczenia.get(i-380).run();
					//}	
				}
				int chk=1;
				for(int i=380; i<781; i++)
				{
					if(obliczenia.get(i-380).isAlive())
						chk--;
				}
				if(chk==1)
				{
					fincal=true;
					obliczenia.clear();
				}
				int comp=0;
			/*	for(int i=380; i<781; i++) {
					obliczenia.get(i-380).stop();
				}*/
				/*	odstep++;
					if(odstep==4)
						odstep=0;
				}*/
			}
		}
		zr.paint(g2d);
	}
	
	public void zapisLambda(int a)
	{
		lambda = a;
		ray.setlambda(lambda);
		repaint();
	}
	
	public void zapiskolor(boolean value)
	{
		kolor=value;
		ray.setkolor(value);
		repaint();
	}

	public void zapisn(float a)
	{
		n = a;
	}
	
	public void symulacja(boolean a)
	{
		aktywny = a;
		repaint();
	}
	
	public void wczytaj (File inputfile)
	{
		try
		{
			int rozmiar=0, nexttyp=0, nx=0, ny=0, nkat=0, nn=0;
			Scanner skaner = new Scanner(inputfile);
			kolor=skaner.nextBoolean();
			ray.setkolor(kolor);
			lambda=skaner.nextInt();
			ray.setlambda(lambda);
			zr.setkat(skaner.nextInt());
			ray.getkaty().set(0, (double)zr.getkat());
			rozmiar = skaner.nextInt();
			for(int i=0; i<rozmiar; i++)
			{
				nexttyp=skaner.nextInt();
				nkat=skaner.nextInt();
				nx=skaner.nextInt();
				ny=skaner.nextInt();
				nn=skaner.nextInt();
				if(nexttyp!=4)
					zwierciadla.add(new ZwierciadlaPryzmaty(nexttyp));
				else
					zwierciadla.add(new ZwierciadlaPryzmaty((float)nn/10));
				zwierciadla.get(i).setkat(nkat);
				zwierciadla.get(i).setxy(nx, ny);
				zwierciadla.get(i).odswiezramke();
			}
			skaner.close();
			repaint();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void zapisz(File outputfile)
	{
		try
		{
			FileWriter writer = new FileWriter(outputfile);
			writer.write(String.valueOf(kolor) + " ");
			writer.write(lambda + " ");
			writer.write(zr.getkat() + " ");
			writer.write(zwierciadla.size() + " " + "\n");
			for(int i=0; i<zwierciadla.size(); i++)
			{
				writer.write(zwierciadla.get(i).gettyp() + " " + zwierciadla.get(i).getkat() + " " + zwierciadla.get(i).getx() + " " + zwierciadla.get(i).gety() + " " + String.valueOf((int)(10*zwierciadla.get(i).getn())));
				writer.write("\n");
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void wyczysc()
	{
		aktywny = false;
		ray = null;
		ray = new Promien();
		ray.getpunkty().add(zr.getstart());
		ray.getkaty().add((double)zr.getkat());
		lambda = 380;
		n = 1;
		promien = null;
		promien = new ArrayList<Point2D>();
		zwierciadla = null;
		zwierciadla = new ArrayList<ZwierciadlaPryzmaty>();
		repaint();
	}
	
	public void obliczpromien(Promien rx, boolean zalamanie)
	{
		boolean naekranie=true;
		int scianapryzmatu=0, nscianapryzmatu=0;
		int i=0;
		double prostopadla=0;
		for(int j=rx.getpunkty().size()-1; j>0; j--)
		{
			rx.getpunkty().remove(j);
		}
		for(int j=rx.getkaty().size()-1; j>0; j--)
		{
			rx.getkaty().remove(j);
		}
		
		int lp=0;
		while(naekranie)
		{
			lp++;
			if(lp>100)
			{
				naekranie=false;
			}
			boolean coll=false;
			double xc=rx.getpunkty().get(i).getX(), yc=rx.getpunkty().get(i).getY();
			double oxc, oyc;
			do
			{
				oxc=xc;
				oyc=yc;
				xc+=Math.cos(Math.toRadians(rx.getkaty().get(i)));
				yc+=Math.sin(Math.toRadians(rx.getkaty().get(i)));
				if(xc>this.getWidth() || xc<0 || yc>this.getHeight() || yc<0)
				{
					naekranie=false;
					coll=true;
				}
				Point p1 = new Point((int)oxc, (int)oyc);
				Point q1 = new Point((int)(xc+Math.cos(Math.toRadians(rx.getkaty().get(i)))),(int)(yc+Math.sin(Math.toRadians(rx.getkaty().get(i)))));
				Point r1 = new Point((int)(xc+2*Math.cos(Math.toRadians(rx.getkaty().get(i)))),(int)(yc+2*Math.sin(Math.toRadians(rx.getkaty().get(i)))));
				for(int j=0; j<zwierciadla.size(); j++)
				{
					if(zwierciadla.get(j).gettyp()==1)
					{
						Point j1 = new Point(zwierciadla.get(j).zwramka().xpoints[0], zwierciadla.get(j).zwramka().ypoints[0]);
						Point j2 = new Point(zwierciadla.get(j).zwramka().xpoints[1], zwierciadla.get(j).zwramka().ypoints[1]);
						Point j3 = new Point(zwierciadla.get(j).zwramka().xpoints[2], zwierciadla.get(j).zwramka().ypoints[2]);
						Point j4 = new Point(zwierciadla.get(j).zwramka().xpoints[3], zwierciadla.get(j).zwramka().ypoints[3]);
						if(Przeciecia.doIntersect(p1, q1, j2, j3) || Przeciecia.doIntersect(p1, q1, j4, j1))
						{
							coll=true;
							rx.getkaty().add(180+2*zwierciadla.get(j).getkat()-rx.getkaty().get(i));
						}
						if(Przeciecia.doIntersect(p1, q1, j1, j2) || Przeciecia.doIntersect(p1, q1, j3, j4))
						{
							naekranie=false;
							coll=true;
						}
					}
					if(zwierciadla.get(j).gettyp()==2)
					{
						Point j1 = new Point(zwierciadla.get(j).zwramka().xpoints[0], zwierciadla.get(j).zwramka().ypoints[0]);
						Point j2 = new Point(zwierciadla.get(j).zwramka().xpoints[1], zwierciadla.get(j).zwramka().ypoints[1]);
						Point j3 = new Point(zwierciadla.get(j).zwramka().xpoints[2], zwierciadla.get(j).zwramka().ypoints[2]);
						Point j4 = new Point(zwierciadla.get(j).zwramka().xpoints[3], zwierciadla.get(j).zwramka().ypoints[3]);
						if(Przeciecia.doIntersect(p1, q1, j1, j2) || Przeciecia.doIntersect(p1, q1, j2, j3) || Przeciecia.doIntersect(p1, q1, j3, j4))
						{
							naekranie=false;
							coll=true;
						}
						double dx =(double)(zwierciadla.get(j).getsrodek().getX()+90-(q1.getx()-((double)q1.getx()-(double)p1.getx())/2));
						double dy =(double)(zwierciadla.get(j).getsrodek().getY()+90-(q1.gety()-((double)q1.gety()-(double)p1.gety())/2));
						double d2x =(double)(zwierciadla.get(j).getsrodek().getX()+90-((double)q1.getx()));
						double d2y =(double)(zwierciadla.get(j).getsrodek().getY()+90-((double)q1.gety()));
						if(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)q1.getx(),(double)q1.gety())) && Math.sqrt(dx*dx+dy*dy)>90 && Math.sqrt(d2x*d2x+d2y*d2y)>90)
						{
							prostopadla = Math.toDegrees(Math.atan2(dy, dx));
							rx.getkaty().add((2*(prostopadla)-rx.getkaty().get(i)+180));
							coll=true;
						}
					}
					if(zwierciadla.get(j).gettyp()==3)
					{
						Point j1 = new Point(zwierciadla.get(j).zwramka().xpoints[0], zwierciadla.get(j).zwramka().ypoints[0]);
						Point j2 = new Point(zwierciadla.get(j).zwramka().xpoints[1], zwierciadla.get(j).zwramka().ypoints[1]);
						Point j3 = new Point(zwierciadla.get(j).zwramka().xpoints[2], zwierciadla.get(j).zwramka().ypoints[2]);
						Point j4 = new Point(zwierciadla.get(j).zwramka().xpoints[3], zwierciadla.get(j).zwramka().ypoints[3]);
						if(Przeciecia.doIntersect(p1, q1, j2, j3))
						{
							naekranie=false;
							coll=true;
						}
						double dx =(double)(zwierciadla.get(j).getsrodek().getX()+90-Math.cos(Math.toRadians(zwierciadla.get(j).getkat()))*12-(q1.getx()-((double)q1.getx()-(double)p1.getx())/2));
						double dy =(double)(zwierciadla.get(j).getsrodek().getY()+90-Math.sin(Math.toRadians(zwierciadla.get(j).getkat()))*12-(q1.gety()-((double)q1.gety()-(double)p1.gety())/2));
						double d2x =(double)(zwierciadla.get(j).getsrodek().getX()+90-Math.cos(Math.toRadians(zwierciadla.get(j).getkat()))*12-((double)q1.getx()));
						double d2y =(double)(zwierciadla.get(j).getsrodek().getY()+90-Math.sin(Math.toRadians(zwierciadla.get(j).getkat()))*12-((double)q1.gety()));
						repaint();
						if(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)q1.getx(),(double)q1.gety())) && (Math.sqrt(dx*dx+dy*dy)<90) && (Math.sqrt(d2x*d2x+d2y*d2y)<90))
						{
							prostopadla = Math.toDegrees(Math.atan2(dy, dx));
							rx.getkaty().add((2*prostopadla-rx.getkaty().get(i)+180));
							coll=true;
						}
					}
					if(zwierciadla.get(j).gettyp()==4 )
					{
						Point j1 = new Point(zwierciadla.get(j).zwramka().xpoints[0], zwierciadla.get(j).zwramka().ypoints[0]);
						Point j2 = new Point(zwierciadla.get(j).zwramka().xpoints[1], zwierciadla.get(j).zwramka().ypoints[1]);
						Point j3 = new Point(zwierciadla.get(j).zwramka().xpoints[2], zwierciadla.get(j).zwramka().ypoints[2]);
						if(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)q1.getx(),(double)q1.gety())) && (Przeciecia.doIntersect(p1, q1, j1, j2) || Przeciecia.doIntersect(p1, q1, j2, j3) || Przeciecia.doIntersect(p1, q1, j3, j1)))
						{
							if(!zalamanie)
							{
								prostopadla = 0;
								if(Przeciecia.doIntersect(p1, q1, j1, j2))
								{
									scianapryzmatu=1;
									prostopadla = zwierciadla.get(j).getkat()+60;
								}
								else if(Przeciecia.doIntersect(p1, q1, j2, j3))
								{
									scianapryzmatu=2;
									prostopadla = zwierciadla.get(j).getkat()+180;
								}
								else if(Przeciecia.doIntersect(p1, q1, j3, j1))
								{
									scianapryzmatu=3;
									prostopadla = zwierciadla.get(j).getkat()-60;
								}
								coll=true;
								double nowykat=0;
								if(!rx.getkolor())
								{
									odstep=0;
									for(int k=380; k<781; k++)
									{
										if(odstep==0)
										{
											if(colorray.get(k-380).getkaty().size()>1)
												nowykat=colorray.get(k-380).getkaty().get(i);
											else
												nowykat=ray.getkaty().get(ray.getkaty().size()-1);
											while(prostopadla>180)
											{
												prostopadla-=360;
											}
											while(prostopadla<-180)
											{
												prostopadla+=360;
											}
											double asinw=0;
											{
												asinw = (double)zwierciadla.get(j).getn()*colorray.get(k-380).getm()*Math.sin(Math.toRadians(180+prostopadla-(double)rx.getkaty().get(i)));
												if(asinw>1)
												{
													asinw--;
													if(prostopadla>0)
													{
														nowykat = (prostopadla+90-Math.toDegrees(Math.asin(asinw)));
													}
													else
													{
														nowykat = (prostopadla+90-Math.toDegrees(Math.asin(asinw)));
													}
												}
												else if(asinw<-1)
												{
													asinw++;
													if(prostopadla>0)
													{
														nowykat = (prostopadla-90-Math.toDegrees(Math.asin(asinw)));
													}
													else
													{
														nowykat = (prostopadla-90-Math.toDegrees(Math.asin(asinw)));
													}
												}
												else
												{
													if(prostopadla>0)
													{
														nowykat = (prostopadla-180-Math.toDegrees(Math.asin(asinw)));
													}
													else
													{
														nowykat = (prostopadla-180-Math.toDegrees(Math.asin(asinw)));
													}
												}
												//System.out.println(asinw);
												
											}
											if(colorray.get(k-380).getkaty().size()==0)
												colorray.get(k-380).getkaty().add(nowykat);
											else
												colorray.get(k-380).getkaty().set(0,nowykat);
											if(colorray.get(k-380).getpunkty().size()==0)
												colorray.get(k-380).getpunkty().add(new Point2D.Double(xc,yc));
											else
												colorray.get(k-380).getpunkty().set(0,new Point2D.Double(xc,yc));
											punktroz.setLocation(p1.getx(),p1.gety());
										}
										naekranie=false;
										coll=true;
										zalamanie=true;
										wielekolorow=true;
										zalamany=j;
									}
								}
								odstep++;
								if(odstep==4)
									odstep=0;
								else if(!wielekolorow)
									nowykat=(180+prostopadla-Math.toDegrees(Math.asin((double)zwierciadla.get(j).getn()*Math.sin(Math.toRadians(180.0+prostopadla-(double)rx.getkaty().get(i))))));
								else
									nowykat=(180+prostopadla-Math.toDegrees(Math.asin((double)zwierciadla.get(j).getn()*rx.getm()*Math.sin(Math.toRadians(180.0+prostopadla-(double)rx.getkaty().get(i))))));
								if(Math.abs(nowykat-(prostopadla+180))<90)
								{
									rx.getkaty().add(nowykat);
									zalamanie = true;
								}
								else
								{
									double asinw = (double)zwierciadla.get(j).getn()*Math.sin(Math.toRadians(180+prostopadla-(double)rx.getkaty().get(i)));
									if(asinw>1)
										asinw--;
									if(asinw<-1)
										asinw++;
									while(prostopadla>180)
									{
										prostopadla-=360;
									}
									while(prostopadla<-180)
									{
										prostopadla+=360;
									}
									if(prostopadla>0)
									{
										nowykat = (prostopadla-90-Math.toDegrees(Math.asin(asinw)));
									}
									else
									{
										nowykat = (prostopadla+90-Math.toDegrees(Math.asin(asinw)));
									}
									rx.getkaty().add((nowykat));
								}
							}
						}
						else if(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)p1.getx(),(double)p1.gety())) && (Przeciecia.doIntersect(p1, q1, j1, j2) || Przeciecia.doIntersect(p1, q1, j2, j3) || Przeciecia.doIntersect(p1, q1, j3, j1)))
						{
							if(zalamanie)
							{
								prostopadla = 0;
								if(Przeciecia.doIntersect(q1, p1, j1, j2))
								{
									nscianapryzmatu=1;
								}
								else if(Przeciecia.doIntersect(q1, p1, j2, j3))
								{
									nscianapryzmatu=2;
								}
								else if(Przeciecia.doIntersect(q1, p1, j3, j1))
								{
									nscianapryzmatu=3;
								}
								if(Przeciecia.doIntersect(q1, p1, j1, j2) && scianapryzmatu!=nscianapryzmatu)
								{
									prostopadla = zwierciadla.get(j).getkat()+60;
									coll=true;
									zalamanie=false;
									rx.getkaty().add((rx.getkaty().get(i)-Math.toDegrees(Math.asin((double)((zwierciadla.get(j).getn()-1)*Math.sin(Math.toRadians(rx.getkaty().get(i)-prostopadla)))))));
								}
								else if(Przeciecia.doIntersect(q1, p1, j2, j3) && scianapryzmatu!=nscianapryzmatu)
								{
									prostopadla = zwierciadla.get(j).getkat()+180;
									coll=true;
									zalamanie=false;
									rx.getkaty().add((rx.getkaty().get(i)-Math.toDegrees(Math.asin((double)((zwierciadla.get(j).getn()-1)*Math.sin(Math.toRadians(rx.getkaty().get(i)-prostopadla)))))));
								}
								else if(Przeciecia.doIntersect(q1, p1, j3, j1) && scianapryzmatu!=nscianapryzmatu)
								{
									prostopadla = zwierciadla.get(j).getkat()-60;
									coll=true;
									zalamanie=false;
									rx.getkaty().add((rx.getkaty().get(i)-Math.toDegrees(Math.asin((double)((zwierciadla.get(j).getn()-1)*Math.sin(Math.toRadians(rx.getkaty().get(i)-prostopadla)))))));
								}
							}
						}
					}
					int niezaw=0;
					if(!(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)p1.getx(),(double)p1.gety()))) && !(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)q1.getx(),(double)q1.gety()))) && !(zwierciadla.get(j).zwramka().contains(new Point2D.Double((double)r1.getx(),(double)r1.gety()))) && (rx.getkaty().get(i)>prostopadla+92 && zwierciadla.get(j).gettyp()==4) && (!coll))
						niezaw++;
					if(niezaw==zwierciadla.size())
						zalamanie=false;
				}
			}
			while(!coll);
			rx.getpunkty().add(new Point2D.Double(xc,yc));
			i++;
		}
	}
	
	public boolean czysymulacja()
	{
		return aktywny;
	}
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if(obiekt>0)
		{
			if(!mode)
			{
				zwierciadla.get(obiekt-1).setxy(e.getX(), e.getY());
				zwierciadla.get(obiekt-1).odswiezramke();
				super.repaint();
			}
			else
			{
				if(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))>myszxy)
					zwierciadla.get(obiekt-1).setkat(zwierciadla.get(obiekt-1).getkat()+(int)(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))-myszxy)/2);
				else
					zwierciadla.get(obiekt-1).setkat(zwierciadla.get(obiekt-1).getkat()-(int)(myszxy-(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))))/2);	
				myszxy=Math.sqrt((double)(e.getX()*e.getX()+e.getY()*e.getY()));
				zwierciadla.get(obiekt-1).odswiezramke();
				super.repaint();
			}
		}
		else
		{
			if(obrotzrodla)
			{
				if(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))>myszxy)
				{
					spowolnienie++;
					if(spowolnienie==1)
					{
						zr.setkat(zr.getkat()+(int)(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))-myszxy)/4);
						if(zr.getkat()>90)
							zr.setkat(90);
						spowolnienie=0;
					}
				}
				else
				{
					spowolnienie++;
					if(spowolnienie==1)
					{
						zr.setkat(zr.getkat()-(int)(myszxy-(Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()))))/4);
						if(zr.getkat()<-90)
							zr.setkat(-90);
						spowolnienie=0;
					}
				}
				myszxy=Math.sqrt((double)((double)e.getX()*(double)e.getX()+(double)e.getY()*(double)e.getY()));
				ray.getkaty().set(0, (double)zr.getkat());
				super.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		for(int i=0; i<zwierciadla.size(); i++)
		{
			if(zwierciadla.get(i).gettyp()==1)
			{
				if(zwierciadla.get(i).zwramka().contains(e.getPoint()))
				{
					zwierciadla.get(i).setwybor(true);
					for(int j=0; j<i; j++)
						zwierciadla.get(j).setwybor(false);
					obiekt=i+1;
					if(obiekt==sobiekt)
					{
						mode=true;
					}
					else
					{
						mode=false;
					}
				}
				else 
				{
					zwierciadla.get(i).setwybor(false);
				}
			}
			if(zwierciadla.get(i).gettyp()==2)
			{
				if(zwierciadla.get(i).zwramka().contains(e.getPoint()))
				{
					zwierciadla.get(i).setwybor(true);
					obiekt=i+1;
					if(obiekt==sobiekt)
					{
						mode=true;
					}
					else
					{
						mode=false;
					}
				}
				else 
				{
					zwierciadla.get(i).setwybor(false);
				}
			}
			if(zwierciadla.get(i).gettyp()==3)
			{
				if(zwierciadla.get(i).zwramka().contains(e.getPoint()))
				{
					zwierciadla.get(i).setwybor(true);
					obiekt=i+1;
					if(obiekt==sobiekt)
					{
						mode=true;
					}
					else
					{
						mode=false;
					}
				}
				else 
				{
					zwierciadla.get(i).setwybor(false);
				}
			}
			if(zwierciadla.get(i).gettyp()==4)
			{
				if(zwierciadla.get(i).zwramka().contains(e.getPoint()))
				{
					zwierciadla.get(i).setwybor(true);
					obiekt=i+1;
					if(obiekt==sobiekt)
					{
						mode=true;
					}
					else
					{
						mode=false;
					}
				}
				else
				{
					zwierciadla.get(i).setwybor(false);
				}
			}
		}
		if(zr.zwramka().contains(e.getPoint()))
		{
			obrotzrodla=true;
		}
		super.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		sobiekt=obiekt;
		obiekt=0;
		mode=false;
		super.repaint();
		obrotzrodla=false;
	}
	public void setdark(boolean value)
	{
		dark=value;
	}

}
