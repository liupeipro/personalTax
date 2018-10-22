package com.hh.personaltax.calculator;

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
import com.hh.personaltax.model.TaxType;
import com.hh.personaltax.util.ReceiverUtils;
import com.hh.personaltax.view.RefreshLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

/**
 * 个税历史 列表
 */
public class CalculatorListFragment extends BaseFragment {
    
    public static CalculatorListFragment newInstance() {
        Bundle args = new Bundle();
        CalculatorListFragment fragment = new CalculatorListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
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
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("参数类型");
        bar_top.addRightImageButton(R.mipmap.plus, R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                       startActivity(new Intent(_mActivity.getApplicationContext(),
                                                CalculatorFilterActivity.class));
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
        //List<TaxType> tempData = TaxApplication.getDaoInstant().getTaxTypeDao().loadAll();
        //if (tempData != null) {
        //    QMUIGroupListView.Section sections =
        //        QMUIGroupListView.newSection(_mActivity).setTitle("").setDescription("");
        //    if (tempData.size() > 0) {
        //        for (final TaxType type : tempData) {
        //            QMUICommonListItemView itemView =
        //                groupListView.createItemView(null, type.getName(), "",
        //                                             QMUICommonListItemView.HORIZONTAL,
        //                                             QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        //            sections.addItemView(itemView, new View.OnClickListener() {
        //                @Override public void onClick(View v) {
        //                    gotoTypeDetail(type);
        //                }
        //            });
        //        }
        //
        //        sections.addTo(groupListView);
        //    } else {
        //        sections.removeFrom(groupListView);
        //        ToastUtils.showShort("数据为空...");
        //    }
        //} else {
        //    ToastUtils.showShort("加载失败，请重新刷新");
        //}
        //
        pull_to_refresh.finishRefresh();
    }
    
    private void gotoTypeDetail(TaxType type) {
        ToastUtils.showShort("类型修改");
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
