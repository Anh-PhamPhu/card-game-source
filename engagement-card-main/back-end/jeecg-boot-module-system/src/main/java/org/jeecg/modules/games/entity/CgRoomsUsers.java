package org.jeecg.modules.games.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CgRoomsUsers {

    private String id;

    private String roomId;

    private String userId;

    private int sequence;
}
