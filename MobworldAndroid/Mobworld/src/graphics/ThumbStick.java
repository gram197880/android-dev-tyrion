package graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ThumbStick {
	Paint paint;
	private float TX = 0;
	private float TY = 0;
	private float RX = 48;
	private float RY = 48;
	
	public ThumbStick()
	{
		paint = new Paint();
		TX = 64;
	}
	
	public void DrawStick(Canvas canvas)
	{
		TY = canvas.getHeight();
		
		//draw the thumbstick
		paint.setColor(Color.LTGRAY);
		canvas.drawCircle(TX, (TY - 64), 48, paint);
		paint.setColor(Color.GRAY);
		canvas.drawCircle(TX, (TY - 64), 28, paint);
		
	}
	
	public boolean IsHit(double locx, double locy)
	{
		boolean isHit = false;
		if(locx <= RX && locx >= -RX &&	locy <= RY && locy >= -RY)
		{
			isHit = true;
		}
		return isHit;
	}
	
	public double MoveMe(double locx, double locy)
	{
		//get position relative to center of thumb
		double relativePosX = locx - 64;
		//flip sign as origin at top!
		double relativePosY = -1 * (locy - (TY - 64));
		//missed it, return a negative
		if(!IsHit(relativePosX, relativePosY))
			return -1;
		
		//0 is up, 45 up-right etc..
		double angle = 0.0;
		angle = calculateAngle(relativePosX, relativePosY);
		//return the angle in degrees
		return angle;
	}
	
	public double calculateAngle(double v2x, double v2y)
	{
		double v1x = 0;
		double v1y = 1;
		double correctedAngle = 0.0;
		double angle = Math.toDegrees(Math.atan2(v1x - v2x, v2y - v1y));
		if(angle < 0)
		{
			correctedAngle = Math.abs(angle);
		}
		else
		{
			correctedAngle = 360 - angle;
		}
		return correctedAngle;		
	}

}
