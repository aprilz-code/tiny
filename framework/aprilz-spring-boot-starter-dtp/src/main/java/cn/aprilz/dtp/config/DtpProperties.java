package cn.aprilz.dtp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties("dtp")
public class DtpProperties {

    private List<DtpExecutorProperties> executors;

    public static class DtpExecutorProperties {
        private String name;
        private Integer corePoolSize = 10;
        private Integer maximumPoolSize = 100;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(Integer corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public Integer getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(Integer maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }
    }

    public List<DtpExecutorProperties> getExecutors() {
        return executors;
    }

    public void setExecutors(List<DtpExecutorProperties> executors) {
        this.executors = executors;
    }
}
