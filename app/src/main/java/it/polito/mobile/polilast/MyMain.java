package it.polito.mobile.polilast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParsePush;
import com.parse.ParseUser;

import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.MyUser;
import it.polito.mobile.polilast.ui.CompanyHomeActivity;
import it.polito.mobile.polilast.ui.ProfessorHomeActivity;
import it.polito.mobile.polilast.ui.StudentHomeActivity;

public class MyMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUser currentUser = (MyUser) ParseUser.getCurrentUser();
        if (currentUser != null) {
            if(currentUser.getType().equals(MyApp.STUDENT_TYPE)){
                //goto student main page activity

                Intent intent = new Intent(this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else if(currentUser.getType().equals(MyApp.COMPANY_TYPE)){
                Intent intent = new Intent(this, CompanyHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                //goto advertiser main page
            }
            else if(currentUser.getType().equals(MyApp.PROFESSOR_TYPE)){
                Intent intent = new Intent(this, ProfessorHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }


        } else {
            // Start and intent for the logged out activity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
