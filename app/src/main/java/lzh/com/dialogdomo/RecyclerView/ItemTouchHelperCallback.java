package lzh.com.dialogdomo.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017/11/20.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchInterface mItemTouchInterfac;
    private boolean longPressDragEnabled = true;
    private boolean itemViewSwipeEnabled = true;
    private int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private int swipeFlags = ItemTouchHelper.LEFT;
    public void setLongPressDragEnabled(boolean enable){
        this.longPressDragEnabled = enable;
    }
    public void setItemViewSwipeEnabled(boolean enable){
        this.itemViewSwipeEnabled = enable;
    }

    public void setDragFlags(int dragFlags){
        this.dragFlags = dragFlags;
    }
    public void setSwipeFlags(int swipeFlags){
        this.swipeFlags = swipeFlags;
    }

    public ItemTouchHelperCallback(ItemTouchInterface itemTouchInterface) {
        this.mItemTouchInterfac = itemTouchInterface;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemTouchInterfac.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mItemTouchInterfac.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return longPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    public interface ItemTouchInterface {
        void onItemMove(int position, int toPosition);

        void onItemDismiss(int position);
    }

}
