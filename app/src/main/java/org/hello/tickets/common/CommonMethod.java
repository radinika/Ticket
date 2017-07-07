package org.hello.tickets.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.hello.tickets.CustomAlertDialog;

/**
 * Created by dhanushi_s on 7/6/2017.
 */

public class CommonMethod {
    private static CommonMethod comMethod;

    public CommonMethod() {
    }

    public static synchronized CommonMethod getInstance(){

        if (comMethod == null) {
            comMethod = new CommonMethod();
        }

        return comMethod;
    }

    public void displayToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void networkEnabledialog(Context context){
        new CustomAlertDialog(context, MessageList.OOPS ,MessageList.NO_NETWORK).setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(CustomAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo    = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
