package com.example.quiiiz.game;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quiiiz.activity.MainActivity;
import com.example.quiiiz.R;


public class StartFragment extends Fragment {
    private OnStartFragmentInteractionListener mListener;

    public StartFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Select answer quantity");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        Button btn2 = view.findViewById(R.id.start_two_button);
        Button btn4 = view.findViewById(R.id.start_four_button);
        Button btn6 = view.findViewById(R.id.start_six_button);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.start_two_button: onQuantitySelected(2); break;
                    case R.id.start_four_button: onQuantitySelected(4); break;
                    case R.id.start_six_button: onQuantitySelected(6); break;
                }
            }
        };

        btn2.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn6.setOnClickListener(listener);

        return view;
    }

    public void onQuantitySelected(int qty) {
        if (mListener != null) {
            mListener.onQuantitySelected(qty);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartFragmentInteractionListener) {
            mListener = (OnStartFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStartFragmentInteractionListener {
        void onQuantitySelected(int qty);
    }
}
