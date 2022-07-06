package org.jeecg.modules.games.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.jeecg.modules.games.dto.AddResultParam;
import org.jeecg.modules.games.entity.CgResult;
import org.jeecg.modules.games.entity.SysUserOrder;
import org.jeecg.modules.games.mapper.CgResultMapper;
import org.jeecg.modules.games.service.CgResultService;
import org.jeecg.modules.games.service.SysUserOrderService;
import org.jeecg.modules.games.vo.CgResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CgResultServiceImpl extends ServiceImpl<CgResultMapper, CgResult> implements CgResultService {

    @Autowired
    private SysUserOrderService sysUserOrderService;

    @Override
    @Transactional
    public Boolean addList(List<AddResultParam> list) {
        List<CgResult> list1 = new ArrayList<>();
        list.stream().forEach(item -> {
            CgResult cgResult = new CgResult();
            BeanUtils.copyProperties(item, cgResult);
            list1.add(cgResult);
        });
        return saveBatch(list1);
    }

    @Override
    public List<CgResultVO> getDetail(String roomId, String customId) {
        LambdaQueryWrapper<CgResult> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CgResult::getRoomId, roomId);
        List<CgResult> selectList = baseMapper.selectList(lambdaQueryWrapper);
        List<CgResultVO> selectListvO = new ArrayList<>();
        selectList.stream().forEach(item -> {
            CgResultVO cgResultVO = new CgResultVO();
            BeanUtils.copyProperties(item, cgResultVO);

            if (item.getUserRole() == 1) {
                if (StringUtils.isBlank(customId)){
                    cgResultVO.setIsShowCard(0);
                }
                SysUserOrder sysUserOrder = sysUserOrderService.getSysUserOrderByUserId(customId);
                if (ObjectUtils.isEmpty(sysUserOrder)) {
                    //这说明这个人没有订阅过
                    cgResultVO.setIsShowCard(0);
                } else {
                    if(sysUserOrder.getProId().equals("prod_LlZwsOKPOn0TCt")){
                        cgResultVO.setIsShowCard(0);
                    }else{
                        cgResultVO.setIsShowCard(1);
                    }
                }
            } else {
                cgResultVO.setIsShowCard(1);
            }
            selectListvO.add(cgResultVO);
        });
        return selectListvO;
    }
}
