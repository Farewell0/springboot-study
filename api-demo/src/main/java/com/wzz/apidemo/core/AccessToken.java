package com.wzz.apidemo.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * AccessToken
 *
 * @author wzz
 * @date 2020/6/21
 **/
@Data
@AllArgsConstructor
public class AccessToken {
    /** token */
    private String token;

    /** 失效时间 */
    private Date expireTime;
}
