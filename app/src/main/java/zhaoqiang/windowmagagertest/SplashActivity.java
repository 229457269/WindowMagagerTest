package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置没有标题样式
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        Window window = getWindow();

        View decorView = window.getDecorView();

        ViewGroup container = (ViewGroup) decorView;

        Button child = new Button(this);

        child.setText("点击按钮");

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                200,150
        );

        lp.gravity = Gravity.RIGHT|Gravity.TOP;

        child.setLayoutParams(lp);

        container.addView(child);

        View childAt = container.getChildAt(0);

        Log.i("info",childAt+"");

    }


}
