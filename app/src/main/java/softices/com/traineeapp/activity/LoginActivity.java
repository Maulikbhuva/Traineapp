package softices.com.traineeapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import softices.com.traineeapp.Database.DbHelper;
import softices.com.traineeapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private DbHelper dbHelper;
    private static final int MAIN_ACTIVITY_REQUEST_CODE = 1;
    private Button btnLogin;
    private EditText edtPassword;
    private EditText edtEmail;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        DebugUtils.backupDatabase(this, DbHelper.DATABASE_NAME);

        TextView txtCreate = findViewById(R.id.txt_creat);
        edtPassword = findViewById(R.id.edt_Password);
        edtEmail = findViewById(R.id.edt_email);
        btnLogin = findViewById(R.id.btn_login);
        edtEmail.setText("maulik@gmail.com");
        edtPassword.setText("maulik123");

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPassword.getText().toString();
                String email = edtEmail.getText().toString();
                if (!isEmailValid(email)) {
                    Toast.makeText(LoginActivity.this, "Enter valid email.", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8 || password.length() > 16) {
                    edtPassword.setError("Enter a 8-16 Character");
                } else {
                    if (dbHelper.isUserLogin(email, password)) {
                        savePreferences(email, true);
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter a valid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtCreate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Intent androidsolved_intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(androidsolved_intent);
                /**/
            }
        });
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void savePreferences(String email, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("is_login", value);
        editor.commit();
    }
}