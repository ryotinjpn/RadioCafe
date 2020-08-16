package jp.co.se.android.RadioCafe;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ListActivity {

    /**
     * Called when the activity is first created.
     */

    public static final String RSS_FEED_URL_TOP = "http://radiocafe.jp/xml.php?t=episodes&app=2";

    public static final String RSS_FEED_URL_TOPICS = "http://radiocafe.jp/xml.php?blog_id=1&app=1";

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

    public static final String FACEBOOK_URL = "http://www.facebook.com/fm797radiocafe";

    public static final String TODAYPROGRAM_URL = "http://radiocafe.jp/schedule/";

    public static final String METHOD_URL = "http://radiocafe.jp/smartphone/";

    public static final String ABOUT_URL = "http://radiocafe.jp/about/";

    public static final String DISASTER_URL = "http://radiocafe.jp/disaster/";

    public static final String WADAI_URL = "http://comiradi.jp/?pp=episodes&latlng=";

    public static final String STATION_URL = "http://comiradi.jp/?pp=stations&latlng=";

    public static final String COMMUNITY_PODCAST_URL = "http://comiradi.jp/?pp=gen_url_episodes&mode=pref";

    public static final String COMMUNITY_RADIO_URL = "http://comiradi.jp/?pp=gen_url_stations&mode=pref";

    //番組RSS
    public static final String RSS_FEED_URL_HONNOSOMMELIER = "http://radiocafe.jp/200812001/xml.php?blog_id=35&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_KANGO = "http://radiocafe.jp/201006001/xml.php?blog_id=26&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_FUKURAJI = "http://radiocafe.jp/201504003/xml.php?blog_id=218&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_OTOME = "http://radiocafe.jp/201610001/xml.php?blog_id=266&t=episodes&app=1&num=6";

    public static final String RSS_FEED_URL_KUKANSOSEI = "http://radiocafe.jp/201507008/xml.php?blog_id=235&t=episodes&app=1&num=6";

    public static final String RSS_FEED_URL_SHIMOGAMOSEVEN = "http://radiocafe.jp/201211001/xml.php?blog_id=17&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_HEARTFULL = "http://radiocafe.jp/201707001/xml.php?blog_id=282&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_SANJYOMEITEN = "http://radiocafe.jp/201004002/xml.php?blog_id=23&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_NISHIKI = "http://radiocafe.jp/201003001/xml.php?blog_id=22&t=episodes&app=2&num=6";

    public static final String RSS_FEED_URL_GCOM = "http://radiocafe.jp/200304002/xml.php?blog_id=28&t=episodes&app=2&num=6";

    private ArrayList<Item> mItems;

    private RssListAdapter mAdapter;

    Timer timer;

    private boolean mode = false;

    //プリファレンス
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.list_header,
                (ViewGroup) findViewById(R.id.header_layout_root));
        lv.addHeaderView(header, null, false);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        customOverflowMenu();

        timer = new Timer();
        TimerTask timerTask = new MyTimerTask(this);
        timer.scheduleAtFixedRate(timerTask, 0, 60000);

        Button honnosommelier = (Button) findViewById(R.id.button_honnosommelier);
        honnosommelier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_HONNOSOMMELIER.toString());
                intent.putExtra("TITTLE", "本のソムリエ".toString());
                startActivity(intent);
            }

            ;
        });

        Button kango = (Button) findViewById(R.id.button_kango);
        kango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_KANGO.toString());
                intent.putExtra("TITTLE", "行列のできる訪問看護ステーション".toString());
                startActivity(intent);
            }

            ;
        });

        Button fukuraji = (Button) findViewById(R.id.button_fukuraji);
        fukuraji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_FUKURAJI.toString());
                intent.putExtra("TITTLE", "ふくらじ".toString());
                startActivity(intent);
            }

            ;
        });

        Button kukansosei = (Button) findViewById(R.id.button_kukansosei);
        kukansosei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_KUKANSOSEI.toString());
                intent.putExtra("TITTLE", "KYOTO space fountain ～きょうと空間創生術～".toString());
                startActivity(intent);
            }

            ;
        });

        Button otome = (Button) findViewById(R.id.button_otome);
        otome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL",
                        RSS_FEED_URL_OTOME.toString());
                intent.putExtra("TITTLE", "はるのと京子の乙女な時間".toString());
                startActivity(intent);
            }

            ;
        });

        Button shimogamoseven = (Button) findViewById(R.id.button_shimogamoseven);
        shimogamoseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_SHIMOGAMOSEVEN.toString());
                intent.putExtra("TITTLE", "下鴨セブン".toString());
                startActivity(intent);
            }

            ;
        });

        Button heartfull = (Button) findViewById(R.id.button_heartfull);
        heartfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_HEARTFULL.toString());
                intent.putExtra("TITTLE", "ハートフルジェネレーション".toString());
                startActivity(intent);
            }

            ;

        });

        Button sanjyomeiten = (Button) findViewById(R.id.button_sanjyomeiten);
        sanjyomeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_SANJYOMEITEN.toString());
                intent.putExtra("TITTLE", "おこしやす三条名店街".toString());
                startActivity(intent);
            }

            ;
        });

        Button gcom = (Button) findViewById(R.id.button_gcom);
        gcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_GCOM.toString());
                intent.putExtra("TITTLE", "京からGreenコミュニケーション！".toString());
                startActivity(intent);
            }

            ;
        });

        Button nishiki = (Button) findViewById(R.id.button_nishiki);
        nishiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        ProgramIntent.class);
                intent.putExtra("URL", RSS_FEED_URL_NISHIKI.toString());
                intent.putExtra("TITTLE", "錦市場でお買い物".toString());
                startActivity(intent);
            }

            ;
        });




        ImageButton litenradio_button = (ImageButton) findViewById(R.id.litenradio_button);
        litenradio_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PackageManager pm = getPackageManager();
                        try {
                            String packageName = "jp.co.mti.android.netradio";
                            pm.getApplicationInfo(packageName, 0);
                            AlertFragment_Litenradio dialog = new AlertFragment_Litenradio();
                            dialog.show(getFragmentManager(), "dialog");
                        } catch (PackageManager.NameNotFoundException e) {
                            AlertFragment_Litenradio2 dialog = new AlertFragment_Litenradio2();
                            dialog.show(getFragmentManager(), "dialog");
                        }
                    }
                });

        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();

        mAdapter = new RssListAdapter(this, mItems);

        // タスクを起動する
        RssParserTask task = new RssParserTask(this, mAdapter);
        task.execute(RSS_FEED_URL_TOP);
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

    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position - 1);
        Intent intent = new Intent(this, Webview.class);
        String Link = item.getLink();
        Link = Link.replaceAll("amp;", "");
        intent.putExtra("LINK", Link);
        startActivity(intent);
    }

    public void OnairName() {
        String url = "http://radiocafe.jp/wp-content/themes/main/get_music_title4app.php";
        AsyncHttpRequest task = new AsyncHttpRequest(this);
        task.owner = this;
        task.execute(url);
    }

    public void Live(View view) {
        TextView textView = (TextView) findViewById(R.id.fm797_live);
        if (view.getId() == R.id.fm797_live) {
            if (!mode) {
                // サービスの起動
                Intent intent = new Intent(MainActivity.this,
                        Livestreaming.class);
                startService(intent);
                textView.setText("■　止める");
                mode = true;
            } else {
                // サービスの停止
                Intent intent = new Intent(MainActivity.this,
                        Livestreaming.class);
                stopService(intent);
                textView.setText("放送を聴く");
                mode = false;
            }
        }
    }

    public void Topics(View view) {
        Intent intent = new Intent(getApplication(),
                TopicsIntent.class);
        intent.putExtra("URL", RSS_FEED_URL_TOPICS.toString());
        intent.putExtra("TITTLE", "ラジオカフェ・トピックス".toString());
        startActivity(intent);
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
                            Intent intent2 = new Intent(activity, Gps.class
                            );
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
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
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
            builder.setPositiveButton("いいえ", new DialogInterface.OnClickListener()
            {
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

    @SuppressLint("ValidFragment")
    public class AlertFragment_Litenradio extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("LitenRadioを起動しますか？");
            builder.setMessage("京都三条ラジオカフェの聴き方\n①ジャンル/カテゴリ\n②全国のラジオ局\n③近畿\n④京都三条ラジオカフェを選択");
            builder.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        PackageManager pm = getPackageManager();

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String packageName = "jp.co.mti.android.netradio";
                            //pm.getApplicationInfo(packageName, 0);
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

    public static class AlertFragment_Litenradio2 extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("LitenRadioをダウンロードしますか？");
            builder.setMessage("京都三条ラジオカフェの聴き方\n①ジャンル/カテゴリ\n②全国のラジオ局\n③近畿\n④京都三条ラジオカフェを選択");
            builder.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=jp.co.mti.android.netradio");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    }
            )
            ;
            builder.setPositiveButton("いいえ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            return builder.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String providers = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        switch (item.getItemId()) { // if使うとエラー（itemがInt形式なため）
            case android.R.id.home:   // アプリアイコン（ホームアイコン）を押した時の処理
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
            case R.id.facebook:
                Intent intent8 = new Intent(this, Webview.class);
                intent8.putExtra("URL", FACEBOOK_URL.toString());
                intent8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent8);
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
                Intent intent14 = new Intent(this, MainActivity.class);
                intent14.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent14);
                break;
        }
        return true;
    }
}