package it.polito.mobile.polilast.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.polito.mobile.polilast.MyMain;
import it.polito.mobile.polilast.R;
import it.polito.mobile.polilast.data.MyUser;

public class ProfessorHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Subject> subjectList;
    FloatingActionButton fab;
    private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_home);
        MyUser currentUser = (MyUser) ParseUser.getCurrentUser();
        initSchedule(currentUser.getSubject());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SendNotification hf = SendNotification.newInstance();
                ft.replace(R.id.professor_main, hf);
                ft.addToBackStack("null");
                ft.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.pro_nav_view);
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
        fragmentManager.beginTransaction().replace(R.id.professor_main, hf).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.professor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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

        if (id == R.id.pr_nav_home) {
            c = HomeFragment.class;
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.pr_nav_schedule) {
            c = ScheduleFragment.class;
            fab.setVisibility(View.GONE);
        } else if (id == R.id.pr_nav_logout) {
            logout();
        }
        if(c != null) {
            try {
                f = (Fragment)c.newInstance();
                fragmentManager.beginTransaction().replace(R.id.professor_main, f).commit();
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
        final ProgressDialog dialog = new ProgressDialog(ProfessorHomeActivity.this);
        dialog.setMessage(getString(R.string.progress_logout));
        dialog.show();
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(ProfessorHomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(ProfessorHomeActivity.this, MyMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ProfessorHomeActivity.this.finish();
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
    private void initSchedule(String userSubject) {

        try {
            subjectList = new ArrayList<>();
            JSONArray subArray = new JSONArray(loadJSONFromAsset());
            for(int i = 0; i< subArray.length(); i++){
                JSONObject item = subArray.getJSONObject(i);

                if (item.getString("name").compareTo(userSubject)==0){
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
                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
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
}
