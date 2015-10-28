package zhaoqiang.windowmagagertest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Project_name : WindowMagagerTest
 * Author : zhaoQiang
 * Date : 2015/10/27,20:30
 * Email : zhaoq_hero@163.com
 */

/**
 * 自定义悬浮  的文本框 用于显示文本歌词信息
 */
public class CustomTextView extends TextView{

    private String text;

    private Handler handler;

    public static int TOOL_BAR_HIGH = 0;


    //参数对象
    public static WindowManager.LayoutParams params =
            new WindowManager.LayoutParams();

    //窗口管理对象：
    WindowManager wm = (WindowManager) getContext()
            .getApplicationContext()
            .getSystemService(Context.WINDOW_SERVICE);


    public CustomTextView(Context context) {
        super(context);

        text = "世上只有妈妈好，有妈妈的孩子像会宝";
        this.setBackgroundColor(Color.argb(90,150,150,150));

        //使用handler  接收消息
        handler = new Handler();
        handler.post(update);
    }

    Runnable update = new Runnable() {
        @Override
        public void run() {

            CustomTextView.this.update();
            handler.postDelayed(update, 5);///延迟刷新事件

        }
    };
    //
    public void update(){
//        Android提供了Invalidate方法实现界面刷新，
//       但是Invalidate不能直接在线程中调用，
//       因为他是违背了单线程模型：Android UI操作并不是线程安全的，
//      并且这些操作必须在UI线程中调用。

         postInvalidate();

//        invalidate()是用来刷新View的，必须是在UI线程中进行工作。
//          比如在修改某个view的显示时，调用invalidate()才能看到重新绘制的界面。
//         invalidate()的调用是把之前的旧的view从主UI线程队列中pop掉。
    }



    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float x;
    private float y;
    //开始位置坐标
    private float startX;
    private float startY;

    //设置触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //触摸点相对与屏幕左上角的位置：
        x = event.getRawX();
        y = event.getRawY() - TOOL_BAR_HIGH;

        //判断事件：
        switch(event.getAction()){
//            按下事件
            case MotionEvent.ACTION_DOWN:
                //获取当前位置
                startX = event.getX();
                startY = event.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                //更新位置：
                params.x = (int) (x - startX);//计算偏移量
                params.y = (int) (y - startY);//

                //使用管理器对象  更新视图位置
                wm.updateViewLayout(this,params);

                break;

            case MotionEvent.ACTION_UP:

                //更新位置：
                params.x = (int) (x - startX);//计算偏移量
                params.y = (int) (y - startY);//
                //使用管理器对象  更新视图位置
                wm.updateViewLayout(this,params);

                startX = startY = 0;
                break;
        }
        //返回为真  事件进行处理  该方法将消费此事件
       return true;
    }


    private float float1 = 0.0f;
    private float float2 = 0.01f;
    //使用绘制方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float1 +=  0.001f;
        float2 +=  0.00f;

        if(float2>1.0){
            float1 = 0.0f;
            float2 = 0.01f;
        }

        this.setText("");
        float len = this.getTextSize() * text.length();
        Shader shader = new LinearGradient(0, 0, len, 0,
                new int[] { Color.YELLOW, Color.RED },  new float[]{float1, float2},
                Shader.TileMode.CLAMP);
        Paint p = new Paint();

        p.setShader(shader);

        p.setTypeface(Typeface.DEFAULT_BOLD);

        canvas.drawText(text, 0, 10, p);
    }
}
