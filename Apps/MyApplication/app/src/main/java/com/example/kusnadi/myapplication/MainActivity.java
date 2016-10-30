package com.example.kusnadi.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.DialogPreference;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dr;
    private Toolbar tl;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        tl = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tl);
        ProsesNavigasi();
    }

    public void ProsesNavigasi(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.home) {
                    dr.closeDrawers();
                    return true;
                } else if (id == R.id.settings) {
                    Intent intentPengaturan = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intentPengaturan);
                    dr.closeDrawers();
                    return true;
                } else if (id == R.id.trash){
                    dr.closeDrawers();
                    return true;
                } else if (id == R.id.mlogout)
                    logout();
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        final ImageView dp = (ImageView) header.findViewById(R.id.dp);
        TextView tv_name = (TextView) header.findViewById(R.id.tv_name);
        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);

        Intent receiveData = getIntent();
        final String rd_name, rd_email, rd_url;
        rd_name = receiveData.getStringExtra("p_name");
        rd_email = receiveData.getStringExtra("p_email");
        rd_url = receiveData.getStringExtra("p_url");

        tv_name.setText(rd_name);
        tv_email.setText(rd_email);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(rd_url);
                    InputStream is = url.openConnection().getInputStream();
                    final Bitmap bmp = BitmapFactory.decodeStream(is);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dp.setImageBitmap(bmp);
                        }
                    });
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        dr = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dr, tl, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }
            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        dr.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            Intent intentSetting = new Intent("com.example.kusnadi.myapplication.SettingActivity");
            startActivity(intentSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda yakin Ingin Logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1){
                        session.setLoggedin(false);
                        finish();
                        Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intentLogout);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1){
                        dr.closeDrawers();
                    }
                });
        //Showing the Alert Dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
