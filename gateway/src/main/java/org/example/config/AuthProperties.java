package org.example.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证用到的属性
 *
 * <p></p>
 *
 * @author WenLong
 * @version 1.0.0
 * @date 2026/7/14 09:28
 * @see AuthProperties
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {
    // 白名单
    private List<String> whitelist = new ArrayList<>();
    private List<String> tokenKeys = new ArrayList<>();
}
