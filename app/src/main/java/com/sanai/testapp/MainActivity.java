package com.sanai.testapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Calendar;

import Teacher.Main3Activity;
import jango.Django;
import root.ChangePassword;
import root.LoginActivity;
import root.converter;


public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;
    FragmentTransaction fragmentTransaction;
    Calendar cal = Calendar.getInstance();
    int [] today_persion = new int[3];
    static ProgressBar Bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bar =findViewById(R.id.bar);

        //**************************************************************************
        fragmentTransaction   = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flcontent,new defaultFragment());
        fragmentTransaction.commit();
        //**************************************************************************

        drawerLayout =  findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nvDrawer = findViewById(R.id.nv);
        setupDrawerContent(nvDrawer);
        //*******************************************************

        //****************************date*********************************


        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        today_persion = converter.gregorian_to_jalali(year,month,day);


        //**************************************************************************
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Django.getUserListFromJango();
                Django.getStudentListFromJango();
                Django.getTeachersListFromJango();
                Django.getDateRangeTimeList();
                Django.getTermList();
                Django.getMajorList();
                Django.getMTList();
                Django.getTMTList();
            }

        });

        /////////////////////////////////////
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle("");
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuToUse = R.menu.my_right_side_menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToUse, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == R.id.btnMyMenu) {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return false;

    }


    public  void  selectItemDrawer(MenuItem menuItem){ //action of clicking in menu item
        fragmentTransaction   = getSupportFragmentManager().beginTransaction();
        switch(menuItem.getItemId()) {
            case R.id.addStudent:
                fragmentTransaction.replace(R.id.flcontent,new AddStudent());
                break;
            case R.id.addTeacher: //done
                fragmentTransaction.replace(R.id.flcontent,new AddTeacher());
                break;
            case R.id.addNewTerm: //done
                fragmentTransaction.replace(R.id.flcontent,new AddNewTerm());
                break;
            case R.id.selectTerm :
                fragmentTransaction.replace(R.id.flcontent,new TermsList());
                break;
            case R.id.setting: //done
                fragmentTransaction.replace(R.id.flcontent,new ChangePassword());
                break;
            case R.id.addNewMajor: //done
                fragmentTransaction.replace(R.id.flcontent,new AddMajorFragment());
                break;
            case R.id.addDateRange: //done
                fragmentTransaction.replace(R.id.flcontent,new AddDateRangeFragment());
                break;
            case R.id.addTmt: //
                fragmentTransaction.replace(R.id.flcontent,new AddTMT());
                break;
            case R.id.logout: //
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();                break;
            default:
                fragmentTransaction.replace(R.id.flcontent,new defaultFragment());

        }
        fragmentTransaction.commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle("کارشناس بخش");
        // Close the navigation drawer
        drawerLayout.closeDrawers();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });
    }





}
