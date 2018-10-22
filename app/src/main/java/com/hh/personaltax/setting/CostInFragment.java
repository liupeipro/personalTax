package com.hh.personaltax.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hh.personaltax.R;
import com.hh.personaltax.base.BaseBackFragment;

public class CostInFragment extends BaseBackFragment {
    private void initView(View view) {
    }
    
    public static CostInFragment newInstance() {
        Bundle args = new Bundle();
        CostInFragment fragment = new CostInFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost_in, container, false);
        
        initView(view);
        
        return view;
    }
    
    @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
