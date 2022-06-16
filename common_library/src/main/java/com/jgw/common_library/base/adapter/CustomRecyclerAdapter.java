package com.jgw.common_library.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jgw.common_library.utils.click_utils.listener.OnItemLongTimeClickListener;
import com.jgw.common_library.utils.click_utils.listener.OnItemSingleClickListener;
import com.jgw.common_library.utils.click_utils.listener.OnPackageLoadMoreListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xsw on 2016/12/2.
 */
@SuppressWarnings("unused")
public abstract class CustomRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //自定义条目监听
    public OnItemSingleClickListener mListener;
    public OnItemLongTimeClickListener mLongListener;
    public OnPackageLoadMoreListener mLoadMoreListener;
    public LayoutInflater mLayoutInflater;
    public Context mContext;
    //item类型
    public static final int ITEM_PAGE_SIZE = 20;
    public static final int ITEM_TYPE_EMPTY = -10;
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_FOOTER = 1;
    public static final int ITEM_TYPE_GROUP = 2;
    public static final int ITEM_TYPE_CONTENT = 3;
    public static final int ITEM_TYPE_CONTENT1 = 4;
    public static final int ITEM_TYPE_CONTENT2 = 5;
    public static final int ITEM_TYPE_CONTENT3 = 6;
    public static final int ITEM_TYPE_CONTENT4 = 7;
    public static final int ITEM_TYPE_CONTENT5 = 8;
    public static final int ITEM_TYPE_CONTENT6 = 9;
    public static final int ITEM_TYPE_CONTENT7 = 10;
    public static final int ITEM_TYPE_CONTENT8 = 11;
    public static final int ITEM_TYPE_CONTENT9 = 12;
    public static final int ITEM_TYPE_CONTENT10 = 13;
    public static final int ITEM_TYPE_CONTENT11 = 14;
    public static final int ITEM_TYPE_CONTENT12 = 15;
    public static final int ITEM_TYPE_CONTENT13 = 16;
    public static final int ITEM_TYPE_CONTENT14 = 17;
    protected List<T> mList;
    public Fragment mFragment;
    private WeakReference<RecyclerView> mRecyclerViewReference;


    public void setOnItemClickListener(OnItemSingleClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongTimeClickListener listener) {
        mLongListener = listener;
    }


    public void setOnLoadMoreListener(OnPackageLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public CustomRecyclerAdapter() {
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
            if (parent instanceof RecyclerView) {
                mRecyclerViewReference = new WeakReference<>((RecyclerView) parent);
            }
        }
        return onCreateCustomViewHolder(parent, viewType);
    }

    public abstract ContentViewHolder<? extends ViewDataBinding> onCreateCustomViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindCustomViewHolder(holder, position);
        if (mLoadMoreListener != null && mList != null && (mList.size() - 1 + getHeaderCount()) == position) {
            mLoadMoreListener.onLoadMore();
        }
    }

    public abstract void onBindCustomViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    public void setLoadMoreList(List<T> list) {
        if (list != null && list.size() != 0) {
            mList.addAll(list);
        }
    }

    public void setDataList(List<T> list) {
        if (mList == null) {
            mList = list;
        } else {
            mList.clear();
            mList.addAll(list);
        }
    }

    public void setNewDataList(List<T> list) {
        mList = list;
    }

    public List<T> getDataList() {
        return mList;
    }

    public void clearDataList() {
        if (mList != null) {
            mList.clear();
        }
    }

    public T getContentItemData(int position){
        return mList.get(position-getHeaderCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        } else {
            return ITEM_TYPE_CONTENT1;
        }
    }

    public class ContentViewHolder<C extends ViewDataBinding>
            extends RecyclerView.ViewHolder implements OnItemSingleClickListener {
        public View mItemView;
        public C mBindingView;

        public ContentViewHolder(C binding) {
            super(binding.getRoot());
            mBindingView = binding;
            mItemView = mBindingView.getRoot();
        }

        @Override
        public void onItemClick(View view, int firstPosition, int secondPosition, int thirdPosition) {

        }


        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onItemClick(View view, int groupPosition, int subPosition) {

        }

        public Context getContext() {
            return mContext;
        }
    }

    public class EmptyViewHolder<E extends ViewDataBinding> extends ContentViewHolder<E> {

        public EmptyViewHolder(E binding) {
            super(binding);
            mBindingView = binding;
            mItemView = itemView;
        }
    }

    public class FooterViewHolder<F extends ViewDataBinding> extends ContentViewHolder<F> {

        public FooterViewHolder(F binding) {
            super(binding);
            mBindingView = binding;
            mItemView = itemView;
        }
    }

    public class CustomHeaderViewHolder<H extends ViewDataBinding> extends ContentViewHolder<H> {

        public CustomHeaderViewHolder(H binding) {
            super(binding);
            mBindingView = binding;
            mItemView = itemView;
        }
    }

    @Override
    public int getItemCount() {
        //noinspection UnnecessaryLocalVariable
        int adapterItemCount = getAdapterItemCount() + getHeaderCount() + getFooterCount() + getEmptyCount();
        return adapterItemCount;
    }

    public int getHeaderCount() {
        return 0;
    }

    /**
     * 脚界面Item
     * 改用{@link com.jgw.common_library.base.view.CustomRecyclerViewContainer}实现空布局时
     * 无法兼容脚布局底部内容,加入List合并入Content
     */
    public int getFooterCount() {
        return 0;
    }

    /**
     * 空界面Item
     * 改用{@link com.jgw.common_library.base.view.CustomRecyclerViewContainer}实现空布局时不可使用此方法
     * @return 0或1
     */
    public int getEmptyCount() {
        return 0;
    }

    public int getAdapterItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public boolean isEmpty() {
        return mList == null || mList.size() == 0;
    }

    public void notifyRefreshList(List<T> list) {
        mList.clear();
        notifyDataSetChanged();
        mList.addAll(list);
        notifyDataSetChanged();
        if (mRecyclerViewReference != null && mRecyclerViewReference.get() != null && !mList.isEmpty()) {
            mRecyclerViewReference.get().scrollToPosition(0);
        }
    }

    public void notifyRemoveListItem() {
        int size = mList.size();
        int otherItemCount = getEmptyCount() + getHeaderCount() + getFooterCount();
        mList.clear();
        if (getEmptyCount() == 1) {
            notifyItemRangeRemoved(getHeaderCount() + 1, size - 1);
            notifyItemChanged(getHeaderCount());
        } else {
            notifyItemRangeRemoved(getHeaderCount(), size);
        }
    }

    public void notifyAddListItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = mList.size();
        int emptyCount = getEmptyCount();
        int startCount = getHeaderCount() + getEmptyCount() + size;
        int offset = list.size() - getEmptyCount();
        mList.addAll(list);
        if (emptyCount == 1) {
            notifyItemChanged(getHeaderCount());
        }
        notifyItemRangeInserted(startCount, offset);
    }

    public void notifyAddItem(T item) {
        notifyAddItem(item, 0);
    }

    public void notifyAddItem(T item, int position) {
        int size = mList.size();
        boolean empty = isEmpty();
        int emptyCount = getEmptyCount();
        mList.add(position, item);
        if (empty && emptyCount == 1) {
            notifyItemChanged(getHeaderCount() + emptyCount - 1);
        } else {
            notifyItemInserted(getHeaderCount() + position);
        }
    }

    public void notifyRemoveItem(int position) {
        int size = mList.size();
        if (size <= 1 && getEmptyCount() == 1) {
            notifyItemChanged(getHeaderCount());
        } else {
            mList.remove(position);
            notifyItemRemoved(getHeaderCount() + position);
        }
    }

    public void notifyRemoveItem(T item) {
        int size = mList.size();
        if (size <= 1 && getEmptyCount() == 1) {
            notifyItemChanged(getHeaderCount());
        } else {
            int index = mList.indexOf(item);
            if (index == -1) {
                return;
            }
            mList.remove(index);
            notifyItemRemoved(getHeaderCount() + index);
        }
    }

    public void notifyRefreshItem(T item) {
        int index = mList.indexOf(item);
        if (index != -1) {
            mList.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void addAndNotifyLastItem(T item) {
        notifyAddItem(item, mList.size());
    }
}
