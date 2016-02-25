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

/**
 * Created by shahboz on 21/02/2016
 * in context of PoliLast.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Subject> data;
    private LayoutInflater inflater = null;

    public MyAdapter(Context context, ArrayList<Subject> data) {

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
            vi = inflater.inflate(R.layout.my_schedule_item, null);

        TextView text = (TextView) vi.findViewById(R.id.start_time);
        TextView text2 = (TextView)vi.findViewById(R.id.slot_title);
        text2.setText(data.get(position).getName());
        text.setText(data.get(position).getStartTime());
        TextView text3 = (TextView)vi.findViewById(R.id.slot_description);
        text3.setText(data.get(position).getStartTime() + " - " + data.get(position).getFinishTime()
                + " / Room " + data.get(position).getRoom());

        Button map = (Button)vi.findViewById(R.id.button_show_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("loc",data.get(position).getLocation());
                intent.putExtra("room",data.get(position).getRoom());
                context.startActivity(intent);
            }
        });
        return vi;
    }
}
