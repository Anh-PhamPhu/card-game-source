package org.jeecg.modules.games.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.games.entity.SysUserOrder;

import java.util.List;

public interface SysUserOrderService extends IService<SysUserOrder> {

    SysUserOrder getSysUserOrderByOrderIdAndProId(String orderId,String proId,String name);

    SysUserOrder getSysUserOrderByUserId(String userId);

    Boolean deleteById(String id);

    List<SysUserOrder> getSysUserOrderListByUserId(String userId);

    int deleteByIds(String id);

    Boolean addItem(SysUserOrder sysUserOrder);
}
