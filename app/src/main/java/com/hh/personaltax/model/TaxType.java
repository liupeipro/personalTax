package com.hh.personaltax.model;

import com.google.gson.reflect.TypeToken;
import com.hh.personaltax.util.GsonUtils;
import java.util.List;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 个税类型
 */
@Entity public class TaxType {
    @Id(autoincrement = true) Long id;
    
    @NotNull @Unique String name;
    
    @NotNull String rules;
    
    @Generated(hash = 1577541050)
    public TaxType(Long id, @NotNull String name, @NotNull String rules) {
        this.id = id;
        this.name = name;
        this.rules = rules;
    }
    
    @Generated(hash = 197996037) public TaxType() {
    }
    
    //************************************************************ 自定义函数
    
    public void setRulesList(List<TaxTypeRule> rs) {
        if (rs != null) {
            String strRs = GsonUtils.toJson(rs);
            this.rules = strRs;
        }
    }
    
    public List<TaxTypeRule> getRulesList() {
        List<TaxTypeRule> result = GsonUtils.fromJson(rules, new TypeToken<List<TaxTypeRule>>() {
        }.getType());
        return result;
    }
    //************************************************************ 自定义函数
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setRules(String rules) {
        this.rules = rules;
    }
    
    public String getRules() {
        return this.rules;
    }
}
