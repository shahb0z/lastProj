package it.polito.mobile.polilast.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import it.polito.mobile.polilast.MyMain;
import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.Notice;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNotice extends Fragment {

    private Spinner userType;
    public AddNotice() {
        // Required empty public constructor
    }

    public static AddNotice newInstance() {

        Bundle args = new Bundle();

        AddNotice fragment = new AddNotice();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_notice, container, false);
        userType=(Spinner)view.findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category, android.R.layout.simple_list_item_1);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.contact_spinner_row_nothing_selected,
                getActivity()
        ));
        final EditText title = (EditText)view.findViewById(R.id.notice_title);
       final EditText desc = (EditText)view.findViewById(R.id.notice_desc);
        Button submit = (Button)view.findViewById(R.id.notice_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desc.getText().toString().trim().length() == 0||
                        title.getText().toString().trim().length() == 0||userType.getSelectedItem()==null){
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_LONG)
                            .show();
                }else{
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getString(R.string.progress_signup));
                    dialog.show();
                    Notice n = new Notice();
                    n.setDescription(desc.getText().toString());
                    n.setTitle(title.getText().toString());
                    n.setCategory(userType.getSelectedItem().toString());
                    n.setUser(ParseUser.getCurrentUser().getEmail());
                    n.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.dismiss();
                            if (e != null) {
                                // Show the error message
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                // Start an intent for the dispatch activity
                                getFragmentManager().popBackStack();
                            }
                        }
                    });
                }


            }
        });
        return view;
    }

}
