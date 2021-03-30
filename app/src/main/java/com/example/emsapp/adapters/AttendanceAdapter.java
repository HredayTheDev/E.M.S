package com.example.emsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.activities.SelfAttendanceDetailsActivity;
import com.example.emsapp.models.Attendance;
import com.example.emsapp.R;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.viewHolder> {
    private Context context;
    private ArrayList<Attendance> attendanceArrayList;

    public AttendanceAdapter(Context context, ArrayList<Attendance> attendanceArrayList) {
        this.context = context;
        this.attendanceArrayList = attendanceArrayList;
    }

    @NonNull
    @Override
    public AttendanceAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view, parent, false);
        return new AttendanceAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.viewHolder holder, int position) {
        final Attendance attendanceInfo = attendanceArrayList.get(position);
        String date = attendanceInfo.getDate();
        String start = attendanceInfo.getStartTime();
        String finish = attendanceInfo.getFinishTime();

        holder.dateTv.setText("Date: "+date);
        holder.startTimeTv.setText("Started: "+start);
        holder.finishTimeTv.setText("Finished: "+finish);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelfAttendanceDetailsActivity.class);
                intent.putExtra("attendanceInfo", attendanceInfo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView dateTv, startTimeTv, finishTimeTv;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.date);
            startTimeTv = itemView.findViewById(R.id.startTime);
            finishTimeTv = itemView.findViewById(R.id.endTime);
        }
    }
}
