package com.edu.feicui.dincan.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.feicui.dincan.utils.CommonUtils;
import com.edu.feicui.dincan.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_username) EditText etUsername;



//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            dialog.hide();
//        }
//    };
    private ProgressDialog dialog;
//权限
    private RequestQueue requestQueue;
    private static final String url = "http://10.0.1.10:8080/GourmetOrderServer/loginServlet";//夜神不能用localhost
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.bt_login) public void login(){
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        String path = url + "?category=user&name=" + username + "&paw=" + password;

        if(!CommonUtils.isConnect(LoginActivity.this)){
            Toast.makeText(LoginActivity.this,"请检查网络状态",Toast.LENGTH_LONG).show();
            return ;
        }

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在登录,请等待");
        dialog.show();

        timer.start();

//        Message msg = handler.obtainMessage();
//        handler.sendMessageAtTime(msg,5000);



        //请求
        StringRequest stringRequest = new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String resultCode = jsonObject.getString("rt");
                    String resultMessage = jsonObject.getString("rtmsg");
                    if("200".equals(resultCode)){
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        if(password.equals("888888")){
                            Intent intent = new Intent(LoginActivity.this,ChangePasswordActivity.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this,resultMessage,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {//请求失败的接口
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this,"访问数据出现异常，请重试",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);


    }

    private CountDownTimer timer = new CountDownTimer(2500,2500) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            dialog.hide();
        }
    };
}
