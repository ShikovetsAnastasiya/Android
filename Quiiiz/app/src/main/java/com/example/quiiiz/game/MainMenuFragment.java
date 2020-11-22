package com.example.quiiiz.game;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quiiiz.R;


public class MainMenuFragment extends Fragment {

    private OnMainMenuFragmentInteractionListener mListener;

    public MainMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button start = view.findViewById(R.id.main_menu_start_game_button);
        Button exit = view.findViewById(R.id.main_menu_exit_button);
        Button showRes = view.findViewById(R.id.main_menu_show_res_button);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.main_menu_start_game_button: onStartPressed(); break;
                    case R.id.main_menu_exit_button: onExitPressed(); break;
                    case R.id.main_menu_show_res_button: onShowResPressed(); break;
                }
            }
        };

        start.setOnClickListener(listener);
        exit.setOnClickListener(listener);
        showRes.setOnClickListener(listener);

        return view;
    }

    public void onStartPressed() {
        if (mListener != null) {
            mListener.onStartGameClick();
        }
    }

    public void onExitPressed() {
        if (mListener != null) {
            mListener.onExitClick();
        }
    }

    public void onShowResPressed() {
        if (mListener != null) {
            mListener.showResClick();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainMenuFragmentInteractionListener) {
            mListener = (OnMainMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainMenuFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMainMenuFragmentInteractionListener {
        void onStartGameClick();
        void onExitClick();
        void showResClick();
    }
}
