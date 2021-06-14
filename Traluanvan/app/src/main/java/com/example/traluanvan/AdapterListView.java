package com.example.traluanvan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterListView extends BaseAdapter {
    Context context;
    private List<ViewInterface> luanvan ;


    public AdapterListView(Context context, List<ViewInterface> luanvan) {
        this.context = context;
        this.luanvan = luanvan;
    }

    @Override
    public int getCount() {
        return luanvan.size();
    }

    @Override
    public ViewInterface getItem(int position) {
        return luanvan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflate.inflate(R.layout.activity_data_view, parent, false);
        TextView LV_Ma,LV_Ten,LV_TenTiengAnh,SV1_Ten,MSSV1,SV2_Ten,MSSV2,GV1_Ten,GV2_Ten;
        //Set up Textview
        LV_Ma= convertView.findViewById(R.id.Lv_Ma);
        LV_Ten = convertView.findViewById(R.id.LV_Ten);
        LV_TenTiengAnh = convertView.findViewById(R.id.LV_TenTiengAnh);
        SV1_Ten = convertView.findViewById(R.id.SV1_Ten);
        MSSV1 = convertView.findViewById(R.id.MSSV1);
        SV2_Ten = convertView.findViewById(R.id.SV2_Ten);
        MSSV2 = convertView.findViewById(R.id.MSSV2);
        GV1_Ten = convertView.findViewById(R.id.GV1_Ten);
        GV2_Ten = convertView.findViewById(R.id.GV2_Ten);

        ViewInterface Luanvan_View = this.getItem(position);
        LV_Ma.setText(Integer.toString(Luanvan_View.getLV_Ma()));
        LV_Ten.setText(Luanvan_View.getLV_Ten());
        LV_TenTiengAnh.setText(Luanvan_View.getLV_TenTiengAnh());
        SV1_Ten.setText(Luanvan_View.getSV1_Ten());
        MSSV1.setText(Luanvan_View.getMSSV1());
        SV2_Ten.setText(Luanvan_View.getSV2_Ten());
        MSSV2.setText(Luanvan_View.getMSSV2());
        GV1_Ten.setText(Luanvan_View.getGV1_Ten());
        GV2_Ten.setText(Luanvan_View.getGV2_Ten());
        return convertView;
    }
}
