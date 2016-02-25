package it.polito.mobile.polilast.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Message;
import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.Notice;
import it.polito.mobile.polilast.data.WritableMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeboardFragment extends Fragment {


    public NoticeboardFragment() {
        // Required empty public constructor
    }

    public static NoticeboardFragment newInstance() {

        Bundle args = new Bundle();

        NoticeboardFragment fragment = new NoticeboardFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_noticeboard, container, false);

        ParseQuery<Notice> query = ParseQuery.getQuery(Notice.class);
        final ListView list = (ListView) view.findViewById(R.id.list_notice);

        final ArrayList<Notice> noticeList = new ArrayList<>();
        final NoticeAdapter adapter2 = new NoticeAdapter(getActivity(), noticeList);
        list.setAdapter(adapter2);
        query.whereNotEqualTo(MyApp.USER, ParseUser.getCurrentUser().getEmail());
        query.findInBackground(new FindCallback<Notice>() {
            @Override
            public void done(List<Notice> objects, ParseException e) {
                if (e == null) {
                    noticeList.clear();
                    noticeList.addAll(objects);
                    adapter2.notifyDataSetChanged();
                    Log.d("NOTICE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("PARSE_QUERY_ERROR: ", e.getLocalizedMessage());
                }
            }
        });

        return view;
    }

}
