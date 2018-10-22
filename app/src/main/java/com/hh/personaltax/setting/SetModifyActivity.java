package com.hh.personaltax.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.hh.personaltax.R;
import com.hh.personaltax.adapter.TaxTypeListAdapter;
import com.hh.personaltax.base.BaseActivity;
import com.hh.personaltax.base.TaxApplication;
import com.hh.personaltax.model.TaxType;
import com.hh.personaltax.util.MoneyConstants;
import com.hh.personaltax.util.ReceiverUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class SetModifyActivity extends BaseActivity {
    
    BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE)) {
                updateTypeList();
            }
        }
    };
    
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_del:
                    doDelete();
                    break;
                
                case R.id.topbar_right_change_button:
                    
                    Intent intent =
                        new Intent(SetModifyActivity.this, SetAddTypeRuleActivity.class);
                    intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, intentItemId);
                    startActivity(intent);
                    break;
            }
        }
    };
    
    private Long intentItemId = -1L;
    private String intentFrom = MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_MODIFY;
    
    private TaxType mTaxType;
    private QMUITopBarLayout bar_top;
    private TextView tv_title;
    private QMUIRoundButton btn_del;
    private ListView listview;
    private TaxTypeListAdapter mListAdapter;
    private View view_add_top;
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_modify);
        
        intentItemId = getIntent().getLongExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, -1);
        intentFrom = getIntent().getStringExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM);
        
        registerLocalReceiver(mUpdateReceiver,
                              new IntentFilter(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE));
        
        tv_title = findViewById(R.id.tv_title);
        view_add_top = findViewById(R.id.view_add_top);
        
        bar_top = findViewById(R.id.bar_top);
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("新增规则", R.id.topbar_right_change_button)
               .setOnClickListener(mOnClickListener);
        
        btn_del = findViewById(R.id.btn_del);
        btn_del.setOnClickListener(mOnClickListener);
        
        listview = findViewById(R.id.listview);
        mListAdapter = new TaxTypeListAdapter(this);
        listview.setAdapter(mListAdapter);
        updateView();
        updateTypeList();
    }
    
    private void updateView() {
        if (intentFrom.equals(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_ADD)) {
            bar_top.setTitle("新增个税类型");
            btn_del.setText("保存");
            
            view_add_top.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
        } else if (intentFrom.equals(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_MODIFY)) {
            bar_top.setTitle("个税类型详情");
            btn_del.setText("删除");
            
            view_add_top.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
        }
    }
    
    private void updateTypeList() {
        if (intentItemId != -1) {
            mTaxType = TaxApplication.getDaoInstant().getTaxTypeDao().load(intentItemId);
            tv_title.setText(mTaxType.getName());
            mListAdapter.addAll(mTaxType.getRulesList());
        }
    }
    
    @Override protected void onDestroy() {
        super.onDestroy();
        unRegistLocalReceiver(mUpdateReceiver);
    }
    
    private void doDelete() {
        if (intentFrom.equals(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_ADD)) {
            //保存
            
        } else if (intentFrom.equals(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_MODIFY)) {
            //删除
            
        }
    }
}
