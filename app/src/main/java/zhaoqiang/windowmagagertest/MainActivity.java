package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activity Window的测试 ，Window的属性

        //获取Activity自身的显示的顶级Window对象
        Window window = getWindow();

        //window.getDecorView()才是真正的，Android UI中一个Activity
        //显示的顶级容器
        View view = window.getDecorView();

        SeekBar bright = (SeekBar) view.findViewById(R.id.brightness);
        bright.setOnSeekBarChangeListener(this);

        //获取当前焦点   返回当前获取焦点的view
        View view1 = window.getCurrentFocus();

        //获取当前  window的参数属性
        WindowManager.LayoutParams attributes = window.getAttributes();

        //设置当前参数 的  gravity = "left|top"
        attributes.gravity = Gravity.LEFT | Gravity.TOP;

        //设置x   坐标  只有gravity设置 LEFT  RIGHT之后起作用
//        attributes.x = 100;

        //设置x   坐标  只有gravity设置 TOP  BUTTON之后起作用
//        attributes.y = 100;


        attributes.width = 400;
        attributes.height = 600;

        attributes.setTitle("你好");

//        attributes.alpha = 0.5f;  设置  透明度：

        //更新属性：
        window.setAttributes(attributes);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();

        Window window = getWindow();

        switch(id){

            case R.id.brightness:

                //调整屏幕的亮度：

                WindowManager.LayoutParams attributes = window.getAttributes();

                attributes.screenBrightness = (float) (progress*0.01);

                window.setAttributes(attributes);

                break;

        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }




}
