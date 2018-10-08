package softices.com.traineeapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import softices.com.traineeapp.Database.DbHelper;
import softices.com.traineeapp.R;

public class SignupActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 1;
    private EditText edtName;
    private Button btnSignup;
    private EditText edtEmail;
    private Button button;
    private EditText edtpassword;
    private EditText edtcnfmPassword;
    private EditText edtNumber;
    private RadioGroup rgGender;
    private DbHelper dbHelper;
    private Menu menu;
    private ImageView imgUser;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DbHelper(getApplicationContext());

        edtName = findViewById(R.id.edt_Name);
        btnSignup = findViewById(R.id.btn_Signup);
        edtEmail = findViewById(R.id.edt_Email);
        edtpassword = findViewById(R.id.edt_password);
        edtcnfmPassword = findViewById(R.id.edt_cnfmPassword);
        edtNumber = findViewById(R.id.edt_mobile);
        rgGender = findViewById(R.id.rdg_group);
        Button btnClick = (Button) findViewById(R.id.btn_Signup);
        btnSignup = findViewById(R.id.btn_Signup);
        imgUser = findViewById(R.id.viewImage);

        edtName.setText("maulik");
        edtEmail.setText("maulik@gmail.com");
        edtpassword.setText("maulik123");
        edtcnfmPassword.setText("maulik123");
        edtNumber.setText("1234567890");

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SignupActivity.this,
                        String.valueOf(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}))
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this,
                            String.valueOf(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}))) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(SignupActivity.this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CAMERA);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    selectImage();
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rgGender.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String gender = radioButton.getText().toString();

                Bitmap bitmap = ((BitmapDrawable) imgUser.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                String password = edtpassword.getText().toString();
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String cnfmpassword = edtcnfmPassword.getText().toString();
                String Mobile = edtNumber.getText().toString();
                if (!isEmailValid(edtEmail.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Enter valid email.", Toast.LENGTH_SHORT).show();
                } else if (Mobile.isEmpty()) {
                    edtNumber.setError("Enter a Mobile Number");
                } else if (password.length() < 8 || password.length() > 16) {
                    edtpassword.setError("Enter a 8-16 Character");
                } else if (cnfmpassword.length() < 8 || cnfmpassword.length() > 16) {
                    edtcnfmPassword.setError("Enter a 8-16 Character");
                } else if (!password.equals(cnfmpassword)) {
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                } else {
                    edtcnfmPassword.setError(null);
                    dbHelper.insertRecord(imageInByte, name, email, gender, Mobile, password);
                    Toast.makeText(SignupActivity.this, "Signup succesfully...", Toast.LENGTH_LONG).show();
                    savePreferences(name, email, true);

                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            }
        });


        EditText txtUserName = (EditText) findViewById(R.id.edt_Name);
        String strUserName = edtName.getText().toString();
        if (strUserName.trim().equals("")) {
            Toast.makeText(SignupActivity.this, "plz enter your name ", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public static boolean isEmailValid(String Email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = Email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    try{
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                    catch(Exception e){
                        Log.e("permission deny",e +"");
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);
                    imgUser.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath+"");
                imgUser.setImageBitmap(thumbnail);
            }
        }
    }

    private void savePreferences(String name, String email, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putBoolean("is_login", value);
        editor.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    selectImage();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }
}