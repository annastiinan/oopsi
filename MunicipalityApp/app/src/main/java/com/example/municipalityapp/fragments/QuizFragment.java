package com.example.municipalityapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.municipalityapp.PicData;
import com.example.municipalityapp.PicDataRetriever;
import com.example.municipalityapp.R;

public class QuizFragment extends Fragment {

    private ImageView nicePicImageView;
    private PicData picData;
    private PicDataRetriever picDataRetriever;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picData = new PicData();
        picDataRetriever = new PicDataRetriever();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        nicePicImageView = view.findViewById(R.id.nicePicId);
        loadRandomCatImage();

        return view;
    }

    private void loadRandomCatImage() {
        picDataRetriever.getPicData(bitmap -> {
            picData.setBitmap(bitmap);
            nicePicImageView.setImageBitmap(bitmap);
        });
    }
}