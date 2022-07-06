package org.jeecg.modules.games.query;

import lombok.Data;

import java.util.List;

@Data
public class UserCardNumbersQuery {

    private String userId;

    private List<Integer> userCards;
}
