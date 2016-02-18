package it.polito.mobile.polilast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SignUpCallback;

import it.polito.mobile.polilast.data.MyUser;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Spinner userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);
        passwordAgainEditText = (EditText)findViewById(R.id.password_again_edit_text);
        //type
        userType=(Spinner)findViewById(R.id.spinner_user_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.users_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(this);
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

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();
        String type = userType.getSelectedItem().toString();
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
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.setType(type);

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
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
