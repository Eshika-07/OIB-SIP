package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Adapter.TodoAdapter;
import com.example.todo.Model.TodoModel;
import com.example.todo.Utility.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{

    private RecyclerView mrecyclerView;
    private FloatingActionButton fab;
    private DatabaseHelper myDB;

    private List<TodoModel> mList;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fb);
        myDB=new DatabaseHelper(MainActivity.this);
        mList=new ArrayList<>();
        adapter=new TodoAdapter(myDB,MainActivity.this);

        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setAdapter(adapter);

        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTask(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Add_task.newInstance().show(getSupportFragmentManager(),Add_task.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mrecyclerView);
    }

    @Override
    public void OnDialogClose(DialogInterface dialogInterface) {
        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTask(mList);
        adapter.notifyDataSetChanged();
    }
}