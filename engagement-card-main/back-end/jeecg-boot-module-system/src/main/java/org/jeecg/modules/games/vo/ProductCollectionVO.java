package org.jeecg.modules.games.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 神州
 * @date 2022年04月25日 17:26
 */
@Data
public class ProductCollectionVO {

    String object;
    List<ProductVO> data;
    Boolean hasMore;
    String url;

}
