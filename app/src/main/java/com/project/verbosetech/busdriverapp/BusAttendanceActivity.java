package com.project.verbosetech.busdriverapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.Student;
import Other.BusRecycleGrid;

/**
 * Created by this pc on 22-05-17.
 */

public class BusAttendanceActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    BusRecycleGrid adapter;
    RecyclerView recyclerView;
    List<Student> studentList;
    String image_address = "http://media.gettyimages.com/photos/male-high-school-student-portrait-picture-id98680202?s=170667a";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_student_status);
        recyclerView=(RecyclerView)findViewById(R.id.bus_attendance_grid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getInformation();
    }

    public void getInformation()
    {
        studentList=new ArrayList<>();
        studentList.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
        studentList.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
        adapter = new BusRecycleGrid(getApplicationContext(), studentList, new BusRecycleGrid.VenueAdapterClickCallbacks() {

            @Override
            public void onCardClick(String p) {

                Toast.makeText(getApplicationContext(),p+"", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }


}
