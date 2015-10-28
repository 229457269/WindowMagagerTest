package zhaoqiang.windowmagagertest;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class QQRocketDemo extends Activity {

    private WindowManager.LayoutParams wmParams=null;
    private Button child;

    private WindowManager windowManager;

    private static ImageView imageView;

    private static final WindowManager.LayoutParams
                    Lp = new WindowManager.LayoutParams();

    private boolean isFlaying = false;

    private Thread flayingThread;//创建线程

    private Handler handler = new Handler(){
        //创间handler准备接受消息
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            windowManager.updateViewLayout(imageView,Lp);//更新位置

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

////        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        setContentView(R.layout.activity_qqrocket_demo);
//        Window window = getWindow();
//
//        View decorView = window.getDecorView();
//        ViewGroup container = (ViewGroup) decorView;//DecorView   是一个帧布局
//        ViewGroup childAt = (ViewGroup) container.getChildAt(0);//是一个  ActionBarOverlayLayout
//        ViewGroup childAt1 = (ViewGroup) childAt.getChildAt(0);//  FrameLayout
//
//        Log.i("info", childAt1 + "==");
//
//        //创建一个Button
//        child = new Button(this);
//        child.setText("点击按钮");
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.gravity = Gravity.LEFT|Gravity.TOP;
//        lp.x = 200;
//        lp.y = 0;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
////        lp.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
////        child.setLayoutParams(lp);
////        getWindowManager().addView(child,lp);
//
//        container.addView(child,lp);



        //初始化参数：
        Lp.format = PixelFormat.TRANSPARENT;
        Lp.gravity = Gravity.LEFT|Gravity.TOP;

        Lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                |WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        Lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        Lp.width = 80;
        Lp.height = 80;

        Lp.x = 300;
        Lp.y = 300;

        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);

//        移除数据
        if (imageView!=null){
            windowManager.removeViewImmediate(imageView);
        }

        //创建对象
        imageView = new ImageView(getApplicationContext());

        imageView.setBackgroundResource(R.mipmap.ic_launcher);

        imageView.setOnTouchListener(new View.OnTouchListener() {

            private float lastX, lastY;//记录移动位置

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean ret = false;

                int action = event.getAction();

                switch (action) {

                    //点击事件：
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();//获取位置  坐标
                        lastY = event.getRawY();//
                        ret = true;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //在移动的时候获取屏幕上的事件点击位置进行增量计算
                        float cx = event.getRawX();
                        float cy = event.getRawY();

                        float ccx = cx - lastX;// 获取增量
                        float ccy = cy - lastY;

                        Lp.x += ccx;
                        Lp.y += ccy;

                        //更新悬浮窗：
                        windowManager.updateViewLayout(imageView, Lp);

                        //保存当前位置坐标：
                        lastX = cx;
                        lastY = cy;

                        break;

                    case MotionEvent.ACTION_UP:

                        if (!isFlaying) {

                            flayingThread = new Thread(new Runnable() {

                                int time = 150;

                                @Override
                                public void run() {

                                    try {

                                        while (Lp.x > 0 || Lp.y > 0) {

                                            Lp.y -= 17;
                                            Lp.x -= 10;

                                            if (time >= 50) {
                                                time = time - 5;
                                            }

                                            Thread.sleep(time);

                                            //将消息发送给主线程：
                                            handler.sendEmptyMessage(0);

                                        }

                                        isFlaying = false;

                                    } catch (Exception e) {

                                    }

                                }

                            });

                            flayingThread.start();
                            isFlaying = true;
                        }
                        break;
                }
                return ret;
                }
            });

        windowManager.addView(imageView,Lp);

        }


    @Override
    protected void onDestroy() {

        WindowManager windowManager = getWindowManager();

        //释放悬浮窗：
        windowManager.removeViewImmediate(child);//立即执行

        super.onDestroy();
    }

}
