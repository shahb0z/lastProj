package it.polito.mobile.polilast.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Message;
import it.polito.mobile.polilast.data.WritableMessage;

/**
 * Created by shahboz on 24/02/2016
 * in context of PoliLast.
 */
public class MyAdapter2 extends BaseAdapter {
    Context context;
    ArrayList<WritableMessage> data;
    private LayoutInflater inflater = null;

    public MyAdapter2(Context context, ArrayList<WritableMessage> data) {

        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.my_notif_item, null);
        TextView text2 = (TextView)vi.findViewById(R.id.slot_title);
        text2.setText(data.get(position).getTitle());
        TextView text3 = (TextView)vi.findViewById(R.id.slot_description);
        text3.setText(data.get(position).getDescription());
        return vi;
    }
}
