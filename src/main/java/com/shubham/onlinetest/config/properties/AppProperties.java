package com.shubham.onlinetest.config.properties;

import com.shubham.onlinetest.utils.PathUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final PathUtils pathUtils;

    private String homeDir = "";

    public AppProperties(PathUtils pathUtils) {
        this.pathUtils = pathUtils;
    }

    public String getHomeDir() {

        if (StringUtils.hasLength(homeDir)) {
            homeDir = pathUtils.getCurrentJarPath();
        }

        return homeDir;
    }
}
