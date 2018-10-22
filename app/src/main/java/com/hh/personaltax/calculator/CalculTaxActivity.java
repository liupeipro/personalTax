package com.hh.personaltax.calculator;

import android.os.Bundle;
import android.view.View;
import com.hh.personaltax.R;
import com.hh.personaltax.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class CalculTaxActivity extends BaseActivity {
    private QMUITopBarLayout bar_top;
    QMUIGroupListView groupListView;
    QMUICommonListItemView typeItemView, beforeItemView, socialSecuityItemView, otherItemView;
    
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
               .setOnClickListener(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                
                   }
               });
        groupListView = findViewById(R.id.groupListView);
        
        addItem();
    }
    
    private void addItem() {
        
        typeItemView =
            groupListView.createItemView(null, "收入类型:", "", QMUICommonListItemView.HORIZONTAL,
                                         QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        beforeItemView =
            groupListView.createItemView(null, "税前收入:", "", QMUICommonListItemView.HORIZONTAL,
                                         QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        socialSecuityItemView =
            groupListView.createItemView(null, "社保扣除金额:", "", QMUICommonListItemView.HORIZONTAL,
                                         QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        otherItemView =
            groupListView.createItemView(null, "其他扣除金额:", "", QMUICommonListItemView.HORIZONTAL,
                                         QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        
        QMUIGroupListView.newSection(this)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(typeItemView, new View.OnClickListener() {
                             @Override public void onClick(View v) {
                
                             }
                         })
                         .addTo(groupListView);
    
    
        QMUIGroupListView.newSection(this)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(beforeItemView, new View.OnClickListener() {
                             @Override public void onClick(View v) {
            
                             }
                         })
                         .addTo(groupListView);
    
    
        QMUIGroupListView.newSection(this)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(socialSecuityItemView, new View.OnClickListener() {
                             @Override public void onClick(View v) {
            
                             }
                         })
                         .addTo(groupListView);
    
    
        QMUIGroupListView.newSection(this)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(otherItemView, new View.OnClickListener() {
                             @Override public void onClick(View v) {
            
                             }
                         })
                         .addTo(groupListView);
        
    }
}
