package com.example.ScheduleManager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class timeTable extends AppCompatActivity {
    TableView tableView;

    static String[] spaceProbeHeaders={"Hour","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        final TableView<String[]> tableView = findViewById(R.id.tView);
        String[][] spaceProbes=new String[4][20];
        try {

    spaceProbes[0][0] = "1";spaceProbes[0][1] = "Pioneer";spaceProbes[0][2] = "Chemical";spaceProbes[0][3] = "Jupiter";
    spaceProbes[1][0] = "2";spaceProbes[1][1] = "Voyager";spaceProbes[1][2] = "Plasma";spaceProbes[1][3] = "Andromeda";
    spaceProbes[2][0] = "3";spaceProbes[2][1] = "Casini";spaceProbes[2][2] = "Solar";spaceProbes[2][3] = "Saturn";
    spaceProbes[3][0] = "4";spaceProbes[3][1] = "Spitzer";spaceProbes[3][2] = "Anti-Matter";spaceProbes[3][3] = "Anti-Matter";


        //tableView.setColumnCount(4);

            tableView.setColumnCount(8);
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
         tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));





    }
}
