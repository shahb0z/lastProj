package it.polito.mobile.polilast.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import it.polito.mobile.polilast.R;

public class ScheduleFragment extends Fragment {


    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance() {
        
        Bundle args = new Bundle();

        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }


   @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
       View result=inflater.inflate(R.layout.fragment_schedule, container, false);
       final ViewPager pager=(ViewPager)result.findViewById(R.id.schedule_viewpager);

       TabLayout tabLayout = (TabLayout) result.findViewById(R.id.tab_layout);
       tabLayout.addTab(tabLayout.newTab().setText(R.string.mon));
       tabLayout.addTab(tabLayout.newTab().setText(R.string.tue));
       tabLayout.addTab(tabLayout.newTab().setText(R.string.wed));
       tabLayout.addTab(tabLayout.newTab().setText(R.string.thu));
       tabLayout.addTab(tabLayout.newTab().setText(R.string.fri));
       tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       pager.setAdapter(buildAdapter());
       pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

       tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               pager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

       return(result);
  }

  private PagerAdapter buildAdapter() {

    return(new SampleAdapter(getActivity(), getChildFragmentManager()));
  }

}
