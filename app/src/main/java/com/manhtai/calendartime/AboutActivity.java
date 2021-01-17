package com.manhtai.calendartime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageButton;

import com.manhtai.calendartime.adapter.AboutAdapter;
import com.manhtai.calendartime.model.About;
import com.manhtai.calendartime.until1.CallBackAboutRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity implements CallBackAboutRecyclerView {
    private ImageButton ivAboutBackSpace;
    private RecyclerView rvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ivAboutBackSpace = findViewById(R.id.iv_about_back_space);
        rvAbout = findViewById(R.id.rv_about);

        setClickBack();
        setRecyclerView();
    }

    private void setRecyclerView() {
        List<About> abouts = new ArrayList<>();
        abouts.add(new About("Theo dõi chúng tôi","https://www.facebook.com/manh.tai.336"));
        abouts.add(new About("Tên ứng dụng","Calendar Time"));
        abouts.add(new About("Phiên bản","1.0"));
        abouts.add(new About("Ngày hoàn thành ứng dụng","17.01.2021"));
        abouts.add(new About("Ngày cập nhật bản vá","Calendar Time"));
        abouts.add(new About("Người đóng góp","Đỗ Mạnh Tài"));
        AboutAdapter aboutAdapter = new AboutAdapter(AboutActivity.this,abouts);
        rvAbout.setAdapter(aboutAdapter);

    }

    private void setClickBack() {
        ivAboutBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getItemPosition(int position) {
        if(position == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "https://www.facebook.com/manh.tai.336";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
            builder.setMessage("Truy cập vào facebook cá nhân của nhà phát triển.");
            builder.show();

        }
    }
}