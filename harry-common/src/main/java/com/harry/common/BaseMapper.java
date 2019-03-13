package com.harry.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author harry
 * @create 2019-03-12 14:10
 **/
public interface BaseMapper<M extends Serializable, K extends Serializable> {

    M select(K var1);

    int insert(M var1);

    int batchInsert(List<M> var1);

    int update(M var1);

    int updateByPrimaryKeySelective(M var1);

    int delete(K var1);

    List<M> selectAll();

    List<M> selectListByModel(M var1);

    M selectByModel(M var1);

    int batchDelete(List<K> var1);

}
