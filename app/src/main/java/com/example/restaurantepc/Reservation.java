package com.example.restaurantepc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reservation extends AppCompatActivity {
    Spinner sItems,sItems2;
    String mail,rname,rmail,productlist;
    ImageView img;
    SQLiteDatabase db;
    DBHelper dbHelper;
    int x;
    String y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Bundle extras = getIntent().getExtras();
        img=(ImageView)findViewById(R.id.imageView3);
        mail=extras.getString("mail");
        rname=extras.getString("restaurantname");
        db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);

        Log.d("112",rname);
        String Query = "Select mail from rinfo where name = '" + rname +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst())
            rmail=cursor.getString(0);
        cursor.close();
        Log.d("112",rmail);

        Query = "Select * from products where email = '" + rmail +"'";
        cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()){
            rmail=cursor.getString(0);
            x=cursor.getInt(1);
            y=cursor.getString(2);
            cursor.close();
        }

        Query = "Select prductlist from products where email = '" + rmail +"'";
        cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()){
            productlist=cursor.getString(0);
            cursor.close();
        }

        Log.d("112",String.valueOf(x));
//first spinner
        List<String> spinnerArray =  new ArrayList<String>();
        for(int i=1;i<=x;i++)
            spinnerArray.add(String.valueOf(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);


//second spinner

        String[] arrOfStr = productlist.split(";");
        Log.d("yye2",arrOfStr[0]);

        List<String> spinnerArray2 =  new ArrayList<String>(Arrays.asList(arrOfStr));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems2 = (Spinner) findViewById(R.id.spinner2);
        sItems2.setAdapter(adapter2);



        Boolean booll=loadImageFromDB(rmail);
    }
    private boolean loadImageFromDB(String mail) {
        try{
            Log.d("ye3","amajuns in loadimage");
            String Query = "Select image from rinfo where mail = '" + rmail +"'";
            Log.d("ye3",Query);
            Cursor cur = db.rawQuery(Query, null);
            byte[] blob;
            if (cur.moveToFirst()) {
                blob = cur.getBlob(0);
                Log.d("ye3",Query);
                cur.close();
                img.setImageBitmap(Utils.getImage(blob));
            }
            return true;
        }
        catch (Exception e){
            dbHelper.close();
            return false;
        }
    }
}