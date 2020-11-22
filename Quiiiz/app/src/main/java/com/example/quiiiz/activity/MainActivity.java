package com.example.quiiiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.quiiiz.R;
import com.example.quiiiz.game.GameFragment;
import com.example.quiiiz.game.MainMenuFragment;
import com.example.quiiiz.game.StartFragment;
import com.example.quiiiz.models.Result;
import com.example.quiiiz.result.ResultFragment;
import com.example.quiiiz.result.ResultListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.OnMainMenuFragmentInteractionListener,
        StartFragment.OnStartFragmentInteractionListener, GameFragment.OnGameEndedListener {
    private ArrayList<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultList = new ArrayList<>();

        Fragment fragment = new MainMenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.commit();
    }

    private void startGame() {
        Fragment fragment = new StartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onStartGameClick() {
        startGame();
    }

    @Override
    public void onExitClick() {
        finish();
    }

    @Override
    public void showResClick() {
        Fragment fragment = ResultListFragment.newInstance(resultList);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onQuantitySelected(int qty) {
        getSupportFragmentManager().popBackStack();
        Fragment fragment = GameFragment.newInstance(qty);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onGameEnded(Result result) {
        getSupportFragmentManager().popBackStack();
        resultList.add(result);

        Fragment fragment = ResultFragment.newInstance(result);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}
