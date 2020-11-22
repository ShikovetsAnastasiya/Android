package com.example.quiiiz.result;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quiiiz.R;
import com.example.quiiiz.activity.MainActivity;
import com.example.quiiiz.models.Result;


public class ResultFragment extends Fragment {
    private Result result;

    public ResultFragment() {

    }


    public static ResultFragment newInstance(Result result) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelable("res", result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            result = getArguments().getParcelable("res");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Result");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        TextView res = view.findViewById(R.id.result_res_text_view);
        TextView type = view.findViewById(R.id.result_type_text_view);
        TextView right = view.findViewById(R.id.result_right_text_view);
        TextView wrong = view.findViewById(R.id.result_wrong_text_view);
        TextView tip = view.findViewById(R.id.result_tip_text_view);

        res.setText(result.isWin() ? "You won!" : "GAME OVER");
        type.setText(result.getOptionsCount() + " answer options");
        right.setText(result.getRightCount() + " right answers");
        wrong.setText(result.getWrongCount() + " wrong answers");
        tip.setText(result.getTipCount() + " tips used");

        return view;
    }
}
