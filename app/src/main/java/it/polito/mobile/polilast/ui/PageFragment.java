package it.polito.mobile.polilast.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;

import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.MyUser;

/**
 * Created by shahboz on 21/02/2016
 * in context of PoliLast.
 */

public class PageFragment extends Fragment {
    private static final String KEY_POSITION="position";

    static PageFragment newInstance(int position) {
        PageFragment frag=new PageFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.page_schedule, container, false);

        int position=getArguments().getInt(KEY_POSITION, -1);
        ArrayList<Subject> values = null;
        MyUser user = (MyUser) ParseUser.getCurrentUser();
        if(user.getType().compareTo(MyApp.PROFESSOR_TYPE)==0){
            values = ((ProfessorHomeActivity)getActivity()).getTodaySubjects(position);
        }else if (user.getType().compareTo(MyApp.STUDENT_TYPE)==0){
            values = ((StudentHomeActivity)getActivity()).getTodaySubjects(position);
        }

        ListView list = (ListView)result.findViewById(R.id.list_schedule);
        MyAdapter adapter = new MyAdapter(getContext(),values);
        list.setAdapter(adapter);

        return(result);
    }

}
