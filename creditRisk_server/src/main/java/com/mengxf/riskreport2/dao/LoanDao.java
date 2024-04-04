package com.mengxf.riskreport2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxf.riskreport2.pojo.LoanPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * LoanDao
 *
 * @author Meng xf
 * @since 2024/3/20 11:21
 */
@Mapper
public interface LoanDao extends BaseMapper<LoanPojo> {

}
