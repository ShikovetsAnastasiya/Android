package com.example.quiiiz.game;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.quiiiz.models.Question;

import java.util.ArrayList;

public class QuestionAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Question> questions;
    private int optionsNumber;
    private OnQuestionFragmentInteractionListener listener;

    public void setOnQuestionAnsweredListener(OnQuestionFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public QuestionAdapter(FragmentManager fm, ArrayList<Question> questions, int optionsNumber) {
        super(fm);
        this.questions = new ArrayList<>();
        this.questions.addAll(questions);
        this.optionsNumber = optionsNumber;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment fragment = QuestionFragment.newInstance(questions.get(position), optionsNumber, position);
        fragment.setOnQuestionAnsweredListener(new QuestionFragment.OnQuestionFragmentInteractionListener() {
            @Override
            public void answered(boolean isRight, int position) {
                listener.onQuestionAnswered(isRight, position);
            }

            @Override
            public void useTip() {
                listener.onTipUsed();
            }
        });

        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    public interface OnQuestionFragmentInteractionListener {
        void onQuestionAnswered(boolean isRight, int position);
        void onTipUsed();
    }
}
