package com.akash.todoapp.modules.todoList.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akash.todoapp.R;
import com.akash.todoapp.model.database.TodoEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> implements View.OnClickListener {
    private List<TodoEntity> todoList = new ArrayList<>();
    private LayoutInflater inflater;
    private Observable<TodoEntity> removeClickObservable;
    private ObservableEmitter<TodoEntity> removeClickEmitter;


    TodoListAdapter() {
        removeClickObservable = Observable.create(emitter -> {
            this.removeClickEmitter = emitter;
        });
    }

    public Observable<TodoEntity> getRemoveClickObservable() {
        return removeClickObservable;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return createVH(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {
        todoViewHolder.bind(todoList.get(i));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTodoList(List<TodoEntity> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }


    private TodoViewHolder createVH(ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new TodoViewHolder(inflater.inflate
                (R.layout.item_todo, viewGroup, false));
    }

    @Override
    public void onClick(View v) {
        removeClickEmitter.onNext((TodoEntity) v.getTag());
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView desc;
        private ImageButton btnRemove;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
        }

        private void findViews() {
            title = itemView.findViewById(R.id.todo_title);
            desc = itemView.findViewById(R.id.todo_desc);
            btnRemove = itemView.findViewById(R.id.btn_todo_remove);
            btnRemove.setOnClickListener(TodoListAdapter.this);
        }

        private void bind(TodoEntity todo) {
            btnRemove.setTag(todo);
            title.setText(todo.title);
            desc.setText(todo.description);

            switch (todo.priority.getLevel()) {
                case 0:
                    itemView.setBackgroundTintList(itemView.getContext().getResources().getColorStateList(R.color.colorHighPriority));
                    break;
                case 1:
                    itemView.setBackgroundTintList(itemView.getContext().getResources().getColorStateList(R.color.colorMediumPriority));
                    break;
                case 2:
                    itemView.setBackgroundTintList(itemView.getContext().getResources().getColorStateList(R.color.colorLowPriority));
                    break;
            }

        }
    }
}
