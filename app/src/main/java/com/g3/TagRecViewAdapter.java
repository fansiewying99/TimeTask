package com.g3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TagRecViewAdapter extends  RecyclerView.Adapter<TagRecViewAdapter.ViewHolder>{

    public List<Tag> tags;
    private SettingsDB db;

    public TagRecViewAdapter(SettingsDB db) {
        this.db = db;
    }

    public TagRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_listview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tag tag = tags.get(position);

        holder.tagName.setText(tag.getName());

    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    private List<Tag> getAllTags() {
        return db.getTags();
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void updateTag(int id,Tag tag){
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId()==id){
                tags.set(id,tag);
            }
        }
    }

    public Tag getTag(int id){
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId()==id){
                return tags.get(id);
            }
        }
        return null;
    }

    public void deleteTag(int id){
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId()==id){
                tags.remove(id);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagName = (TextView) itemView.findViewById(R.id.tagName);

        }
    }
}
