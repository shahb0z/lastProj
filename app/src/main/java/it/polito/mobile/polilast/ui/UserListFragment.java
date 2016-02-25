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
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Chat;
import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.MyUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    ArrayList<String> names;
    ListView usersListView;
    ArrayAdapter<String> namesArrayAdapter;
    MyUser user;
    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {

        Bundle args = new Bundle();

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        names = new ArrayList<String>();

        //query
        ParseQuery<MyUser> query = ParseQuery.getQuery(MyUser.class);
        user  = (MyUser) ParseUser.getCurrentUser();

//don't include yourself
        query.whereNotEqualTo("email",user.getEmail());
        query.whereEqualTo(MyApp.TYPE, MyApp.STUDENT_TYPE);
        query.findInBackground(new FindCallback<MyUser>() {
            public void done(List<MyUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }

                    usersListView = (ListView) view.findViewById(R.id.list_users);
                    namesArrayAdapter =
                            new ArrayAdapter<String>(getActivity(),
                                    R.layout.chat_item, names);
                    usersListView.setAdapter(namesArrayAdapter);

                    usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            openConversation(names, i);
                        }
                    });

                } else {
                    Toast.makeText(getActivity(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    public void openConversation(ArrayList<String> names, int pos) {
        Chat object = new Chat();
        object.setUser1(user.getEmail());
        final String s = names.get(pos);
        object.setUser2(s);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    ((StudentHomeActivity)getActivity()).startChat(s, 2);
                }
            }
        });
    }

}
