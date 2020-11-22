package com.example.quiiiz.result;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quiiiz.R;
import com.example.quiiiz.activity.MainActivity;
import com.example.quiiiz.models.Result;

import java.util.ArrayList;
import java.util.List;


public class ResultListFragment extends Fragment {
    private List<Result> resultList;

    public ResultListFragment() {
    }

    public static ResultListFragment newInstance(ArrayList<Result> results) {
        ResultListFragment fragment = new ResultListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", results);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resultList = new ArrayList<>();
            resultList.addAll(getArguments().<Result>getParcelableArrayList("list"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Results");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.results_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ResultAdapter adapter = new ResultAdapter(resultsToStringList());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> resultsToStringList() {
        List<String> results = new ArrayList<>();

        for (Result res : resultList) {
            results.add((res.isWin() ? "Win,\t" : "Lose,\t") + res.getOptionsCount() + " answer options\tRight: " + res.getRightCount() + "\tWrong: " + res.getWrongCount() +
                    "\tTip used: " + res.getTipCount());
        }

        return results;
    }
}
