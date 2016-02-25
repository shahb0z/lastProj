package it.polito.mobile.polilast.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Message;
import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.MyUser;
import it.polito.mobile.polilast.data.WritableMessage;


public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        TextView todayTitle = (TextView)view.findViewById(R.id.text_today);
        todayTitle.setText("Today lectures");
        MyUser user = (MyUser) ParseUser.getCurrentUser();
        ArrayList<Subject> values = null;
        final ArrayList<WritableMessage> values2 = new ArrayList<>();
        TextView notTitle = (TextView)view.findViewById(R.id.text_notification);
        notTitle.setText("Notifications");
        ListView list2 = (ListView)view.findViewById(R.id.list_notification);
        Log.d("NAMES:", String.valueOf(values2.size()));
        final MyAdapter2 adapter2 = new MyAdapter2(getContext(), values2);
        list2.setAdapter(adapter2);
        if(user.getType().compareTo(MyApp.PROFESSOR_TYPE)==0){
            values = ((ProfessorHomeActivity)getActivity()).getTodaySubjects(day-2);
        }else if (user.getType().compareTo(MyApp.STUDENT_TYPE)==0){
            values = ((StudentHomeActivity)getActivity()).getTodaySubjects(day - 2);
            ArrayList<String> names = ((StudentHomeActivity)getActivity()).getSubjectNames();
            List<ParseQuery<Message>> queries = new ArrayList<ParseQuery<Message>>();


            Log.d("NAMES:", String.valueOf(names.size()));
            for(int i = 0; i<names.size(); i++){
                ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
                query.whereEqualTo(MyApp.RECEIVER, names.get(i));
                queries.add(query);
            }
            ParseQuery<Message> mainQuery = ParseQuery.or(queries);
            mainQuery.orderByDescending("createdAt");
            mainQuery.setLimit(20);
            mainQuery.findInBackground(new FindCallback<Message>() {
                @Override
                public void done(List<Message> objects, ParseException e) {

                    if (e == null) {
                        for (int i = 0; i < objects.size(); i++) {
                            Log.d("MESSAGE: ", objects.get(i).getSender() + ",  " + objects.get(i).getDescription());
                            WritableMessage wm = new WritableMessage(objects.get(i).getReceiver().get(0),
                                    objects.get(i).getDescription());
                            values2.add(wm);
                        }
                        adapter2.notifyDataSetChanged();
                    } else {
                        Log.d("PARSE_QUERY_ERROR: ", e.getLocalizedMessage());
                    }
                }
            });
        }
        ListView list = (ListView)view.findViewById(R.id.list_today);
        MyAdapter adapter = new MyAdapter(getContext(),values);
        list.setEmptyView(view.findViewById(R.id.empty1));
        list.setAdapter(adapter);

        return view;
    }

}
