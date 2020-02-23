package com.example.chuanke.chuanke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.component.DateTimePickDialog;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayTimeActivity extends BaseActivity implements View.OnClickListener {
    private Button button1;
    private Button button2;
    private TextView tv_choose_start_date;
    private TextView tv_choose_end_date;
    private TextView tv_play_times;//播放次数
    private TextView tv_order_price;//订单金额
    private TextView tv_sum_price;//总金额
    private TextView tv_one_price;//单价金额
    private TextView tv_pay_method;//支付方式
    private LinearLayout ll_submit;//支付方式

    private DateTime dateTime1 = new DateTime();
    private DateTime dateTime2 = new DateTime();

    private Long startDateMills;
    private Long endDateMills;
    private Long currentMills;

    private Calendar c;
    private Calendar c1;

    private int uid = MyApplication.uid;
    private int sid = -1;
    private int fid = -1;
    private double sprice;
    private int payMethod = 1;
    private double osum = 1;
    private String ostarttime ;
    private String oendtime ;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_play_time;
    }

    @Override
    public void initView() {
        topBar.setText("选择投放时间");
        button1 = findViewById(R.id.datepicker1);
        button2 = findViewById(R.id.datepicker2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        tv_choose_start_date = findViewById(R.id.tv_choose_start_date);
        tv_choose_end_date = findViewById(R.id.tv_choose_end_date);
        tv_play_times = findViewById(R.id.tv_play_times);
        tv_order_price = findViewById(R.id.tv_order_price);
        tv_sum_price = findViewById(R.id.tv_sum_price);
        tv_one_price = findViewById(R.id.tv_one_price);
        tv_pay_method = findViewById(R.id.tv_pay_method);
        tv_pay_method.setOnClickListener(this);
        ll_submit = findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid",-1);
        fid = intent.getIntExtra("fid",-1);
        String strPrice = intent.getStringExtra("sprice");
        if(null != strPrice && !"".equals(strPrice)){
            sprice = Double.parseDouble(strPrice);

        }
        tv_one_price.setText("￥" + sprice + "/小时");
    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.datepicker1:
                c = Calendar.getInstance();
                //给定Calendar c,就能将日期和时间进行初始化，打开时间弹窗
                new DateTimePickDialog(this, c).setOnDateTimeSetListener(new DateTimePickDialog.OnDateTimeSetListener() {

                    @Override
                    public void onDateTimeSet(DatePicker dp, TimePicker tp, int year,
                                              int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
                        // 保存选择后时间
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear + 1);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        dateTime1.year = year;
                        dateTime1.month = monthOfYear + 1;
                        dateTime1.day = dayOfMonth;
                        dateTime1.hour = hourOfDay;
                        dateTime1.minute = minute;
                        tv_choose_start_date.setText(dateTime1.year + "-" + dateTime1.month + "-"
                                + dateTime1.day + "  " + dateTime1.hour + ":" + dateTime1.minute);
                        ostarttime = dateTime1.year + "-" + dateTime1.month + "-"
                                + dateTime1.day + "  " + dateTime1.hour + ":" + dateTime1.minute;
                        currentMills = System.currentTimeMillis();
                        startDateMills = dateTime2mill(dateTime1.year + "-" + dateTime1.month
                                + "-" + dateTime1.day + " " + dateTime1.hour + ":" + dateTime1.minute);
                        if(currentMills >= startDateMills){
                            tv_choose_start_date.setText("");
                            Toast.makeText(PlayTimeActivity.this, "开始时间不能早于当前时间！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!tv_choose_end_date.getText().toString().trim().equals("") && tv_choose_end_date != null) {
                            endDateMills = dateTime2mill(dateTime2.year + "-" + dateTime2.month
                                    + "-" + dateTime2.day + " " + dateTime2.hour + ":" + dateTime2.minute);
                            Log.i(startDateMills + "", endDateMills + " ");

                            if (startDateMills < endDateMills && currentMills < startDateMills) {
                                if (endDateMills - startDateMills < 3600 * 1000) {
                                    Toast.makeText(PlayTimeActivity.this, "不足一小时按一小时计算！", Toast.LENGTH_SHORT).show();
                                }
                                tv_play_times.setText((((endDateMills - startDateMills) / 1000 / 90 ) +1) + "次");
                                if((endDateMills - startDateMills) / 1000 % 3600 != 0){
                                    tv_order_price.setText((((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice) + "");
                                    tv_sum_price.setText((((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice) + "");
                                    osum = ((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice;
                                } else {
                                    tv_order_price.setText((((endDateMills - startDateMills) / 1000 / 3600) * sprice) + "");
                                    tv_sum_price.setText((((endDateMills - startDateMills) / 1000 / 3600) * sprice) + "");
                                    osum = ((endDateMills - startDateMills) / 1000 / 3600) * sprice;
                                }
                            } else {
                                tv_choose_start_date.setText("");
                                tv_play_times.setText("0次");
                                tv_order_price.setText("￥0.00");
                                tv_sum_price.setText("￥0.00");
                                Toast.makeText(PlayTimeActivity.this, "结束时间不能早于开始时间！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.datepicker2:
                c1 = Calendar.getInstance();
                //给定Calendar c,就能将日期和时间进行初始化
                new DateTimePickDialog(this, c1).setOnDateTimeSetListener(new DateTimePickDialog.OnDateTimeSetListener() {

                    @Override
                    public void onDateTimeSet(DatePicker dp, TimePicker tp, int year,
                                              int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
                        // 保存选择后时间
                        c1.set(Calendar.YEAR, year);
                        c1.set(Calendar.MONTH, monthOfYear + 1);
                        c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c1.set(Calendar.MINUTE, minute);
                        dateTime2.year = year;
                        dateTime2.month = monthOfYear + 1;
                        dateTime2.day = dayOfMonth;
                        dateTime2.hour = hourOfDay;
                        dateTime2.minute = minute;
                        tv_choose_end_date.setText(dateTime2.year + "-" + dateTime2.month + "-"
                                + dateTime2.day + "  " + dateTime2.hour + ":" + dateTime2.minute);
                        oendtime = dateTime2.year + "-" + dateTime2.month + "-"
                                + dateTime2.day + "  " + dateTime2.hour + ":" + dateTime2.minute;
                        currentMills = System.currentTimeMillis();
                        endDateMills = dateTime2mill(dateTime2.year + "-" + dateTime2.month
                                + "-" + dateTime2.day + " " + dateTime2.hour + ":" + dateTime2.minute);
                        if(currentMills >= endDateMills){
                            tv_choose_end_date.setText("");
                            Toast.makeText(PlayTimeActivity.this, "结束时间不能早于当前时间！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!tv_choose_start_date.getText().toString().trim().equals("") && tv_choose_start_date != null) {
                            ostarttime = dateTime1.year + "-" + dateTime1.month
                                    + "-" + dateTime1.day + " " + dateTime1.hour + ":" + dateTime1.minute;
                            Log.i(startDateMills + "", endDateMills + " ");

                            if (startDateMills < endDateMills) {
                                if (endDateMills - startDateMills < 3600 * 1000) {
                                    Toast.makeText(PlayTimeActivity.this, "不足一小时按一小时计算！", Toast.LENGTH_SHORT).show();
                                }
                                tv_play_times.setText((((endDateMills - startDateMills) / 1000 / 90 ) +1) + "次");
                                if((endDateMills - startDateMills) / 1000 % 3600 != 0){
                                    tv_order_price.setText((((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice) + "");
                                    tv_sum_price.setText((((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice) + "");
                                    osum = ((endDateMills - startDateMills) / 1000 / 3600 + 1) * sprice;
                                } else {
                                    tv_order_price.setText((((endDateMills - startDateMills) / 1000 / 3600) * sprice) + "");
                                    tv_sum_price.setText((((endDateMills - startDateMills) / 1000 / 3600) * sprice) + "");
                                    osum = ((endDateMills - startDateMills) / 1000 / 3600) * sprice;
                                }
                            } else {
                                tv_choose_end_date.setText("");
                                tv_play_times.setText("0次");
                                tv_order_price.setText("￥0.00");
                                tv_sum_price.setText("￥0.00");
                                Toast.makeText(PlayTimeActivity.this, "结束时间不能早于开始时间！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;

            case R.id.tv_pay_method:
                final String[] items = {"支付宝","微信"};
                new AlertDialog.Builder(this)
                        .setTitle("单选对话框").setIcon(R.drawable.device)
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PlayTimeActivity.this, items[which], Toast.LENGTH_SHORT).show();
                                if(which == 0){
                                    tv_pay_method.setText("支付宝");
                                    payMethod = 1;
                                } else {
                                    tv_pay_method.setText("微信");
                                    payMethod = 2;
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.ll_submit:
                if(!"".equals(tv_choose_start_date.getText().toString().trim()) && null != tv_choose_start_date.getText().toString().trim()){
                    JSONObject jsonObject = new JSONObject();
                    uid = 1;
                    jsonObject.put("uid",uid);
                    jsonObject.put("sid",sid);
                    jsonObject.put("fid",fid);
                    jsonObject.put("opay",payMethod);
                    jsonObject.put("osum",osum);
                    jsonObject.put("ostarttime",ostarttime);
                    jsonObject.put("oendtime",oendtime);
                    HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/add/order",jsonObject.toJSONString());
                } else {
                    Toast.makeText(PlayTimeActivity.this,"请选择时间！",Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null && !result.equals("null") && !result.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String msg1 = jsonObject.getString("msg");
                if(jsonObject.getString("status").trim().equals("1")){
                    String strOid = jsonObject.getString("oid");
                    int oid = Integer.parseInt(strOid);
                    Toast.makeText(PlayTimeActivity.this,"下单成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PlayTimeActivity.this,OrderDetailsActivity.class);
                    intent.putExtra("oid",oid).putExtra("opay",payMethod);
                    startActivity(intent);
                } else {
                    Toast.makeText(PlayTimeActivity.this,msg1,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public long dateTime2mill(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//要转换的日期格式，根据实际调整""里面内容
        long dateToSecond = 0L;
        try {
            dateToSecond = sdf.parse(date).getTime();//sdf.parse()实现日期转换为Date格式，然后getTime()转换为毫秒数值
            System.out.print(dateToSecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToSecond;
    }

    class DateTime {
        int year;
        int month;
        int day;
        int hour;
        int minute;
    }
}
