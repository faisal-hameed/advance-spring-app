package pk.habsoft.demo.estore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties("mma")
@Getter
public class AppConfigurations {

	private String appName;
}
