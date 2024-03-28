package com.mengxf.riskreport2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import javafx.application.Application;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoanPojo
 *
 * @author Meng xf
 * @since 2024/3/20 11:11
 */
@Data
@AllArgsConstructor
@TableName(value = "loan")
public class LoanPojo {

    @TableId(value = "loanID", type = IdType.NONE)
    private int loanID;
    @TableField(value = "userID")
    private int userID;
    @TableField(value = "purpose")
    private String purpose;
    @TableField(value = "application_type")
    private String application_type;
    @TableField(value = "loan_amnt")
    private float loan_amnt;
    @TableField(value = "term")
    private String term;
    @TableField(value = "int_rate")
    private float int_rate;
    @TableField(value = "installment")
    private float installment;
    @TableField(value = "grade")
    private String grade;

}
