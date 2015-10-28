package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import zhaoqiang.windowmagagertest.widget.CustomTextView;

/**
 * popupWindowActivity 演示：
 *
 * 类似于 Activity级别的悬浮窗，封装了属性的设置，可以直接使用
 *
 * 2，能够和指定的控件结合在一起 如spinner  不需要计算x y 的坐标
 */

/**
 * 使用dialog样式的Activity：
 */
public class DialogTestActivity extends Activity {

    private PopupWindow popupWindow;

    private CustomTextView textView ;//使用自定义的TextView显示信息
    private WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置无标题的样式：  使用代码效果：
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_dialog_test);

        //使用dialog的样式   通过自定义样式让宽度填满屏幕
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                300);

        //------------------------------------
        //使用自定义的   textView: 显示歌词信息
        showView();
    }

    private void showView() {

        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        CustomTextView.TOOL_BAR_HIGH = frame.top;

        //获取管理器 和参数
        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = CustomTextView.params;

        //添加系统权限
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

        //设置无焦点和 让他下面的获得点击事件
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置参数对象
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        params.gravity=Gravity.LEFT|Gravity.TOP;

        //以屏幕左上角为原点，设置x、y初始值
        params.x = 0;
        params.y = 0;

        textView = new CustomTextView(this);
        windowManager.addView(textView, params);

    }

//    弹出菜单按钮事件
    public void popubOnclick(View view){
        //为空才创建  弹出窗口
        if (popupWindow == null) {
            ImageView imageView = new ImageView(this);

            imageView.setImageResource(R.mipmap.ic_launcher);

            popupWindow = new PopupWindow(imageView,100,100);
        }

        if (popupWindow.isShowing()) {
            //让图片消失
            popupWindow.dismiss();

        }else{
            //设置锚点   让图片显示在按钮的右方
//            popupWindow.showAsDropDown(view,100,100, Gravity.RIGHT);
            //默认显示在  按钮的下方
//            popupWindow.showAsDropDown(view);

            popupWindow.showAsDropDown(
                    view, //以哪一个容器为基准  进行定位
                    Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, //以控件的哪个位置为基准
                    0,0//相对的偏移量
                    );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textView != null && textView.isShown()){
			windowManager.removeView(textView);
		}
    }
}
