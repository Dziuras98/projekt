package interfejs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Zrodlo 
{
	int kat;
	final int x=0,y=300;
	int[] xp, yp;
	Polygon ramka;
	Point2D pw;
	Zrodlo()
	{
		pw = new Point2D.Double(x,y+10);
		xp = new int[4];
		yp = new int[4];
		kat=0;
		xp[0]=x;
		xp[1]=x+20;
		xp[2]=x+20;
		xp[3]=x;
		yp[0]=y-10;
		yp[1]=y-10;
		yp[2]=y+10;
		yp[3]=y+10;
		ramka = new Polygon(xp, yp, 4);
	}
	public void paint(Graphics2D g2d)
	{
		BasicStroke bc = new BasicStroke(1);
		g2d.setStroke(bc);
		g2d.setColor(Color.gray);
		g2d.fillOval(x-10, y, 20, 20);
	}
	public Polygon zwramka()
	{
		return ramka;
	}
	public int getkat()
	{
		return kat;
	}
	public void setkat(int a)
	{
		kat=a;
	}
	public Point2D getstart()
	{
		return pw;
	}
}
