package interfejs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Promien 
{
	ArrayList<Point2D> punkty;
	int lambda;
	boolean kolor;
	ArrayList<Double> kat;
	double nmultiplier;
	public Promien()
	{
		punkty = new ArrayList<Point2D>();
		kat = new ArrayList<Double>();
		kolor=false;
		lambda=380;
		nmultiplier=1;
	}
	public ArrayList<Double> getkaty()
	{
		return kat;
	}
	public void setkolor(boolean value)
	{
		kolor=value;
	}
	public boolean getkolor()
	{
		return kolor;
	}
	public void setlambda(int value)
	{
		lambda=value;
		kolor=true;
		if(lambda==0)
			lambda=380;
	}
	public ArrayList<Point2D> getpunkty()
	{
		return punkty;
	}
	public void setm (double m)
	{
		nmultiplier=m;
	}
	public double getm()
	{
		return nmultiplier;
	}
	public void paint(Graphics2D g2d)
	{
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		if(kolor)
		{
			if(lambda<440)
			{
				g2d.setColor(new Color((int)((440-(double)lambda)/(440-380)*255),0,255));
			}
			else if(lambda<490)
			{
				g2d.setColor(new Color(0,(int)(255*((double)lambda-440)/(490-440)),255));
			}
			else if(lambda<510)
			{
				g2d.setColor(new Color(0,255,255*((int)((510-(double)lambda)/(510-490)))));
			}
			else if(lambda<580)
			{
				g2d.setColor(new Color((int)(255*((double)lambda-510)/(580-510)),255,0));
			}
			else if(lambda<645)
			{
				g2d.setColor(new Color(255,(int)(255*((645-(double)lambda)/(645-580))),0));
			}
			else
			{
				double factor=1;
				if(lambda>700)
				{
					factor=0.3+0.7*(780-lambda)/(780-700);
				}
				g2d.setColor(new Color((int)Math.round(255*Math.pow(factor,0.8)),0,0));
				if(factor==0)
					g2d.setColor(Color.black);
			}
		}
		else
		{
			g2d.setColor(Color.white);
		}
		for(int i=1; i<punkty.size(); i++)
		{
			g2d.drawLine((int)punkty.get(i-1).getX(), (int)punkty.get(i-1).getY(), (int)punkty.get(i).getX(), (int)punkty.get(i).getY());
		}
	}
}
