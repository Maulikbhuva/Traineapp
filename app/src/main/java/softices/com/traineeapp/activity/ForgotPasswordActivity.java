package softices.com.traineeapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import softices.com.traineeapp.Database.DataModel;
import softices.com.traineeapp.Database.DbHelper;
import softices.com.traineeapp.Model.UserModel;
import softices.com.traineeapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edtMail;
    private Button btnFetch;
    private String mail;
    private DbHelper dbHelper;
    private UserModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
    private void init() {

        dbHelper = new DbHelper(this);
        model = new UserModel();

        edtMail = (EditText) findViewById(R.id.edt_mail);

        btnFetch = (Button) findViewById(R.id.btn_fetch);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = edtMail.getText().toString();
                model = (UserModel) dbHelper.getUserData(mail);
                Toast.makeText(ForgotPasswordActivity.this, "Password is: " + model.getPassword(),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    /** \
     * This method functions when clicked on toolbar items
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}

