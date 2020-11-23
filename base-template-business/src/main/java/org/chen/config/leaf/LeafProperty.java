package org.chen.config.leaf;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * leaf配置文件
 *  
 * @author YuChen
 * @date 2020/8/20 13:51
 **/

@ConfigurationProperties(prefix = "leaf", ignoreUnknownFields = true)
@Data
@ToString
@Component
public class LeafProperty {
    private String name;

    private LeafSegmentProperty segment;

    private LeafSnowflakeProperty snowflake;

    public static class LeafSegmentProperty{
        private boolean enable;
        private String jdbcUrl;
        private String jdbcUsername;
        private String jdbcPassword;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public void setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        public String getJdbcUsername() {
            return jdbcUsername;
        }

        public void setJdbcUsername(String jdbcUsername) {
            this.jdbcUsername = jdbcUsername;
        }

        public String getJdbcPassword() {
            return jdbcPassword;
        }

        public void setJdbcPassword(String jdbcPassword) {
            this.jdbcPassword = jdbcPassword;
        }
    }

    public static class LeafSnowflakeProperty{
        private Boolean enable;
        private String zkAddress;
        private Integer port;
        private Long twepoch;

        public Long getTwepoch() {
            return twepoch;
        }

        public void setTwepoch(Long twepoch) {
            this.twepoch = twepoch;
        }

        public Boolean isEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getZkAddress() {
            return zkAddress;
        }

        public void setZkAddress(String zkAddress) {
            this.zkAddress = zkAddress;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer zkPort) {
            this.port = zkPort;
        }
    }
}
