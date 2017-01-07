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
import com.edu.feicui.dincan.api.UserApi;
import com.edu.feicui.dincan.entity.UserResult;
import com.edu.feicui.dincan.utils.CommonUtils;
import com.edu.feicui.dincan.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_username)
    EditText etUsername;

    private ProgressDialog dialog;
    private static final String url = "http://101.200.222.64:8080/GourmetOrderServer/";//夜神不能用localhost   Gourmet  http://59.110.161.30:8080/order/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在登录,请等待");
    }

    @OnClick(R.id.bt_login)
    public void login() {

        dialog.show();
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        dialog.show();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志拦截器
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<UserResult> call = userApi.login("user",username, password);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, retrofit2.Response<UserResult> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    if(dialog.isShowing()){
                        dialog.cancel();
                    }
                    return;
                }


                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                if (password.equals("888888")) {
                    Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog.isShowing()){
            dialog.cancel();
        }
    }

    //    private CountDownTimer timer = new CountDownTimer(2500,2500) {
//        @Override
//        public void onTick(long l) {
//
//        }
//
//        @Override
//        public void onFinish() {
//            dialog.hide();
//        }
//    };
}
