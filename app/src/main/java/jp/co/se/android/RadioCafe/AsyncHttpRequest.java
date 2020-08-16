package jp.co.se.android.RadioCafe;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Adminstrator on 2014/10/02.
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, String> {
    public Activity owner;
    private String ReceiveStr;

    public AsyncHttpRequest(Activity activity) {
        owner = activity;
    }

    @Override
    protected String doInBackground(String... url) {
        try {
            HttpGet httpGet = new HttpGet(url[0]);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpGet.setHeader("Connection", "Keep-Alive");

            HttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                throw new Exception("");
            } else {
                ReceiveStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        TextView textView = (TextView) owner.findViewById(R.id.onair_name);
        textView.setText(ReceiveStr);
    }

}