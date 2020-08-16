package jp.co.se.android.RadioCafe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RssListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private TextView mProgram_title;
    private TextView mDescription;
    private Button mButton;
    private Button mButton2;
    private ImageView imageView;
    private ProgressBar waitBar;

    public RssListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_row, null);
        }

        // 現在参照しているリストの位置からItemを取得する
        final Item item = this.getItem(position);
        if (item != null) {
            // Itemから必要なデータを取り出し、それぞれTextViewにセットする
            String program_title = item.getProgram_title().toString();
            mProgram_title = (TextView) view.findViewById(R.id.item_program_title);
            mProgram_title.setText(program_title);

            String title = item.getTitle().toString();
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mTitle.setText(title);

            String description = item.getDescription().toString();
            mDescription = (TextView) view.findViewById(R.id.description);
            mDescription.setText(description);

            mButton = (Button) view.findViewById(R.id.play_button);
            mButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(item.getSound());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "audio/mp3");
                    v.getContext().startActivity(intent);
                }
            });

            imageView = (ImageView) view
                    .findViewById(R.id.ImageThumb);
            waitBar = (ProgressBar) view.findViewById(R.id.WaitBar);

            // 画像を隠し、プログレスバーを表示
            waitBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);

            // リスト表示用画像のURLを取得
            String thumbUrl = item.getImage().toString();

            imageView.setImageResource(R.drawable.radiocaferogo);

            // 画像読込
            try {
                imageView.setTag(thumbUrl);
                // AsyncTaskは１回しか実行できない為、毎回インスタンスを生成
                DownloadTask task = new DownloadTask(imageView, waitBar);
                task.execute(thumbUrl);
            } catch (Exception e) {
                waitBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }

            /*final String movieUrl = item.getMovie().toString();
            mButton2 = (Button) view.findViewById(R.id.movie_button);
            mButton2.setVisibility(View.GONE);
            if (movieUrl.equals("")) {
                mButton2.setVisibility(View.GONE);
            } else {
                mButton2.setVisibility(View.VISIBLE);
                mButton2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(movieUrl));
                        //intent.setPackage("com.google.android.youtube");
                        v.getContext().startActivity(intent);
                    }
                });

            }*/
        }
        return view;
    }
}
