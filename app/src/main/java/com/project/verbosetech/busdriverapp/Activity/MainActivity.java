package com.project.verbosetech.busdriverapp.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.verbosetech.busdriverapp.Fragment.AbsentFragment;
import com.project.verbosetech.busdriverapp.Fragment.All_Fragement;
import com.project.verbosetech.busdriverapp.Fragment.HomeFragment;
import com.project.verbosetech.busdriverapp.Fragment.PickedFragment;
import com.project.verbosetech.busdriverapp.Models.Student;
import com.project.verbosetech.busdriverapp.Other.BusRecycleGrid;
import com.project.verbosetech.busdriverapp.Other.PrefManager;
import com.project.verbosetech.busdriverapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.project.verbosetech.busdriverapp.R.drawable.ic_filter_list_brown_24dp;
import static com.project.verbosetech.busdriverapp.R.drawable.ic_filter_list_white_24dp;

/**
 * Created by this pc on 23-05-17.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,HomeFragment.OnHeadlineSelectedListener,All_Fragement.OnHeadlineSelectedListener,PickedFragment.OnHeadlineSelectedListener,AbsentFragment.OnHeadlineSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, filterbutton;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private static ArrayList<Student> data;
    private static BusRecycleGrid adapter;
    private static BusRecycleGrid adapterToPass0;
    private static BusRecycleGrid adapterToPass1;
    private static BusRecycleGrid adapterToPass2;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://www.whatswithtech.com/wp-content/uploads/2015/09/google-material-design-wallpaper-full-hd.jpg";
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CONTACT = "contact";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Menu menu;
    PrefManager pref;
    FrameLayout frameLayout;
    LinearLayout linearLayout;

    String image_address = "http://media.gettyimages.com/photos/male-high-school-student-portrait-picture-id98680202?s=170667a";


    private TabLayout tabLayout;
    private ViewPager viewPager;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        pref = new PrefManager(getApplicationContext());

        frameLayout=(FrameLayout)findViewById(R.id.frame);
        linearLayout=(LinearLayout)findViewById(R.id.linear_layout);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tab_host);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(pref.getName());
        txtWebsite.setText("Driver");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(pref.getBusNo());
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_contact:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CONTACT;
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.filter) {
            if (pref.getFilter() == null)
                {
                    item.setIcon(ic_filter_list_brown_24dp);
                    frameLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    pref.setFilter("1");
                }

                else
                {
                    item.setIcon(ic_filter_list_white_24dp);
                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    pref.setFilter(null);
                }
        }
        return true;
        }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        data=new ArrayList<>();
        if(pref.getFilter()==null)
        {
            data.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
            data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));


        }
        else {


            if (viewPager.getCurrentItem() == 0) {
                data.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                adapter=getAllAdapter();
            } else if (viewPager.getCurrentItem() == 1) {
                data.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                adapter=getPickedAdapter();
            } else {

                data.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
                adapter=getAbsentAdapter();
            }

        }

        final List<Student> filteredModelList = filter(data, newText);
        adapter.setFilter(filteredModelList);
        return true;
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
    public void onArticleSelected(BusRecycleGrid a) {
        adapter=a;
    }

    @Override
    public void onAllSelected(BusRecycleGrid a) {
        adapterToPass0=a;
    }

    @Override
    public void onAbsentSelected(BusRecycleGrid a) {
        adapterToPass2=a;
    }

    @Override
    public void onPickedSelected(BusRecycleGrid a) {
        adapterToPass1=a;
    }

    public static BusRecycleGrid getAllAdapter() {
        return adapterToPass0;
    }
    public static BusRecycleGrid getPickedAdapter() {
        return adapterToPass1;
    }
    public static BusRecycleGrid getAbsentAdapter() {
        return adapterToPass2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pref.setFilter(null);
    }


    private void createViewPager(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new All_Fragement(), "All(24)");
        adapter.addFrag(new PickedFragment(), "Picked(10)");
        adapter.addFrag(new AbsentFragment(), "Absent(02)");
        viewPager.setAdapter(adapter);
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
}
