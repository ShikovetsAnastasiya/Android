package com.example.quiiiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
    private boolean isWin;
    private int optionsCount;
    private int rightCount;
    private int wrongCount;
    private int tipCount;

    public Result(int optionsCount) {
        this.optionsCount = optionsCount;
        isWin = false;
        rightCount = 0;
        wrongCount = 0;
        tipCount = 0;
    }

    public int getOptionsCount() {
        return optionsCount;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public int getRightCount() {
        return rightCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public int getTipCount() {
        return tipCount;
    }

    public void incrRight() {
        rightCount++;
    }

    public void incrWrong() {
        wrongCount++;
    }

    public void incrTip() {
        tipCount++;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel parcel) {
            return new Result(parcel);
        }

        @Override
        public Result[] newArray(int i) {
            return new Result[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(optionsCount);
        parcel.writeInt(rightCount);
        parcel.writeInt(wrongCount);
        parcel.writeInt(tipCount);
        parcel.writeInt(isWin ? 1 : 0);
    }

    private Result(Parcel parcel) {
        optionsCount = parcel.readInt();
        rightCount = parcel.readInt();
        wrongCount = parcel.readInt();
        tipCount = parcel.readInt();
        isWin = parcel.readInt() == 1;
    }
}
