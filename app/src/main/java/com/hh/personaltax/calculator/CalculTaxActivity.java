package com.hh.personaltax.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.blankj.utilcode.util.ToastUtils;
import com.hh.personaltax.R;
import com.hh.personaltax.base.BaseActivity;
import com.hh.personaltax.base.TaxApplication;
import com.hh.personaltax.model.TaxType;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import java.util.ArrayList;
import java.util.List;

public class CalculTaxActivity extends BaseActivity {
    
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_tax_type:
                    ToastUtils.showShort("收入类型");
                    showChooseTaxTypeView();
                    break;
                case R.id.topbar_right_change_button:
                    ToastUtils.showShort("保存该记录");
                    break;
            }
        }
    };
    
    private QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener mTaxTypeSheetListener =
        new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                dialog.dismiss();
                updateChooseTaxType(position);
            }
        };
    
    private QMUIRoundButton btn_del;
    private QMUITopBarLayout bar_top;
    private QMUIGroupListView groupListView;
    private QMUICommonListItemView item_tax_type, item_tax_start_amount;
    private EditText et_before_amount, et_social_secutry_amount, et_other_amount, et_bonus,et_contrace;
    private TaxType mChooseTaxType;
    View view_before_amount, view_social_amount, view_other_amount, view_bonus,view_contrace;
    
    private List<TaxType> mTaxTypeList = new ArrayList<>();
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_tax);
        
        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("计算");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("保存该记录", R.id.topbar_right_change_button)
               .setOnClickListener(mOnClickListener);
        
        groupListView = findViewById(R.id.groupListView);
        et_before_amount = findViewById(R.id.et_before_amount);
        et_social_secutry_amount = findViewById(R.id.et_social_secutry_amount);
        et_other_amount = findViewById(R.id.et_other_amount);
        et_bonus = findViewById(R.id.et_bonus);
        et_contrace = findViewById(R.id.et_contrace);
        
        view_before_amount = findViewById(R.id.view_before_amount);
        view_social_amount = findViewById(R.id.view_social_amount);
        view_other_amount = findViewById(R.id.view_other_amount);
        view_bonus = findViewById(R.id.view_bonus);
        view_contrace = findViewById(R.id.view_contrace);
        
        item_tax_type = findViewById(R.id.item_tax_type);
        item_tax_type.setText("收入类型");
        item_tax_type.setOnClickListener(mOnClickListener);
        
        item_tax_start_amount = findViewById(R.id.item_tax_start_amount);
        item_tax_start_amount.setText("起征点");
        item_tax_start_amount.setOrientation(QMUICommonListItemView.HORIZONTAL);
        item_tax_start_amount.setDetailText("5000");
    
        btn_del = findViewById(R.id.btn_del);
    
        btn_del.setText("计算");
        
        updateData();
    }
    
    private void updateData() {
        mTaxTypeList = TaxApplication.getDaoInstant().getTaxTypeDao().loadAll();
    }
    
    /**
     * 显示收入类型列表
     */
    private void showChooseTaxTypeView() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
            new QMUIBottomSheet.BottomListSheetBuilder(this, true);
        
        for (TaxType type : mTaxTypeList) {
            builder.addItem(type.getName());
        }
        
        int selectedIndex = 0;
        if (mTaxTypeList != null && mChooseTaxType != null) {
            selectedIndex = mTaxTypeList.indexOf(mChooseTaxType);
        }
        
        builder.setOnSheetItemClickListener(mTaxTypeSheetListener);
        builder.setCheckedIndex(selectedIndex);
        builder.build().show();
    }
    
    private void updateChooseTaxType(int position) {
        mChooseTaxType = mTaxTypeList.get(position);
        item_tax_type.setDetailText(mChooseTaxType.getName());
        updateChooseViews();
    }
    
    private void updateChooseViews() {
        //
        if (mChooseTaxType.getName().equals(getString(R.string.tax_type_1))) {
            view_before_amount.setVisibility(View.VISIBLE);
            view_social_amount.setVisibility(View.VISIBLE);
            view_other_amount.setVisibility(View.VISIBLE);
            view_bonus.setVisibility(View.GONE);
        } else if (mChooseTaxType.getName().equals(getString(R.string.tax_type_2))) {
            view_before_amount.setVisibility(View.GONE);
            view_social_amount.setVisibility(View.GONE);
            view_other_amount.setVisibility(View.GONE);
            view_bonus.setVisibility(View.VISIBLE);
        } else if (mChooseTaxType.getName().equals(getString(R.string.tax_type_3))
            || mChooseTaxType.getName()
                             .equals(getString(R.string.tax_type_4))
            || mChooseTaxType.getName()
                             .equals(getString(R.string.tax_type_5))
            || mChooseTaxType.getName().equals(getString(R.string.tax_type_6))
            || mChooseTaxType.getName().equals(getString(R.string.tax_type_7))
            || mChooseTaxType.getName().equals(getString(R.string.tax_type_9))
            || mChooseTaxType.getName().equals(getString(R.string.tax_type_10))) {
            view_before_amount.setVisibility(View.VISIBLE);
            view_social_amount.setVisibility(View.GONE);
            view_other_amount.setVisibility(View.GONE);
            view_bonus.setVisibility(View.GONE);
        } else if (mChooseTaxType.getName().equals(getString(R.string.tax_type_8))) {
        
        }
    }
}
