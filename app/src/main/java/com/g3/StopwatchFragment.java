package com.g3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StopwatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopwatchFragment extends Fragment {
    long tick, startTime, buffer, updateTime = 0L ;
    int sec, min, msec;
    boolean running = false;
    TextView time;
    Handler handler;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopwatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StopwatchFragment newInstance(String param1, String param2) {
        StopwatchFragment fragment = new StopwatchFragment();
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
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button start = (Button) view.findViewById(R.id.BtnStart);
        Button reset = (Button) view.findViewById(R.id.BtnReset);
        Button lap = (Button) view.findViewById(R.id.BtnLap);
        time = view.findViewById(R.id.SWTime);
        ListView laps = view.findViewById(R.id.Laps);
        String[] ListElements = new String[] {};
        List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        handler = new Handler(Looper.myLooper());
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        laps.setAdapter(adapter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = !running;
                if(running){
                    start.setText("Stop");
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    reset.setEnabled(false);
                }
                else {
                    start.setText("Start");
                    buffer += tick;
                    handler.removeCallbacks(runnable);
                    reset.setEnabled(true);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick = 0L ;
                startTime = 0L ;
                buffer = 0L ;
                updateTime = 0L ;
                sec = 0 ;
                min = 0 ;
                msec = 0 ;
                time.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementsArrayList.add(time.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            tick = SystemClock.uptimeMillis() - startTime;
            updateTime = buffer + tick;
            sec = (int) (updateTime / 1000);
            min = sec / 60;
            sec = sec % 60;
            msec = (int) (updateTime % 1000);
            time.setText("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%03d", msec));
            handler.postDelayed(this, 0);
        }
    };
}