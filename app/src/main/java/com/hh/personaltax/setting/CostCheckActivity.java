package com.hh.personaltax.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.hh.personaltax.R;
import com.hh.personaltax.base.BaseActivity;
import com.hh.personaltax.base.TaxApplication;
import com.hh.personaltax.greendao.gen.CostItemDao;
import com.hh.personaltax.model.CostItem;
import com.hh.personaltax.util.MoneyConstants;
import com.hh.personaltax.util.ReceiverUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class CostCheckActivity extends BaseActivity {
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_del:
                    showDelDialog();
                    
                    break;
                
                case R.id.topbar_right_change_button:
                    Intent intent = new Intent(CostCheckActivity.this, CostAddActivity.class);
                    
                    intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, mItem.get_id());
                    startActivity(intent);
                    finish();
                    
                    break;
            }
        }
    };
    CostItem mItem;
    private QMUITopBarLayout bar_top;
    TextView tv_amount;
    QMUIRoundButton btn_del;
    QMUIGroupListView mGroupListView;
    QMUICommonListItemView itemAccount, itemCreateTime, itemLastModifyTime, itemRemark;
    
    private void doDeleteRecord() {
        TaxApplication.getDaoInstant().getCostItemDao().delete(mItem);
        ToastUtils.showShort("删除成功");
        sendBroadcast(new Intent(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE));
        onBackPressed();
    }
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_check);
        
        Long intentItemId = getIntent().getLongExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, -1);
        
        if (intentItemId != -1) {
            mItem = TaxApplication.getDaoInstant()
                                  .getCostItemDao()
                                  .queryBuilder()
                                  .where(CostItemDao.Properties._id.eq(intentItemId))
                                  .unique();
        }
        
        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("查看记账");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("编辑", R.id.topbar_right_change_button)
               .setOnClickListener(mOnClickListener);
        tv_amount = findViewById(R.id.tv_amount);
        btn_del = findViewById(R.id.btn_del);
        btn_del.setText("删除");
        btn_del.setOnClickListener(mOnClickListener);
        mGroupListView = findViewById(R.id.groupListView);
        
        //      金额类型
        //      itemAmountTypeView = mGroupListView.createItemView(null, "", "",
        //              QMUICommonListItemView.HORIZONTAL,
        //              QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        //      账户
        itemAccount =
            mGroupListView.createItemView(null, "账户", "", QMUICommonListItemView.HORIZONTAL,
                                          QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        
        //      创建时间
        itemCreateTime =
            mGroupListView.createItemView(null, "创建时间", "", QMUICommonListItemView.HORIZONTAL,
                                          QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        
        //      最后修改时间
        itemLastModifyTime =
            mGroupListView.createItemView(null, "最后修改时间", "", QMUICommonListItemView.HORIZONTAL,
                                          QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        
        //      备注
        itemRemark = mGroupListView.createItemView(null, "备注", "", QMUICommonListItemView.VERTICAL,
                                                   QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        update(mItem);
    }
    
    private void showDelDialog() {
        final QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(this);
        
        builder.setTitle("确认要删除吗？")
               .setMessage("辛苦记的帐就找不回来啦！")
               .setCancelable(true)
               .addAction("取消", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       dialog.dismiss();
                   }
               })
               .addAction("确定", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       doDeleteRecord();
                   }
               })
               .create(R.style.qmui_dialog_wrap)
               .show();
    }
    
    private void update(CostItem item) {
        if (item != null) {
            String amount = "";
            
            if (item.getCostAmountType().getName().equals("收入")) {
                amount = "￥ +" + item.getCostAmount() + " 元";
                tv_amount.setTextColor(getColor(R.color.text_plus));
            } else {
                amount = "￥ -" + item.getCostAmount() + " 元";
                tv_amount.setTextColor(getColor(R.color.text_sub));
            }
            
            tv_amount.setText(amount);
            itemAccount.setDetailText(item.getCostAccount().getCostAccountame());
            itemCreateTime.setDetailText(item.getTempCreateTime());
            itemLastModifyTime.setDetailText(item.getTempLastModifyTime());
            itemRemark.setDetailText(item.getRemark());
        }
        
        QMUIGroupListView.newSection(this)
                         .setTitle("")
                         .setDescription("")
                         .addItemView(itemAccount, mOnClickListener)
                         .addItemView(itemCreateTime, mOnClickListener)
                         .addItemView(itemLastModifyTime, mOnClickListener)
                         .addItemView(itemRemark, mOnClickListener)
                         .addTo(mGroupListView);
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
