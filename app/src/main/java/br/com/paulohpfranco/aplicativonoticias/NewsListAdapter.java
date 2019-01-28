package br.com.paulohpfranco.aplicativonoticias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<News> {

    public NewsListAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        // Find the TextView in the earthquake_list_item.xml layout with the ID newsTitle.
        TextView textViewTitle = listItemView.findViewById(R.id.newsTitle);
        textViewTitle.setText(currentNews.getNewsTitle());

        // Find the TextView in the earthquake_list_item.xml layout with the ID newsAuthor.
        TextView textViewAuthor = listItemView.findViewById(R.id.newsAuthor);

        if(currentNews.getmNewsAuthor() != null) {
            textViewAuthor.setText(currentNews.getmNewsAuthor());
        } else {
            textViewAuthor.setVisibility(View.GONE);
        }

        // Find the TextView in the earthquake_list_item.xml layout with the ID newsSection.
        TextView textViewSection = listItemView.findViewById(R.id.newsSection);
        textViewSection.setText(currentNews.getNewsSectionName());

        // Find the TextView in the earthquake_list_item.xml layout with the ID newsSection.
        TextView textViewDate = listItemView.findViewById(R.id.newsDate);

        if(currentNews.getmNewsDate() != null) {
            textViewDate.setText(currentNews.getmNewsDate());
        } else {
            textViewDate.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
