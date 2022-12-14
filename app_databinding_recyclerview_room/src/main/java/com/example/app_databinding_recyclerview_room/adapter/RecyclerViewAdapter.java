package com.example.app_databinding_recyclerview_room.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_databinding_recyclerview_room.R;
import com.example.app_databinding_recyclerview_room.databinding.LayoutItemBinding;
import com.example.app_databinding_recyclerview_room.model.Student;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Student> students;

    public RecyclerViewAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemBinding layoutItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_item, parent, false);
        return new MyViewHolder(layoutItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = students.get(position);
        holder.layoutItemBinding.setStudent(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LayoutItemBinding layoutItemBinding;

        public MyViewHolder(LayoutItemBinding itemView) {
            super(itemView.getRoot());
            layoutItemBinding = itemView;
        }
    }

}
