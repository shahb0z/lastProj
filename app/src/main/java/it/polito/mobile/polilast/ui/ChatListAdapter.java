package it.polito.mobile.polilast.ui;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Message;

/**
 * Created by shahboz on 24/02/2016
 * in context of PoliLast.
 */
public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUser;
    private ImageView imageOther;
    private ImageView imageMe;
    private TextView body;
    public ChatListAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUser = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.message_item, parent, false);
        }
            imageOther = (ImageView)convertView.findViewById(R.id.ivProfileOther);
            imageMe = (ImageView)convertView.findViewById(R.id.ivProfileMe);
            body = (TextView)convertView.findViewById(R.id.tvBody);


        final Message message = getItem(position);
        body.setText(message.getDescription());
        final boolean isMe = message.getSender().equals(mUser);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            imageMe.setVisibility(View.VISIBLE);
            imageOther.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            imageOther.setVisibility(View.VISIBLE);
            imageMe.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView = isMe ? imageMe : imageOther;
        Picasso.with(getContext()).load(getProfileUrl(message.getSender())).into(profileView);

        Log.d("MESSAGE_BODY",message.getDescription());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }


}
