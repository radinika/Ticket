package org.hello.tickets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.hello.tickets.CustomAlertDialog;
import org.hello.tickets.R;
import org.hello.tickets.common.CommonInfo;
import org.hello.tickets.common.CommonMethod;
import org.hello.tickets.common.MessageList;
import org.hello.tickets.serverTask.ProgressPOSTServerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.hello.tickets.R.id.lay_field_empty;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{
    String url ="";
    Button btn_login;
    EditText txtUsername, txtPassword;
    String userName, password;
    LinearLayout lay_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Initialize();
    }

    private void Initialize() {
        btn_login   = (Button) findViewById(R.id.btn_login);
        txtUsername = (EditText) findViewById(R.id.et_username);
        txtPassword = (EditText) findViewById(R.id.et_password);
        lay_empty   = (LinearLayout) findViewById(lay_field_empty);
        txtUsername.setOnTouchListener(this);
        txtPassword.setOnTouchListener(this);
        btn_login.setOnClickListener(this);
    }

    private void LoginRequest() {
        try {
                url = CommonInfo.LOGIN_URL;

                JSONObject encriptObject = new JSONObject();
                JSONArray array = new JSONArray();
                array.put("all_all");
                encriptObject.put("userName", "kasun.g@duosoftware.com");
                encriptObject.put("password", "ADTest123!");
                encriptObject.put("scope",array);
                encriptObject.put("console", "AGENT_CONSOLE");
                encriptObject.put("clientID", "e8ea7bb0-5026-11e7-a69b-b153a7c332b9");

                String encriptbody = encriptObject.toString();

                String body = encriptbody;

                new ProgressPOSTServerTask(LoginActivity.this, new ProgressPOSTServerTask.OnRequestCompleted() {
                    @Override
                    public void OnRequestCompleted(String cmd, CustomAlertDialog dialog) {
                        if (!cmd.equals("error")) {
                            if (cmd.length() > 0) {
                                dialog.dismiss();
                                JSONObject jsonObject1 = null;
                                try {
                                    jsonObject1 = new JSONObject(cmd);
                                    CommonInfo.LOGIN_TOKEN = (String) jsonObject1.get("token");

                                    txtUsername.setText("");
                                    txtPassword.setText("");
                                    Intent intent = new Intent(LoginActivity.this, ShowTickets.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dialog.changeAlertType(CustomAlertDialog.ERROR);
                                dialog.setErrorMessage(MessageList.INVALIED_RESPONSE);
                            }
                        }else{
                            dialog.changeAlertType(CustomAlertDialog.ERROR);
                            dialog.setErrorMessage(MessageList.CONNECTION_ERROR);
                        }
                    }
                }).execute(url, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        lay_empty.setVisibility(View.INVISIBLE);
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            userName = txtUsername.getText().toString();
            password = txtPassword.getText().toString();

            if(userName.length() == 0 || password.length() == 0){
                lay_empty.setVisibility(View.VISIBLE);
            }
            else{
                lay_empty.setVisibility(View.INVISIBLE);
                if(CommonMethod.getInstance().isOnline(this)) {
                   LoginRequest();
                }else
                    CommonMethod.getInstance().networkEnabledialog(this);
            }
        }
    }
}
