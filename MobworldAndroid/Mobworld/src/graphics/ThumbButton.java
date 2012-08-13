package graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ThumbButton {
	public float AX;
	public float AY;
	public float BX;
	public float BY;
	public ThumbButton()
	{
		AX = 0; AY = 0; BX = 0; BY = 0; 
	}

	public void DrawButtons(Canvas canvas)
	{

		float screenWidth = canvas.getWidth();
		float screenHeight = canvas.getHeight();
		Paint paint = new Paint();
		paint.setTextSize(20);
		
		//draw the buttons
		//a button
		AX = screenWidth - 85;
		AY = screenHeight - 50;
		paint.setColor(Color.RED);
		canvas.drawCircle(AX, AY, 20, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("A", AX - 5, AY + 5, paint);
		
		//b button
		BX = screenWidth - 40;
		BY = screenHeight - 50;
		paint.setColor(Color.YELLOW);
		canvas.drawCircle(BX, BY, 20, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("B", BX -5, BY + 5, paint);
	}


	public boolean ButtonAHit(float locx, float locy)
	{
		boolean iMHit = false;
		if(locx <= (AX + 20) && locx >= (AX - 20) &&
				locy <= (AY + 20) && locy >= (AY - 20))
		{
			iMHit = true;
		}
		return iMHit;
	}
	
	public boolean ButtonBHit(float locx, float locy)
	{
		boolean iMHit = false;
		if(locx <= (BX + 20) && locx >= (BX - 20) &&
				locy <= (BY + 20) && locy >= (BY - 20))
		{
			iMHit = true;
		}
		return iMHit;
	}
}
