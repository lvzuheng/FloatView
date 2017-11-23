package lzh.com.dialogdomo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lzh.com.dialogdomo.RecyclerView.NoticeAdapater;
import lzh.com.dialogdomo.RecyclerView.RecyclerViewAdapter;
import lzh.com.dialogdomo.utils.FloatView;
import lzh.com.dialogdomo.utils.NoticeInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final FloatView floatView = new FloatView(MainActivity.this);
                LinearLayout mFloatLayout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_notice, null);

                RecyclerView recyclerView = (RecyclerView) mFloatLayout.findViewById(R.id.notice_rl);
                Button btn = (Button) mFloatLayout.findViewById(R.id.notice_cancel);

                List<NoticeInfo> nList = new ArrayList<NoticeInfo>();
                for (int i = 0; i < 5; i++) {
                    nList.add(new NoticeInfo("99dcxxxx,800001,,c0,c136,c171102 093955,120.55.162.188,9026,ddddddddddddddddddd,zdm"+i+"#"));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                final NoticeAdapater noticeAdapater = new NoticeAdapater(recyclerView.getContext(), recyclerView);
                //单击点击事件
                noticeAdapater.setOnClickItemListener(new RecyclerViewAdapter.OnClickItemListener() {
                    @Override
                    public void onClick(View view, int i) {
                        Log.e("notice", "短点击事件:"+i);
                        noticeAdapater.pauseRing();
                    }
                });
                noticeAdapater.addItem(nList);
                noticeAdapater.setMaxHeightDisplay(3);
                int x = floatView.getDisplayMetrics().widthPixels / 20;
                int y = floatView.getDisplayMetrics().heightPixels / 4;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                int weight = floatView.getDisplayMetrics().widthPixels * 9 / 10;
                floatView.createFloatView(mFloatLayout,x,y,weight,height);
                //悬浮窗取消事件
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        floatView.remove();
                        noticeAdapater.pauseRing();
                    }
                });
                //来消息发出声音
//                noticeAdapater.ring(10000);
            }
        });
    }
}
