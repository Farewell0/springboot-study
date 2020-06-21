package com.wzz.apidemo.core;

import lombok.Data;

/**
 * TokenInfo
 *
 * @author wzz
 * @date 2020/6/21
 **/
@Data
public class TokenInfo {
    /** token类型: api:0 、user:1 */
    private Integer tokenType;

    /** App 信息 */
    private AppInfo appInfo;

    /** 用户其他数据 */
    private UserInfo userInfo;
}
