package com.aki.springbootlogisticsadmin.mapper;

import com.aki.springbootlogisticsadmin.entity.SysOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@Repository
public interface SysOrderMapper extends BaseMapper<SysOrder> {

    @Select("select sys_user.username,sys_order.* from sys_user,sys_order where sys_order.uid=sys_user.id")
    List<SysOrder> listOrderInfo();
}
