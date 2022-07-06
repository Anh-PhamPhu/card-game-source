package org.jeecg.modules.games.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.games.entity.CgResultAnalysis;

import java.util.List;

public interface CgResultAnalysisService extends IService<CgResultAnalysis> {

    JSONObject getNumber(List<Integer> param);
}
