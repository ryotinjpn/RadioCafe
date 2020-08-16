package jp.co.se.android.RadioCafe;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.lang.reflect.Field;

public class Pdfviewer extends Activity {
    private WebView web;

    Activity activity; // instead of context we can use activity

    String GoogleDocs = "http://docs.google.com/gview?embedded=true&url=";

    public static final String COMMUNITY_URL = "http://radiocafe.jp/?tt=com";

    public static final String ENVIRONMENTURL_URL = "http://radiocafe.jp/?tt=env";

    public static final String HEALTH_URL = "http://radiocafe.jp/?tt=med";

    public static final String JOURNALISM_URL = "http://radiocafe.jp/?tt=jou";

    public static final String WORLD_URL = "http://radiocafe.jp/?tt=glo";

    public static final String KYOTO_URL = "http://radiocafe.jp/?tt=kyo";

    public static final String CULTURE_URL = "http://radiocafe.jp/?tt=cul";

    public static final String STUDENT_URL = "http://radiocafe.jp/?tt=edu";

    public static final String MUSIC_URL = "http://radiocafe.jp/?tt=mus";

    public static final String TALK_URL = "http://radiocafe.jp/?tt=tal";

    public static final String ETC_URL = "http://radiocafe.jp/?tt=etc";

    public static final String NOWONAIR_URL = "https://twitter.com/kyotofm797";

    public static final String TWITTER_URL = "http://twitter.com/#!/fm797radiocafe";

    public static final String USTREAM_URL = "http://www.ustream.tv/channel/fm797-radiocafe-live-program-from-kyoto";

    public static final String ABOUT_URL = "http://radiocafe.jp/about/";

    public static final String DISASTER_URL = "http://radiocafe.jp/disaster/";

    public static final String TODAYPROGRAM_URL = "http://radiocafe.jp/schedule/";

    public static final String METHOD_URL = "http://radiocafe.jp/smartphone/";

    public static final String WADAI_URL = "http://comiradi.jp/?pp=episodes&latlng=";

    public static final String STATION_URL = "http://comiradi.jp/?pp=stations&latlng=";

    public static final String COMMUNITY_PODCAST_URL = "http://comiradi.jp/?pp=gen_url_episodes&mode=pref";

    public static final String COMMUNITY_RADIO_URL = "http://comiradi.jp/?pp=gen_url_stations&mode=pref";

    //プリファレンス
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        customOverflowMenu();

        Intent intent = getIntent();
        String PDF_URL = intent.getStringExtra("PDF_URL");

        setContentView(R.layout.webview);

