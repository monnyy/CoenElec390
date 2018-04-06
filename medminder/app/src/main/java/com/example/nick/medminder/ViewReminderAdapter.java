package com.example.nick.medminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nick.medminder.data.ViewReminderProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zichenghe on 2018-04-03.
 */

public class ViewReminderAdapter extends ArrayAdapter {
    List list = new ArrayList();
    String A;
    public ViewReminderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView name,time,date,repeat,active;


    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        ViewReminderProvider viewReminderProvider = (ViewReminderProvider) this.getItem(position);
        if (row == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.viewreminder_items, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.name = (TextView) row.findViewById(R.id.view_reminders_MN);
            layoutHandler.date = (TextView) row.findViewById(R.id.view_reminders_MD);
            layoutHandler.time = (TextView) row.findViewById(R.id.view_reminders_MT);
            layoutHandler.repeat = (TextView) row.findViewById(R.id.view_reminders_MR);
            layoutHandler.active = (TextView) row.findViewById(R.id.view_reminders_AT);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();

        }

        layoutHandler.name.setText("Medicine: " + viewReminderProvider.getMedicationname());
        layoutHandler.time.setText("Time: " + viewReminderProvider.getTime());
        layoutHandler.date.setText("Date: " + viewReminderProvider.getDate());
       layoutHandler.active.setText("This Reminder Active: " +viewReminderProvider.getActive());
       if (viewReminderProvider.getRepeat() != null) {
           if (viewReminderProvider.getRepeat().equals("true")) {
               layoutHandler.repeat.setText("Repeat Every " + viewReminderProvider.getRepeatNo() + " "
                       + viewReminderProvider.getRepatType());

           } else {
               layoutHandler.repeat.setText("Repeat off");
           }
       }
        return row;
    }
}