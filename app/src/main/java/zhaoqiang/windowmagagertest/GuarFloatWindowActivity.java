package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 悬浮窗实例：类似于360安全卫士  该悬浮窗属于activity级别：  当activity消失，悬浮窗也没有了
 */
public class GuarFloatWindowActivity extends Activity {

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guar_float_window);

        //悬浮窗的显示方式   使用windowManager
        WindowManager windowManager = getWindowManager();

        //2.1 准备参数 和对象（作为悬浮窗和整体的view）
        imgView = new ImageView(this);

        imgView.setImageResource(R.mipmap.ic_launcher);

        //2.2 设置添加对象的参数  准备addView的参数二
        WindowManager.LayoutParams layoutParams
                 = new WindowManager.LayoutParams();
            //设置悬浮窗所在的内容范围   左上角  到屏幕右下角 基准点
        layoutParams.gravity = Gravity.LEFT|Gravity.TOP;

        //x,y相对于基准点而言，设置的偏移量
        layoutParams.x = 100;
        layoutParams.y = 100;

        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //注意事项：
//        悬浮窗 通过addView  之后  如果悬浮窗可以获取焦点
//        那么下层的activity将接受不到任何事件   包括返回键
            //解决：
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //解决  悬浮窗中  背景为黑色的情况  设置显示格式：  为背景透明  否则会变黑色
        layoutParams.format = PixelFormat.TRANSPARENT;


        //2,添加悬浮窗  WindowManager  addView（view，LayoutParams）
        windowManager.addView(imgView,layoutParams);
    }

    @Override
    protected void onDestroy() {

        WindowManager windowManager = getWindowManager();

        //释放悬浮窗：
        windowManager.removeViewImmediate(imgView);//立即执行

        super.onDestroy();
    }
}
