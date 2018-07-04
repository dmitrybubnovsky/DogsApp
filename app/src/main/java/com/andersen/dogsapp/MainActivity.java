package com.andersen.dogsapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    private Button btnActTwo;
    private Button btnActThree;
    private Button btnActFour;
    private Button btnActFive;
    private Button btnActSix;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);

        btnActThree = (Button) findViewById(R.id.btnActThree);
        btnActThree.setOnClickListener(this);

        btnActFour = (Button) findViewById(R.id.btnActFour);
        btnActFour.setOnClickListener(this);

        btnActFive = (Button) findViewById(R.id.btnActFive);
        btnActFive.setOnClickListener(this);

        btnActSix = (Button) findViewById(R.id.btnActSix);
        btnActSix.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnActTwo:
                intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.btnActThree:
                startActivity(new Intent(this, ActivityThree.class));
                break;
            case R.id.btnActFour:
                startActivity(new Intent(this, ActTextViewInFrameLayout.class));
                break;
            case R.id.btnActFive:
                startActivity(new Intent(this, ActTextViewInRelativeLayout.class));
                break;
            case R.id.btnActSix:
                startActivity(new Intent(this, ActTextViewInConstLayout.class));
                break;
            default:
                break;
        }
    }
}