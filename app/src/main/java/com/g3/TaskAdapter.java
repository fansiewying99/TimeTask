package com.g3;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    // Store a member variable for the contacts
    //private List<Task> mContacts;
    private List<Task> tasks;
        private SettingsDB db;

    // Pass in the contact array into the constructor
    /*public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }*/
    public TaskAdapter(){
        this.tasks=new ArrayList<>();
    }
    public TaskAdapter(SettingsDB db){
        this.db=db;
        //this.tasks=db.getTasks();
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View taskView = inflater.inflate(R.layout.task_listview, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(taskView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Task task = tasks.get(position);

        // Set item views based on your views and data model

        TextView task_id=holder.task_id;

        task_id.setText(Integer.toString(task.getId()));
        TextView task_name=holder.task_name;
        task_name.setText(task.getName());
        Button task_color=holder.task_color;
        task_color.setBackgroundColor(Color.parseColor(task.getColor()));
        TextView task_end_date=holder.task_end_date;
        task_end_date.setText("Ends at "+task.getEndDate());
        TextView task_end_time=holder.task_end_time;
        task_end_time.setText(" "+task.getEndTime());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void updateTasks(int id, Task task){
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getId()==id){
                tasks.set(i, task);
            }
        }
    }

    public Task getTask(int id){
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getId()==id){
                return tasks.get(id);
            }
        }
        return null;
    }

    public void deleteTask(int id){
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getId()==id){
                tasks.remove(id);
            }
        }
    }
    //set tasks list to be view in recycler
    public void setAllTasks(){
        this.tasks=getAllTasks();
    }

    //set tasks list to be view in recycler
    public void setMonthTasks(int month){
        tasks=getMonthTasks(month);
        //return tasks;
    }
    //get tasks to be viewed in recycler
    public List<Task> getTasks(){
        //this.tasks=db.getTasks();
        return tasks;
    }
    //get task in the month only, no set
    public List<Task> getMonthTasks(int month){
        List<Task> tempAllTasks=getAllTasks();

        List<Task> tempTasks=new ArrayList<>();
        for(int i=0; i<tempAllTasks.size(); i++){
            String endDate=tempAllTasks.get(i).getEndDate();
            int taskMonth=Integer.parseInt((endDate.split("-"))[1]);
            if(taskMonth==month){

                tempTasks.add(tempAllTasks.get(i));
            }
        }
        return tempTasks;
        //return tasks;
    }
    //get all tasks only
    public List<Task> getAllTasks(){
        return db.getTasks();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;
        public TextView task_id;
        public TextView task_name;
        public TextView task_end_date;
        public TextView task_end_time;
        public Button task_color;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            task_id=(TextView) itemView.findViewById((R.id.task_id));
            task_name=(TextView) itemView.findViewById((R.id.task_name));
            task_end_date=(TextView) itemView.findViewById((R.id.task_end_date));
            task_end_time=(TextView) itemView.findViewById((R.id.task_end_time));
            task_color=(Button) itemView.findViewById(R.id.task_color);

            //nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            //messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }
}