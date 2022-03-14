package com.g3;

import java.util.ArrayList;

public class Task extends SQLRecord {
    private int id;
    private String name;
    private String color;
    private String tags;
    private String endDate;
    private String endTime;
    private String startDate;
    private String startTime;

    public String getName(){ return name; }
    public String getColor(){ return color; }
    public String getEndDate(){ return endDate; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public Task(){

    }

    public Task(int id, String name, String tags, String color, String startDate, String startTime, String endDate, String endTime){
        this.id=id;
        this.color=color;
        this.name=name;
        this.tags=tags;
        this.startDate=startDate;
        this.startTime=startTime;
        this.endDate=endDate;
        this.endTime=endTime;
    }

    @Override
    public String toString() {
        return "id:" + this.id + "," +
                "name:" + this.name + "," +
                "tags:" + this.tags + "," +
                "color:"+this.color + "," +
                "startDate" + this.startDate + "," +
                "startTime" + this.endTime + "," +
                "endDate" + this.endDate + "," +
                "endTime" + this.endTime;
    }

    public String[] toStringArray() {
        return new String[]{"id", "name", "tags", "color", "startDate", "startTime", "endDate", "endTime"};
    }

    public void editTask(String name, String tags, String color, String startDate, String startTime, String endDate, String endTime){
        this.color=color;
        this.name=name;
        this.tags=tags;
        this.startDate=startDate;
        this.startTime=startTime;
        this.endDate=endDate;
        this.endTime=endTime;
    }

    /*public static ArrayList<Task> createTaskList() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        tasks.add(new Task(1, "Task 1", "tag1, tag2", "#F56969", "1-1-2022", "00:00", "5-1-2022", "00:00"));
        tasks.add(new Task(2, "Task 2", "tag1, tag3", "#A17DEF", "9-1-2022", "00:00", "8-2-2022", "00:00"));

        return tasks;
    }*/
}
