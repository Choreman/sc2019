package com.xzsd.app.dict.dao;


import com.xzsd.app.dict.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictMapper {

    /**
     * 根据key查找字典值
     * @param key
     * @return
     */
    Dict selectDictByKey(@Param("key") String key);

}