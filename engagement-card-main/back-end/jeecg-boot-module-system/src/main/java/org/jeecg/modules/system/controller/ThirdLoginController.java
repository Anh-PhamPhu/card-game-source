package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stripe.exception.StripeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGoogleScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFacebookRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.system.service.ISysUserUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * zlx
 */
@Controller
@RequestMapping("/oauth")
@Api(tags = "第三方登录")
@Slf4j
public class ThirdLoginController {
	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysUserUrlService sysUserUrlService;

	@Autowired
	private ISysBaseAPI sysBaseAPI;
	@Autowired
    private RedisUtil redisUtil;

	@Value("${redirect.url}")
	private String url;

	@Value("${justauth.type.facebook.client-id}")
	private String faceBookClientId;

	@Value("${justauth.type.facebook.client-secret}")
	private String faceBookClientSecret;

	@Value("${justauth.type.facebook.redirect-uri}")
	private String faceBookRedirectUri;

	@Value("${justauth.type.google.client-id}")
	private String googleClientId;

	@Value("${justauth.type.google.client-secret}")
	private String googleClientSecret;

	@Value("${justauth.type.google.redirect-uri}")
	private String googleRedirectUri;



	/**
	 * 获取授权Request
	 *
	 * @return AuthRequest
	 */
	private AuthRequest getGoogleAuthRequest() {
		return new AuthGoogleRequest(AuthConfig.builder()
				//gitee中的id
				.clientId(googleClientId)
				//gitee中的密钥
				.clientSecret(googleClientSecret)
				//在gitee中申请的回调地址
				.redirectUri(googleRedirectUri)
				.ignoreCheckState(false)
				.scopes(AuthScopeUtils.getScopes(AuthGoogleScope.USER_EMAIL, AuthGoogleScope.USER_PROFILE, AuthGoogleScope.USER_OPENID))
				.build());
	}

	/**
	 * 获取授权Request
	 *
	 * @return AuthRequest
	 */
	private AuthRequest getFacebookAuthRequest() {
		return new AuthFacebookRequest(AuthConfig.builder()
				//gitee中的id
				.clientId(faceBookClientId)
				//gitee中的密钥
				.clientSecret(faceBookClientSecret)
				//在gitee中申请的回调地址
				.redirectUri(faceBookRedirectUri)
				.build());
	}


	@ApiOperation(value = "进入第三方登录地址")
	@RequestMapping(value = "/render/{source}",method = RequestMethod.GET)
    public void render(@ApiParam(value = "第三方平台名字（facebook,google）") @PathVariable("source") String source, HttpServletResponse response) throws IOException {
        log.info("第三方登录进入render：" + source);
		AuthRequest authRequest = null;
		if(source.equals("facebook")){
			authRequest = getFacebookAuthRequest();
		}else if(source.equals("google")){
			authRequest = getGoogleAuthRequest();
		}
		String token = AuthStateUtils.createState();
		//生成授权url
		String authorizeUrl = authRequest.authorize(token);

//        AuthRequest authRequest = factory.get(source);
//        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("第三方登录认证地址：" + authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

	@RequestMapping("/callback/{source}")
    public Result<JSONObject> login(@PathVariable("source") String source, AuthCallback callback,ModelMap modelMap,HttpServletResponse responses) throws IOException, StripeException {
		log.info("第三方登录进入callback：" + source + " params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = null;
		if(source.equals("facebook")){
			authRequest = getFacebookAuthRequest();
		}else if(source.equals("google")){
			authRequest = getGoogleAuthRequest();
		}
        AuthResponse<AuthUser> response = authRequest.login(callback);
        log.info(JSONObject.toJSONString(response));
        Result<JSONObject> result = new Result<JSONObject>();
        if(response.ok()) {

        	JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(response.getData()));
        	String username = data.getString("username");
        	String avatar = data.getString("avatar");
        	String uuid = data.getString("uuid");

        	//判断有没有这个人
        	LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<SysUser>();
        	query.eq(SysUser::getThirdId, uuid);
        	query.eq(SysUser::getThirdType, source);
        	List<SysUser> thridList = sysUserService.list(query);
        	SysUser user = null;
        	if(thridList==null || thridList.size()==0) {
        		user = new SysUser();
        		user.setActivitiSync(CommonConstant.ACT_SYNC_0);
        		user.setDelFlag(CommonConstant.DEL_FLAG_0);
        		user.setStatus(1);
        		user.setThirdId(uuid);
        		user.setThirdType(source);
        		user.setAvatar(avatar);
//        		user.setUsername(uuid);
				user.setUsername(username);
				user.setNickname(username.substring(0,6));
				user.setEmail(username);
        		user.setRealname(username);
				user.setCustomerId(sysUserUrlService.getCustomerId(username));

        		//设置初始密码
        		String salt = oConvertUtils.randomGen(8);
    			user.setSalt(salt);
    			String passwordEncode = PasswordUtil.encrypt(user.getUsername(), "123456", salt);
    			user.setPassword(passwordEncode);
        		sysUserService.saveThirdUser(user);
        	}else {
        		//已存在 只设置用户名 不设置头像
        		user = thridList.get(0);
        		//user.setUsername(username);
        		//sysUserService.updateById(user);
        	}
        	// 生成token
    		String token = JwtUtil.sign(user.getUsername(), user.getPassword());
    		redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
    		// 设置超时时间
    		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
    		modelMap.addAttribute("token", token);
			responses.sendRedirect(url+"?token="+token);
        }
		result.setCode(response.getCode());
        result.setSuccess(false);
        result.setMessage(response.getMsg());
        return result;
    }

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据第三方用户token获取用户信息")
	@RequestMapping(value = "/getLoginUser/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Result<JSONObject> getLoginUser(@PathVariable("token") String token) throws Exception {
		Result<JSONObject> result = new Result<JSONObject>();
		String username = JwtUtil.getUsername(token);

		//1. 校验用户是否有效
			SysUser sysUser = sysUserService.getUserByName(username);
		result = sysUserService.checkUserIsEffective(sysUser);
		if(!result.isSuccess()) {
			return result;
		}
		JSONObject obj = new JSONObject();
		//用户登录信息
		obj.put("userInfo", sysUser);
		//token 信息
		obj.put("token", token);
		result.setResult(obj);
		result.setSuccess(true);
		result.setCode(200);
		sysBaseAPI.addLog("用户名: " + username + ",登录成功[第三方用户]！", CommonConstant.LOG_TYPE_1, null);
		return result;
	}

}
