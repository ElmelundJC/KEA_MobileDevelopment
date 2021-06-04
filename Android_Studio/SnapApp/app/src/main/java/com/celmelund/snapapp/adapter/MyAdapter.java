package com.celmelund.snapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.celmelund.snapapp.R;
import com.celmelund.snapapp.model.Snap;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<String> snaps; // holds the data
    private LayoutInflater layoutInflater; // can inflate layout files - can take the layout file and make it into a java object.
    View snapView;

    public MyAdapter(List<String> snaps, Context context) {
        this.snaps = snaps;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return snaps.size();
    }

    @Override
    public Object getItem(int position) {
        return snaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // make layout .xml file first...
        if (view == null){
            view = layoutInflater.inflate(R.layout.myrow, null);
        }
        snapView = view;
        return snapView;
    }
}
