package com.manhtai.calendartime.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.manhtai.calendartime.R;
import com.manhtai.calendartime.ThongKeActivity;
import com.manhtai.calendartime.UpdateWorkActivity;
import com.manhtai.calendartime.WorkTimeActivity;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.CallBackRecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {

    private static final String TAG = "AAA";
    Context context;
    List<Work> works;
    List<Work> workDeleteList;
    boolean isChecked;
    CallBackRecyclerView callBackRecyclerView;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public WorkAdapter(Context context, List<Work> works) {
        this.context = context;
        this.works = works;
        callBackRecyclerView = (CallBackRecyclerView) context;
    }

    public WorkAdapter(Context context, List<Work> works, boolean isChecked) {
        this.context = context;
        this.works = works;
        this.isChecked = isChecked;
        callBackRecyclerView = (CallBackRecyclerView) context;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work, parent,false);
        workDeleteList = new ArrayList<>();
        return new WorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        Work work = works.get(position);
        Log.d(TAG, "onBindViewHolder: " + work.getDate());
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(work.getId()));

        holder.tvItemDate.setText(work.getDate().split(",")[0] + "\n" + work.getDate().split("\\.")[1] + "." + work.getDate().split("\\.")[2].substring(0,2));
        String dateTmp = work.getDate().split(",")[0];
//        if(dateTmp.equals("Th 7") || dateTmp.equals("Th 8") ||
//                dateTmp.equals("7") || dateTmp.equals("8") ||
//                dateTmp.equals("CN") || dateTmp.equals("1") ){
//            holder.tvItemDate.setBackgroundColor(Color.RED);
//        }
        if(work.isPunish()){
            holder.tvItemPunish.setText("Phạt: " + work.getMoneyPunish());
        }else{
            holder.tvItemPunish.setText("");
        }
        holder.tvItemTitle.setText(work.getNameEvent());
        holder.tvItemShifts.setText("Ca làm: " + work.getShifts());
        holder.tvItemSumTime.setText("Tổng thời gian làm: " + work.getNumberOfTime() + " tiếng");

        holder.ibItemWorkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xóa thông tin ngày " + work.getDate().split("\\.")[1] + "." + work.getDate().split("\\.")[2].substring(0,2) + " ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase.getInstance(context).workDao().deleteWork(works.get(position));
                        if(WorkTimeActivity.workAdapter == null){
                            Toast.makeText(context, "Vui lòng mở lại để cập nhật danh sách", Toast.LENGTH_SHORT).show();
                        }else {
                            WorkTimeActivity.workAdapter = new WorkAdapter(context,AppDatabase.getInstance(context).workDao().getAllOrdered());
                            WorkTimeActivity.rvWorkTime.setAdapter(WorkTimeActivity.workAdapter);
                        }

                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });

        holder.ibItemWorkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateWorkActivity.class);
                intent.putExtra("key_id_work", works.get(position).getId());
                context.startActivity(intent);
            }
        });
        if(isChecked){
            holder.cbItemWork.setChecked(true);
        }else{
            holder.cbItemWork.setChecked(false);
        }
        holder.cbItemWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean b = isChecked;
                if(isChecked){
                    workDeleteList.add(work);
                }else{
                    workDeleteList.remove(work);
                }
                callBackRecyclerView.getItemChecked(workDeleteList,b);
            }
        });



    }
    @Override
    public int getItemCount() {
        if(works == null){
            return 0;
        }
        return works.size();
    }

    protected class WorkViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemDate;
        private TextView tvItemTitle;
        private TextView tvItemPunish;
        private TextView tvItemShifts;
        private TextView tvItemSumTime;
        private SwipeRevealLayout swipeRevealLayout;
        private ImageButton ibItemWorkDelete;
        private ImageButton ibItemWorkUpdate;
        private CheckBox cbItemWork;




        public WorkViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItemWork = itemView.findViewById(R.id.cb_item_work);
            tvItemDate = itemView.findViewById(R.id.tv_item_date);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            tvItemPunish = itemView.findViewById(R.id.tv_item_punish);
            tvItemShifts = itemView.findViewById(R.id.tv_item_shifts);
            tvItemSumTime = itemView.findViewById(R.id.tv_item_sum_time);
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            ibItemWorkDelete = itemView.findViewById(R.id.ib_item_work_delete);
            ibItemWorkUpdate = itemView.findViewById(R.id.ib_item_work_update);

        }
    }
}
