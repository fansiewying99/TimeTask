package com.g3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int taskId;
    String taskName;
    String taskTags;
    String taskColor;
    String taskStartDate;
    String taskStartTime;
    String taskEndDate;
    String taskEndTime;

    public EditTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTaskFragment newInstance(String param1, String param2) {
        EditTaskFragment fragment = new EditTaskFragment();
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
            //task_id = getArguments().getInt("task_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String value1 = getArguments().getString("key1");
        //Log.i("bundle", getArguments().toString());
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.TBMainAct);
        toolbar.setTitle("Edit Task");

        if (getArguments() != null) {
            //get task id to get data from db.
            taskId=getArguments().getInt("task_id");
        }

        //set data gotten from db
        SettingsDB db=((MainActivity)getActivity()).getSettingsDB();
        Task task=db.getTask(taskId);
        Log.i("task id", Integer.toString(taskId));
        taskName=task.getName();
        taskTags=task.getTags();
        taskColor=task.getColor();
        taskStartDate=task.getStartDate();
        taskStartTime=task.getStartTime();
        taskEndDate=task.getEndDate();
        taskEndTime=task.getEndTime();

        //set init value to text input
        TextInputEditText edit_task_name=view.findViewById(R.id.edit_task_name);
        edit_task_name.setText(taskName);
        TextInputEditText edit_task_tags=view.findViewById(R.id.edit_task_tags);
        edit_task_tags.setText(taskTags);
        TextInputEditText edit_task_color=view.findViewById(R.id.edit_task_color);
        edit_task_color.setText(taskColor);
        Button edit_task_color_view=view.findViewById(R.id.edit_task_color_view);
        edit_task_color_view.setBackgroundColor(Color.parseColor(taskColor));
        TextInputEditText edit_task_start_date = view.findViewById(R.id.edit_task_start_date);
        edit_task_start_date.setText(taskStartDate);
        TextInputEditText edit_task_start_time = view.findViewById(R.id.edit_task_start_time);
        edit_task_start_time.setText(taskStartTime);
        TextInputEditText edit_task_end_date = view.findViewById(R.id.edit_task_end_date);
        edit_task_end_date.setText(taskEndDate);
        TextInputEditText edit_task_end_time = view.findViewById(R.id.edit_task_end_time);
        edit_task_end_time.setText(taskEndTime);

        //set onCLick listener
        //https://github.com/QuadFlask/colorpicker
        edit_task_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(Color.parseColor(taskColor))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                                edit_task_color.setText("#"+Integer.toHexString(selectedColor));
                                edit_task_color_view.setBackgroundColor(Color.parseColor("#"+Integer.toHexString(selectedColor)));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                //changeBackgroundColor(selectedColor);
                                //initColor=Integer.toHexString(selectedColor);

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        edit_task_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment("edit_task_start_date");
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        edit_task_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment("edit_task_start_time");
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        edit_task_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment("edit_task_end_date");
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        edit_task_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment("edit_task_end_time");
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        //set Add and Cancel button\
        FloatingActionButton edit_task_cancel_btn = view.findViewById(R.id.edit_task_cancel_btn);
        edit_task_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        FloatingActionButton edit_task_btn = view.findViewById(R.id.edit_task_btn);
        edit_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get to be submitted data
                int id=taskId;
                String name=edit_task_name.getEditableText().toString();
                String tags=edit_task_tags.getEditableText().toString();
                String color=edit_task_color.getEditableText().toString();
                String start_date=edit_task_start_date.getEditableText().toString();
                String start_time=edit_task_start_time.getEditableText().toString();
                String end_date=edit_task_end_date.getEditableText().toString();
                String end_time=edit_task_end_time.getEditableText().toString();

                boolean validate=true;

                if(name.isEmpty()){
                    TextInputLayout edit_task_name_layout=getActivity().findViewById(R.id.edit_task_name_layout);
                    edit_task_name_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(tags.isEmpty()){
                    TextInputLayout edit_task_tags_layout=getActivity().findViewById(R.id.edit_task_tags_layout);
                    edit_task_tags_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(color.isEmpty()){
                    TextInputLayout edit_task_color_layout=getActivity().findViewById(R.id.edit_task_color_layout);
                    edit_task_color_layout.setError("This field cannot be empty.");
                    validate=false;
                }

                TextInputLayout edit_task_start_date_layout=getActivity().findViewById(R.id.edit_task_start_date_layout);
                TextInputLayout edit_task_start_time_layout=getActivity().findViewById(R.id.edit_task_start_time_layout);
                TextInputLayout edit_task_end_date_layout=getActivity().findViewById(R.id.edit_task_end_date_layout);
                TextInputLayout edit_task_end_time_layout=getActivity().findViewById(R.id.edit_task_end_time_layout);
                if(start_date.isEmpty()){

                    edit_task_start_date_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(start_time.isEmpty()){

                    edit_task_start_time_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(end_date.isEmpty()){

                    edit_task_end_date_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(end_time.isEmpty()){

                    edit_task_end_time_layout.setError("This field cannot be empty.");
                    validate=false;
                }
                if(!validate){
                    return;
                }
                else{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    Date endDateTime = null;
                    Date startDateTime = null;
                    try {
                        endDateTime = sdf.parse(end_date+" "+end_time);
                        startDateTime = sdf.parse(start_date+" "+start_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar endDate_cal = Calendar.getInstance();
                    Calendar startDate_cal = Calendar.getInstance();
                    endDate_cal.setTime(endDateTime);
                    startDate_cal.setTime(startDateTime);

                    if(startDateTime.compareTo(endDateTime)>0) {
                        //startDate is after endDate
                        edit_task_end_date_layout.setError("End date invalid.");
                        edit_task_end_time_layout.setError("End time invalid.");
                        return;
                    }
                }
                //submit set data
                //set data in task list fragment
                //back to task list fragment //update task list onresume
                SettingsDB settingsDB =((MainActivity)getActivity()).getSettingsDB();

                TaskAdapter taskAdapter=((MainActivity)getActivity()).getTaskAdapter();
                //set data in task list fragment
                Task task=taskAdapter.getTask(taskId);
                task.editTask(name, tags, color, start_date, start_time, end_date, end_time);
                settingsDB.updateTask(taskId, task);
                taskAdapter.updateTasks(taskId, task);
                ((MainActivity)getActivity()).restartTasksCountdownService();

                String date=end_date;
                String[] dateArr=date.split("-");
                int day=Integer.parseInt(dateArr[0]);
                int month=Integer.parseInt(dateArr[1]);
                int year=Integer.parseInt(dateArr[2]);

                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    //String item = "Pig";
                    //int insertIndex = 2;

                    getFragmentManager().popBackStack();
                }
            }
        });

        FloatingActionButton delete_task_btn = view.findViewById(R.id.delete_task_btn);
        delete_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDB settingsDB =((MainActivity)getActivity()).getSettingsDB();
                TaskAdapter taskAdapter=((MainActivity)getActivity()).getTaskAdapter();

                settingsDB.deleteTask(taskId);
                taskAdapter.deleteTask(taskId);
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}