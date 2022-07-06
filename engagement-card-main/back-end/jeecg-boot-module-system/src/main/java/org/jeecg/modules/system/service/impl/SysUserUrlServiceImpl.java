package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.jeecg.modules.games.dto.CustomerCreateParam;
import org.jeecg.modules.games.vo.CustomerVO;
import org.jeecg.modules.system.entity.SysUserUrl;
import org.jeecg.modules.system.mapper.SysUserUrlMapper;
import org.jeecg.modules.system.service.ISysUserUrlService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SysUserUrlServiceImpl extends ServiceImpl<SysUserUrlMapper, SysUserUrl> implements ISysUserUrlService {

    @Value("${Stripe.apikey}")
    private String apikey;


    @Override
    public void saveUserUrl(SysUserUrl sysUserUrl) {
        save(sysUserUrl);
    }

    @Override
    public String getCustomerId(String email) throws StripeException {
        Stripe.apiKey = apikey;
        CustomerCreateParam customerCreateParam = new CustomerCreateParam();
        customerCreateParam.setEmail(email);
        CustomerCreateParams customerCreateParams1 = CustomerCreateParams.builder().build();
        BeanUtils.copyProperties(customerCreateParam, customerCreateParams1);
        Customer customer = Customer.create(customerCreateParams1);

        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer, customerVO);

        return customerVO.getId();
    }


    @Override
    public SysUserUrl getUrl(String userId,String proName) {
        LambdaQueryWrapper<SysUserUrl> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserUrl::getProName,proName);
        lambdaQueryWrapper.eq(SysUserUrl::getUserId,userId);
        return getOne(lambdaQueryWrapper);
    }
}
