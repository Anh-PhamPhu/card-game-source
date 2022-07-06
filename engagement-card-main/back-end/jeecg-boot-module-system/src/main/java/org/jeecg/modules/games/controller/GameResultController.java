package org.jeecg.modules.games.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.games.entity.CgResultAnalysis;
import org.jeecg.modules.games.service.CgResultAnalysisService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 神州
 * @date 2022年05月19日 14:37
 */
@RestController
@Api(tags = "游戏结果")
@RequestMapping("/games/result")
public class GameResultController {

    @Autowired
    private CgResultAnalysisService cgResultAnalysisService;

    @ApiOperation(value = "获得雷达图数据[结果：resultList（雷达图list）,detailContent(详细文)]",notes = "获得雷达图数据")
    @GetMapping("/getCharacter")
    public  Result<JSONObject> getResultImgResult(@ApiParam(value = "cardNumber[所剩牌id的集合（逗号隔开传参）]") @RequestParam(value = "cardNumber",required = true) @NotBlank(message="cardNumber不能为空")  String cardNumber){
        Result<JSONObject> result = new Result<>();

        List<String> cardNumbers = Arrays.asList(cardNumber.split(","));
        List<Integer> cardNumberList = cardNumbers.stream().map(Integer::parseInt).collect(Collectors.toList());
        JSONObject cgResultAnalysis = cgResultAnalysisService.getNumber(cardNumberList);


        result.setResult(cgResultAnalysis);
        return result;
    }

}
