package com.example.restaurantepc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainnRestaurant2 extends AppCompatActivity implements View.OnClickListener{

    TextView message;
    EditText nrMese,product;
    Button addp,finish;
    String listaProduse="",mail;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn_restaurant2);
        message=(TextView) findViewById(R.id.hellotext);
        nrMese=(EditText) findViewById(R.id.textView);
        product=(EditText) findViewById(R.id.textView2);
        addp=(Button) findViewById(R.id.addp);
        finish=(Button) findViewById(R.id.finish);

        Bundle extras = getIntent().getExtras();
        mail=extras.getString("mail");
        message.setText(new String("Restaurant "+ mail+ "!"));

        db = openOrCreateDatabase("PCrestauranteDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS products(email varchar(40),nroftables integer(3),prductlist varchar(200));");
    }
    public void onClick(View view){
        if(view == addp){
            String newproduct = product.getText().toString();
            listaProduse = listaProduse + newproduct;
        }
        if(view == finish){
            int nroftables=Integer.parseInt(nrMese.getText().toString());
            db.execSQL("INSERT INTO products VALUES('"+mail+"','"+nroftables+"','"+listaProduse+"')");
            openRestaurantComplete(mail);
        }
    }
    public void openRestaurantComplete(String mail){
        Intent intent=new Intent(this,RestaurantComplete.class);
        intent.putExtra("mail",mail);
        startActivity(intent);
    }
}