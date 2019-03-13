package com.harry.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author harry
 * @create 2019-03-12 14:08
 **/
public abstract class BaseService<M extends Serializable, K extends Serializable> {

    private BaseMapper<M, K> baseMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis key
     *
     * @return key
     */
    protected abstract String getRedisKey();


    public void setBaseMapper(BaseMapper<M, K> baseMapper) {
        this.baseMapper = baseMapper;
    }

    public M select(K id) throws Exception {
        return this.baseMapper.select(id);
    }

    public int delete(K id) throws Exception {
        return this.baseMapper.delete(id);
    }

    public int update(M model) throws Exception {
        return this.baseMapper.update(model);
    }

    public int insert(M model) throws Exception {
        return this.baseMapper.insert(model);
    }

    public List<M> selectListByModel(M m) throws Exception {
        return this.baseMapper.selectListByModel(m);
    }

    /**
     * 添加
     *
     * @param key  key
     * @param data 对象
     */
    public void put(String key, Object data) {
        redisTemplate.opsForValue().set(key, data);
    }

    /**
     * 删除
     *
     * @param keys 传入key的名称
     */
    @SuppressWarnings("unchecked")
    public void remove(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    /**
     * 查询
     *
     * @param key 查询的key
     * @return model
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key是否存在redis中
     *
     * @param key 传入key的名称
     * @return boolean
     */
    public boolean isKeyExists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
