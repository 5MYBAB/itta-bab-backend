package com.fivemybab.ittabab.store.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StoreFavoriteInfoDto {

    private Long favoriteId;
    private Long userId;
    private Long storeId;
    private String storeName;

}
