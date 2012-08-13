package gram.test.project;
import android.graphics.*;

public class GameLoopThread extends Thread {
	private GameView view;
	private boolean running = false;
	static final long FPS = 24;
	
	public GameLoopThread(GameView inView)
	{
		this.view = inView;
	}
	
	public void setRunning(boolean run){
		running = run;
	}
	
	@Override
	public void run()
	{
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		
		while(running){
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try
			{
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			}
			finally {
				if( c != null)
				{
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try
			{
				if(sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			}
			catch (Exception e)
			{
				//swallow - lol
			}
		}

	}
	
}
