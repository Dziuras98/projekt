package interfejs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ZwierciadlaPryzmaty 
{
	int x, y, typ, kat, sx, sy;
	int [] xp, yp;
	float n;
	boolean wybor, dark;
	Polygon ramka;
	Point2D srodekkola;
	public ZwierciadlaPryzmaty(int a)
	{
		n=1;
		xp = new int[4];
		yp = new int[4];
		x=200;
		y=200;
		typ=a;
		ramka = new Polygon();
		dark=false;
		srodekkola = new Point2D.Double(0,0);
		if(typ==1) 
		{
			xp[0]=x;
			xp[1]=x+8;
			xp[2]=x+8;
			xp[3]=x;
			yp[0]=y;
			yp[1]=y;
			yp[2]=y+120;
			yp[3]=y+120;
			ramka.addPoint(x, y);
			ramka.addPoint(x+8, y);
			ramka.addPoint(x+8, y+120);
			ramka.addPoint(x, y+120);
		}
		if(typ==2)
		{
			xp[0]=x-20;
			xp[1]=x+15;
			xp[2]=x+15;
			xp[3]=x-20;
			yp[0]=y-5;
			yp[1]=y-5;
			yp[2]=y+115;
			yp[3]=y+115;
			ramka.addPoint(x-20, y-5);
			ramka.addPoint(x+15, y-5);
			ramka.addPoint(x+15, y+115);
			ramka.addPoint(x-20, y+115);
		}
		if(typ==3)
		{
			xp[0]=x+5;
			xp[1]=x+30;
			xp[2]=x+30;
			xp[3]=x+5;
			yp[0]=y-5;
			yp[1]=y-5;
			yp[2]=y+120;
			yp[3]=y+120;
			ramka.addPoint(x+5, y-5);
			ramka.addPoint(x+30, y-5);
			ramka.addPoint(x+30, y+120);
			ramka.addPoint(x+5, y+120);
		}
		for(int i=0; i<4; i++) 
		{
			sx+=xp[i];
			sy+=yp[i];
		}
	}
	public ZwierciadlaPryzmaty(float nn)
	{
		xp = new int[3];
		yp = new int[3];
		x=200;
		y=200;
		typ=4;
		n=nn;
		ramka = new Polygon();
		ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat))*50), y+(int)(Math.sin(Math.toRadians(kat))*50));
		ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat+120))*50), y+(int)(Math.sin(Math.toRadians(kat+120))*50));
		ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat+240))*50), y+(int)(Math.sin(Math.toRadians(kat+240))*50));
	}
	
	public void paint(Graphics2D g2d)
	{
		AffineTransform oldform = g2d.getTransform();
		if(typ==1)
		{
			
			AffineTransform at = new AffineTransform();
			at.setToTranslation(sx/4, sy/4);
			g2d.transform(at);
			at.setToRotation(Math.toRadians(kat));
			g2d.transform(at);
			g2d.fillRect(-4, -60, 8, 120);
		}
		if(typ==2)
		{
			AffineTransform at = new AffineTransform();
			at.setToTranslation(sx/4, sy/4);
			srodekkola.setLocation(sx/4-90-Math.cos(Math.toRadians(kat))*80,sy/4-90-Math.sin(Math.toRadians(kat))*80);
			g2d.transform(at);
			at.setToRotation(Math.toRadians(kat));
			g2d.transform(at);
			g2d.fillRect(-16, -60, 34, 120);
			if(dark)
				g2d.setColor(Color.black);
			else
				g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillArc(-170, -90, 180, 180, -45, 90);
			g2d.setColor(Color.GRAY);
		}
		if(typ==3)
		{
			AffineTransform at = new AffineTransform();
			at.setToTranslation(sx/4, sy/4);
			srodekkola.setLocation(sx/4-90+Math.cos(Math.toRadians(kat))*90,sy/4-90+Math.sin(Math.toRadians(kat))*90);
			g2d.transform(at);
			at.setToRotation(Math.toRadians(kat));
			g2d.transform(at);
			g2d.fillArc(-12, -90, 180, 180, -135, -90);
			if(dark)
				g2d.setColor(Color.black);
			else
				g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(14, -63, 65, 126);
			g2d.setColor(Color.GRAY);
			
		}
		if(typ==4)
		{
			for(int i=0; i<3; i++) 
			{
				xp[i]=x+(int)(Math.cos(Math.toRadians(kat+120*i))*44);
				yp[i]=y+(int)(Math.sin(Math.toRadians(kat+120*i))*44);
			}
			g2d.drawPolygon(xp, yp, 3);
		}
		g2d.setTransform(oldform);
		if(wybor) 
		{
			BasicStroke bc = new BasicStroke(1);
			g2d.setStroke(bc);
			if(dark)
				g2d.setColor(Color.white);
			else
				g2d.setColor(Color.black);
			g2d.drawPolygon(ramka);
			g2d.setColor(Color.gray);
			BasicStroke bs = new BasicStroke(6);
			g2d.setStroke(bs);
		}
	}
	
	public void setxy(int a, int b)
	{
		x=a;
		y=b;
	}
	
	public int getx()
	{
		return x;
	}
	
	public int gety()
	{
		return y;
	}	
	public float getn()
	{
		return n;
	}
	public int gettyp()
	{
		return typ;
	}
	public void setkat(int deg)
	{
		kat=deg;
	}
	public int getkat()
	{
		return kat;
	}
	public void setwybor(boolean c) 
	{
		wybor=c;
	}
	public void odswiezramke ()
	{
		if(typ==1 || typ==2 || typ==3) 
		{
			if(typ==1) 
			{
				xp[0]=x;
				xp[1]=x+8;
				xp[2]=x+8;
				xp[3]=x;
				yp[0]=y;
				yp[1]=y;
				yp[2]=y+120;
				yp[3]=y+120;
			}
			if(typ==2)
			{
				xp[0]=x-20;
				xp[1]=x+15;
				xp[2]=x+15;
				xp[3]=x-20;
				yp[0]=y-5;
				yp[1]=y-5;
				yp[2]=y+115;
				yp[3]=y+115;
			}
			if(typ==3)
			{
				xp[0]=x+5;
				xp[1]=x+30;
				xp[2]=x+30;
				xp[3]=x+5;
				yp[0]=y-5;
				yp[1]=y-5;
				yp[2]=y+120;
				yp[3]=y+120;
			}
			
			ArrayList<Point2D> punkty = new ArrayList();
			sx=0;
			sy=0;
			for(int i=0; i<4; i++)
			{
				sx+=xp[i];
				sy+=yp[i];
				punkty.add(new Point2D.Double(xp[i],yp[i]));
			}
			Point2D[] obrotPunkty = new Point2D[punkty.size()];
			AffineTransform obrot = AffineTransform.getRotateInstance(Math.toRadians(kat), sx/4, sy/4);
			obrot.transform(punkty.toArray(new Point2D[0]), 0, obrotPunkty, 0, obrotPunkty.length);
			int[] ixp = new int[punkty.size()];
			int[] iyp = new int[punkty.size()];
			for(int i=0; i<4; i++) 
			{
				ixp[i]=(int)obrotPunkty[i].getX();
				iyp[i]=(int)obrotPunkty[i].getY();
			}
			ramka = new Polygon(ixp, iyp, 4);
		}
		if(typ==4)
		{
			ramka.reset();
			ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat))*50), y+(int)(Math.sin(Math.toRadians(kat))*50));
			ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat+120))*50), y+(int)(Math.sin(Math.toRadians(kat+120))*50));
			ramka.addPoint(x+(int)(Math.cos(Math.toRadians(kat+240))*50), y+(int)(Math.sin(Math.toRadians(kat+240))*50));
		}
	}
	public Polygon zwramka() 
	{
		return ramka;
	}
	public void setdark(boolean value)
	{
		dark=value;
	}
	public Point2D getsrodek()
	{
		return srodekkola;
	}
}
