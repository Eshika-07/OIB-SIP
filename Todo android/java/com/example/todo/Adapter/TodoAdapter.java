package com.example.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Add_task;
import com.example.todo.MainActivity;
import com.example.todo.Model.TodoModel;
import com.example.todo.R;
import com.example.todo.Utility.DatabaseHelper;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {


    private List<TodoModel> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;

   public TodoAdapter(DatabaseHelper myDB,MainActivity activity){
       this.activity=activity;
       this.myDB=myDB;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_task_view,parent,false);
        return new MyViewHolder(v);
}

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
    final TodoModel item=mList.get(position);
    holder.mCheckBox.setText(item.getTask());
    holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
    holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (isChecked){
              myDB.updateStatus(item.getId(),1);
          }else
              myDB.updateStatus(item.getId(),0);
        }
    });
   }

   public boolean toBoolean(int num){
       return num!=0;
   }

   public Context getContext(){
       return activity;
   }

   public void setTask(List<TodoModel> mList){
       this.mList=mList;
       notifyDataSetChanged();
   }

   public void deleteTask(int position){
       TodoModel item=mList.get(position);
       myDB.deleteTask(item.getId());
       mList.remove(position);
       notifyItemRemoved(position);
   }

   public void editItem(int position){
       TodoModel item=mList.get(position);
       Bundle bundle=new Bundle();
       bundle.putInt("id",item.getId());
       bundle.putString("task",item.getTask());

       Add_task task=new Add_task();
       task.setArguments(bundle);
       task.show(activity.getSupportFragmentManager(),task.getTag());
   }

   @Override
   public int getItemCount(){
        return mList.size();
   }


   public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.checkbox);

        }
   }
}