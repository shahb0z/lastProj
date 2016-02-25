package it.polito.mobile.polilast.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import it.polito.mobile.polilast.data.Message;
import it.polito.mobile.polilast.data.MyApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    ListView lvChat;
    ArrayList<Message> mMessages;
    ChatListAdapter mAdapter;
    EditText etMessage;
    Button btSend;
    boolean mFirstLoad;
    String userId;
    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String email) {

        Bundle args = new Bundle();
        args.putString("receiver",email);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        etMessage = (EditText) view.findViewById(R.id.etMessage);
        btSend = (Button) view.findViewById(R.id.btSend);
        lvChat = (ListView) view.findViewById(R.id.lvChat);
        mMessages = new ArrayList<>();
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;

        userId = ParseUser.getCurrentUser().getEmail();
        final ArrayList<String> receiver = new ArrayList<>();
        receiver.add(getArguments().getString("receiver"));
        mAdapter = new ChatListAdapter(getActivity(), userId, mMessages);
        lvChat.setAdapter(mAdapter);
        refreshMessages();
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setSender(userId);
                message.setReceiver(receiver);
                message.setDescription(data);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getActivity(), "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                        refreshMessages();
                    }
                });
                etMessage.setText(null);
            }
        });

        return view;
    }
    void refreshMessages() {
        // Construct query to execute
        List<ParseQuery<Message>> queries = new ArrayList<ParseQuery<Message>>();

        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo(MyApp.RECEIVER,getArguments().getString("receiver"));
        query.whereEqualTo(MyApp.SENDER,userId);
        queries.add(query);
        //
        ParseQuery<Message> query2 = ParseQuery.getQuery(Message.class);
        query2.whereEqualTo(MyApp.SENDER,getArguments().getString("receiver"));
        query2.whereEqualTo(MyApp.RECEIVER,userId);
        queries.add(query2);
        //main query
        ParseQuery<Message> mainQuery = ParseQuery.or(queries);
        mainQuery.orderByAscending("createdAt");
        mainQuery.setLimit(30);
        mainQuery.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    Log.d("RECEIVED_SIZE", String.valueOf(messages.size()));
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }

}
