package org.hello.tickets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hello.tickets.R;
import org.json.JSONException;
import org.json.JSONObject;


public class TicketDetails extends AppCompatActivity {
    String result;
    TextView txtName, txtPhone, txtEmail, txtDescription, txtReference, txtCompany, txtChannel;
    LinearLayout card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        result          = getIntent().getStringExtra("data");
        txtName         = (TextView) findViewById(R.id.txtName);
        txtPhone        = (TextView) findViewById(R.id.txtPhone);
        txtEmail        = (TextView) findViewById(R.id.txtEmail);
        txtDescription  = (TextView) findViewById(R.id.txtDescription);
        txtReference    = (TextView) findViewById(R.id.txtReference);
        txtCompany      = (TextView) findViewById(R.id.txtCompany);
        txtChannel      = (TextView) findViewById(R.id.txtChannel);
        setData();
    }

    private void setData() {
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(result);

            JSONObject jsonObject2  = jsonObject1.getJSONObject("Result");

            if(jsonObject2.has("requester")) {
                JSONObject requesterObject = jsonObject2.getJSONObject("requester");
                String re_name = requesterObject.getString("name");
                String re_phone = requesterObject.getString("phone");
                String re_email = requesterObject.getString("email");
                txtName.setText(":" + re_name);
                txtPhone.setText(":" + re_phone);
                txtEmail.setText(":" + re_email);
            }
            String description          = jsonObject2.getString("description");
            String reference            = jsonObject2.getString("reference");
            String company              = jsonObject2.getString("company");
            String channel              = jsonObject2.getString("channel");

            txtDescription.setText(description);
            txtChannel.setText(channel);
            txtReference.setText(reference);
            txtCompany.setText(company);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
