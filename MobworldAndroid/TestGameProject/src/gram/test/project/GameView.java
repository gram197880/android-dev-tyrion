package gram.test.project;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.*;

public class GameView extends SurfaceView {
private Bitmap bmp;
private SurfaceHolder holder;
private GameLoopThread gameLoopThread;
private List<Sprite> sprites = new ArrayList<Sprite>();
private long lastClick;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
				
			}
			
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub				
			}
		});

	}
	
    private void createSprites() {
		sprites.add(createSprite(R.drawable.blue));
		sprites.add(createSprite(R.drawable.dark));    	
    }
    
	private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);
	}
	
	private void BumpSprites(float f, float g)
	{
		for(Sprite sprite : sprites)
		{
			sprite.Bump((int)f, (int)g);
		}
	}
	
	@Override 
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);             
		for (Sprite sprite : sprites) { sprite.onDraw(canvas);}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//add a delay so that multi touches aren't fired
		if(System.currentTimeMillis() - lastClick > 300){
			lastClick = System.currentTimeMillis();
			synchronized(getHolder()) {
				BumpSprites(event.getX(), event.getY());
			}
		}
		return true;
	}

}
