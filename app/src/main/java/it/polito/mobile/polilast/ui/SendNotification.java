package it.polito.mobile.polilast.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import java.util.ArrayList;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Message;
import it.polito.mobile.polilast.data.MyUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNotification extends Fragment {


    public SendNotification() {
        
    }

    public static SendNotification newInstance() {

        Bundle args = new Bundle();

        SendNotification fragment = new SendNotification();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_notification, container, false);
        final EditText mText = (EditText)view.findViewById(R.id.edit_text_not);
        Button send = (Button)view.findViewById(R.id.button_notif);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data;
                if (mText.getText().toString().trim().length() == 0)
                    Toast.makeText(getActivity(), "Please enter message!", Toast.LENGTH_LONG)
                            .show();
                else{
                    data = mText.getText().toString();
                    Message message = new Message();
                    MyUser user = (MyUser) ParseUser.getCurrentUser();
                    //sending notification
                    ParsePush push = new ParsePush();
                    push.setChannel(user.getSubject().replace(" ","_"));
                    push.setMessage(data);
                    push.setExpirationTimeInterval(60*60*2);
                    push.sendInBackground(new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("push", "The push campaign has been created.");
                            } else {
                                Log.d("push", "Error sending push:" + e.getMessage());
                            }
                        }
                    });
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(user.getSubject());
                    //set message paramters
                    message.setSender(user.getEmail());
                    message.setReceiver(list);
                    message.setDescription(data);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(getActivity(), "Message sent!", Toast.LENGTH_LONG)
                                    .show();
                            InputMethodManager inputManager =
                                    (InputMethodManager) getActivity().
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            getFragmentManager().popBackStack();



                        }
                    });

                }
            }
        });
        return view;
    }

}
