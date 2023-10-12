package com.example.katanemimena;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class BarChartActivity extends AppCompatActivity {

    private ImageView imageView4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart chart = findViewById(R.id.barchart);

        imageView4 = (ImageView) findViewById(R.id.imageView4);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new BarEntry(0, (float) GpxSender.totalD));
        NoOfEmp.add(new BarEntry(1, (float) GpxSender.totalDT));
        NoOfEmp.add(new BarEntry(2, (float) GpxSender.totalDTA));
        NoOfEmp.add(new BarEntry(3, (float) GpxSender.totalE));
        NoOfEmp.add(new BarEntry(4, (float) GpxSender.totalET));
        NoOfEmp.add(new BarEntry(5, (float) GpxSender.totalETA));
        NoOfEmp.add(new BarEntry(6, (float) GpxSender.totalT));
        NoOfEmp.add(new BarEntry(7, (float) GpxSender.totalTT));
        NoOfEmp.add(new BarEntry(8, (float) GpxSender.totalTTA));
        NoOfEmp.add(new BarEntry(9, (float) GpxSender.averageS));
        NoOfEmp.add(new BarEntry(10, (float) GpxSender.totalAT));
        NoOfEmp.add(new BarEntry(11, (float) GpxSender.totalATA));


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Statistics for " + GpxSender.user);
        chart.animateY(500);
        BarData data = new BarData(bardataset);
        bardataset.setValueTextSize(14f);
        chart.getDescription().setTextSize(12f);
        chart.getDescription().setText("1-3 Distance 4-6 Elevation 7-9 Time 10-12 Speed");
        int[] colors = new int[] { Color.BLUE, Color.RED, Color.GREEN};
        bardataset.setColors(ColorTemplate.createColors(colors));
        chart.setData(data);
    }
}