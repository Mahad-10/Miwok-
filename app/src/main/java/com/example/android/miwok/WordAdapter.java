 package com.example.android.miwok;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {
    private static final String LOG_TAG = Word.class.getSimpleName();
    private int mBackgroundColor;
    private int imageId;

    public WordAdapter(Activity context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    public WordAdapter(Activity context, ArrayList<Word> words, int background_color){
        super(context, 0, words);
        mBackgroundColor = background_color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        miwokTextView.setText(currentWord.getmMiwokTranslation());

        TextView englishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        englishTextView.setText(currentWord.getmDefaultTranslation());

        ImageView iconView = (ImageView)listItemView.findViewById(R.id.list_item_icon);
        if(currentWord.hasImage()){
            iconView.setImageResource(currentWord.getmImageResourceId());
            iconView.setVisibility(View.VISIBLE);
        }
        else
            iconView.setVisibility(View.GONE);

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mBackgroundColor);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }





}



