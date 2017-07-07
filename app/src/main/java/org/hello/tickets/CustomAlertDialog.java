package org.hello.tickets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomAlertDialog extends Dialog {
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private Dialog dialogBuilder;
    private Button btnCancel, btnOk;
    private LinearLayout progress_layout, layout_dialog;
    private View mDialogView;
    TextView txtMsg1, txtMsg2, txtTitle;
    public static int SUCCESS = 1, PROGRESS = 2, ERROR = 3;

    public interface OnSweetClickListener {
        public void onClick(CustomAlertDialog sweetAlertDialog);
    }

    public CustomAlertDialog(Context context, String title, String text) {
        super(context, R.style.alert_dialog);
        dialogBuilder = new Dialog(context);
        final LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View dialogView = mInflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.setContentView(dialogView);
        dialogBuilder.setCancelable(false);

        mDialogView     = getWindow().getDecorView().findViewById(android.R.id.content);
        txtTitle        = (TextView) dialogView.findViewById(R.id.txtTitle);
        btnOk           = (Button) dialogView.findViewById(R.id.ok);
        btnCancel       = (Button) dialogView.findViewById(R.id.cancel);
        progress_layout = (LinearLayout) dialogBuilder.findViewById(R.id.layout_progress);
        layout_dialog   = (LinearLayout) dialogBuilder .findViewById(R.id.layout_dialog);
        txtMsg1         = (TextView) dialogView.findViewById(R.id.txtMsg1);
        txtMsg2         = (TextView) dialogView.findViewById(R.id.txtMsg2);

        txtMsg2.setText(text);
        txtTitle.setText(title);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmClickListener.onClick(CustomAlertDialog.this);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                //mCancelClickListener.onClick(CustomAlertDialog.this);
            }
        });
        dialogBuilder.getWindow().getAttributes().windowAnimations = R.style.AlertDialogTheme;
        dialogBuilder.show();
        Window dialogWindow = dialogBuilder.getWindow();
        dialogWindow.setLayout(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setCancelButton(Context context, String text){
        btnCancel.setVisibility(View.VISIBLE);
        btnOk.setText(text);
    }


    public void setMessage(String message){
       txtMsg1.setText(message);
    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }

    public void setErrorMessage(String errorMessage){
        txtMsg2.setText(errorMessage);
    }


    public void changeAlertType(int type){
        if(type == ERROR){
            layout_dialog.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.GONE);
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);

        }else if(type == SUCCESS){
            layout_dialog.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.GONE);
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mConfirmClickListener.onClick(CustomAlertDialog.this);
                }
            });
        }
        else if(type == PROGRESS){
            layout_dialog.setVisibility(View.GONE);
            progress_layout.setVisibility(View.VISIBLE);
        }
    }

    public void dismiss(){
       dialogBuilder.dismiss();
    }


    public CustomAlertDialog setCancelClickListener (OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public CustomAlertDialog setConfirmClickListener (OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }
}