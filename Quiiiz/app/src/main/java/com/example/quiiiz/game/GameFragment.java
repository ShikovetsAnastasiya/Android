package com.example.quiiiz.game;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.example.quiiiz.R;
import com.example.quiiiz.models.Question;
import com.example.quiiiz.models.Result;

import java.util.ArrayList;



public class GameFragment extends Fragment {
    private QuestionAdapter adapter;
    private ViewPager viewPager;

    private int answerQty = 2;
    private Result gameResult;
    private OnGameEndedListener mListener;
    private ArrayList<Question> questions;

    public GameFragment() {
    }

    public static GameFragment newInstance(int qty) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt("qty", qty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            answerQty = getArguments().getInt("qty");
            gameResult = new Result(answerQty);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        viewPager = view.findViewById(R.id.game_view_pager);
        Chronometer timer = view.findViewById(R.id.game_timer_chronometer);

        timer.setBase(SystemClock.elapsedRealtime() + 30000);
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();

                if (time >= 0) {
                    endGame();
                }
            }
        });
        timer.start();

        questions = Question.getQuestions();
        adapter = new QuestionAdapter(getActivity().getSupportFragmentManager(), questions, answerQty);
        adapter.setOnQuestionAnsweredListener(new QuestionAdapter.OnQuestionFragmentInteractionListener() {
            @Override
            public void onQuestionAnswered(boolean isRight, int position) {
                if (isRight) {
                    gameResult.incrRight();
                } else {
                    gameResult.incrWrong();
                }

                questions.get(position).setAnswered(true);

                if (isAllQuestionsAnswered()) {
                    gameResult.setWin(gameResult.getRightCount() == questions.size());
                    endGame();
                }
            }

            @Override
            public void onTipUsed() {
                gameResult.incrTip();
            }
        });

        viewPager.setAdapter(adapter);

        return view;
    }

    private boolean isAllQuestionsAnswered() {
        for (Question question : questions) {
            if (!question.isAnswered()) {
                return false;
            }
        }

        return true;
    }

    public void endGame() {
        if (mListener != null) {
            mListener.onGameEnded(gameResult);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameEndedListener) {
            mListener = (OnGameEndedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGameEndedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnGameEndedListener {
        void onGameEnded(Result result);
    }
}
