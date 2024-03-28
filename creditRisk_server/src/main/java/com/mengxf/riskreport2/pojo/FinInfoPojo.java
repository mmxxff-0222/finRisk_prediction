package com.mengxf.riskreport2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FinInfoPojo
 *
 * @author Meng xf
 * @since 2024/3/20 11:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "financial_health")
public class FinInfoPojo {
    @TableId(value = "userID",type = IdType.NONE)
    private int userID;
    @TableField(value = "dti")
    private float dti;
    @TableField(value = "delinq_2yrs")
    private float delinq_2yrs;
    @TableField(value = "open_acc")
    private float open_acc;
    @TableField(value = "pub_rec")
    private float pub_rec;
    @TableField(value = "acc_now_delinq")
    private float acc_now_delinq;
    @TableField(value = "tot_coll_amt")
    private float tot_coll_amt;
    @TableField(value = "tot_cur_bal")
    private float tot_cur_bal;
    @TableField(value = "revol_bal")
    private float revol_bal;
    @TableField(value = "revol_util")
    private float revol_util;
    @TableField(value = "total_acc")
    private float total_acc;
    @TableField(value = "total_pymnt")
    private float total_pymnt;
    @TableField(value = "total_rec_late_fee")
    private float total_rec_late_fee;
    @TableField(value = "last_pymnt_amnt")
    private float last_pymnt_amnt;

    public void updateAttributes(FinInfoPojo newFin){
        this.setUserID(newFin.getUserID());
        this.setDti(newFin.getDti());
        this.setDelinq_2yrs(newFin.getDelinq_2yrs());
        this.setOpen_acc(newFin.getOpen_acc());
        this.setPub_rec(newFin.getPub_rec());
        this.setAcc_now_delinq(newFin.getAcc_now_delinq());
        this.setTot_coll_amt(newFin.getTot_coll_amt());
        this.setTot_cur_bal(newFin.getTot_cur_bal());
        this.setRevol_bal(newFin.getRevol_bal());
        this.setRevol_util(newFin.getRevol_util());
        this.setTotal_acc(newFin.getTotal_acc());
        this.setTotal_pymnt(newFin.getTotal_pymnt());
        this.setTotal_rec_late_fee(newFin.getTotal_rec_late_fee());
        this.setLast_pymnt_amnt(newFin.getLast_pymnt_amnt());
    }
}
