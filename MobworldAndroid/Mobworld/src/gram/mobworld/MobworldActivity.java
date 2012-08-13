package gram.mobworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MobworldActivity extends Activity {
	MobworldView mobview;

	protected void onResume(){
		super.onResume();
		mobview.Resume();
	}
	
	protected void onPause()
	{
		super.onPause();
		mobview.Pause();
	}
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    		WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    mobview = new MobworldView(this);
	    setContentView(mobview);
    }
}