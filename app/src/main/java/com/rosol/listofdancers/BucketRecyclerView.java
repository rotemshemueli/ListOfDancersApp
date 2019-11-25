package com.rosol.listofdancers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BucketRecyclerView extends RecyclerView {

    private View mEmptyView;
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            initEmptyView();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            initEmptyView();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            initEmptyView();

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            initEmptyView();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            initEmptyView();

        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            initEmptyView();

        }
    };

    private void initEmptyView() {
        if(mEmptyView !=null){
            mEmptyView.setVisibility(
                    getAdapter()==null || getAdapter().getItemCount()==0 ? VISIBLE : GONE);
            BucketRecyclerView.this.setVisibility(
                    getAdapter()==null ||getAdapter().getItemCount()==0 ?VISIBLE: VISIBLE);
        }
    }

    public BucketRecyclerView(@NonNull Context context) {
        super(context);
    }

    public BucketRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Adapter oldAdapter= getAdapter();
        super.setAdapter(adapter);

        if (oldAdapter!=null){
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }

        if (adapter!=null){
            adapter.registerAdapterDataObserver(mObserver);
        }
    }

    public void setEmptyView(View view){
        this.mEmptyView=view;
        initEmptyView();
    }


}
