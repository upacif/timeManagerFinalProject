package com.example.ScheduleManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DialogActivity extends AppCompatActivity {

String owner;
Button timeTablee, announcements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Bundle bundle=getIntent().getExtras();

        owner=bundle.getString("stringToPass");
        timeTablee=findViewById(R.id.timeT);
        announcements=findViewById(R.id.messagB);

        openTimetable();
        openMessages();
    }
    public void openTimetable(){


        timeTablee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), timeTableList.class);
                intent.putExtra("stringToPass", owner);
                intent.putExtra("whatweneed", "timetableee");
                startActivity(intent);
            }
        });
    }


    public void  openMessages(){



        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), timeTableList.class);
                intent.putExtra("stringToPass", owner);
                intent.putExtra("whatweneed", "messagess");
                startActivity(intent);
            }
        });


    }



}
