package org.hello.tickets.serverTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.hello.tickets.CustomAlertDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class ProgressPOSTServerTask extends AsyncTask<String, String, String> {

    private final String REQUEST_METHOD = "POST";
    private final int READ_TIMEOUT      = 15000;
    private final int CONNECTION_TIMEOUT = 15000;
    CustomAlertDialog dialog;
    OnRequestCompleted listener;
    Context context;
    String error = "error";
    int statusCode;

    public interface OnRequestCompleted {
        void OnRequestCompleted(String cmd, CustomAlertDialog dialog);
    }

    public ProgressPOSTServerTask(Context context, OnRequestCompleted listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        dialog = new CustomAlertDialog(context, "Oops","");
        dialog.changeAlertType(CustomAlertDialog.PROGRESS);
        dialog.setMessage("Please wait...");
    }


    @Override
    protected String doInBackground(String... params) {

        String stringUrl = params[0];
        String result = "";
        String inputLine;
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params[1]);
            writer.flush();

            statusCode = connection.getResponseCode();
            Log.i("xxxxxxxxxxxxx ","............."+ statusCode);

            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
            }else
                result = error;

        } catch (SocketException e) {
            e.printStackTrace();
            result = error;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            result = error;
        } catch (Exception e) {
            result = error;
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String result) {
        listener.OnRequestCompleted(result, dialog);
    }
}
