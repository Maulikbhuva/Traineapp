package softices.com.traineeapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import softices.com.traineeapp.Database.DbHelper;
import softices.com.traineeapp.R;

public class ProfileActivity extends AppCompatActivity {
    private Button btnedit;
    private ImageView imageView;
    private String gender ;
    private TextView txtname;
    private RadioButton male;
    private RadioButton female;
    private TextView txtmobilenumber;
    private Button Logout;
    private Handler handler;
    private DbHelper dbHelper;
    public Cursor cursor;
    public Bitmap bitmap;
    private String name, email1, mobilenumber, g = "", password;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtname = findViewById(R.id.edt_Nm);
        Logout = findViewById(R.id.btn_logout);
        txtmobilenumber = findViewById(R.id.edt_mbl);
        imageView = findViewById(R.id.img_viewImage);
        male = findViewById(R.id.rdb_ML);
        female = findViewById(R.id.rdb_FML);
//        getValues();
        btnedit = findViewById(R.id.btn_Edit);
        dbHelper = new DbHelper(this);
        btnedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Intent androidsolved_intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(androidsolved_intent);
                /**/
            }
        });

        Logout = (Button) findViewById(R.id.btn_logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent in = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(in);
                finishAffinity();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email", "");
        cursor = dbHelper.getUserData(email);
        if (cursor.moveToFirst()) {
            do {

                name = cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME));
                email1 = cursor.getString(cursor.getColumnIndex(DbHelper.USER_EMAIL));
                g = cursor.getString(cursor.getColumnIndex(DbHelper.USER_GENDER));
                mobilenumber = cursor.getString(cursor.getColumnIndex(DbHelper.USER_MOBILENUMBER));
                password = cursor.getString(cursor.getColumnIndex(DbHelper.USER_PASSWORD));
                byte[] decodedString = cursor.getBlob(cursor.getColumnIndex(DbHelper.USER_IMAGE));
                bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            } while (cursor.moveToNext());
        }
        cursor.close();
        imageView.setImageBitmap(bitmap);
        txtname.setText(name);
        txtmobilenumber.setText(mobilenumber);
        boolean correct = "male".equals(gender);
            if (correct) {
                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
    }
//        public void getValues () {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            String image = sharedPreferences.getString("image", "");
//            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            String name = sharedPreferences.getString("name", "");
//            String gender = sharedPreferences.getString("gender", "");
//            String mobilenumber = sharedPreferences.getString("mobilenumber", "");
//            boolean correct = "male".equals(gender);
//            if (correct) {
//                male.setChecked(true);
//            } else {
//                female.setChecked(true);
//            }
//            imageView.setImageBitmap(decodedByte);
//            txtname.setText(name);
//            txtmobilenumber.setText(mobilenumber);
//        }


}