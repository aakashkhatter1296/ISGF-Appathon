package com.nsit.hack.energy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.fragments.Devices;
import com.nsit.hack.energy.fragments.Geofencing;
import com.nsit.hack.energy.fragments.Hotspot;
import com.nsit.hack.energy.fragments.MainAccess;
import com.nsit.hack.energy.fragments.PhysicalConditions;
import com.nsit.hack.energy.fragments.PowerMonitor;
import com.nsit.hack.energy.fragments.Root;
import com.nsit.hack.energy.fragments.Scheduler;
import com.nsit.hack.energy.fragments.Settings;
import com.nsit.hack.energy.fragments.Weather;
import com.pushbots.push.Pushbots;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Pushbots.sharedInstance().init(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new Root()).commit();
        setTitle("Home");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment newFragment = null;

        if (id == R.id.nav_home) {
            setTitle("Home");
            newFragment = new Root();
        } else if (id == R.id.nav_devices) {
            setTitle("Devices");
            newFragment = new Devices();
        } else if (id == R.id.nav_scheduler) {
            setTitle("Scheduler");
            newFragment = new Scheduler();
        } else if (id == R.id.nav_power) {
            setTitle("Power Monitor");
            newFragment = new PowerMonitor();
        } else if (id == R.id.nav_geofencing) {
            setTitle("Geofencing");
            newFragment = new Geofencing();
//            setTitle("Main Access");
//            newFragment = new MainAccess();
        } else if (id == R.id.nav_weather) {
            setTitle("Physical Conditions");
            newFragment = new PhysicalConditions();
        } else if (id == R.id.nav_hotspot) {
            setTitle("Hotspot");
            newFragment = new Hotspot();
        }else if (id == R.id.nav_settings) {
            setTitle("Settings");
            newFragment = new Settings();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, newFragment);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
