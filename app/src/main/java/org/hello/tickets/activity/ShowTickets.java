package org.hello.tickets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.hello.tickets.CustomAlertDialog;
import org.hello.tickets.R;
import org.hello.tickets.adapter.ItemAdapter;
import org.hello.tickets.common.CommonInfo;
import org.hello.tickets.common.MessageList;
import org.hello.tickets.models.ItemBean;
import org.hello.tickets.serverTask.ProgressGETServerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowTickets extends AppCompatActivity {

    ListView listView;
    ItemAdapter adapter;
    ArrayList<ItemBean> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tickets);

        listView = (ListView)findViewById(R.id.list_view);
        SendRequest();
    }

    public void SendRequest(){
        String url = CommonInfo.TICKET_REQUEST_URL;
            try {
                new ProgressGETServerTask(ShowTickets.this, new ProgressGETServerTask.OnRequestCompleted() {
                    @Override
                    public void OnRequestCompleted(String cmd, CustomAlertDialog dialog) {
                        if (!cmd.equals("error")) {
                            dialog.dismiss();
                            if (cmd.length() > 0) {
                                    JSONObject jsonObject1 = null;
                                    try {
                                        jsonObject1 = new JSONObject(cmd);
                                        JSONArray jsonarray1 = jsonObject1.getJSONArray("Result");

                                        for (int i = 0; i < jsonarray1.length(); i++) {
                                            JSONObject jsonObject2 = jsonarray1.getJSONObject(i);
                                            String id       = jsonObject2.getString("_id");
                                            String subject  = jsonObject2.getString("subject");
                                            String type     = jsonObject2.getString("type");
                                            String priority = jsonObject2.getString("priority");
                                            String status   = jsonObject2.get("status").toString();

                                            arrayList.add(new ItemBean(id,subject, type, priority, status));
                                        }

                                        adapter = new ItemAdapter(ShowTickets.this, R.layout.item_ticket, arrayList);
                                        listView.setAdapter(adapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            } else {
                                dialog.changeAlertType(CustomAlertDialog.ERROR);
                                dialog.setErrorMessage(MessageList.INVALIED_RESPONSE);
                                dialog.setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(CustomAlertDialog sweetAlertDialog) {
                                        //onBackPressed();
                                    }
                                });
                            }
                        }else{
                            dialog.changeAlertType(CustomAlertDialog.ERROR);
                            dialog.setErrorMessage(MessageList.CONNECTION_ERROR);
                            dialog.setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(CustomAlertDialog sweetAlertDialog) {
                                   // onBackPressed();
                                }
                            });
                        }
                    }
                }).execute(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
