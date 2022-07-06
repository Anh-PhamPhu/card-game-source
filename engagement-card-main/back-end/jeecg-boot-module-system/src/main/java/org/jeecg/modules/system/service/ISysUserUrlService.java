package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stripe.exception.StripeException;
import org.jeecg.modules.games.vo.CustomerVO;
import org.jeecg.modules.system.entity.SysUserUrl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户链接表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
public interface ISysUserUrlService extends IService<SysUserUrl> {


	/**
	 * 保存链接
	 * @param
	 */
	void saveUserUrl(SysUserUrl sysUserUrl);


	/**
	 * 生成customerid
	 * @param email
	 * @return
	 * @throws StripeException
	 */
	String getCustomerId(String email) throws StripeException;

	/**
	 * 根据用户id查询
	 * @param
	 * @return
	 */
	SysUserUrl getUrl(String userId,String proName);
}
