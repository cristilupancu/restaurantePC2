package com.example.restaurantepc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {
    EditText editEmail2, editPassword2;
    Button  login2,button;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        editEmail2 = (EditText) findViewById(R.id.editTextTextEmailAddress);
       editPassword2 = (EditText) findViewById(R.id.editTextTextPassword);

        login2 = (Button) findViewById(R.id.login2);
        db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(email varchar(40),password varchar(16),restaurant boolean);");
        String Query222 = "Select * from rinfo where mail = 'restaurantert@gmail.com'";
        Cursor cur222 = db.rawQuery(Query222, null);
        if(cur222.getCount() <= 0){
            cur222.close();
            Log.d("ye4","corect");
        }
        else Log.d("ye4","incorect");

    }
    public void onClick(View view) {
        if (view == login2) {
            String a=editEmail2.getText().toString();
            String b=editPassword2.getText().toString();

            if(CheckIsDataAlreadyInDBorNot(a,b)) {
                Log.d("ye", "gasit");
                Boolean rest=CheckIfRestaurantOrUser(a,b); //1-> restaurant 0->user
                Log.d("ye2", rest.toString());
                if(rest==false) {
                    openMain(a);

                }
                else {
                    Boolean isComplete=CheckIfInfoSaved(a);
                    Log.d("ye5",isComplete.toString());
                    if(isComplete)
                        openRestaurantComplete(a);
                    else
                        openMainRestaurant(a);
                }
            }
            else
                Log.d("ye","nu a gasit");

        }

    }

    private Boolean CheckIfInfoSaved(String a) {
        SQLiteDatabase db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        String Query = "Select * from rinfo where mail = '" + a +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String fieldValue1,String fieldValue2) {
        SQLiteDatabase db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        String Query = "Select * from users where email = '" + fieldValue1 +"' and password ='"+ fieldValue2+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public boolean CheckIfRestaurantOrUser(String fieldValue1,String fieldValue2) {
        SQLiteDatabase db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        String Query = "Select restaurant from users where email = '" + fieldValue1 +"' and password ='"+ fieldValue2+"'";
        Cursor cursor = db.rawQuery(Query, null);
        Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        if (cursor.moveToFirst()) {

            Log.d("Cursor Object",String.valueOf(cursor.getString(0)));
            boolean rez=false;
            if(cursor.getString(0).equals("true"))
                rez=true;
            Log.d("ye",String.valueOf(rez));
            cursor.close();
            return rez;
        }
        cursor.close();
        return false;
    }
    public void openMain(String mail){
        Intent intent=new Intent(this,Mainn.class);
        intent.putExtra("mail",mail);
        startActivity(intent);
    }
    public void openMainRestaurant(String mail){
        Intent intent=new Intent(this,MainnRestaurant.class);
        intent.putExtra("mail",mail);
        startActivity(intent);
    }
    public void openRestaurantComplete(String mail){
        Intent intent=new Intent(this,RestaurantComplete.class);
        intent.putExtra("mail",mail);
        startActivity(intent);
    }
}