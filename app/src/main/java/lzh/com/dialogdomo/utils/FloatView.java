package lzh.com.dialogdomo.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/17.
 */

public class FloatView{
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    private WindowManager mWindowManager;
    private boolean isShowing = false;

    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;


    public WindowManager getWindowManager() {
        return mWindowManager;
    }

    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
    public FloatView(Context context) {
        this.context = context;
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
    }


    public FloatView createFloatView(LinearLayout mFloatLayout) {
        if (!isShowing) {
            DisplayMetrics dm = new DisplayMetrics();
            mWindowManager.getDefaultDisplay().getMetrics(dm);
            return createFloatView(mFloatLayout,dm.widthPixels / 20,dm.heightPixels / 4,dm.widthPixels * 9 / 10,WindowManager.LayoutParams.WRAP_CONTENT);
        }
        return null;
    }


    public FloatView createFloatView(LinearLayout mFloatLayout, int x, int y, int width, int height) {
        this.mFloatLayout = mFloatLayout;
        if (wmParams == null) {
            wmParams = new WindowManager.LayoutParams();
            //设置window type
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            //设置图片格式，效果为背景透明
            wmParams.format = PixelFormat.RGBA_8888;
            //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            //调整悬浮窗显示的停靠位置为左侧置顶
            wmParams.gravity = Gravity.LEFT | Gravity.TOP;
            // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
            mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                    .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }

        wmParams.x = x;
        wmParams.y = y;
        //设置悬浮窗口长宽数据
        wmParams.width = width;
        wmParams.height = height;
        mWindowManager.addView(mFloatLayout, wmParams);
        isShowing = true;
        return this;
    }

    public void remove() {
        if (isShowing && mWindowManager != null && mFloatLayout != null) {
            isShowing = false;
            mWindowManager.removeView(this.mFloatLayout);
        }
    }

}
