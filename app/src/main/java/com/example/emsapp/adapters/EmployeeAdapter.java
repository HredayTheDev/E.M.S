package com.example.emsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.activities.EmployeeDetailsActivity;
import com.example.emsapp.models.Employee;
import com.example.emsapp.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.viewHolder> {
    private Context context;
    private ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.viewHolder holder, int position) {
        final Employee employeeInfo = employeeArrayList.get(position);

        holder.eName.setText("Name: "+employeeInfo.getUserName());
        holder.eDepartment.setText("Department: "+employeeInfo.getUserDepartment());
        holder.eDesignation.setText("Designation: "+employeeInfo.getUserDesignation());
        holder.ePhone.setText("Contact: "+employeeInfo.getUserPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeDetailsActivity.class);
                intent.putExtra("employeeInfo", employeeInfo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView eName, eDepartment, eDesignation, ePhone;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            eName = itemView.findViewById(R.id.name);
            eDepartment = itemView.findViewById(R.id.department);
            eDesignation = itemView.findViewById(R.id.designation);
            ePhone = itemView.findViewById(R.id.phone);
        }
    }
}
