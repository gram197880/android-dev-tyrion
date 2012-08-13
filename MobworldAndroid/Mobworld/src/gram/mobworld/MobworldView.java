package gram.mobworld;

import java.util.ArrayList;
import java.util.List;

import graphics.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MobworldView extends SurfaceView implements  Runnable
{
	Thread renderThread = null;
	SurfaceHolder holder;
	Paint paint;
	ThumbStick stick;
	ThumbButton button;
	static final long FPS = 24;
	volatile boolean running = false;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private long lastClick;
	String angleTest = "";
	
	public MobworldView(Context context)
	{
		super(context);
		holder = getHolder();
		paint = new Paint();
		stick = new ThumbStick();
		button = new ThumbButton();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
			}
			
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub				
			}
		});

	}
	
	public void Resume()
	{
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	public void Pause()
	{
		running = false;
		while(true)
		{
			try
			{
				renderThread.join();
			}
			catch(InterruptedException e)
			{
				//do some error handling here...
			}
		}
	}

	public void run() 
	{
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while(running)
		{
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try
			{
				c = this.getHolder().lockCanvas();
				if(c != null)
				{
					synchronized (this.getHolder()) {
						this.onDraw(c);
					}
				}
			}
			finally {
				if( c != null)
				{
					this.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try
			{
				if(sleepTime > 0)
					Thread.sleep(sleepTime);
				else
					Thread.sleep(10);
			}
			catch (Exception e)
			{
				//swallow - lol
			}
		}
		
	}
	private void createSprites() {
		sprites.add(createSprite(R.drawable.blue));
		sprites.add(createSprite(R.drawable.dark));    	
    }
    
	private graphics.Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);
	}
	
	private void BumpSprites(double f)
	{
		for(Sprite sprite : sprites)
		{
			sprite.Bump(f);
		}
	}
	
	@Override 
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);	             
		for (Sprite sprite : sprites) { sprite.onDraw(canvas);}
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);
		canvas.drawText(angleTest, 50, 50, paint);
		
		stick.DrawStick(canvas);
		button.DrawButtons(canvas);
		postInvalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//add a delay so that multi touches aren't fired
		if(System.currentTimeMillis() - lastClick > 300){
			lastClick = System.currentTimeMillis();
			double moveAngle = stick.MoveMe(event.getX(), event.getY());
			angleTest = Double.toString(moveAngle);
			synchronized(getHolder()) {
				BumpSprites(moveAngle);
			}
		}
		return true;
	}
}
