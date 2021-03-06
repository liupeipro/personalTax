package com.hh.personaltax.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.ToastUtils;
import com.hh.personaltax.R;
import com.hh.personaltax.base.BaseFragment;
import com.hh.personaltax.base.TaxApplication;
import com.hh.personaltax.model.CostItem;
import com.hh.personaltax.model.TaxType;
import com.hh.personaltax.util.MoneyConstants;
import com.hh.personaltax.util.ReceiverUtils;
import com.hh.personaltax.view.RefreshLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import java.util.List;

public class SetsFragment extends BaseFragment {
    BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE)) {
                pull_to_refresh.doRefresh();
            }
        }
    };
    private QMUITopBarLayout bar_top;
    RefreshLayout pull_to_refresh;
    QMUIGroupListView groupListView;
    
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_del:
                    //showDelDialog();
                    
                    break;
                
                case R.id.topbar_right_change_button:
                    //Intent intent = new Intent(CostCheckActivity.this, CostAddActivity.class);
                    //
                    //intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, mItem.get_id());
                    //startActivity(intent);
                    //finish();
                    
                    break;
            }
        }
    };
    
    private void gotoCheck(CostItem item) {
        Intent intent = new Intent(_mActivity, CostCheckActivity.class);
        intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, item.get_id());
        startActivity(intent);
    }
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("参数类型");
        bar_top.addRightTextButton("新增类型", R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                       gotoTypeDetail(-1L, MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_ADD);
                   }
               });
        pull_to_refresh = view.findViewById(R.id.pull_to_refresh);
        pull_to_refresh.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override public void onMoveTarget(int offset) {
            }
            
            @Override public void onMoveRefreshView(int offset) {
            }
            
            @Override public void onRefresh() {
                loadAllList();
            }
        });
        
        groupListView = view.findViewById(R.id.groupListView);
        
        pull_to_refresh.doRefresh();
    }
    
    private void loadAllList() {
        // 获取数据库中数据
        //List<TaxType> tempData = null;
        List<TaxType> tempData = TaxApplication.getDaoInstant().getTaxTypeDao().loadAll();
        if (tempData != null) {
            QMUIGroupListView.Section sections =
                QMUIGroupListView.newSection(_mActivity).setTitle("").setDescription("");
            sections.removeFrom(groupListView);
            if (tempData.size() > 0) {
                for (final TaxType type : tempData) {
                    QMUICommonListItemView itemView =
                        groupListView.createItemView(null, type.getName(), "",
                                                     QMUICommonListItemView.HORIZONTAL,
                                                     QMUICommonListItemView.ACCESSORY_TYPE_NONE);
                    sections.addItemView(itemView, new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            gotoTypeDetail(type.getId(),
                                           MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM_MODIFY);
                        }
                    });
                }
                
                sections.addTo(groupListView);
            } else {
                ToastUtils.showShort("数据为空...");
            }
        } else {
            ToastUtils.showShort("加载失败，请重新刷新");
        }
        
        pull_to_refresh.finishRefresh();
    }
    
    private void gotoTypeDetail(Long typeId, String from) {
        Intent intent = new Intent(_mActivity, SetModifyActivity.class);
        intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_ID, typeId);
        intent.putExtra(MoneyConstants.INTENT_TAX_TYPE_MODIFY_FROM, from);
        startActivity(intent);
    }
    
    public static SetsFragment newInstance() {
        Bundle args = new Bundle();
        SetsFragment fragment = new SetsFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerLocalReceiver(mUpdateReceiver,
                              new IntentFilter(ReceiverUtils.RECEIVER_TAX_TYPE_LIST_UPDATE));
    }
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost, container, false);
        
        initView(view);
        
        return view;
    }
    
    @Override public void onDestroy() {
        super.onDestroy();
        unRegistLocalReceiver(mUpdateReceiver);
    }
    
    @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
