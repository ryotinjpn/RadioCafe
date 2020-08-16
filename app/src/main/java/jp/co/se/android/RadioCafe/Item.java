package jp.co.se.android.RadioCafe;

public class Item {
    // 記事のタイトル
    private CharSequence mTitle;
    private String mProgram_title;
    // 記事の本文
    private String mImage;
    private String mDescription;
    private String mLink;
    private String mSound;
    private String mMovie;
    private String mEnclosure;

    public Item() {
        mTitle = "";
        mProgram_title = "";
        mImage = "";
        mDescription = "";
        mSound = "";
        mMovie = "";
        mEnclosure = "";
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public String getProgram_title() {
        return mProgram_title;
    }

    public void setProgram_title(String program_title) {
        mProgram_title = program_title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getSound() {
        return mSound;
    }

    public void setSound(String sound) {
        mSound = sound;
    }

    public String getMovie() {
        return mMovie;
    }

    public void setMovie(String movie) {
        mMovie = movie;
    }

    public String getEnclosure() {
        return mEnclosure;
    }

    public void setEnclosure(String enclosure) {
        mEnclosure = enclosure;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }


}
