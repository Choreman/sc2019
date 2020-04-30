package com.xzsd.pc.driver.dao;


import com.xzsd.pc.driver.entity.Driver;
import com.xzsd.pc.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 司机信息数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-03-29
 */
@Mapper
public interface DriverMapper {

    /**
     * 新增司机信息
     *
     * @param driver 司机信息
     * @return
     */
    int insertSelective(Driver driver);

    /**
     * 查询是否有相同省市区的司机信息
     *
     * @param driver 包含省市区编号
     * @return
     */
    int countDriverByArea(Driver driver);

    /**
     * 根据传入的查询条件查询司机存放在user表中的信息
     *
     * @param user   查询条件，存放在user表里的司机信息
     * @param driver 查询条件，存放在driver表里的司机信息
     * @return
     */
    List<User> listDrivers(@Param("user") User user, @Param("driver") Driver driver);

    /**
     * 根据传入的查询条件查询司机存放在user表中的信息
     * 只查询负责店长绑定店铺的司机列表信息（因为登录的是店长，权限只能查看店长绑定门店的司机列表信息）
     *
     * @param user   查询条件，存放在user表里的司机信息
     * @param driver 查询条件，存放在driver表里的司机信息
     * @return
     */
    List<User> listStoreDrivers(@Param("user") User user, @Param("driver") Driver driver);

    /**
     * 根据id查询司机信息（包含用户表、司机表、区域名称表里的信息）
     *
     * @param userId 司机在user表中的id
     * @return
     */
    User findDriverById(String userId);

    /**
     * 根据司机表关联的用户表编号修改司机信息
     *
     * @param driver 要修改的司机信息
     * @return
     */
    int updateByDriverUserCodeSelective(Driver driver);

    /**
     * 根据司机表关联的用户编号删除司机信息（修改字段is_delete状态，并非真正删除）
     *
     * @param listIds        要删除的司机信息列表
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteDriverByUserId(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

    int deleteByPrimaryKey(String driverId);

    int insert(Driver record);

    Driver selectByPrimaryKey(String driverId);

    int updateByPrimaryKey(Driver record);
}