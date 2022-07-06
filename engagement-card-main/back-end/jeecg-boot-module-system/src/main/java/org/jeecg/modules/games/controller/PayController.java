package org.jeecg.modules.games.controller;

import com.alibaba.fastjson.JSONObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.model.checkout.SessionCollection;
import com.stripe.net.Webhook;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.games.dto.*;
import org.jeecg.modules.games.entity.SysUserOrder;
import org.jeecg.modules.games.query.*;
import org.jeecg.modules.games.service.SysUserOrderService;
import org.jeecg.modules.games.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * @Title: DuplicateCheckAction
 * @Description: 重复校验工具
 * @Author 张代浩
 * @Date 2019-03-25
 * @Version V1.0
 */
@Slf4j
@Api(tags = "支付")
@RestController
@RequestMapping("/v1")
@Validated
public class PayController {


    @Value("${Stripe.apikey}")
    private String apikey;

    @Autowired
    private SysUserOrderService sysUserOrderService;


    /**
     * 创建customer_id
     */
    @ApiOperation(notes = "创建customer_id", value = "创建customer_id")
    @PostMapping("/createCustomer")
    public Result<CustomerVO> customers(@RequestBody CustomerCreateParam customerCreateParam) throws StripeException {
        Result<CustomerVO> result = new Result<>();
        Stripe.apiKey = apikey;
        CustomerCreateParams customerCreateParams1 = CustomerCreateParams.builder().build();
        BeanUtils.copyProperties(customerCreateParam, customerCreateParams1);
        Customer customer = Customer.create(customerCreateParams1);

        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer, customerVO);

