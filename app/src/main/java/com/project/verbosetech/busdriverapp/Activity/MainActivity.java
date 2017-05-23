package com.project.verbosetech.busdriverapp.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.verbosetech.busdriverapp.Fragment.AbsentFragment;
import com.project.verbosetech.busdriverapp.Fragment.All_Fragement;
import com.project.verbosetech.busdriverapp.Fragment.HomeFragment;
import com.project.verbosetech.busdriverapp.Fragment.PickedFragment;
import com.project.verbosetech.busdriverapp.Fragment.TabFragment;
import com.project.verbosetech.busdriverapp.Models.Student;
import com.project.verbosetech.busdriverapp.Other.BusRecycleGrid;
import com.project.verbosetech.busdriverapp.Other.PrefManager;
import com.project.verbosetech.busdriverapp.R;

import java.util.ArrayList;
import java.util.List;

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

    String image_address = "http://media.gettyimages.com/photos/male-high-school-student-portrait-picture-id98680202?s=170667a";

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


        data = new ArrayList<>();
        data.add(new Student("Abhimanyu Khurana ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));
        data.add(new Student("Sachin Parekh ", "Class 10th B Division", "Near Sahar Circle, Old Street, New Delhi", "Rajesh Roy", "Jyoti Roy", "+91 903 335 6708", "+91 987 654 3210", image_address));

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
        if(pref.getFilter()!=null){
            MenuItem item= menu.findItem(R.id.search);
            item.setVisible(false);
            MenuItem item2= menu.findItem(R.id.bfilter);
            item2.setVisible(false);
        }
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.filter) {
            if (pref.getFilter() == null) {
                Fragment fragment = new TabFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Inflate transitions to apply
                    Transition changeTransform = TransitionInflater.from(this).
                            inflateTransition(R.transition.change_image_transform);
                    Transition explodeTransform = TransitionInflater.from(this).
                            inflateTransition(android.R.transition.explode);

                    // Setup exit transition on first fragment
                    getHomeFragment().setSharedElementReturnTransition(changeTransform);
                    getHomeFragment().setExitTransition(explodeTransform);

                    // Setup enter transition on second fragment
                    fragment.setSharedElementEnterTransition(changeTransform);
                    fragment.setEnterTransition(explodeTransform);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_host);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment).addSharedElement(tabLayout, tabLayout.getTransitionName());
                    fragmentTransaction.commitAllowingStateLoss();
                }

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
    public void onAbsentSelected(BusRecycleGrid a) {
        adapterToPass2=a;
    }

    @Override
    public void onPickedSelected(BusRecycleGrid a) {
        adapterToPass1=a;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pref.setFilter(null);
    }
}
