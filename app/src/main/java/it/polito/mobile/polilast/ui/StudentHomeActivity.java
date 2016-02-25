package it.polito.mobile.polilast.ui;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import it.polito.mobile.polilast.MyMain;
import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.MyUser;

public class StudentHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Subject> subjectList;
    FloatingActionButton fab;
    NavigationView navigationView;
    int some=0;
    private HashSet<String> subjectNames = new HashSet<>();
    private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        //read schedule data
        MyUser currentUser = (MyUser) ParseUser.getCurrentUser();
        initSchedule(currentUser.getMajor());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigationView.getMenu().getItem(2).isChecked()){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    UserListFragment hf = UserListFragment.newInstance();
                    ft.replace(R.id.student_main, hf);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    fab.setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    AddNotice hf = AddNotice.newInstance();
                    ft.replace(R.id.student_main, hf);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle(navigationView.getMenu().getItem(0).getTitle());
        View headerLayout = navigationView.getHeaderView(0);
        TextView nameText = (TextView)headerLayout.findViewById(R.id.text_name);
        nameText.setText(currentUser.getName());
        TextView nameEmail = (TextView)headerLayout.findViewById(R.id.text_email);
        nameEmail.setText(currentUser.getEmail());
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment hf = HomeFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.student_main, hf).commit();
        fab.setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (navigationView.getMenu().getItem(2).isChecked()){
            fab.setVisibility(View.VISIBLE);
                if(some!=0) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    InboxFragment hf = InboxFragment.newInstance();
                    ft.replace(R.id.student_main, hf);
                    ft.commit();
                }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = null;
        Class c = null;
        if (id == R.id.st_nav_home) {
            // Handle the camera action
            c = HomeFragment.class;
            fab.setVisibility(View.GONE);
        } else if (id == R.id.st_nav_schedule) {
            c = ScheduleFragment.class;
            fab.setVisibility(View.GONE);
        } else if (id == R.id.st_nav_inbox) {
            c = InboxFragment.class;
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.st_nav_notice) {
            c= NoticeboardFragment.class;
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.st_nav_logout) {
            logout();
        }
        if(c != null) {
            try {
                f = (Fragment)c.newInstance();
                fragmentManager.beginTransaction().replace(R.id.student_main, f).commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());
        return true;
    }
    private void logout(){
        final ProgressDialog dialog = new ProgressDialog(StudentHomeActivity.this);
        dialog.setMessage(getString(R.string.progress_logout));
        dialog.show();
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(StudentHomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(StudentHomeActivity.this, MyMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    StudentHomeActivity.this.finish();
                }
            }
        });
    }



    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("schedule.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    private void initSchedule(String userMajor) {

        try {
            subjectList = new ArrayList<>();
            JSONArray subArray = new JSONArray(loadJSONFromAsset());
            for(int i = 0; i< subArray.length(); i++){
                JSONObject item = subArray.getJSONObject(i);
                Log.d("CHECK-> ", item.getString("name"));
                JSONArray majorArray = item.getJSONArray("major");
                if (majorArray.toString().contains(userMajor)){
                    Log.d("MAJOR-> ", userMajor);
                    JSONArray rooms = item.getJSONArray("room");
                    JSONArray days = item.getJSONArray("day");
                    JSONArray interval = item.getJSONArray("interval");
                    JSONArray locations = item.getJSONArray("location");
                    for(int j = 0; j < rooms.length(); j++){
                        Subject s = new Subject();
                        String [] times = interval.get(j).toString().split("-");
                        //set parameters
                        s.setName(item.getString("name"));
                        s.setProf(item.getString("professor"));
                        s.setDay(days.getString(j));
                        s.setStartTime(times[0]);
                        s.setFinishTime(times[1]);
                        s.setRoom(rooms.getString(j));
                        //location set
                        LatLng loc = new LatLng(locations.getJSONArray(j).getDouble(0),locations.getJSONArray(j).getDouble(1));
                        s.setLocation(loc);
                        subjectList.add(s);
                        subjectNames.add(s.getName());
                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(String s: subjectNames){
            ParsePush.subscribeInBackground(s.replace(" ","_"));
        }
    }

    public ArrayList<Subject> getTodaySubjects(int position) {
        ArrayList<Subject> todayList = new ArrayList<>();
        if (position < 5 && position >= 0){
            String today = days[position];
            for (int i = 0; i<subjectList.size(); i++){
                if(today.compareTo(subjectList.get(i).getDay())==0){
                    todayList.add(subjectList.get(i));
                }
            }
        }

        return  todayList;
    }

    public ArrayList<String> getSubjectNames() {
        return new ArrayList<>(subjectNames);
    }

    public void startChat(String email, int b) {
        some=b;
        fab.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ChatFragment hf = ChatFragment.newInstance(email);
        ft.replace(R.id.student_main, hf);
        ft.commit();
    }
}
