package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LuanvanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LuanvanModel> mListLuanvan;
    int viewtype;
    int Loadingposition=0;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public void setData(List<LuanvanModel> list) {
        this.mListLuanvan = list;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }


    public LuanvanAdapter(Context mContext,OnLoadMoreListener onLoadMoreListener) {
        this.mContext = mContext;
        this.onLoadMoreListener=onLoadMoreListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Return a new holder instance
        View view = null;
        switch(viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luanvantomtat, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luanvantomtatsv1gv2, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luanvantomtatsv2gv1, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luanvantomtatsv2gv2, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luanvantomtat, parent, false);
                break;
        }

        RecyclerView.ViewHolder viewHolder = new LuanvanViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        LuanvanModel luanvan = mListLuanvan.get(position);
        if (getItemViewType(position)!=0){
            ((LuanvanViewHolder) holder).setdata(mListLuanvan.get(position),getItemViewType(position));
            ((LuanvanViewHolder)holder).setItemClickListener(new LuanvanViewHolder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(!isLongClick) {
                        try {
                            transferdata(position);
                        }
                        catch (Exception e){
                            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                        Toast.makeText(mContext, "Không copy được đâu bạn ơi ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mListLuanvan.get(position)!=null) {
            if (mListLuanvan.get(position).getSV2_Ten().matches("0") && mListLuanvan.get(position).getGV2_Ten().matches("0"))
                viewtype = 1;
            else if (mListLuanvan.get(position).getSV2_Ten().matches("0")) {
                viewtype = 2;
            } else if (mListLuanvan.get(position).getGV2_Ten().matches("0")) {
                viewtype = 3;
            } else {
                viewtype = 4;
            }
        }
    else{
        viewtype = 0;
    }
        return viewtype;
    }

    @Override
    public int getItemCount() {
        if (mListLuanvan != null) {
            return mListLuanvan.size();
        }
        return 0;
    }


    public static class LuanvanViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView name;
        private TextView tensinhvien, tensinhvien_2;
        private TextView maluanvan;
        private TextView gvhuongdan, gvhuongdan_2;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        public LuanvanViewHolder(@NonNull @NotNull View itemView, int viewtype) {
            super(itemView);
            if (viewtype != 0) {
                name = itemView.findViewById(R.id.tenluanvan);
                tensinhvien = itemView.findViewById(R.id.tensinhvien1);
                maluanvan = itemView.findViewById(R.id.maluanvan);
                gvhuongdan = itemView.findViewById(R.id.gvhuongdan1);
                switch (viewtype) {
                    case 2:
                        gvhuongdan_2 = itemView.findViewById(R.id.gvhuongdan2);
                    case 3:
                        tensinhvien_2 = itemView.findViewById(R.id.tensinhvien2);
                    case 4:
                        tensinhvien_2 = itemView.findViewById(R.id.tensinhvien2);
                        gvhuongdan_2 = itemView.findViewById(R.id.gvhuongdan2);
                }
            }

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        private interface ItemClickListener {
            void onClick(View view, int position, boolean isLongClick);

        }

        private void setdata(LuanvanModel luanvan, int viewtype) {
            if (viewtype != 0) {
                name.setText(luanvan.getLV_Ten());
                tensinhvien.setText(luanvan.getSV1_Ten());
                maluanvan.setText(Integer.toString(luanvan.getLV_Ma()));
                gvhuongdan.setText(luanvan.getGV1_Ten());
                switch (viewtype) {
                    case 2:
                        gvhuongdan_2.setText(luanvan.getGV2_Ten());
                        break;
                    case 3:
                        tensinhvien_2.setText(luanvan.getSV2_Ten());
                        break;
                    case 4:
                        gvhuongdan_2.setText(luanvan.getGV2_Ten());
                        tensinhvien_2.setText(luanvan.getSV2_Ten());
                        break;
                }
            }
        }

    }

    //Set Loading

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }
    public void showLoading() {
        if (isMoreLoading && mListLuanvan != null&&onLoadMoreListener!=null) {
            isMoreLoading = false;
            mListLuanvan.add(null);
            notifyItemInserted(mListLuanvan.size() - 1);
            onLoadMoreListener.onLoadMore();
        }
    }



    public void additem(int page,List<LuanvanModel> luanvan){
        int sizepage = page*mListLuanvan.size();
        mListLuanvan.addAll(luanvan);
        notifyItemRangeChanged(sizepage, sizepage+mListLuanvan.size());
    }
    public void adddall(List<LuanvanModel> luanvan){
        mListLuanvan.addAll(luanvan);
        notifyDataSetChanged();
    }
    public List<LuanvanModel> getList(){
        return mListLuanvan;
    }
    public void dismissLoading() {
        if (mListLuanvan != null && mListLuanvan.size() > 0) {
            mListLuanvan.remove(mListLuanvan.size() - 1);
            notifyItemRemoved(mListLuanvan.size());
            isMoreLoading=true;
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
    private void transferdata(int position){
        Bundle bundle = new Bundle();
        bundle.putString("LV_Ma", Integer.toString(mListLuanvan.get(position).getLV_Ma()));
        bundle.putInt("view_type",getItemViewType(position));
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);
        FragmentManager fragmentManager=((MainActivity)mContext).getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, infoFragment,"Info").addToBackStack("Info");
        transaction.commit();
    }
}
