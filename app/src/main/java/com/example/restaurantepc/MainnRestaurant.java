package com.example.restaurantepc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

public class MainnRestaurant extends AppCompatActivity implements View.OnClickListener {

    TextView hellotext;
    private static final int SELECT_PICTURE=100;
    private static final String TAG="MainnRestaurant";
    LinearLayout lnrLyt;
    ImageView imgView;
    FloatingActionButton btnSelectImage;
    Button next;
    DBHelper dbHelper;
    EditText name,number,adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn_restaurant);
        hellotext=(TextView) findViewById(R.id.hellotext);
        btnSelectImage=findViewById(R.id.btnSelectImage);
        imgView=findViewById(R.id.imageView2);
        dbHelper=new DBHelper(this);
        name=(EditText) findViewById(R.id.textView);
        number=(EditText) findViewById(R.id.textView2);
        adress=(EditText) findViewById(R.id.textView3);
        next=(Button) findViewById(R.id.finish);
        btnSelectImage.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        String mail=extras.getString("mail");
        String hello="Restaurant "+ mail+ "!";
        hellotext.setText(hello);


    }

    @Override
    public void onClick(View view) {
        if(view== btnSelectImage) {
            if (hasStoragePermission(MainnRestaurant.this)) {
                openImageChooser();
            } else {
                ActivityCompat.requestPermissions(((AppCompatActivity) MainnRestaurant.this),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        200);
            }
        }
        if(view== next){
            openMainnRestaurant2(getIntent().getExtras().getString("mail"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = getIntent().getExtras();
        String mail=extras.getString("mail");
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectImageUri = data.getData();
                if (selectImageUri != null) {
                    if (saveImageInDB(selectImageUri,mail,name.getText().toString(),number.getText().toString(),adress.getText().toString())) {
                        imgView.setImageURI(selectImageUri);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB(mail)) {
                                imgView.setVisibility(View.VISIBLE);
                                showMessage("Succes", "Data saved!");
                            }
                        }
                    }, 3500);
                }
            }
        }
    }

    private boolean loadImageFromDB(String mail) {
        try{
            dbHelper.open();
            byte[] bytes=dbHelper.retrieveImageFromDB(mail);
            dbHelper.close();
            imgView.setImageBitmap(Utils.getImage(bytes));
            return true;
        }
        catch (Exception e){
            dbHelper.close();
            return false;
        }
    }
    private boolean saveImageInDB(Uri selectImageUri,String a,String b,String c,String d) {
        try{
            dbHelper.open();
            InputStream iStream= getContentResolver().openInputStream(selectImageUri);
            byte[] inputData=Utils.getBytes(iStream);
            dbHelper.insertImage(inputData,a,b,c,d);
            dbHelper.close();
            return true;
        }
        catch (Exception e){
            dbHelper.close();
            return false;
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),SELECT_PICTURE);
    }

    private boolean hasStoragePermission(Context context) {
        int read= ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void openMainnRestaurant2(String mail){
        Intent intent=new Intent(this,MainnRestaurant2.class);
        intent.putExtra("mail",mail);
        startActivity(intent);
    }
}