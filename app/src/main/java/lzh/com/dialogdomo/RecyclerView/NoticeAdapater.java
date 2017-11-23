package lzh.com.dialogdomo.RecyclerView;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import lzh.com.dialogdomo.utils.NoticeInfo;
import lzh.com.dialogdomo.R;
import lzh.com.dialogdomo.utils.SoundUtil;

/**
 * Created by Administrator on 2017/11/16.
 */

public class NoticeAdapater extends RecyclerViewAdapter<NoticeInfo> implements ItemTouchHelperCallback.ItemTouchInterface {

    public static String ING = "请求通话中";
    public static String STOP = "超时未响应";
    int lastX = 0;
    int lastY = 0;
    private boolean isRinging = false;
    private int soundId = R.raw.on8k16bit;
    ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(this);

    public NoticeAdapater(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
        mList = new ArrayList<>();
        init();
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_recyclerview, parent, false);
        return new Holder(view);
    }

    @Override
    public void addItem(final NoticeInfo noticeInfo) {
        mList.add(noticeInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (noticeInfo != null && noticeInfo.isStatus()) {
                    noticeInfo.setStatus(false);
                    notifyDataSetChanged();
                    SystemClock.sleep(10000);
                }
            }
        }).start();
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int position, int toPosition) {
        Collections.swap(mList,position,toPosition);
        notifyItemMoved(position,toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyOnItemRemoved(position);
    }


    class Holder extends RecyclerViewAdapter<NoticeInfo>.ViewHolder {


        private TextView mUser;
        private TextView mStatus;
        private TextView mTime;

        public Holder(View itemView) {
            super(itemView);
            mUser = (TextView) itemView.findViewById(R.id.notice_tv_user);
            mStatus = (TextView) itemView.findViewById(R.id.notice_tv_status);
            mTime = (TextView) itemView.findViewById(R.id.notice_tv_time);
        }

        @Override
        public void setDate(NoticeInfo noticeInfo) {
            mUser.setText(noticeInfo.getClient());
            mStatus.setText(noticeInfo.isStatus() ? ING : STOP);
            mTime.setText(noticeInfo.getDate());
        }
    }

    private void init() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        touchHelper.attachToRecyclerView(mView);
    }

    public void setDragEnable(boolean enable){
        itemTouchHelperCallback.setLongPressDragEnabled(enable);
    }
    public void setSwipeEnable(boolean enable){
        itemTouchHelperCallback.setItemViewSwipeEnabled(enable);
    }


    public void ring(final int interval) {
        isRinging = true;
        SoundUtil.getInstance().Play(context, soundId, 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(interval);
                if (isRinging) {
                    pauseRing();
                }
            }
        }).start();
    }

    public void pauseRing() {
        if (isRinging) {
            isRinging = false;
            SoundUtil.getInstance().PauseSound(soundId);
        }
    }
}
