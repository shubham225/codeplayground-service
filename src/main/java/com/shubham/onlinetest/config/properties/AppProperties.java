package com.shubham.onlinetest.config.properties;

import com.shubham.onlinetest.utils.PathUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String homeDir = "";
}
