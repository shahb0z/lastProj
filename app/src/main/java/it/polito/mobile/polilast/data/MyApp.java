package it.polito.mobile.polilast.data;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by shahboz on 9/21/15.
 */
public class MyApp extends android.app.Application {

    //student type
    public static final String STUDENT_TYPE = "student";
    //company type
    public static final String COMPANY_TYPE = "company";
    //professor type
    public static final String PROFESSOR_TYPE = "professor";
    //type attribute of App_User class
    public static final String TYPE = "userType";
    //for user of notice and message
    public static final String USER = "user";
    public static final String SENDER = "user";
    public static final String RECEIVER = "user";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String ADDRESS = "address";
    public static final String QUALIFICATION = "qual";
    public static final String SALARY = "salary";
    public static final String SUBJECT_NAME = "name";
    public static final String DEPARTMENT = "department";
    public static final String MAJOR = "major";
    public static final String COURSE_ID = "id";
    public static final String PROFESSOR = "professor";
    public static final String ROOM = "room";
    public static final String START_TIME = "startTime";
    public static final String FINISH_TIME = "finishTime";
    public static final String LOCATION = "location";

    @Override
    public void onCreate() {
        super.onCreate();
        ParseUser.registerSubclass(MyUser.class);
        ParseUser.registerSubclass(Course.class);
        ParseUser.registerSubclass(Job.class);
        ParseUser.registerSubclass(Message.class);
        ParseUser.registerSubclass(Notice.class);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
