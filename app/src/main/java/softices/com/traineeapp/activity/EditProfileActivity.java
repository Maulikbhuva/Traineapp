package softices.com.traineeapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import softices.com.traineeapp.Database.DbHelper;
import softices.com.traineeapp.R;

public class EditProfileActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 1;
    private ImageView imageView;
    private EditText Email;
    private EditText name;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private EditText mobilenumber;
    private EditText password;
    private EditText confirmpassword;
    private Button save;
    private DbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDb = new DbHelper(this);
        imageView = findViewById(R.id.img_Eimage);
        Email = findViewById(R.id.edt_Eemail);
        name = findViewById(R.id.edt_Ename);
        gender = findViewById(R.id.rdg_Egroup);
        male = findViewById(R.id.rdb_Emale);
        female = findViewById(R.id.rdb_Efemle);
        mobilenumber = findViewById(R.id.edt_Emobile);
        password = findViewById(R.id.edt_Epassword);
        confirmpassword = findViewById(R.id.edt_Econfirmpassword);
        save = findViewById(R.id.btn_Save);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(EditProfileActivity.this,
                        String.valueOf(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}))
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this,
                            String.valueOf(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}))) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(EditProfileActivity.this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CAMERA);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    selectEImage();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {

                int radioButtonID = gender.getCheckedRadioButtonId();
                View radioButton = gender.findViewById(radioButtonID);
                int idx = gender.indexOfChild(radioButton);
                RadioButton r = (RadioButton) gender.getChildAt(idx);
                String gender = r.getText().toString();

                updatedata();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                String Password = password.getText().toString();
                String Name = name.getText().toString();
                String email = Email.getText().toString();
                String cnfmpassword = confirmpassword.getText().toString();
                String Mobile = mobilenumber.getText().toString();

                if (Mobile.isEmpty()) {
                    mobilenumber.setError("Enter a Mobile Number");
                } else if (Password.length() < 8 || Password.length() > 16) {
                    password.setError("Enter a 8-16 Character");
                } else if (cnfmpassword.length() < 8 || cnfmpassword.length() > 16) {
                    confirmpassword.setError("Enter a 8-16 Character");
                } else if (!Password.equals(cnfmpassword)) {
                    Toast.makeText(EditProfileActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                } else {
                    confirmpassword.setError(null);
                    myDb.updateRecord(imageInByte, Name, email, gender, Mobile, Password);
                    Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                }

            }


        });
        EditText txtUserName = (EditText) findViewById(R.id.edt_Name);
        String strUserName = name.getText().toString();
        if (strUserName.trim().equals("")) {
            Toast.makeText(EditProfileActivity.this, "plz enter your name ", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void selectEImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    } catch (Exception e) {
                        Log.e("permission deny", e + "");


                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });

        builder.show();

    }

    public void updatedata() {

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
      //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();


        int radioButtonID = gender.getCheckedRadioButtonId();
        View radioButton = gender.findViewById(radioButtonID);
        int idx = gender.indexOfChild(radioButton);
        RadioButton r = (RadioButton) gender.getChildAt(idx);


        boolean isUpdate = myDb.updateRecord(
                imageInByte,
                name.getText().toString(),
                Email.getText().toString(),
                r.getText().toString(),
                mobilenumber.getText().toString(),
                password.getText().toString());
        if (!isUpdate == true)
            Toast.makeText(EditProfileActivity.this, "Data Update", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(EditProfileActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    selectEImage();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }

        // other 'case' lines to check for other
        // permissions this app might request.
    }
}
