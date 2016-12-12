package com.edu.feicui.dincan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.feicui.dincan.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/11/25.
 */

public class ChangePasswordActivity extends Activity {

    @BindView(R.id.et_old_pw) EditText etOld;
    @BindView(R.id.et_new_pw) EditText etNew;
    @BindView(R.id.et_again_new_pw) EditText etOkNew;
    @BindView(R.id.tv_flName) TextView tvName;
    private static RequestQueue requestQueue;
    private static final String url = "http://10.0.1.10:8080/GourmetOrderServer/loginServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);
        ButterKnife.bind(this);
        tvName.setText("用户名 " + getIntent().getStringExtra("username"));
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }
    }

    @OnClick(R.id.bt_ensure) public void ok(){
        sendStringRequest(url,listener,errorListener);
    }

    public void sendStringRequest(String url, Response.Listener<String> listener,Response.ErrorListener errorListener){
        final String name = getIntent().getStringExtra("username");
        final String opaw = etOld.getText().toString();
        final String npaw = etNew.getText().toString();


        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("opaw",opaw);
                params.put("npaw",npaw);
                return params;
            }
        };
        requestQueue.add(request);
    }

    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            Toast.makeText(ChangePasswordActivity.this,"密码修改成功",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChangePasswordActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(ChangePasswordActivity.this,"网络异常，修改密码失败",Toast.LENGTH_LONG).show();
        }
    };

}
