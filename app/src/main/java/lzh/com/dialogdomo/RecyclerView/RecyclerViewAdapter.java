package lzh.com.dialogdomo.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    protected Context context;
    protected List<T> mList;
    protected RecyclerView mView;
    protected ViewHolder mHolder;
    private int maxHeightDisplay = -1;
    private int maxWidthDisplay = -1;
    private int itemHeight = -1;
    private int itemWidth = -1;
    private boolean ismaxHeightDisplay = false;
    private boolean ismaxWidthDisplay = false;
    private List<OnNotifyListener> notifyListenerList = new ArrayList<>();


    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.mView = recyclerView;
        recyclerView.setAdapter(this);
    }

    public RecyclerViewAdapter setDate(List<T> list) {
        this.mList = list;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = createHolder(parent, viewType);
        this.mHolder = holder;
        if (itemHeight <= 0 || itemWidth <= 0) {
            mHolder.itemView.measure(0, 0);
            this.itemHeight = mHolder.itemView.getMeasuredHeight();
            this.itemWidth = mHolder.itemView.getMeasuredWidth();
            if(ismaxHeightDisplay)
                setViewHeight(RecyclerViewAdapter.this.maxHeightDisplay > getItemCount() ?WindowManager.LayoutParams.WRAP_CONTENT: RecyclerViewAdapter.this.maxHeightDisplay *itemHeight);
            if(ismaxWidthDisplay)
                setViewWidth(RecyclerViewAdapter.this.maxWidthDisplay > getItemCount() ?WindowManager.LayoutParams.WRAP_CONTENT: RecyclerViewAdapter.this.maxWidthDisplay *itemHeight);
        }
        return holder;
    }


    @Override
    public void onViewAttachedToWindow(RecyclerViewAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewRecycled(RecyclerViewAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public abstract ViewHolder createHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.setDate(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClick(holder.itemView, position);
                }
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (onTouchItemListener != null) {
                    return onTouchItemListener.onTouch(view, position, motionEvent);
                }
                return false;
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickItemListener != null) {
                    return onLongClickItemListener.onLongClick(view, position);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public int getItemHeight(int size) {
        return itemWidth;
    }


    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setDate(T t);

    }


    public interface OnClickItemListener {
        void onClick(View view, int i);
    }

    public interface OnLongClickItemListener {
        boolean onLongClick(View view, int i);
    }

    public interface OnTouchItemListener {
        boolean onTouch(View view, int position, MotionEvent motionEvent);
    }


    private OnLongClickItemListener onLongClickItemListener;

    public void setOnLongClickItemListener(OnLongClickItemListener onLongClickItemListener) {
        this.onLongClickItemListener = onLongClickItemListener;
    }

    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    private OnTouchItemListener onTouchItemListener;

    public void setOnTouchItemListener(OnTouchItemListener onTouchItemListener) {
        this.onTouchItemListener = onTouchItemListener;
    }


    public void addItem(T t) {
        mList.add(t);
        notifyOnItemInsert(getItemCount());
    }

    public void addItem(List<T> tList) {
        mList.addAll(tList);
        notifyOnItemInsert(getItemCount(),tList.size());
    }

    public void addItem(int index, T t) {
        mList.add(index, t);
        notifyOnItemInsert(index);
    }

    public void addItem(int index, List<T> tList) {
        mList.addAll(index, tList);
        notifyOnItemInsert(index,tList.size());
    }

    public void removeItem(T t) {
        mList.remove(t);
        notifyOnDataChanged();
    }

    public void removeItem(int index) {
        mList.remove(index);
        notifyOnItemRemoved(index);
    }

    public void removeItem(List<T> tList) {
        mList.removeAll(tList);
        notifyOnDataChanged();
    }

    protected void setViewHeight(int height) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
        linearParams.height = height;
        mView.setLayoutParams(linearParams);
    }

    protected void setViewWidth(int width) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
        linearParams.width = width;
        mView.setLayoutParams(linearParams);
    }

    protected void setViewParams(int width, int height) {
        if (width < 0 || height < 0)
            return;
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
        linearParams.width = width;
        linearParams.height = height;
        mView.setLayoutParams(linearParams);
    }

    protected void setMaxDisplay(final int maxHeightDisplay, int maxWidthDisplay) {
        if (maxHeightDisplay < 0 || maxWidthDisplay < 0) {
            return;
        }
        this.maxHeightDisplay = maxHeightDisplay;
        this.maxWidthDisplay = maxWidthDisplay;
        ismaxWidthDisplay = true;
        ismaxHeightDisplay = true;
        setOnNotifyListener(new OnNotifyListener() {
            @Override
            public void onListener() {
                setViewParams(RecyclerViewAdapter.this.maxWidthDisplay > getItemCount() ? RecyclerViewAdapter.this.maxWidthDisplay *itemWidth: WindowManager.LayoutParams.WRAP_CONTENT,
                        RecyclerViewAdapter.this.maxHeightDisplay > getItemCount() ? RecyclerViewAdapter.this.maxHeightDisplay *itemHeight: WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public void setMaxHeightDisplay(final int maxHeightDisplay) {
        if (maxHeightDisplay < 0) {
            return;
        }
        this.maxHeightDisplay = maxHeightDisplay;
        ismaxHeightDisplay = true;
        setOnNotifyListener(new OnNotifyListener() {
            @Override
            public void onListener() {
                setViewHeight(RecyclerViewAdapter.this.maxHeightDisplay > getItemCount() ?WindowManager.LayoutParams.WRAP_CONTENT: RecyclerViewAdapter.this.maxHeightDisplay *itemHeight);
            }
        });
    }

    protected void setMaxWidthDisplay( int maxWidthDisplay) {
        if (maxHeightDisplay < 0) {
            return;
        }
        this.maxWidthDisplay = maxWidthDisplay;
        ismaxWidthDisplay = true;
        setOnNotifyListener(new OnNotifyListener() {
            @Override
            public void onListener() {
                setViewHeight(RecyclerViewAdapter.this.maxWidthDisplay > getItemCount() ? WindowManager.LayoutParams.WRAP_CONTENT:RecyclerViewAdapter.this.maxWidthDisplay *itemWidth);
            }
        });
    }

    public void notifyOnDataChanged() {
        for (OnNotifyListener onNotifyListener : notifyListenerList) {
            onNotifyListener.onListener();
        }
        notifyDataSetChanged();
    }
    public void notifyOnItemRemoved(int i) {
        for (OnNotifyListener onNotifyListener : notifyListenerList) {
            onNotifyListener.onListener();
        }
        notifyItemRemoved(i);
        if(i!= getItemCount()){
            notifyItemRangeChanged(i, getItemCount()-i+1);
        }
    }
    public void notifyOnItemRemoved(int i,int count) {
        for (OnNotifyListener onNotifyListener : notifyListenerList) {
            onNotifyListener.onListener();
        }
        notifyItemRangeRemoved(i,count);
        if(i!= getItemCount()){
            notifyItemRangeChanged(i, getItemCount()-i+count);
        }
    }
    public void notifyOnItemInsert(int i) {
        for (OnNotifyListener onNotifyListener : notifyListenerList) {
            onNotifyListener.onListener();
        }
        notifyItemInserted(i);
        if(i!= getItemCount()){
            notifyItemRangeChanged(i,getItemCount()-i+1);
        }
    }
    public void notifyOnItemInsert(int i,int count) {
        for (OnNotifyListener onNotifyListener : notifyListenerList) {
            onNotifyListener.onListener();
        }
        notifyItemRangeInserted(i,count);
        if(i!= getItemCount()){
            notifyItemRangeChanged(i,getItemCount()-i+count);
        }
    }

    public interface OnNotifyListener {
        void onListener();
    }

    public void setOnNotifyListener(OnNotifyListener listener) {
        notifyListenerList.add(listener);
    }




}
