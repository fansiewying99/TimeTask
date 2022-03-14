package com.g3;

import static androidx.core.content.ContextCompat.getSystemService;


import android.app.Notification;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {
    long timeLeft;
    EditText hours, minutes, seconds;
    Button startStop, reset;
    Handler handler;
    Boolean running = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
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
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        hours = (EditText) view.findViewById(R.id.timerHours);
        minutes = (EditText) view.findViewById(R.id.timerMinutes);
        seconds = (EditText) view.findViewById(R.id.timerSeconds);
        startStop = (Button) view.findViewById(R.id.TMStart);
        reset = (Button) view.findViewById(R.id.TMReset);
        if (timeLeft == 0)
            reset.setEnabled(false);
        handler = new Handler(Looper.myLooper());
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = !running;
                reset.setEnabled(!running);
                if(!running) {
                    hours.setEnabled(true);
                    minutes.setEnabled(true);
                    seconds.setEnabled(true);
                    handler.removeCallbacks(runnable);
                    startStop.setText("Start");
                }
                else {
                    hours.setEnabled(false);
                    minutes.setEnabled(false);
                    seconds.setEnabled(false);
                    timeLeft = Integer.parseInt(hours.getText().toString()) * 3600L
                            + Integer.parseInt(minutes.getText().toString()) * 60L
                            + Integer.parseInt(seconds.getText().toString());
                    handler.postDelayed(runnable, 0);
                    startStop.setText("Stop");
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeft = 0;
                hours.setText("00");
                minutes.setText("00");
                seconds.setText("00");
            }
        });
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            hours.setText(String.format("%02d", (int) (timeLeft / 3600L)));
            minutes.setText(String.format("%02d", (int) (timeLeft / 60L % 60L)));
            seconds.setText(String.format("%02d", (int) (timeLeft % 60L)));
            if(timeLeft > 0 && running) {
                timeLeft--;
                handler.postDelayed(this, 1000);
            }
            else {
                running = !running;
                Notifications notifications = new Notifications(getContext());
                Notification.Builder nb = notifications.
                        getAndroidChannelNotification("Timer", "Times up!", "timer");
                notifications.getManager().notify(1, nb.build());
                hours.setEnabled(true);
                minutes.setEnabled(true);
                seconds.setEnabled(true);
                startStop.setText("Start");
            }
        }
    };
}