        result.setCode(200);
        result.setResult(customerVO);
        result.setSuccess(true);
        return result;
    }



    /**
     * 创建price_id
     */
    @ApiOperation(notes = "创建price_id", value = "创建price_id")
    @PostMapping("/createPrice")
    public Result<PriceVO> prices(@RequestBody PriceCreateParam priceCreateParam) throws StripeException {
        Result<PriceVO> result = new Result<>();
        Stripe.apiKey = apikey;

        Map<String, Object> recurring = new HashMap<>();
        recurring.put("interval", priceCreateParam.getInterval());
        Map<String, Object> params = new HashMap<>();
        params.put("unit_amount", priceCreateParam.getUnitAmount());
        params.put("currency", priceCreateParam.getCurrency());
        params.put("recurring", recurring);
        params.put("product", priceCreateParam.getProduct());

        Price price = Price.create(params);
        PriceVO priceVO = new PriceVO();
        BeanUtils.copyProperties(price, priceVO);
        result.setCode(200);
        result.setResult(priceVO);
        result.setSuccess(true);
        return result;
    }

    /**
     * 创建product
     */
    @ApiOperation(notes = "创建product", value = "创建product")
    @PostMapping("/createProduct")
    public Result<ProductVO> createProduct(@Valid @RequestBody ProductCreateParam productCreateParam) throws StripeException {
        Result<ProductVO> result = new Result<>();
        Stripe.apiKey = apikey;

        Map<String, Object> params = new HashMap<>();
        params.put("name", productCreateParam.getName());

        Product product = Product.create(params);

        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);

        result.setCode(200);
        result.setResult(productVO);
        result.setSuccess(true);
        return result;
    }


    /**
     * product列表
     */
    @ApiOperation(notes = "product列表", value = "product列表")
    @PostMapping("/productList")
    public Result<List<ProductListVO>> productList(@RequestBody ProductListQuery productListQuery) throws StripeException {
        Result<List<ProductListVO>> result = new Result<>();
        Stripe.apiKey = apikey;


        ProductListParams productListParams = ProductListParams.builder().build();
        BeanUtils.copyProperties(productListQuery, productListParams);
        ProductCollection product = Product.list(productListParams);

        ProductCollectionVO productCollectionVO = new ProductCollectionVO();
        BeanUtils.copyProperties(product, productCollectionVO);

        List<ProductListVO> resultList = new ArrayList<>();

        ProductListVO productListVO = new ProductListVO();
        productListVO.setId("one");
        productListVO.setName("1on1プラン");
        productListVO.setPrice("無料/月");
        productListVO.setDescribe("お試しプランでまずチームメンバーと\n" +
                "ゲームしよう！");
        List<String> onePer = new ArrayList<>();
        onePer.add("1ID1ルーム");
        onePer.add("自分の結果データを保存");
        productListVO.setJurisdiction(onePer);
        resultList.add(productListVO);
        List<ProductListVO> resultListStripe = new ArrayList<>();
        product.getData().stream().forEach(item ->
        {
            ProductListVO productListStripeVO = new ProductListVO();
            productListStripeVO.setId(item.getId());
            productListStripeVO.setName(item.getName());
            productListStripeVO.setDescribe("お試しプランでまずチームメンバーと\n" +
                    "ゲームしよう！");
            List<String> stripePer = new ArrayList<>();
            if (item.getName().equals("基本プラン")) {
                stripePer.add("1〜4人ルームを作成可能");
                stripePer.add("過去の分析では自分のカードしか表示されません");
                stripePer.add("固定URL機能は使用できません");
                productListStripeVO.setPrice("990円/月");
                productListStripeVO.setPriceCount(990);
                productListStripeVO.setCompany("円");
                productListStripeVO.setTime("月");
            } else if (item.getName().equals("法人力 夕")){
                stripePer.add("同時に最大5つの部屋を作成することが可能");
                stripePer.add("過去の分析では、全員のカードを確認することができます");
                stripePer.add("固定URL機能は使用できます");
                productListStripeVO.setPrice("5000円/月");
                productListStripeVO.setPriceCount(5000);
                productListStripeVO.setCompany("円");
                productListStripeVO.setTime("月");
            } else {
                stripePer.add("同時に最大5つの部屋を作成することが可能");
                stripePer.add("過去の分析では、全員のカードを確認することができます");
                stripePer.add("固定URL機能は使用できません");
                productListStripeVO.setPrice("3300円/月");
                productListStripeVO.setPriceCount(3300);
                productListStripeVO.setCompany("円");
                productListStripeVO.setTime("月");
            }
            productListStripeVO.setJurisdiction(stripePer);
            resultListStripe.add(productListStripeVO);
        });
        Collections.reverse(resultListStripe);
        resultList.addAll(resultListStripe);
        result.setCode(200);
        result.setResult(resultList);
        result.setSuccess(true);
        return result;
    }


    /**
     * 创建plan
     */
    @ApiOperation(notes = "创建plan", value = "创建plan")
    @PostMapping("/createPlan")
    public Result<PlanVO> createPlan(@Valid @RequestBody PlanCreateParam planCreateParams) throws StripeException {
        Result<PlanVO> result = new Result<>();
        Stripe.apiKey = apikey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", planCreateParams.getAmount());
        params.put("currency", planCreateParams.getCurrency());
        params.put("interval", planCreateParams.getInterval());
        params.put("product", planCreateParams.getProduct());

        Plan plan = Plan.create(params);

        PlanVO planVO = new PlanVO();
        BeanUtils.copyProperties(plan, planVO);

        result.setCode(200);
        result.setResult(planVO);
        result.setSuccess(true);
        return result;
    }

    /**
     * plan列表[需改成上边格式，不能用支付封装类]
     */
    @ApiOperation(notes = "plan列表", value = "plan列表")
    @PostMapping("/planList")
    public Result<PlanCollectionVO> planList(@RequestBody PlanListQuery planListQuery) throws StripeException {
        Result<PlanCollectionVO> result = new Result<>();
        Stripe.apiKey = apikey;

        PlanListParams planListParams = PlanListParams.builder().build();
        BeanUtils.copyProperties(planListQuery, planListParams);
        PlanCollection planCollection = Plan.list(planListParams);

        PlanCollectionVO planCollectionVO = new PlanCollectionVO();
        BeanUtils.copyProperties(planCollection, planCollectionVO);

        result.setCode(200);
        result.setResult(planCollectionVO);
        result.setSuccess(true);
        return result;
    }


    public static void main(String[] args) throws StripeException {
        Stripe.apiKey = "sk_test_51KttzDJynwYQPikFCXTg8Se1sFkQMe25r8DGHvupHqEZP49Mhw6rXBpjBtUDH0lzLJSBCj43aAeE6wW6ld7xjPz300waf7hxaE";

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 8);

        SubscriptionCollection subscriptions =
                Subscription.list(params);
