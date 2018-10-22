package com.hh.personaltax.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hh.personaltax.R;
import com.hh.personaltax.model.TaxTypeRule;

/**
 * 个税类型规则列表
 */
public class TaxTypeListAdapter extends AbsMultiTypeAdapter<TaxTypeRule> {
    
    public TaxTypeListAdapter(Context c) {
        super(c);
    }
    
    private void bindView(ItemTaxTypeVholder holder, TaxTypeRule item) {
        holder.update(item);
    }
    
    @Override protected void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        TaxTypeRule item = getItem(position);
        
        if (viewHolder instanceof ItemTaxTypeVholder) {
            bindView((ItemTaxTypeVholder) viewHolder, item);
        }
    }
    
    @Override
    protected BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType) {
        return new ItemTaxTypeVholder(view);
    }
    
    @Override protected int getLayoutResId(int position) {
        return R.layout.item_tax_type;
    }
    
    @Override public int getViewTypeCount() {
        return 2;
    }
    
    public static class ItemTaxTypeVholder extends BaseViewHolder {
        public TextView tv_amount, et_fix_amount, et_scale;
        
        public ItemTaxTypeVholder(View itemView) {
            super(itemView);
            tv_amount = mItemView.findViewById(R.id.tv_amount);
            et_fix_amount = mItemView.findViewById(R.id.et_fix_amount);
            et_scale = mItemView.findViewById(R.id.et_scale);
        }
        
        public void update(TaxTypeRule item) {
            if (item != null) {
                String tempAmountType = item.getAmountType();
                String tempAmount = "";
                if (tempAmountType.equals(TaxTypeRule.TYPE_AMOUNT_ABOVE)) {
                    tempAmount += "> ￥" + item.getMinAmount();
                } else if (tempAmountType.equals(TaxTypeRule.TYPE_AMOUNT_BELOW)) {
                    tempAmount += "<= ￥" + item.getMaxAmount();
                } else if (tempAmountType.equals(TaxTypeRule.TYPE_AMOUNT_NORMAL)) {
                    tempAmount += "￥" + item.getMinAmount() + " - ￥" + item.getMaxAmount();
                }
                
                tv_amount.setText(tempAmount);
                
                if (item.getFixAmount() != null && item.getFixAmount() > 0) {
                    et_fix_amount.setText(item.getFixAmount().toString());
                }
                if (item.getScale() != null && item.getScale() > 0) {
                    et_scale.setText(item.getScale().toString());
                }
            }
        }
    }
}
