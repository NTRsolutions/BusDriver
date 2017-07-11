package com.project.verbosetech.busdriverapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.project.verbosetech.busdriverapp.Models.Student;
import com.project.verbosetech.busdriverapp.Other.BusRecycleGrid;
import com.project.verbosetech.busdriverapp.Other.PrefManager;
import com.project.verbosetech.busdriverapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by this pc on 23-05-17.
 */

public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    BusRecycleGrid adapter;
    RecyclerView recyclerView;
    List<Student> studentList;
    String image_address = "http://media.gettyimages.com/photos/male-high-school-student-portrait-picture-id98680202?s=170667a";
    Button picked;
    PrefManager pref;
    HomeFragment.OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(BusRecycleGrid a);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bus_student_status, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.bus_attendance_grid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        pref = new PrefManager(getActivity());
        getInformation();
        getAdapter();
        return view;
    }

    public void getInformation() {
        studentList = new ArrayList<>();

        //filling the cards with dummy data
        studentList.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        studentList.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));

        adapter = new BusRecycleGrid(getActivity(), studentList, new BusRecycleGrid.VenueAdapterClickCallbacks() {

            @Override
            public void onCardClick(String p) {

                Toast.makeText(getActivity(), p + "", Toast.LENGTH_LONG).show();
            }
        });


        recyclerView.setAdapter(adapter);
    }

    public void getAdapter() {

        mCallback.onArticleSelected(adapter);
    }
}
