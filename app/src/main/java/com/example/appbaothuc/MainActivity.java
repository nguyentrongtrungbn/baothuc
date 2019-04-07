package com.example.appbaothuc;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnhengio,btnstop;
    TextView txthienthi;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnhengio=(Button)findViewById(R.id.btnhengio);
        btnstop=(Button)findViewById(R.id.btnstop);
        txthienthi=(TextView)findViewById(R.id.tvhengio);
        timePicker=(TimePicker)findViewById(R.id.timepicker);
        calendar=Calendar.getInstance();
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
        btnhengio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio=timePicker.getCurrentHour();
                int phut=timePicker.getCurrentMinute();
                String string_gio=String.valueOf(gio);
                String string_phut=String.valueOf(phut);
                if(phut<10)
                {
                    string_phut="0"+String.valueOf(phut);
                }
                intent.putExtra("extra","on");
                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


                txthienthi.setText("Giờ bạn đặt là "+string_gio+":"+string_phut);
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txthienthi.setText("Stop");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}
