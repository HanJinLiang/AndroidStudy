package com.hanjinliang.androidstudy.customerviews.SearchView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;


public class SearchViewActivity extends BaseActivity {
    SearchView mSearchView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        mSearchView=findView(R.id.SearchView);
        mSearchView.setSearchViewListener(new SearchView.SearchViewListener() {
            @Override
            public void startSearching() {
            }
        });

        button=findView(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.searchFinish();
            }
        });
    }
}
