package com.example.restaurantepc;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Mainn extends Activity implements OnClickListener{
    TextView hellotext;
    SQLiteDatabase db;
    LinearLayout col0;
    ArrayList<String> restaurantNames=new ArrayList<String>();
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
        hellotext=(TextView) findViewById(R.id.hellotext);
        col0 = (LinearLayout) findViewById(R.id.col0);
        Bundle extras = getIntent().getExtras();
        mail=extras.getString("mail");


        db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        getRestaurantNamesFromDb();
        for(int i = 0; i < restaurantNames.size(); i++)
            Log.d("numerestaurant",String.valueOf(restaurantNames.size()));
        for(int i = 0; i < restaurantNames.size(); i++)
        {
            try
            {
                Button newButton = new Button(this);
                newButton.setId(R.id.class.getField("b"+i).getInt(null));
                // Since API Level 17, you can also use View.generateViewId()
                newButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
                newButton.setText(restaurantNames.get(i));
                newButton.setOnClickListener(this);
                Log.d("numerestaurant",restaurantNames.get(i));
                col0.addView(newButton);
                newButton.setWidth(200);
            }
            catch (Exception e)
            {
                // Unknown button id !
                // We skip it
            }
        }

    }
    void getRestaurantNamesFromDb(){
        String Query = "Select name from rinfo ";
        Cursor cur = db.rawQuery(Query, null);
        while (cur.moveToNext()) {
            String x;
            x = cur.getString(0);
            restaurantNames.add(x);
        }
        cur.close();
    }

    @Override
    public void onClick(View view) {
        if(view==(Button)findViewById(R.id.b0) ){
            openReservation(restaurantNames.get(0),mail);
        }
        if(view==(Button)findViewById(R.id.b1) ){
            openReservation(restaurantNames.get(1),mail);
        }
        if(view==(Button)findViewById(R.id.b2) ){
            openReservation(restaurantNames.get(2),mail);
        }
        if(view==(Button)findViewById(R.id.b3) ){
            openReservation(restaurantNames.get(3),mail);
        }if(view==(Button)findViewById(R.id.b4) ){
            openReservation(restaurantNames.get(4),mail);
        }
        if(view==(Button)findViewById(R.id.b5) ){
            openReservation(restaurantNames.get(5),mail);
        }
        if(view==(Button)findViewById(R.id.b6) ){
            openReservation(restaurantNames.get(6),mail);
        }

    }
    void openReservation(String restaurantName,String mail){
        Intent intent=new Intent(this,Reservation.class);
        intent.putExtra("mail",mail);
        intent.putExtra("restaurantname",restaurantName);
        startActivity(intent);
    }
}