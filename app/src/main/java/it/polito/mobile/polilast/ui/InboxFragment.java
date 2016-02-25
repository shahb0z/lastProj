package it.polito.mobile.polilast.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Chat;
import it.polito.mobile.polilast.data.MyUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {
    HashSet<String> names;
    ArrayList<String> namesArray;
    ListView usersListView;
    ArrayAdapter<String> namesArrayAdapter;
    MyUser user;

    public InboxFragment() {
        // Required empty public constructor
    }

    public static InboxFragment newInstance() {

        Bundle args = new Bundle();

        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result=inflater.inflate(R.layout.fragment_inbox, container, false);
        names = new HashSet<>();

        //query
        List<ParseQuery<Chat>> queries = new ArrayList<ParseQuery<Chat>>();
        ParseQuery<Chat> query = ParseQuery.getQuery(Chat.class);
        user  = (MyUser) ParseUser.getCurrentUser();
        query.whereEqualTo("user1",user.getEmail());
        queries.add(query);
        ParseQuery<Chat> query2 = ParseQuery.getQuery(Chat.class);
        query2.whereEqualTo("user2",user.getEmail());
        queries.add(query2);
        ParseQuery<Chat> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<Chat>() {
            @Override
            public void done(List<Chat> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        if(objects.get(i).getUser1().compareTo(user.getEmail())==0)
                            names.add(objects.get(i).getUser2());
                        else
                            names.add(objects.get(i).getUser1());
                    }
                    namesArray = new ArrayList<String>(names);
                    usersListView = (ListView) result.findViewById(R.id.list_chat);
                    namesArrayAdapter =
                            new ArrayAdapter<String>(getActivity(),
                                    R.layout.chat_item, namesArray);
                    usersListView.setAdapter(namesArrayAdapter);

                    usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            ((StudentHomeActivity)getActivity()).startChat(namesArray.get(i),1);
                        }
                    });

                } else {
                    Toast.makeText(getActivity(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return(result);
    }

}
