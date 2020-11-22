package com.example.quiiiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Question implements Parcelable {
    private String text;
    private Answer rightAnswer;
    private ArrayList<Answer> answerList;
    private boolean isAnswered;

    public Question(String text, Answer rightAnswer, ArrayList<Answer> answerList) {
        this.text = text;
        this.rightAnswer = rightAnswer;
        this.answerList = answerList;
        isAnswered = false;
    }

    public String getText() {
        return text;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public ArrayList<Answer> getAnswerList(int size) {
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(rightAnswer);

        for (int i = 0; i < size - 1; i++) {
            answers.add(answerList.get(i));
        }

        Collections.shuffle(answers);
        return answers;
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel parcel) {
            return new Question(parcel);
        }

        @Override
        public Question[] newArray(int i) {
            return new Question[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeParcelable(rightAnswer, i);
        parcel.writeParcelableArray((Answer[]) answerList.toArray(), i);
    }

    private Question(Parcel parcel) {
        text = parcel.readString();
        rightAnswer = parcel.readParcelable(Answer.class.getClassLoader());
        answerList = new ArrayList<>();
        Answer[] answers = (Answer[]) parcel.readParcelableArray(Answer.class.getClassLoader());
        answerList.addAll(Arrays.asList(answers));
    }

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(createQuestion("Сколько в среднем весят мышцы человека?", "28 кг", "29 кг", "10 кг", "35 кг", "48 кг", "50 кг"));
        questions.add(createQuestion("Сколько мышц в теле человека", "Более 600", "Менее 600", "550", "580", "700", "750"));
        questions.add(createQuestion("Где находятся самые маленькие кости человека?", "Ухо", "Нос", "Палец", "Стопа", "Кисть", "Бедро"));
        questions.add(createQuestion("Сколько процентов воды содержит кость?", "До 22", "До 13", "До 25", "До 34", "До 21", "До 5"));
//        questions.add(createQuestion("Сколько рецепторов-колбочек находится в глазу человека?", "6-7 миллионов", "5-6 миллионов", "6 миллионов", "7-8 миллионов", "8 миллионов", "7 миллионов"));
//        questions.add(createQuestion("Вопрос на удачу:", "Это верный ответ :)", "Верный ответ", "Тут верный ответ", "Правда", "Ложь", "Правда:("));


        return questions;
    }

    private static Question createQuestion(String question, String rightAnswerTxt,
                                           String answer1Txt, String answer2Txt, String answer3Txt, String answer4Txt, String answer5Txt) {

        Answer rightAnswer = new Answer(rightAnswerTxt, true);
        Answer answer1 = new Answer(answer1Txt, false);
        Answer answer2 = new Answer(answer2Txt, false);
        Answer answer3 = new Answer(answer3Txt, false);
        Answer answer4 = new Answer(answer4Txt, false);
        Answer answer5 = new Answer(answer5Txt, false);
        ArrayList<Answer> answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3, answer4, answer5));

        return new Question(question, rightAnswer, answers);
    }
}
