package org.jeecg.modules.games.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserOrder {

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String userId;

    private String proId;

    private String proName;

    private String orderId;
}
