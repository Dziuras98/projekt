package interfejs;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class GradientButton extends JButton
{
	Color c1, c2;
	int dark;
	public GradientButton(String text, Color color1, Color color2) 
	{
        super(text);
        this.c1 = color1;
        this.c2 = color2;
        setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		if(dark==1)
		{
			c1=Color.darkGray;
			c2 = Color.black;
	        Paint gradient = new GradientPaint(0, 0, c1, 0, h, c2);
	        g2d.setPaint(gradient);
	        g2d.fillRect(0, 0, w, h);
	        super.paintComponent(g);
		}
		else
		{
			c1=Color.white;
			c2 = Color.lightGray;
	        Paint gradient = new GradientPaint(0, 0, c1, 0, h, c2);
	        g2d.setPaint(gradient);
	        g2d.fillRect(0, 0, w, h);
	        super.paintComponent(g);
		}
	}
	
	void setMode(int mode)
	{
		dark=mode;
	}
}