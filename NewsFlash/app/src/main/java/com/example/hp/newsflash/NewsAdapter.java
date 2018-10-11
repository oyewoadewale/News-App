package com.example.hp.newsflash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oyewo Adewale on 10/10/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news at the given position in the list of news
        News currentNews = getItem(position);


        // Find the TextView with view ID title_text_view
        TextView newsTitleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        String title = currentNews.getTitle();
        // Display the headlines of the current news in that TextView
        newsTitleTextView.setText(title);


        // Find the TextView with view ID category_text_view
        TextView newsCategorytextView = (TextView) listItemView.findViewById(R.id.category_text_view);
        String category = currentNews.getSectionName();
        // Display the category/ section of news of the current news in that TextView
        newsCategorytextView.setText(category);

        // Find the TextView with view ID date_text_view
        TextView newsDatetextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        String date = currentNews.getDate();
        // Display the date of the of the current news in that TextView
        newsDatetextView.setText(date);

        // Find the TextView with view ID author_text_view
        TextView newsAuthortextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        String author = currentNews.getAuthor();

        //Check Author if empty show this message
        if(author.equals("")){
            author = getContext().getString(R.string.noauthor);
        }
        // Display the author of news of the current news in that TextView
        newsAuthortextView.setText(author);

        return listItemView;
    }
}