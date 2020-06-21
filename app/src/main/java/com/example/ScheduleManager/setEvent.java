package com.example.ScheduleManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
private EditText action,owner;
private Button send,emergencyInfo;
TextView startT,endT;
TextView day;
String period;
    Spinner spinner;
    String passedUserName;

   boolean select;

FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
DatabaseReference ref=firebaseDatabase.getReference();
MainActivity mainActivity=new MainActivity();

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hour=String.valueOf(hourOfDay);
        String min=String.valueOf(minute);
        String h=hour+"h:"+min;
        if(select)
            startT.setText(h);
        else
            endT.setText(h);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_event);
        endT=findViewById(R.id.end);
        startT=findViewById(R.id.start);
        day=findViewById(R.id.day);
        action=findViewById(R.id.event);
        send=findViewById(R.id.send);

        emergencyInfo=findViewById(R.id.gotoEmergence);

        spinner = findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       passedUserName = getIntent().getStringExtra("passedUserName");
        setInitial();
        addEvent();
       setEnd();
       openEmergencyPanel();

    }



    public void openEmergencyPanel(){

        emergencyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),sendInfoClass.class);
                intent.putExtra("stringToPasss", passedUserName);
                startActivity(intent);
            }
        });
    }
    public void  setInitial(){


       startT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
select=true;
               DialogFragment timePickert=new TimePickerFragment();
               timePickert.show(getSupportFragmentManager(),"time picker");

           }
       });
    }


    public void  setEnd(){


        endT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select=false;
                DialogFragment timePickert=new TimePickerFragment();
                timePickert.show(getSupportFragmentManager(),"time picker");


            }
        });
    }





    private  void addEvent(){


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day Day=new day();
                Day.setAction(action.getText().toString());
                Day.setPeriod(startT.getText().toString()+"-"+endT.getText().toString());

                DatabaseReference ref = firebaseDatabase.getReference("staff").child(passedUserName).child("Table");
                try {
                    //ref.child(day.getText().toString()).push().setValue(Day);
                    String setDay=spinner.getSelectedItem().toString();
                    ref.child(setDay).child(setDay).setValue(Day);
                    mainActivity.displayMessage("Well done!!",getApplicationContext());
                }catch (Exception e){
                    mainActivity.displayMessage(e.toString(),getApplicationContext());

                }
//                String url="jdbc:mysql://192.168.137.1:3306/testdb";
//                String dbuser="titi";
//                String dbpassword="titi";
//                Connection con;
//                Statement st=null;
//
//                try{
//                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//
//                    Class.forName("com.mysql.jdbc.Driver");
//                  con= DriverManager.getConnection(url,dbuser,dbpassword);
//                    st = con.createStatement();
//
//                }catch (Exception e){
//                    mainActivity.displayMessage(e.toString(),getApplicationContext());
//                }
//
//
//                String sql="insert into users(user_name,passwordd) values('Pacifique','12345')";
//try {
//    st.executeQuery(sql);
//}catch(Exception e){
//    mainActivity.displayMessage(e.toString(),getApplicationContext());
//}

            }
        });
    }


}
