package org.jeecg.modules.games.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.games.dto.AddResultParam;
import org.jeecg.modules.games.entity.CgResult;
import org.jeecg.modules.games.vo.CgResultVO;

import java.util.List;

public interface CgResultService extends IService<CgResult> {

    Boolean addList(List<AddResultParam> list);

    List<CgResultVO> getDetail(String roomId,String customId);
}
