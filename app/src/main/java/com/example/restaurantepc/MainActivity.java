package com.example.restaurantepc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends Activity implements OnClickListener {
    EditText editEmail, editPassword;
    Button register, login;
    SQLiteDatabase db;
    Switch sw;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        sw= (Switch) findViewById(R.id.switch1);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(email varchar(40),password varchar(16),restaurant boolean);");

    }
    public void onClick(View view) {
        if (view == register) {
            if (editEmail.getText().toString().trim().length() == 0 ||
                    editPassword.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter all data");
                return;
            }
            if(!CheckIsDataAlreadyInDBorNot(editEmail.getText().toString(),editPassword.getText().toString())) {
                Boolean switchState = sw.isChecked();
                db.execSQL("INSERT INTO users VALUES('" + editEmail.getText() + "','" + editPassword.getText() + "','" + switchState + "');");
                Log.d("rez", switchState.toString());
                showMessage("Success", "Account registered");
                openActivity2();
            }
            else  showMessage("Error", "Email unavailable");
            clearText();

        }
        if (view == login) {
            openActivity2();
            }
    }
    public boolean CheckIsDataAlreadyInDBorNot(String fieldValue1,String fieldValue2) {
        SQLiteDatabase db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        String Query = "Select * from users where email = '" + fieldValue1 +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public void openActivity2(){
        Intent intent=new Intent(MainActivity.this,Login.class);
        finish();
        startActivity(intent);
    }
    public void showMessage(String title, String message) {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        editPassword.setText("");
        editEmail.setText("");

    }
}