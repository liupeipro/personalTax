package com.hh.personaltax.model;

/**
 * 个税类型-规则设置
 */
public class TaxTypeRule {
    
    /**
     * **以上
     */
    public static final String TYPE_AMOUNT_ABOVE = "1";
    /**
     * ** <= ** <= **
     */
    public static final String TYPE_AMOUNT_NORMAL = "0";
    /**
     * **以下
     */
    public static final String TYPE_AMOUNT_BELOW = "2";
    
    Long id;
    
    /**
     * 最小金额
     */
    Integer minAmount;
    
    /**
     * 最大金额
     */
    Integer maxAmount;
    
    /**
     * 1000 以下
     * 或 20000以上
     * 或 中间
     */
    String amountType = TYPE_AMOUNT_NORMAL;
    
    /**
     * 比例
     */
    Integer scale;
    
    /**
     * 速算扣除数
     */
    Integer fixAmount;
    
    //************************************************************ 自定义函数
    public void setTypeAmountNormal() {
        this.amountType = TYPE_AMOUNT_NORMAL;
    }
    
    public void setTypeAmountAbove() {
        this.amountType = TYPE_AMOUNT_ABOVE;
    }
    
    public void setTypeAmountBelow() {
        this.amountType = TYPE_AMOUNT_BELOW;
    }
    
    //************************************************************ 自定义函数
    
    public TaxTypeRule() {
        setTypeAmountNormal();
    }
    
    public TaxTypeRule(Integer minAmount, Integer maxAmount, String amountType, Integer scale,
                       Integer fixAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.amountType = amountType;
        this.scale = scale;
        this.fixAmount = fixAmount;
    }
    
    public Integer getMinAmount() {
        return minAmount;
    }
    
    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }
    
    public Integer getMaxAmount() {
        return maxAmount;
    }
    
    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }
    
    public String getAmountType() {
        return amountType;
    }
    
    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }
    
    public Integer getScale() {
        return scale;
    }
    
    public void setScale(Integer scale) {
        this.scale = scale;
    }
    
    public Integer getFixAmount() {
        return fixAmount;
    }
    
    public void setFixAmount(Integer fixAmount) {
        this.fixAmount = fixAmount;
    }
}
