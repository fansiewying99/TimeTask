package com.g3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TaskAdapter taskAdapter;
    TagRecViewAdapter tagAdapter;
    static SettingsDB settingsDB;
    private static Context context;

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i("countdownservice in main", "Countdown seconds remaining: " +  millisUntilFinished / 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context=getApplicationContext();

        settingsDB = new SettingsDB(this);
        //this.deleteDatabase("settings.db");
        taskAdapter=new TaskAdapter(settingsDB);

        try {
            settingsDB.getSettings(1);
        } catch(android.database.CursorIndexOutOfBoundsException ex){
            settingsDB.initSettings();
        }
        switch(settingsDB.getSettings(1).getDarkMode()){
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        switch(settingsDB.getSettings(1).getTaskNotify()) {
            case 1:
                startService(new Intent(this, TasksCountdownService.class));
                Log.i("countdownservice in main", "Started service");
                break;
            case 0:
                stopService(new Intent(this, TasksCountdownService.class));
                Log.i("countdownservice in main", "Stopped service");
                break;

        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = host.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        DrawerLayout drawerLayout = findViewById(R.id.DLMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setupNavMenu(navController);

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter());
        Log.i("countdownservice in main", "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i("countdownservice in main", "Unregistered broacast receiver");
    }
    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        //stopService(new Intent(this, TasksCountdownService.class));
        //Log.i("countdownservice in main", "Stopped service");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    private void setupNavMenu(NavController navController) {
        NavigationView sideNav = findViewById(R.id.sideNav);
        NavigationUI.setupWithNavController(sideNav, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            Navigation.findNavController(this, R.id.NHFMain).navigate(item.getItemId());
            return true;
        } catch(Exception ex) {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.NHFMain).navigateUp();
    }

    public TaskAdapter getTaskAdapter(){
        return taskAdapter;
    }
    public TagRecViewAdapter getTagAdapter(){
        return tagAdapter;
    }
    public static SettingsDB getSettingsDB(){
        return settingsDB;
    }
    //static Context context=MainActivity.context;
    public static Context getAppContext(){return MainActivity.context;}
    public void restartTasksCountdownService(){
        stopService(new Intent(this, TasksCountdownService.class));
        startService(new Intent(this, TasksCountdownService.class));
    }

    //test-timetable
//    private TimetableView timetable;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        init();
//    }
//
//    private void init(){
//        this.context = this;
//        addBtn = findViewById(R.id.add_btn);
//        clearBtn = findViewById(R.id.clear_btn);
//        saveBtn = findViewById(R.id.save_btn);
//        loadBtn = findViewById(R.id.load_btn);
//
//        timetable = findViewById(R.id.timetable);
//        timetable.setHeaderHighlight(2);
//        initView();
//    }
//
//    private void initView(){
//        addBtn.setOnClickListener(this);
//        clearBtn.setOnClickListener(this);
//        saveBtn.setOnClickListener(this);
//        loadBtn.setOnClickListener(this);
//
//        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
//            @Override
//            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
//                Intent i = new Intent(context, EditActivity.class);
//                i.putExtra("mode",REQUEST_EDIT);
//                i.putExtra("idx", idx);
//                i.putExtra("schedules", schedules);
//                startActivityForResult(i,REQUEST_EDIT);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.add_btn:
//                Intent i = new Intent(this,EditActivity.class);
//                i.putExtra("mode",REQUEST_ADD);
//                startActivityForResult(i,REQUEST_ADD);
//                break;
//            case R.id.clear_btn:
//                timetable.removeAll();
//                break;
//            case R.id.save_btn:
//                saveByPreference(timetable.createSaveData());
//                break;
//            case R.id.load_btn:
//                loadSavedData();
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode){
//            case REQUEST_ADD:
//                if(resultCode == EditActivity.RESULT_OK_ADD){
//                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
//                    timetable.add(item);
//                }
//                break;
//            case REQUEST_EDIT:
//                /** Edit -> Submit */
//                if(resultCode == EditActivity.RESULT_OK_EDIT){
//                    int idx = data.getIntExtra("idx",-1);
//                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
//                    timetable.edit(idx,item);
//                }
//                /** Edit -> Delete */
//                else if(resultCode == EditActivity.RESULT_OK_DELETE){
//                    int idx = data.getIntExtra("idx",-1);
//                    timetable.remove(idx);
//                }
//                break;
//        }
//    }
//
//    /** save timetableView's data to SharedPreferences in json format */
//    private void saveByPreference(String data){
//        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = mPref.edit();
//        editor.putString("timetable_demo",data);
//        editor.commit();
//        Toast.makeText(this,"saved!",Toast.LENGTH_SHORT).show();
//    }
//
//    /** get json data from SharedPreferences and then restore the timetable */
//    private void loadSavedData(){
//        timetable.removeAll();
//        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
//        String savedData = mPref.getString("timetable_demo","");
//        if(savedData == null && savedData.equals("")) return;
//        timetable.load(savedData);
//        Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
//    }





}