package org.jeecg.modules.games.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.games.entity.SysUserOrder;
import org.jeecg.modules.games.mapper.SysUserOrderMapper;
import org.jeecg.modules.games.service.SysUserOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserOrderServiceImpl extends ServiceImpl<SysUserOrderMapper, SysUserOrder> implements SysUserOrderService {

    @Override
    public SysUserOrder getSysUserOrderByOrderIdAndProId(String orderId,String proId,String proName){
        LambdaQueryWrapper<SysUserOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserOrder::getOrderId,orderId);
        lambdaQueryWrapper.eq(SysUserOrder::getProId,proId);
        lambdaQueryWrapper.eq(SysUserOrder::getProName,proName);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public SysUserOrder getSysUserOrderByUserId(String userId){
        LambdaQueryWrapper<SysUserOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserOrder::getUserId,userId);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public Boolean deleteById(String id){
        return removeById(id);
    }

    @Override
    public List<SysUserOrder> getSysUserOrderListByUserId(String userId) {
        LambdaQueryWrapper<SysUserOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserOrder::getUserId,userId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @Transactional
    public int deleteByIds(String id) {
        LambdaQueryWrapper<SysUserOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserOrder::getUserId,id);
        return baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public Boolean addItem(SysUserOrder sysUserOrder){
        return save(sysUserOrder);
    }
}
