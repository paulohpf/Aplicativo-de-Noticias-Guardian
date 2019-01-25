package br.com.paulohpfranco.aplicativonoticias;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /**
     * Tag para mensagens de log
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * URL da busca
     */
    private String mUrl;

    /**
     * Constrói um novo {@link NewsLoader}.
     *
     * @param context O contexto da activity
     * @param url     A URL de onde carregaremos dados
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        ArrayList<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
