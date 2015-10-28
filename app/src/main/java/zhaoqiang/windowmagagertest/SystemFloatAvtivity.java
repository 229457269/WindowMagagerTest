package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.GregorianCalendar;

/**
 * 系统级别的悬浮窗：  开启的悬浮窗  可以在手机桌面上显示
 */
public class SystemFloatAvtivity extends Activity {


    private static WindowManager wm;
    private WindowManager.LayoutParams LP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_float_avtivity);

        //系统级别的悬浮窗：

        //1，使用系统级别的WindowManager   需要使用getApplicationContext获取全局的应用
        wm = (WindowManager) getApplication().getSystemService(
                WINDOW_SERVICE
        );
        //2.1使用进程  Application  context 来创建控件
        //避免Activity退出导致的context问题

        final ImageView imgView = new ImageView(getApplicationContext());

        imgView.setImageResource(R.mipmap.ic_launcher);

        //3,设置  ImageView 的监听事件：
        //建议使用匿名内部类 来设置触摸的监听器
        imgView.setOnTouchListener(new View.OnTouchListener() {

            private float lastX,lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean ret = false;

                int action = event.getAction();

                switch(action){

                    case MotionEvent.ACTION_DOWN:

                        lastX = event.getRawX();
                        lastY = event.getRawY();

                        ret = true;

                        Log.i("info","DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        Log.i("info","MOVE");

                        //在移动的时候  获取屏幕上的事件点击位置进行计算

                        float cx = event.getRawX();
                        float cy = event.getRawY();

                        float ccx = cx - lastX;
                        float ccy = cy - lastY;

                        //计算增量：
                        LP.x += ccx;
                        LP.y += ccy;

                        //更新悬浮窗：
                        wm.updateViewLayout(
                               imgView,LP);

                        //将当前坐标保存成 lastX,lastY,在下一次移动的时候使用
                        lastX = cx;
                        lastY = cy;

                        break;
                    case MotionEvent.ACTION_UP:

                        Log.i("info","UP");

                        break;
                }
                return ret;//true为被父容器处理了，false为没有被处理
            }
        });




        //2.2设置系统级别的悬浮窗的参数：保证悬浮窗悬在手机的桌面上
        LP = new WindowManager.LayoutParams();

        //系统级别需要指定  type  对于应用程序使用的系统类型
        //TYPE_SYSTEM_ALERT   允许接受事件
        //TYPE_SYSTEM_OVERLAY  只是悬浮在系统上

        //使用系统级别的悬浮窗  必须要指定权限
        LP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

        // 系统级别悬浮窗  必须要指定权限  NOT_FOCUSABLE  允许手指点击炫富创之外的控件：并且能够将事件向下传递：
        LP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;


        //左上角
        LP.gravity = Gravity.LEFT|Gravity.TOP;

//        位置
        LP.x = 100;
        LP.y = 100;

//        包裹内容：
        LP.width = WindowManager.LayoutParams.WRAP_CONTENT;
        LP.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LP.format = PixelFormat.TRANSPARENT;//透明 背景


        //2，addView 添加
        wm.addView(imgView, LP);

    }
}
