package org.hello.tickets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.hello.tickets.CustomAlertDialog;
import org.hello.tickets.R;
import org.hello.tickets.activity.TicketDetails;
import org.hello.tickets.common.CommonInfo;
import org.hello.tickets.common.MessageList;
import org.hello.tickets.models.ItemBean;
import org.hello.tickets.serverTask.ProgressGETServerTask;

import java.util.ArrayList;

/**
 * Created by dhanushi_s on 7/5/2017.
 */

public class ItemAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<ItemBean> data = new ArrayList();

    public ItemAdapter(Context context, int layoutResourceId, ArrayList<ItemBean> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        final ItemBean item = data.get(position);
        final String  id= item.getId();

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.subject   = (TextView) row.findViewById(R.id.txtSubject);
            holder.priority  = (TextView) row.findViewById(R.id.txPriority);
            holder.status    = (TextView) row.findViewById(R.id.txtStatus);
            holder.type      = (TextView) row.findViewById(R.id.txtType);
            holder.card      = (CardView) row.findViewById(R.id.card);

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   sendRequest(id);
                }
            });

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.subject.setText(item.getSubject());
        holder.status.setText(": "+item.getStatus());
        holder.priority.setText(": "+item.getPriority());
        holder.type.setText(": "+item.getType());

        if(position%2 == 0)
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.cardColor1));
        else
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.cardColor2));
        return row;
    }

    private static class ViewHolder {
        TextView subject, type, priority, status;
        CardView card;
    }


    private void sendRequest(String id){
        String url = CommonInfo.TICKET_DETAILS +id+"/Details";

        try {
            new ProgressGETServerTask(context, new ProgressGETServerTask.OnRequestCompleted() {
                @Override
                public void OnRequestCompleted(String cmd, final CustomAlertDialog dialog) {
                    if (!cmd.equals("error")) {
                        dialog.dismiss();
                        if (cmd.length() > 0) {
                            Log.i("xxxxxxxxxxxxx ","............."+ cmd);

                            Intent intent = new Intent(context, TicketDetails.class);
                            intent.putExtra("data", cmd);
                            context.startActivity(intent);

                        } else {
                            dialog.changeAlertType(CustomAlertDialog.ERROR);
                            dialog.setErrorMessage(MessageList.INVALIED_RESPONSE);
                            dialog.setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(CustomAlertDialog sweetAlertDialog) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }else{
                        dialog.changeAlertType(CustomAlertDialog.ERROR);
                        dialog.setErrorMessage(MessageList.CONNECTION_ERROR);
                        dialog.setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(CustomAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
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
