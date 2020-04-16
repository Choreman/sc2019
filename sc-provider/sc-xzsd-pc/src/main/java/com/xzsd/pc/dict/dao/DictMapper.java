package com.xzsd.pc.dict.dao;


import com.xzsd.pc.dict.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictMapper {

    /**
     * 根据字典的key去更新字典的value
     * @param dict 包含要更新的字典的key和value
     * @param updatePersonId 更新人id
     * @return
     */
    int updateDictByKey(@Param("dict") Dict dict, @Param("updatePersonId") String updatePersonId);

    int deleteByPrimaryKey(String dictId);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(String dictId);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}