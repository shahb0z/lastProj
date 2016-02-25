package it.polito.mobile.polilast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import it.polito.mobile.polilast.data.MyApp;
import it.polito.mobile.polilast.data.MyUser;
import it.polito.mobile.polilast.ui.NothingSelectedSpinnerAdapter;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Spinner userType;
    private Spinner course;
    private Spinner subject;
    NothingSelectedSpinnerAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);
        passwordAgainEditText = (EditText)findViewById(R.id.password_again_edit_text);
        nameEditText = (EditText)findViewById(R.id.edit_text_name);
        //type

        userType=(Spinner)findViewById(R.id.spinner_user_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.users_array, android.R.layout.simple_list_item_1);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.contact_spinner_row_nothing_selected,
                this
        ));
        userType.setOnItemSelectedListener(this);
        //course automcomplete

        course = (Spinner)findViewById(R.id.spinner_course);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_array, android.R.layout.simple_list_item_1);

       // courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        course.setAdapter(new NothingSelectedSpinnerAdapter(
                courseAdapter,
                R.layout.contact_spinner_row_nothing_selected2,
                this
        ));
        //subject
        subject = (Spinner)findViewById(R.id.spinner_subject);
        ArrayAdapter<CharSequence> subjectAdapater = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_list_item_1);

        // courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject.setAdapter(new NothingSelectedSpinnerAdapter(
                subjectAdapater,
                R.layout.contact_spinner_row_nothing_selected3,
                this
        ));
        //submit button
        Button mActionButton = (Button) findViewById(R.id.signup_confirm_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
            }
        });
    }
    /**
     * The method for handling sign up validation and sign up with <a href="http://parse.com">parse.com</a> .
     */
    private void signup() {
        String name = nameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();
        String courseString=null;
        String type = null;
        if(course.getSelectedItem()!=null)
            courseString = course.getSelectedItem().toString();
        if(userType.getSelectedItem()!=null)
            type = userType.getSelectedItem().toString();
        String subjectString=null;
        if(subject.getSelectedItem()!=null)
            subjectString = subject.getSelectedItem().toString();
        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));

        if(username.length() == 0){
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }

        if(!isEmailValid(username)){
            if(validationError){
                validationErrorMessage.append(getString(R.string.error_email_not_valid));
            }else{
                validationError = true;
                validationErrorMessage.append(getString(R.string.error_email_not_valid));
            }
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }else{
                validationError = true;
                validationErrorMessage.append(getString(R.string.error_blank_password));
            }

        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }else{
                validationError = true;
                validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
            }

        }
        if(type == null){
            if(validationError){
                validationErrorMessage.append(getString(R.string.error_user_type));
            }else{
                validationError=true;
                validationErrorMessage.append(getString(R.string.error_user_type));
            }
        }
        if(type!=null && type.equals(MyApp.STUDENT_TYPE)&&courseString == null){
            if(validationError){
                validationErrorMessage.append(getString(R.string.error_course));
            }else{
                validationError=true;
                validationErrorMessage.append(getString(R.string.error_course));
            }
        }
        if(type!=null && type.equals(MyApp.PROFESSOR_TYPE)&&subjectString == null){
            if(validationError){
                validationErrorMessage.append(getString(R.string.error_subject));
            }else{
                validationError=true;
                validationErrorMessage.append(getString(R.string.error_subject));
            }
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(SignupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();
        MyUser user = new MyUser();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.setType(type);
        if (type.equals(MyApp.STUDENT_TYPE)){
            user.setMajor(courseString);
        }else if(type.equals(MyApp.PROFESSOR_TYPE)){
            user.setSubject(subjectString);
        }


        // Set up a new Parse user

        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(SignupActivity.this, MyMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getSelectedItem()==null){
            course.setVisibility(LinearLayout.GONE);
            subject.setVisibility(LinearLayout.GONE);
        }else{
            String type = parent.getSelectedItem().toString();
            if(type.equals(MyApp.COMPANY_TYPE)){
                course.setVisibility(LinearLayout.GONE);
                subject.setVisibility(LinearLayout.GONE);
            }
            else if(type.equals(MyApp.PROFESSOR_TYPE)){

                course.setVisibility(LinearLayout.GONE);
                subject.setVisibility(LinearLayout.VISIBLE);

            }else {
                subject.setVisibility(LinearLayout.GONE);
                course.setVisibility(LinearLayout.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
