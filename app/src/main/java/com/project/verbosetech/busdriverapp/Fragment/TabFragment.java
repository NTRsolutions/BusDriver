package com.project.verbosetech.busdriverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.project.verbosetech.busdriverapp.Activity.MainActivity;
import com.project.verbosetech.busdriverapp.Models.Student;
import com.project.verbosetech.busdriverapp.Other.BusRecycleGrid;
import com.project.verbosetech.busdriverapp.Other.PrefManager;
import com.project.verbosetech.busdriverapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by this pc on 23-05-17.
 */

public class TabFragment extends Fragment implements SearchView.OnQueryTextListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    PrefManager pref;

    private static ArrayList<Student> data;
    private static BusRecycleGrid adapter;
    String image_address = "http://media.gettyimages.com/photos/male-high-school-student-portrait-picture-id98680202?s=170667a";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab_layout,container,false);
        setHasOptionsMenu(true);
        pref=new PrefManager(getActivity());

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout)view.findViewById(R.id.tab_host);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
        return view;
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new All_Fragement(), "All(24)");
        adapter.addFrag(new PickedFragment(), "Picked(10)");
        adapter.addFrag(new AbsentFragment(), "Absent(02)");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        data=new ArrayList<>();
        if(viewPager.getCurrentItem()==0)
        {
            data.add(new Student("Abhimanyu Khurana ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            adapter= MainActivity.getAllAdapter();
        }
        else if(viewPager.getCurrentItem()==1)
        {
            data.add(new Student("Abhimanyu Khurana ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            adapter=MainActivity.getPickedAdapter();
        }
        else {

            data.add(new Student("Abhimanyu Khurana ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            data.add(new Student("Sachin Parekh ","Class 10th B Division","Near Sahar Circle, Old Street, New Delhi","Rajesh Roy","Jyoti Roy","+91 903 335 6708","+91 987 654 3210",image_address));
            adapter=MainActivity.getAbsentAdapter();
        }


        final List<Student> filteredModelList = filter(data, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void createTabIcons() {

        tabLayout.getTabAt(0).setText("All(24)");

        tabLayout.getTabAt(1).setText("Picked(10)");

        tabLayout.getTabAt(2).setText("Absent(02)");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item= menu.findItem(R.id.action_search);
        item.setVisible(false);
        MenuItem item2= menu.findItem(R.id.filter);
        item2.setVisible(false);
        inflater.inflate(R.menu.menu_fragment,menu);
//        pref.setFilter("1");
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private List<Student> filter(List<Student> models, String query) {
        query = query.toLowerCase();

        final List<Student> filteredModelList = new ArrayList<>();
        for (Student model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bfilter:
                Fragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                return false;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
