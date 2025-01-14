package org.jeecg.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.shiro.vo.DefContants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * @Author scott
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2Config implements WebMvcConfigurer {

	/**
	 *
	 * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	//配置content type
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
			new HashSet<String>(Arrays.asList("application/json","application/xml"));


	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 *
	 * @return Docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.globalOperationParameters(setHeaderToken())
				.select()
				//此包路径下的类，才生成接口文档
				.apis(RequestHandlerSelectors.basePackage("org.jeecg.modules"))
				//加了ApiOperation注解的类，才生成接口文档
	            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(Collections.singletonList(securityScheme()));

	}

	/**
	 * 不需要传递token的接口
	 *
	 * @param environment 用户获取环境
	 * @return Docket
	 */
//	@Bean
//	public Docket docketNotToken(Environment environment) {
//
//		Profiles profiles = Profiles.of("dev");
//		boolean flag = environment.acceptsProfiles(profiles);
//
//			return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				//.enable(flag)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("org.jeecg.modules"))
//				.paths(PathSelectors.ant("/sys/user/register"))
//		        .paths(PathSelectors.ant("/sys/user/changePassword"))
//				.paths(PathSelectors.ant("/sys/login"))
//				.build()
//				.groupName("不需要token接口")
//				.pathMapping("/");
//	}

	/**
	 * 需要传递token的接口
	 *
	 * @param environment 用户获取环境
	 * @return Docket
	 */
//	@Bean
//	public Docket docketNeedToken(Environment environment) {
//
//		Profiles profiles = Profiles.of("dev");
//		boolean flag = environment.acceptsProfiles(profiles);
//
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				//.enable(flag)
//				.globalOperationParameters(setHeaderToken())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("org.jeecg.modules"))
//				// 可以自己进行定义过滤的接口
//				.paths(path -> {
//					// 过滤掉不需要token验证的接口
//					return !path.contains(DefContants.X_ACCESS_TOKEN);
//				})
//				.build()
//				.groupName("需要token接口")
//				.pathMapping("/");
//	}

	/***
	 * oauth2配置
	 * 需要增加swagger授权回调地址
	 * http://localhost:8888/webjars/springfox-swagger-ui/o2c.html
	 * @return
	 */
	@Bean
	SecurityScheme securityScheme() {
		return new ApiKey(DefContants.X_ACCESS_TOKEN, DefContants.X_ACCESS_TOKEN, "header");
	}
	/**
	 * JWT token
	 * @return
	 */
	private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(DefContants.X_ACCESS_TOKEN).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

	/**
	 * api文档的详细信息函数,注意这里的注解引用的是哪个
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// //大标题
				.title("棋牌后台服务API接口文档")
				// 版本号
				.version("1.0")
//				.termsOfServiceUrl("NO terms of service")
				// 描述
				.description("后台API接口")
				// 作者
				.contact("棋牌")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}

}
