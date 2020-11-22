package com.example.quiiiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private String text;
    private boolean isRight;

    public Answer(String text, boolean isRight) {
        this.text = text;
        this.isRight = isRight;
    }

    public String getText() {
        return text;
    }

    public boolean isRight() {
        return isRight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(isRight ? 1 : 0);
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel parcel) {
            return new Answer(parcel);
        }

        @Override
        public Answer[] newArray(int i) {
            return new Answer[i];
        }
    };

    private Answer(Parcel parcel) {
        text = parcel.readString();
        isRight = parcel.readInt() == 1;
    }
}
