package com.example.quiiiz.game;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiiiz.R;
import com.example.quiiiz.models.Answer;
import com.example.quiiiz.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class QuestionFragment extends Fragment {
    private Button answerBtn1, answerBtn2, answerBtn3, answerBtn4, answerBtn5, answerBtn6, tipButton;
    private Button[] buttonsArray;

    private Question question;
    private List<Answer> answerList;
    private int optionsNumber;
    private int position;
    private OnQuestionFragmentInteractionListener listener;
    private boolean isAnswered = false;
    private boolean isTipUsed = false;

    public QuestionFragment() { }


    public static QuestionFragment newInstance(Question question, int optionsNumber, int position) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable("question", question);
        args.putInt("options", optionsNumber);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        answerList = new ArrayList<>();

        if (getArguments() != null) {
            question = getArguments().getParcelable("question");
            optionsNumber = getArguments().getInt("options");
            position = getArguments().getInt("position");
            answerList.addAll(question.getAnswerList(optionsNumber));
        }

        if (savedInstanceState != null) {
            isAnswered = savedInstanceState.getBoolean("isAnswered");
            isTipUsed = savedInstanceState.getBoolean("isTipUsed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionTextView = view.findViewById(R.id.question_text_text_view);
        answerBtn1 = view.findViewById(R.id.question_answer_1);
        answerBtn2 = view.findViewById(R.id.question_answer_2);
        answerBtn3 = view.findViewById(R.id.question_answer_3);
        answerBtn4 = view.findViewById(R.id.question_answer_4);
        answerBtn5 = view.findViewById(R.id.question_answer_5);
        answerBtn6 = view.findViewById(R.id.question_answer_6);
        tipButton = view.findViewById(R.id.question_tip_button);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAnswered) {
                    switch (view.getId()) {
                        case R.id.question_answer_1: answerClicked(0); break;
                        case R.id.question_answer_2: answerClicked(1); break;
                        case R.id.question_answer_3: answerClicked(2); break;
                        case R.id.question_answer_4: answerClicked(3); break;
                        case R.id.question_answer_5: answerClicked(4); break;
                        case R.id.question_answer_6: answerClicked(5); break;
                        case R.id.question_tip_button: useTip(); break;
                    }
                }
            }
        };

        answerBtn1.setOnClickListener(listener);
        answerBtn2.setOnClickListener(listener);
        answerBtn3.setOnClickListener(listener);
        answerBtn4.setOnClickListener(listener);
        answerBtn5.setOnClickListener(listener);
        answerBtn6.setOnClickListener(listener);
        tipButton.setOnClickListener(listener);

        questionTextView.setText(question.getText());

        initAnswerButtons();

        if (optionsNumber == 2) {
            tipButton.setVisibility(View.GONE);
        }

        if (isTipUsed) {
            tipButton.setVisibility(View.GONE);
        }

        return view;
    }

    private void initAnswerButtons() {
        buttonsArray = new Button[optionsNumber];

        switch (optionsNumber) {
            case 2: twoOptions(); break;
            case 4: fourOptions(); break;
            case 6: sixOptions(); break;
        }

        mapButtonsWithAnswers(buttonsArray);
    }

    private void mapButtonsWithAnswers(Button[] buttonsArray) {
        for (int i = 0; i < optionsNumber; i++) {
            buttonsArray[i].setText(answerList.get(i).getText());
        }
    }

    private void twoOptions() {
        answerBtn3.setVisibility(View.GONE);
        answerBtn4.setVisibility(View.GONE);
        answerBtn5.setVisibility(View.GONE);
        answerBtn6.setVisibility(View.GONE);

        buttonsArray[0] = answerBtn1;
        buttonsArray[1] = answerBtn2;
    }

    private void fourOptions() {
        answerBtn5.setVisibility(View.GONE);
        answerBtn6.setVisibility(View.GONE);

        buttonsArray[0] = answerBtn1;
        buttonsArray[1] = answerBtn2;
        buttonsArray[2] = answerBtn3;
        buttonsArray[3] = answerBtn4;
    }

    private void sixOptions() {
        buttonsArray[0] = answerBtn1;
        buttonsArray[1] = answerBtn2;
        buttonsArray[2] = answerBtn3;
        buttonsArray[3] = answerBtn4;
        buttonsArray[4] = answerBtn5;
        buttonsArray[5] = answerBtn6;
    }

    private void answerClicked(int index) {
        isAnswered = true;
        Answer answer = answerList.get(index);
        listener.answered(answer.isRight(), position);

        if (answer.isRight()) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.right_button_background);
            buttonsArray[index].setBackground(drawable);
        } else {
            Drawable wrongDrawable = ContextCompat.getDrawable(getContext(), R.drawable.start_button_background);
            Drawable rightDrawable = ContextCompat.getDrawable(getContext(), R.drawable.right_button_background);
            buttonsArray[index].setBackground(wrongDrawable);
            buttonsArray[getRightAnswerIndex()].setBackground(rightDrawable);
        }
    }

    private int getRightAnswerIndex() {
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).isRight()) {
                return i;
            }
        }

        return -1;
    }

    public void useTip() {
        isTipUsed = true;

        int rightIndex = getRightAnswerIndex();
        int[] btnIndices = generateRandomNumbers(rightIndex);

        for (int index : btnIndices) {
            buttonsArray[index].setVisibility(View.INVISIBLE);
        }

        tipButton.setVisibility(View.GONE);
        listener.useTip();
    }

    private int[] generateRandomNumbers(int except) {
        Random random = new Random(System.currentTimeMillis());
        int[] numbers = new int[optionsNumber / 2];
        int currIndex = 0;

        while (currIndex < numbers.length) {
            int num = random.nextInt(optionsNumber);

            if (num != except && !isArrayContainsValue(numbers, num)) {
                numbers[currIndex] = num;
                currIndex++;
            }
        }

        return numbers;
    }

    private boolean isArrayContainsValue(int[] array, int num) {
        for (int item : array) {
            if (item == num) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isAnswered", isAnswered);
        outState.putBoolean("isTipUsed", isTipUsed);
    }

    public void setOnQuestionAnsweredListener(OnQuestionFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public interface OnQuestionFragmentInteractionListener {
        void answered(boolean isRight, int position);
        void useTip();
    }
}
