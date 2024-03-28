package com.mengxf.riskreport2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import org.apache.ibatis.annotations.Mapper;

/**
 * BorrowerDao
 *
 * @author Meng xf
 * @since 2024/3/20 14:17
 */
@Mapper
public interface BorrowerDao extends BaseMapper<BorrowerPojo> {
}
