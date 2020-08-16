package jp.co.se.android.RadioCafe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adminstrator on 2014/10/09.
 */
public class RssListAdapter_Topics extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private TextView mDescription;


    public RssListAdapter_Topics(Context context, List<Item> objects) {
        super(context, 0, objects);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_row2, null);
        }

        // 現在参照しているリストの位置からItemを取得する
        final Item item = this.getItem(position);
        if (item != null) {
            // Itemから必要なデータを取り出し、それぞれTextViewにセットする
            String title = item.getTitle().toString();
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mTitle.setText(title);
            String description = item.getDescription().toString();
            mDescription = (TextView) view.findViewById(R.id.item_description);
            mDescription.setText(description);

        }
        return view;
    }
}