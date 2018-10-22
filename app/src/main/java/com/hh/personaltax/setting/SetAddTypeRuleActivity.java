package com.hh.personaltax.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.hh.personaltax.R;
import com.hh.personaltax.base.TaxApplication;
import com.hh.personaltax.model.TaxType;
import com.hh.personaltax.model.TaxTypeRule;
import com.hh.personaltax.util.MoneyConstants;
import com.hh.personaltax.util.ReceiverUtils;
import com.hh.personaltax.view.BottomBar;
import com.hh.personaltax.view.BottomTextBarTab;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增个税类型-规则
 */
public class SetAddTypeRuleActivity extends AppCompatActivity {
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topbar_right_change_button:
                    doSubmit();
                    break;
            }
        }
    };
    
    private BottomBar.OnTabSelectedListener mOnTabSelectedListener =
        new BottomBar.OnTabSelectedListener() {
            public void onTabSelected(int position, int prePosition) {
                updateAmountType(position);
            }
            
            public void onTabUnselected(int position) {
            }
            
            public void onTabReselected(int position) {
            }
        };
    
    private TaxType mTypeItem;
    private QMUITopBarLayout bar_top;
    private BottomBar mBottomBar;
    private EditText et_fix_amount, et_scale, et_max_amount, et_min_amount;
    private TextView tv_1, tv_2;
    
    private List<TaxTypeRule> mRules = new ArrayList<>();
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_add_type_rule);
        
        Long typeId = getIntent().getLongExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, -1);
        if (typeId != -1) {
            mTypeItem = TaxApplication.getDaoInstant().getTaxTypeDao().load(typeId);
            if (mTypeItem != null) {
                mRules = mTypeItem.getRulesList();
            }
        }
        
        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("新增规则");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("提交", R.id.topbar_right_change_button)
               .setOnClickListener(mOnClickListener);
        
        mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
        
        mBottomBar.addItem(new BottomTextBarTab(this, "小于"));
        mBottomBar.addItem(new BottomTextBarTab(this, "中间"));
        mBottomBar.addItem(new BottomTextBarTab(this, "大于"));
        
        et_fix_amount = findViewById(R.id.et_fix_amount);
        et_scale = findViewById(R.id.et_scale);
        et_max_amount = findViewById(R.id.et_max_amount);
        et_min_amount = findViewById(R.id.et_min_amount);
        
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        
        updateAmountType(0);
    }
    
    private void updateAmountType(int position) {
        
        switch (position) {
            case 0:
                tv_1.setText("<= ");
                et_min_amount.setVisibility(View.VISIBLE);
                tv_1.setVisibility(View.VISIBLE);
                
                tv_2.setText("");
                tv_2.setVisibility(View.GONE);
                et_max_amount.setVisibility(View.GONE);
                break;
            case 2:
                tv_2.setText("> ");
                et_max_amount.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.VISIBLE);
                
                tv_1.setText("");
                tv_1.setVisibility(View.GONE);
                et_min_amount.setVisibility(View.GONE);
                break;
            case 1:
            default:
                tv_1.setText("");
                et_min_amount.setVisibility(View.VISIBLE);
                tv_1.setVisibility(View.VISIBLE);
                
                tv_2.setText(" - ");
                tv_2.setVisibility(View.VISIBLE);
                et_max_amount.setVisibility(View.VISIBLE);
        }
    }
    
    private void doSubmit() {
        
        if (onCheckSubmit()) {
            Integer maxAmount = Integer.valueOf(et_max_amount.getText().toString());
            Integer minAmount = Integer.valueOf(et_min_amount.getText().toString());
            Integer fixAmount = Integer.valueOf(et_fix_amount.getText().toString());
            Integer scale = Integer.valueOf(et_scale.getText().toString());
            
            TaxTypeRule rule = new TaxTypeRule();
            switch (mBottomBar.getCurrentItemPosition()) {
                case 0:
                    rule.setTypeAmountBelow();
                    rule.setMaxAmount(maxAmount);
                    break;
                case 2:
                    rule.setTypeAmountAbove();
                    rule.setMinAmount(minAmount);
                    break;
                case 1:
                default:
                    rule.setTypeAmountNormal();
                    rule.setMinAmount(minAmount);
                    rule.setMaxAmount(maxAmount);
            }
            
            rule.setScale(scale);
            rule.setFixAmount(fixAmount);
            
            mRules.add(rule);
            
            mTypeItem.setRulesList(mRules);
            
            Long result = TaxApplication.getDaoInstant().getTaxTypeDao().insertOrReplace(mTypeItem);
            if (result != -1) {
                ToastUtils.showShort("提交成功");
                sendBroadcast(new Intent(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE));
                finish();
            } else {
                ToastUtils.showShort("提交失败，请重试");
            }
        }
    }
    
    private boolean onCheckSubmit() {
        boolean result = true;
        switch (mBottomBar.getCurrentItemPosition()) {
            case 0:
                if (et_max_amount.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请输入最大金额");
                    result = false;
                }
                break;
            case 2:
                if (et_min_amount.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请输入最小金额");
                    result = false;
                }
                break;
            case 1:
            default:
                if (et_max_amount.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请输入最大金额");
                    result = false;
                }
                if (et_min_amount.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请输入最小金额");
                    result = false;
                }
        }
        
        if (et_fix_amount.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入税率");
            result = false;
        }
        
        if (et_scale.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入速算扣除数");
            result = false;
        }
        
        if (mTypeItem == null) {
            ToastUtils.showShort("提交失败，请重试");
            result = false;
        }
        
        return result;
    }
}
