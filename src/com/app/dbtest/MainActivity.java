package com.app.dbtest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btFillDb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbFiller.fillDb(MainActivity.this, ((CheckBox) findViewById(R.id.cbClear)).isChecked());
            }
        });

        ((Button) findViewById(R.id.btShowDbContent)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dbShowContentIntent = new Intent(MainActivity.this, DbContentActivity.class);
                startActivity(dbShowContentIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    
}
