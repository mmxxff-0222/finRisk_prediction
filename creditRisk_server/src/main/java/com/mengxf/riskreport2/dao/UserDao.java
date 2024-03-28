package com.mengxf.riskreport2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxf.riskreport2.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDao
 *
 * @author Meng xf
 * @since 2024/3/20 10:56
 */
@Mapper
public interface UserDao extends BaseMapper<UserPojo> {
}
