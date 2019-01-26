package br.com.paulohpfranco.aplicativonoticias;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News {

    private static String LOG_TAG = News.class.getSimpleName();

    private String mNewsTitle;
    private String mNewsSectionName;
    private String mNewsDate;
    private String mNewsAuthor;
    private String mNewsURL;

    public News(String newsTitle, String newsSectionName, String newsDate, String newsAuthor, String newsURL) {
        mNewsTitle = newsTitle;
        mNewsSectionName = newsSectionName;
        mNewsAuthor = newsAuthor;
        mNewsURL = newsURL;

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(newsDate);
            mNewsDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getNewsSectionName() {
        return mNewsSectionName;
    }

    public String getmNewsDate() {
        return mNewsDate;
    }

    public String getmNewsAuthor() { return mNewsAuthor; }

    public String getNewsURL() {
        return mNewsURL;
    }
}
