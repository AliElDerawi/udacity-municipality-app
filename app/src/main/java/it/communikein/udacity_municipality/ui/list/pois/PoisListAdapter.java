package it.communikein.udacity_municipality.ui.list.pois;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.communikein.udacity_municipality.R;
import it.communikein.udacity_municipality.data.model.Poi;
import it.communikein.udacity_municipality.databinding.ListItemPoiBinding;
import it.communikein.udacity_municipality.ui.list.pois.PoisListAdapter.PoiViewHolder;

public class PoisListAdapter extends RecyclerView.Adapter<PoiViewHolder> {

    private ArrayList<Poi> mList;

    @Nullable
    private OnListItemClickListener mOnClickListener;
    public interface OnListItemClickListener {
        void onListPoiClick(Poi poi);
    }

    /**
     * Creates a PoisListAdapter.
     *
     * @param onListItemClickListener Used to talk to the UI and app resources
     */
    PoisListAdapter(@Nullable OnListItemClickListener onListItemClickListener) {
        mOnClickListener = onListItemClickListener;
    }

    @Override
    public PoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPoiBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item_poi, parent, false);

        return new PoiViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(PoiViewHolder holder, int position) {
        Poi poi = mList.get(position);

        holder.mBinding.setPoi(poi);
        holder.mBinding.titleTextview.setText(poi.getTitle());
        holder.mBinding.descriptionTextview.setText(poi.getDescription());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public void setList(final ArrayList<Poi> newList) {
        if (mList == null) {
            mList = newList;
            notifyItemRangeInserted(0, mList.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mList.size();
                }

                @Override
                public int getNewListSize() {
                    return newList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mList.get(oldItemPosition).equals(newList.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Poi newItem = newList.get(newItemPosition);
                    Poi oldItem = mList.get(oldItemPosition);
                    return oldItem.displayEquals(newItem);
                }
            });
            mList = newList;
            result.dispatchUpdatesTo(this);
        }
    }


    class PoiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ListItemPoiBinding mBinding;

        PoiViewHolder(ListItemPoiBinding binding) {
            super(binding.getRoot());

            binding.getRoot().setOnClickListener(this);

            this.mBinding = binding;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.onListPoiClick(mBinding.getPoi());
            }
        }
    }
}
