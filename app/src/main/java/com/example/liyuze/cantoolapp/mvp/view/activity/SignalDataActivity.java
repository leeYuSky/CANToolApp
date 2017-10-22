package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class SignalDataActivity extends AppCompatActivity {

    LineChartView lineChartView;
    Axis axisY;
    Axis axisX;
    ArrayList<Line> linesList;
    ArrayList<PointValue> pointValueList;
    LineChartData lineChartData;
    ArrayList<PointValue> points;
    boolean isFinish = false;
    Timer timer;
    int position = 0;

    private LineChartView lineChart;
    String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};//X轴的标注
    int[] score= {50,42,90,33,10,74,22,18,79,20};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_data);

        setupWindow();

        TextView textView = (TextView) findViewById(R.id.my);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        int childPosition = intent.getIntExtra("childPosition",Integer.MIN_VALUE);
//        if(childPosition != Integer.MIN_VALUE) {
//            String signal = Constants.DATATABLE.get(groupName).get(childPosition).getName();
//            textView.setText(groupName + " : " + signal);
//        }else{
//            textView.setText("有错误");
//        }
        setTitle(Constants.DATATABLE.get(groupName).get(childPosition).getName());

//        lineChart = (LineChartView) findViewById(R.id.line_chart);
//        getAxisXLables();//获取x轴的标注
//        getAxisPoints();//获取坐标点
//        initLineChart();//初始化

        initAxisView();
        showMovingLineChart();


    }

    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setupWindow(){
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        // d.getHeight()/d.getWidth() 已经被弃用
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        p.height = (int) (metrics.heightPixels * 0.9);   //高度设置为屏幕的1.0
        p.width = (int) (metrics.widthPixels * 1.0);    //宽度设置为屏幕的0.8

//        p.height = (int) (d.getHeight() * 0.9);   //高度设置为屏幕的1.0
//        p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的0.8

        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);
    }

    private void initAxisView() {

        lineChartView = (LineChartView) findViewById(R.id.line_chart);
        pointValueList = new ArrayList<PointValue>();
        linesList = new ArrayList<Line>();

        System.out.println();

        /** 初始化X轴 */
        axisX = new Axis();
        axisX.setHasTiltedLabels(false);//X坐标轴字体是斜的显示还是直的，true是斜的显示
//        axisX.setTextColor(Color.CYAN);//设置X轴颜色
        axisX.setName("时间（单位：s）");//X轴名称
        axisX.setHasLines(true);//X轴分割线
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(1);//设置0的话X轴坐标值就间隔为1
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        for (int i = 0; i < 61; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(i+""));
        }
        axisX.setValues(mAxisXValues);//填充X轴的坐标名称
        lineChartData.setAxisXBottom(axisX);//X轴在底部

        lineChartView.setLineChartData(lineChartData);

        Viewport port = initViewPort(0,10);//初始化X轴10个间隔坐标
        lineChartView.setCurrentViewportWithAnimation(port);
        lineChartView.setInteractive(false);//设置不可交互
        lineChartView.setScrollEnabled(true);
        lineChartView.setValueTouchEnabled(false);
        lineChartView.setFocusableInTouchMode(false);
        lineChartView.setViewportCalculationEnabled(false);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.startDataAnimation();

        loadData();//加载待显示数据
    }

    private Viewport initViewPort(float left,float right) {
        Viewport port = new Viewport();
        port.top = 100;//Y轴上限，固定(不固定上下限的话，Y轴坐标值可自适应变化)
        port.bottom = 0;//Y轴下限，固定
        port.left = left;//X轴左边界，变化
        port.right = right;//X轴右边界，变化
        return port;
    }

    private void loadData() {
        points = new ArrayList<PointValue>();
        for (int i = 0; i < 60; i++) {
            points.add(new PointValue(i + 1, i % 5 * 10 + 30));
        }
    }

    private void showMovingLineChart() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isFinish){
                    pointValueList.add(points.get(position));//实时添加新的点

                    //根据新的点的集合画出新的线
                    Line line = new Line(pointValueList);
                    line.setColor(Color.parseColor("#FFCD41"));//设置折线颜色
                    line.setShape(ValueShape.CIRCLE);//设置折线图上数据点形状为 圆形 （共有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
                    line.setCubic(false);//曲线是否平滑，true是平滑曲线，false是折线
                    line.setHasLabels(true);//数据是否有标注
//        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据,设置了line.setHasLabels(true);之后点击无效
                    line.setHasLines(true);//是否用线显示，如果为false则没有曲线只有点显示
                    line.setHasPoints(true);//是否显示圆点 ，如果为false则没有原点只有点显示（每个数据点都是个大圆点）
                    linesList.add(line);
                    lineChartData = new LineChartData(linesList);
                    lineChartData.setAxisYLeft(axisY);//设置Y轴在左
                    lineChartData.setAxisXBottom(axisX);//X轴在底部
                    lineChartView.setLineChartData(lineChartData);

                    float xAxisValue = points.get(position).getX();
                    //根据点的横坐标实时变换X坐标轴的视图范围
                    Viewport port;
                    if(xAxisValue > 10){
                        port = initViewPort(xAxisValue - 10,xAxisValue);
                    }
                    else {
                        port = initViewPort(0,10);
                    }
                    lineChartView.setMaximumViewport(port);
                    lineChartView.setCurrentViewport(port);

                    position++;

                    if(position > points.size()-1) {
                        isFinish = true;
                    }
                }
            }
        },1000,1000);
    }

}
