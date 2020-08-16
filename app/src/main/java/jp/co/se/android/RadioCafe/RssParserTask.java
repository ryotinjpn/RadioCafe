package jp.co.se.android.RadioCafe;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RssParserTask extends AsyncTask<String, Integer, RssListAdapter> {
    private MainActivity mActivity;
    private RssListAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    // コンストラクタ
    public RssParserTask(MainActivity activity, RssListAdapter adapter) {
        mActivity = activity;
        mAdapter = adapter;
    }

    // タスクを実行した直後にコールされる
    @Override
    protected void onPreExecute() {
        // プログレスバーを表示する
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setMessage("Now Loading...");
        mProgressDialog.show();
    }

    // バックグラウンドにおける処理を担う。タスク実行時に渡された値を引数とする
    @Override
    protected RssListAdapter doInBackground(String... params) {
        RssListAdapter result = null;
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            URL url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();
            result = parseXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ここで返した値は、onPostExecuteメソッドの引数として渡される
        return result;
    }

    // メインスレッド上で実行される
    @Override
    protected void onPostExecute(RssListAdapter result) {
        mProgressDialog.dismiss();
        mActivity.setListAdapter(result);
    }

    // XMLをパースする
    public RssListAdapter parseXml(InputStream is) throws IOException,
            XmlPullParserException {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Item currentItem = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            currentItem = new Item();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("program_title")) {
                                currentItem.setProgram_title(parser.nextText());
                            } else if (tag.equals("link")) {
                                currentItem.setLink(parser.nextText());
                            } else if (tag.equals("description")) {
                                currentItem.setDescription(parser.nextText());
                            } else if (tag.equals("image")) {
                                currentItem.setImage(parser.nextText());
                            } else if (tag.equals("sound")) {
                                currentItem.setSound(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            mAdapter.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAdapter;
    }
}