package it.polito.mobile.polilast.data;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import it.polito.mobile.polilast.ui.Subject;

/**
 * Created by shahboz on 9/21/15.
 */
public class MyApp extends android.app.Application {

    //student type
    public static final String STUDENT_TYPE = "Student";
    //company type
    public static final String COMPANY_TYPE = "Company";
    //professor type
    public static final String PROFESSOR_TYPE = "Professor";
    //type attribute of App_User class
    public static final String TYPE = "userType";
    //for user of notice and message
    public static final String USER = "user";
    public static final String SENDER = "sender";
    public static final String RECEIVER = "receiver";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String ADDRESS = "address";
    public static final String QUALIFICATION = "qual";
    public static final String SALARY = "salary";
    public static final String SUBJECT_NAME = "subject";
    public static final String MAJOR = "major";
    public static final String GROUP = "group";
    public static final String SINGLE = "single";

    public static final String FULLNAME = "fullname";


    @Override
    public void onCreate() {
        super.onCreate();
        ParseUser.registerSubclass(MyUser.class);
        ParseUser.registerSubclass(Job.class);
        ParseUser.registerSubclass(Chat.class);
        ParseUser.registerSubclass(Message.class);
        ParseUser.registerSubclass(Notice.class);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
