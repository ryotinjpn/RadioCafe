package jp.co.se.android.RadioCafe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView image;
    private ProgressBar progress;
    private String tag;

    public DownloadTask(ImageView _image, ProgressBar _progress) {
        // 対象の項目を保持しておく
        image = _image;
        progress = _progress;
        tag = image.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // ここでHttp経由で画像を取得します。取得後Bitmapで返します。
        try {
            // キャッシュより画像データを取得
            Bitmap image = ImageCache.getImage(params[0]);
            if (image == null) {
                // 読み込み用のオプションオブジェクトを生成
                BitmapFactory.Options options = new BitmapFactory.Options();
                // この値をtrueにすると実際には画像を読み込まず、
                // 画像のサイズ情報だけを取得することができます。
                options.inJustDecodeBounds = true;

                // 画像ファイル読み込み
                // ここでは上記のオプションがtrueのため実際の画像は読み込まれないです。

                // 読み込んだサイズはoptions.outWidthとoptions.outHeightに格納されるので、その値から読み込む際の縮尺を計算します。
                // このサンプルではどんな大きさの画像でもHVGAに収まるサイズを計算しています。
                //int scaleW = options.outWidth / 320 + 7;
                //int scaleH = options.outHeight / 180 + 7;

                // 縮尺は整数値で、2なら画像の縦横のピクセル数を1/2にしたサイズ。3なら1/3にしたサイズで読み込まれます。
                //int scale = Math.max(scaleW, scaleH);

                // 今度は画像を読み込みたいのでfalseを指定
                options.inJustDecodeBounds = false;

                // 先程計算した縮尺値を指定
                //options.inSampleSize = scale;

                // これで指定した縮尺で画像を読み込めます。もちろん容量も小さくなるので扱いやすいです。
                // image = BitmapFactory.decodeFile(imageIs, options);
                // キャッシュにデータが存在しない場合はwebより画像データを取得

                URL imageUrl = new URL(params[0]);
                InputStream imageIs;
                imageIs = imageUrl.openStream();
                image = BitmapFactory.decodeStream(imageIs, null, options);
                // 取得した画像データをキャッシュに保持
                ImageCache.setImage(params[0], image);
            }
            return image;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // Tagが同じものか確認して、同じであれば画像を設定する
        // （Tagの設定をしないと別の行に画像が表示されてしまう）
        if (tag.equals(image.getTag())) {
            if (result != null) {
                // 画像の設定
                image.setImageBitmap(result);
            } else {
                // エラーの場合は×印を表示
                image.findViewById(R.drawable.icon);
            }
            // プログレスバーを隠し、取得した画像を表示
            progress.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
        }
    }
}