        activity = this;
        web = (WebView) findViewById(R.id.webview);
        // following lines are to show the loader untile downloading the pdf
        // file for view.
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView web, String url) {
                if (url.endsWith(".mp3")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "audio/mp3");
                    startActivity(intent);
                }
            }
        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setDisplayZoomControls(false);
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                View pBarWrapper = findViewById(R.id.progress_bar_wrapper);
                ProgressBar pBar = (ProgressBar) findViewById(R.id.progress_bar);
                pBar.setProgress(newProgress);

                if (newProgress == 100) {
                    pBarWrapper.setVisibility(View.GONE);
                } else {
                    pBarWrapper.setVisibility(View.VISIBLE);
                }
            }
        });

        web.loadUrl(GoogleDocs + PDF_URL);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 端末のBACKキーで一つ前のページヘ戻る
        if(keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,  event);
    }

    private void customOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);

                // 物理メニューキーの有無フラグ
                // false を設定するとactionbarのoverflowメニューが必ず表示される
                // true  を設定するとactionbarのoverflowメニューは表示されない
                // 物理メニューキーのない端末 かつ true 設定すると、
                // メニュー出せなくなる。詰み。
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AlertFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("位置情報取得を許可にして下さい");
            builder.setMessage("ネットワーク経由の位置情報設定をONにして下さい。");
            builder.setPositiveButton("位置情報の設定を開く",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }
            );
            return builder.create();
        }
    }

    @SuppressLint("ValidFragment")
    public class AlertFragment_start_station extends DialogFragment {
        String providers = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("このアプリは位置情報を取得します");
            builder.setMessage("現在の位置情報を使って放送局や番組を探します");
            builder.setNegativeButton("許可する\n※次回以降表示しない",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (providers.indexOf("network", 0) < 0) {
                                AlertFragment dialog_2 = new AlertFragment();
                                dialog_2.show(getFragmentManager(), "dialog");
                            } else {
                                AlertFragment_Comiradi dialog_1 = new AlertFragment_Comiradi();
                                dialog_1.show(getFragmentManager(), "dialog");
                            }
                        }
                    }
            );
            builder.setPositiveButton("許可しない", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    editor.putBoolean("Launched", false);
                    editor.commit();
                }
            });
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // Disable Back key and Search key
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                        case KeyEvent.KEYCODE_SEARCH:
                            return true;
                        default:
                            return false;
                    }
                }
            });
            return builder.create();
        }
    }

    public static class AlertFragment_Comiradi extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            CharSequence[] items = {"近くの話題＜Podcast＞を探す", "近くのコミュニティFM局を探す", "県別Podcast一覧", "県別放送局一覧"};

            final Activity activity = getActivity();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(activity, Gps.class);
                            intent.putExtra("URL", WADAI_URL.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent2 = new Intent(activity, Gps.class);
                            intent2.putExtra("URL", STATION_URL.toString());
                            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            break;
                        case 2:
                            Intent intent3 = new Intent(activity, Webview.class);
                            intent3.putExtra("URL", COMMUNITY_PODCAST_URL.toString());
                            intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                            break;
                        case 3:
                            Intent intent4 = new Intent(activity, Webview.class);
                            intent4.putExtra("URL", COMMUNITY_RADIO_URL.toString());
                            intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent4);
                            break;
                        default:
                            break;
                    }
                }
            });
            return builder.create();
        }
    }

    @SuppressLint("ValidFragment")
    public class AlertFragment_TuneIn extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("TuneIn Radioを起動しますか？");
            builder.setMessage("ストリーミング放送の聴取");
            builder.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        PackageManager pm = getPackageManager();

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String packageName = "tunein.player";
                            Intent intent = pm.getLaunchIntentForPackage(packageName);
                            startActivityForResult(intent, 1);
                            startActivity(intent);

                        }
                    }
            );
            builder.setPositiveButton("いいえ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            return builder.create();
        }
    }

    @SuppressLint("ValidFragment")
    public class AlertFragment_TuneIn2 extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("TuneIn Radioをダウンロードしますか？");
            builder.setMessage("ストリーミング放送の聴取");
            builder.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=tunein.player");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }
            );
            builder.setPositiveButton("いいえ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            return builder.create();
        }
    }

    public static class AlertFragment_Genre extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            CharSequence[] items = {"地域・まちづくり", "環境・防災", "健康・医療・福祉", "報道・ジャーナリズム", "世界", "京都・観光", "文化・伝統", "学生", "音楽", "トーク", "その他"};

            final Activity activity = getActivity();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(activity, Webview.class);
                            intent.putExtra("URL", COMMUNITY_URL.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            break;
                        case 1:
                            Intent intent2 = new Intent(activity, Webview.class);
                            intent2.putExtra("URL", ENVIRONMENTURL_URL.toString());
                            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            break;
                        case 2:
                            Intent intent3 = new Intent(activity, Webview.class);
                            intent3.putExtra("URL", HEALTH_URL.toString());
                            intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                            break;
                        case 3:
                            Intent intent4 = new Intent(activity, Webview.class);
                            intent4.putExtra("URL", JOURNALISM_URL.toString());
                            intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent4);
                            break;
                        case 4:
                            Intent intent5 = new Intent(activity, Webview.class);
                            intent5.putExtra("URL", WORLD_URL.toString());
                            intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent5);
                            break;
                        case 5:
                            Intent intent6 = new Intent(activity, Webview.class);
                            intent6.putExtra("URL", KYOTO_URL.toString());
                            intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent6);
                            break;
                        case 6:
                            Intent intent7 = new Intent(activity, Webview.class);
                            intent7.putExtra("URL", CULTURE_URL.toString());
                            intent7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent7);
                            break;
                        case 7:
                            Intent intent8 = new Intent(activity, Webview.class);
                            intent8.putExtra("URL", STUDENT_URL.toString());
                            intent8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent8);
                            break;
                        case 8:
                            Intent intent9 = new Intent(activity, Webview.class);
                            intent9.putExtra("URL", MUSIC_URL.toString());
                            intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent9);
                            break;
                        case 9:
                            Intent intent10 = new Intent(activity, Webview.class);
                            intent10.putExtra("URL", TALK_URL.toString());
                            intent10.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent10);
                            break;
                        case 10:
                            Intent intent11 = new Intent(activity, Webview.class);
                            intent11.putExtra("URL", ETC_URL.toString());
                            intent11.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent11);
                            break;
                        default:
                            break;
                    }
                }
            });
            return builder.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String URL = web.getUrl();
        String providers = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        switch (item.getItemId()) { // if使うとエラー（itemがInt形式なため）
            case android.R.id.home:   // アプリアイコン（ホームアイコン）を押した時の処理
                finish();
                break;
            case R.id.comiradi:
                preference = getSharedPreferences("Preference Name", MODE_PRIVATE);
                editor = preference.edit();
                if (preference.getBoolean("Launched", false) == false) {
                    //初回起動時の処理
                    AlertFragment_start_station dialog = new AlertFragment_start_station();
                    dialog.show(getFragmentManager(), "dialog");
                    //プリファレンスの書き変え
                    editor.putBoolean("Launched", true);
                    editor.commit();
                } else {
                    //二回目以降の処理
                    if (providers.indexOf("network", 0) < 0) {
                        AlertFragment dialog = new AlertFragment();
                        dialog.show(getFragmentManager(), "dialog");
                    } else {
                        AlertFragment_Comiradi dialog2 = new AlertFragment_Comiradi();
                        dialog2.show(getFragmentManager(), "dialog");
                    }
                }
                return true;
            case R.id.genre:
                AlertFragment_Genre dialog1 = new AlertFragment_Genre();
                dialog1.show(getFragmentManager(), "dialog");
                return true;
            case R.id.onair:
                Intent intent1 = new Intent(this, Webview.class);
                intent1.putExtra("URL", NOWONAIR_URL.toString());
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return true;
            case R.id.today_program:
                Intent intent2 = new Intent(this, Webview.class);
                intent2.putExtra("URL", TODAYPROGRAM_URL.toString());
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                return true;
            case R.id.method:
                Intent intent3 = new Intent(this, Webview.class);
                intent3.putExtra("URL", METHOD_URL.toString());
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                return true;
            case R.id.about:
                Intent intent4 = new Intent(this, Webview.class);
                intent4.putExtra("URL", ABOUT_URL.toString());
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                return true;
            case R.id.disaster:
                Intent intent5 = new Intent(this, Webview.class);
                intent5.putExtra("URL", DISASTER_URL.toString());
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent5);
                return true;
            case R.id.tunein_apk:
                PackageManager pm = getPackageManager();
                try {
                    String packageName = "tunein.player";
                    pm.getApplicationInfo(packageName, 0);
                    AlertFragment_TuneIn dialog = new AlertFragment_TuneIn();
                    dialog.show(getFragmentManager(), "dialog");
                } catch (PackageManager.NameNotFoundException e) {
                    AlertFragment_TuneIn2 dialog = new AlertFragment_TuneIn2();
                    dialog.show(getFragmentManager(), "dialog");
                }
                return true;
            case R.id.ustream:
                Intent intent6 = new Intent(this, Webview.class);
                intent6.putExtra("URL", USTREAM_URL.toString());
                intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent6);
                return true;
            case R.id.twitter:
                Intent intent9 = new Intent(this, Webview.class);
                intent9.putExtra("URL", TWITTER_URL.toString());
                intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent9);
                return true;
            case R.id.description:
                Intent intent10 = new Intent(this, Description.class);
                intent10.putExtra("URL", TWITTER_URL.toString());
                intent10.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent10);
                return true;
            case R.id.mail:
                Intent intent11 = new Intent();
                intent11.setAction(Intent.ACTION_SEND);
                intent11.setType("message/rfc822");
                intent11.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"info@radiocafe.jp"});
                try {
                    startActivity(Intent.createChooser(intent11,
                            "ラジオカフェにメールを送ります"));
                } catch (android.content.ActivityNotFoundException e) { // 該当するActivityがないときの処理
                }
                return true;
            case R.id.reload:
                web.loadUrl(URL);
                return true;
            case R.id.share:
                Intent intent13 = new Intent();
                intent13.setAction(Intent.ACTION_SEND);
                intent13.setType("text/plain");
                intent13.putExtra(Intent.EXTRA_TEXT, URL);
                try {
                    startActivity(Intent.createChooser(intent13, "友達に知らせます"));
                } catch (android.content.ActivityNotFoundException e) {
                }
                break;
        }
        return true;
    }

    protected void onDestroy() {
        web.destroy();
        super.onDestroy();
    }
}
