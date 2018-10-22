package com.hh.personaltax.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.hh.personaltax.greendao.gen.DaoSession;
import com.hh.personaltax.greendao.gen.TaxHistoryDao;
import com.hh.personaltax.greendao.gen.TaxTypeDao;

/**
 * 个税-历史记录
 */
@Entity public class TaxHistory {
    
    @Id(autoincrement = true) Long id;
    
    /**
     * 类型 ID
     */
    Long taxTypeId;
    
    /**
     * 个税类型
     */
    @NotNull @ToOne(joinProperty = "taxTypeId") TaxType taxType;
    
    /**
     * 税前收入
     */
    @NotNull String beforeTax;
    
    /**
     * 税后收入
     */
    @NotNull String afterTax;
    
    /**
     * 扣除社保
     */
    String socialSecurityAmount;
    
    /**
     * 其他扣除
     */
    String otherAmount;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2122502627)
    private transient TaxHistoryDao myDao;

    @Generated(hash = 201575150)
    public TaxHistory(Long id, Long taxTypeId, @NotNull String beforeTax,
            @NotNull String afterTax, String socialSecurityAmount,
            String otherAmount) {
        this.id = id;
        this.taxTypeId = taxTypeId;
        this.beforeTax = beforeTax;
        this.afterTax = afterTax;
        this.socialSecurityAmount = socialSecurityAmount;
        this.otherAmount = otherAmount;
    }

    @Generated(hash = 1141208523)
    public TaxHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxTypeId() {
        return this.taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getBeforeTax() {
        return this.beforeTax;
    }

    public void setBeforeTax(String beforeTax) {
        this.beforeTax = beforeTax;
    }

    public String getAfterTax() {
        return this.afterTax;
    }

    public void setAfterTax(String afterTax) {
        this.afterTax = afterTax;
    }

    public String getSocialSecurityAmount() {
        return this.socialSecurityAmount;
    }

    public void setSocialSecurityAmount(String socialSecurityAmount) {
        this.socialSecurityAmount = socialSecurityAmount;
    }

    public String getOtherAmount() {
        return this.otherAmount;
    }

    public void setOtherAmount(String otherAmount) {
        this.otherAmount = otherAmount;
    }

    @Generated(hash = 1938726012)
    private transient Long taxType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1571026648)
    public TaxType getTaxType() {
        Long __key = this.taxTypeId;
        if (taxType__resolvedKey == null || !taxType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TaxTypeDao targetDao = daoSession.getTaxTypeDao();
            TaxType taxTypeNew = targetDao.load(__key);
            synchronized (this) {
                taxType = taxTypeNew;
                taxType__resolvedKey = __key;
            }
        }
        return taxType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 599688376)
    public void setTaxType(TaxType taxType) {
        synchronized (this) {
            this.taxType = taxType;
            taxTypeId = taxType == null ? null : taxType.getId();
            taxType__resolvedKey = taxTypeId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 519861591)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaxHistoryDao() : null;
    }
}