//        Product product = Product.retrieve("prod_LlZwsOKPOn0TCt");
//        Subscription subscription =
//                Subscription.retrieve(
//                        "sub_1Ky4RKH6oY2NyzIRzza2dKQK"
//                );
////sub_1Ky4RKH6oY2NyzIRzza2dKQK
//        Subscription subscriptions =
//                subscription.cancel();

//        SessionCollection session =
//                Session.list(params);
        System.out.println(subscriptions);


    }

    /**
     * 查询订阅(因为一人一订阅，可以通过客户id查询订阅信息)
     */
    @ApiOperation(notes = "查询订阅", value = "查询订阅")
    @GetMapping("/subscriptionSearch")
    public Result<SubscriptionDataVO> subscriptionSearch(@RequestParam(value = "customerId",required = true) @NotBlank(message="customerId不能为空")  String customerId) throws StripeException {
        Result<SubscriptionDataVO> result = new Result<>();
        Stripe.apiKey = apikey;

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
//        params.put("limit", 3);
        SubscriptionCollection subscriptions = Subscription.list(params);
        if(subscriptions.getData().size() > 0){
            result.setCode(200);
            SubscriptionDataVO subscriptionDataVO = new SubscriptionDataVO();
            subscriptionDataVO.setId(subscriptions.getData().get(0).getId());
            String productId = subscriptions.getData().get(0).getItems().getData().get(0).getPrice().getProduct();
            Product product =  Product.retrieve(productId);


            //查询是否已经订阅了(如果订阅了不添加了，如果没有就添加)
            SysUserOrder sysUserOrder = sysUserOrderService.getSysUserOrderByOrderIdAndProId(subscriptionDataVO.getId(),product.getId(),product.getName());
            if(ObjectUtils.isEmpty(sysUserOrder)){
                List<SysUserOrder> sysUserOrderUserId = sysUserOrderService.getSysUserOrderListByUserId(subscriptions.getData().get(0).getCustomer());
                if(!CollectionUtils.isEmpty(sysUserOrderUserId)) {
                    sysUserOrderService.deleteByIds(subscriptions.getData().get(0).getCustomer());
                }
                SysUserOrder sysUserOrders = new SysUserOrder();
                sysUserOrders.setOrderId(subscriptionDataVO.getId());
                sysUserOrders.setUserId(subscriptions.getData().get(0).getCustomer());
                sysUserOrders.setProId(product.getId());
                sysUserOrders.setProName(product.getName());
                sysUserOrderService.addItem(sysUserOrders);
            }


            ProductListVO productListVO = new ProductListVO();
            productListVO.setId(product.getId());
            productListVO.setName(product.getName());
            productListVO.setDescribe("お試しプランでまずチームメンバーと\n" +
                    "ゲームしよう！");
            List<String> stripePer = new ArrayList<>();
            if (productListVO.getName().equals("基本プラン")) {
                stripePer.add("1〜4人ルームを作成可能");
                stripePer.add("過去の分析では自分のカードしか表示されません");
                stripePer.add("固定URL機能は使用できません");
                productListVO.setPrice("990円/月");
                productListVO.setPriceCount(990);
                subscriptionDataVO.setIsCreateUrl(0);
            } else if (productListVO.getName().equals("法人力 夕")){
                stripePer.add("同時に最大5つの部屋を作成することが可能");
                stripePer.add("過去の分析では、全員のカードを確認することができます");
                stripePer.add("固定URL機能は使用できます");
                productListVO.setPrice("5000円/月");
                productListVO.setPriceCount(5000);
                subscriptionDataVO.setIsCreateUrl(1);
            } else {
                stripePer.add("同時に最大5つの部屋を作成することが可能");
                stripePer.add("過去の分析では、全員のカードを確認することができます");
                stripePer.add("固定URL機能は使用できません");
                productListVO.setPrice("3300円/月");
                productListVO.setPriceCount(3300);
                subscriptionDataVO.setIsCreateUrl(0);
            }
            productListVO.setCompany("円");
            productListVO.setTime("月");
            productListVO.setJurisdiction(stripePer);
            subscriptionDataVO.setProductListVO(productListVO);
            subscriptionDataVO.setStartTime(DateUtils.longtoStr(subscriptions.getData().get(0).getCurrentPeriodStart()));
            subscriptionDataVO.setEndTime(DateUtils.longtoStr(subscriptions.getData().get(0).getCurrentPeriodEnd()));
            result.setResult(subscriptionDataVO);
            result.setSuccess(true);
            return result;
        }
//
//        "current_period_end": 1656403750,
//                "current_period_start": 1653725350,

        result.setCode(200);
        result.setResult(null);
        result.setSuccess(true);
        return result;
    }


    /**
     * 创建session
     */
    @ApiOperation(notes = "创建session", value = "创建session")
    @PostMapping("/createSession")
    public Result<SessionVO> createSession(@Valid @RequestBody SessionCreateParam sessionCreateParam) throws StripeException {
        Result<SessionVO> result = new Result<>();
        Stripe.apiKey = apikey;

        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem1 = new HashMap<>();
//        lineItem1.put("price", "price_1KxshnH6oY2NyzIRses8uonC");
        lineItem1.put("price", sessionCreateParam.getPrice());
        lineItem1.put("quantity", 1);


        lineItems.add(lineItem1);
        Map<String, Object> params = new HashMap<>();
        params.put(
                "success_url",
                "https://yiju.lanyanceshi.com/cardgame/#/pay/choose"
        );
        params.put(
                "cancel_url",
                "https://cardgame.unismart.dev/dev/jeecg-boot/v1/cancel"
        );
        params.put("line_items", lineItems);
        params.put("mode", SessionCreateParams.Mode.SUBSCRIPTION);
        params.put("customer",sessionCreateParam.getCustomerId());

        List<String> method_types = new ArrayList<>();
        method_types.add("card");
        params.put("payment_method_types",method_types);

        Session session = Session.create(params);

        SessionVO sessionVO = new SessionVO();
        BeanUtils.copyProperties(session, sessionVO);

        result.setCode(200);
        result.setResult(sessionVO);
        result.setSuccess(true);
        return result;
    }


    /**
     * 创建取消页面
     */
    @ApiOperation(notes = "cancel页面", value = "cancel页面")
    @PostMapping("/cancel")
    public Result<String> cancel() throws StripeException {
        Result<String> result = new Result<>();
        Stripe.apiKey = apikey;

        String resultData = "<html><body><h1>Cancel for your order, !</h1></body></html>";

        result.setCode(200);
        result.setResult(resultData);
        result.setSuccess(true);
        return result;
    }

    /**
     * 取消订阅
     */
    @ApiOperation(notes = "取消订阅", value = "取消订阅")
    @PostMapping("/cancelSubscription")
    public Result<SubscriptionVO> cancelSubscription(@RequestBody @Valid Map<String,String> map) throws StripeException {
        Result<SubscriptionVO> result = new Result<>();
        Stripe.apiKey = apikey;
        String id =  map.get("customerId");
        if (StringUtils.isEmpty(id)) {
            return result.error500("参数错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customer", map.get("customerId"));
        SubscriptionCollection subscriptions = Subscription.list(params);


        String subId = subscriptions.getData().get(0).getId();
        Subscription subscription = Subscription.retrieve(subId);
        Subscription deletedSubscription = subscription.cancel();

        SysUserOrder sysUserOrder = sysUserOrderService.getSysUserOrderByUserId(map.get("customerId"));
        if(!ObjectUtils.isEmpty(sysUserOrder)) {
            sysUserOrderService.deleteById(sysUserOrder.getId());
        }

        SubscriptionVO subscriptionVO = new SubscriptionVO();
        BeanUtils.copyProperties(deletedSubscription, subscriptionVO);

        result.setCode(200);
        result.setResult(subscriptionVO);
        result.setSuccess(true);
        return result;
    }

}
