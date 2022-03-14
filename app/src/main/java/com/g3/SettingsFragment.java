package com.g3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SettingsDB settingsDB = new SettingsDB(this.getContext());
        UserSettings settings = settingsDB.getSettings(1);

        SwitchMaterial toggleTTNotify = view.findViewById(R.id.ToggleTTNotify);
        toggleTTNotify.setChecked(settings.getTimeNotify() == 1);
        SwitchMaterial.OnCheckedChangeListener TTNotify = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTimeNotify(isChecked ? 1:0);
                settingsDB.updateSettings(1, settings);
            }
        };
        toggleTTNotify.setOnCheckedChangeListener(TTNotify);

        SwitchMaterial toggleTSNotify = view.findViewById(R.id.ToggleTSNotify);
        toggleTSNotify.setChecked(settings.getTaskNotify() == 1);
        SwitchMaterial.OnCheckedChangeListener TSNotify = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTaskNotify(isChecked ? 1:0);
                settingsDB.updateSettings(1, settings);
                switch(settingsDB.getSettings(1).getTaskNotify()) {
                    case 1:
                        getContext().startService(new Intent(getContext(), TasksCountdownService.class));
                        Log.i("countdownservice in main", "Started service");
                        break;
                    case 0:
                        getContext().stopService(new Intent(getContext(), TasksCountdownService.class));
                        Log.i("countdownservice in main", "Stopped service");
                        break;
                }
            }
        };
        toggleTSNotify.setOnCheckedChangeListener(TSNotify);

        SwitchMaterial toggleDarkMode = view.findViewById(R.id.ToggleDarkMode);
        toggleDarkMode.setChecked(settings.getDarkMode() == 1);
        SwitchMaterial.OnCheckedChangeListener DarkMode = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setDarkMode(isChecked ? 1:0);
                settingsDB.updateSettings(1, settings);
                switch(isChecked ? 1:0) {
                    case 0:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case 1:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
            }
        };
        toggleDarkMode.setOnCheckedChangeListener(DarkMode);
    }
}