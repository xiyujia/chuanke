package com.example.chuanke.chuanke.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.bean.FileBean;

import java.util.List;

public class FileFragmentListAdapter extends RecyclerView.Adapter<FileFragmentListAdapter.ViewHolder> {
    private Context mContext;
    private List<FileBean> mList;


    public FileFragmentListAdapter(List<FileBean> List) {
        this.mList=List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.file_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FileBean bean=mList.get(i);
        viewHolder.fileName.setText(bean.getFineName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName=itemView.findViewById(R.id.tv_file_name);
        }
    }
    }
