package gram.test.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TestGameProjectActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removes the grey bar at the top!
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));
    }
}