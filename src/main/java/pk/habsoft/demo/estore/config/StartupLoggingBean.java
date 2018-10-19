package pk.habsoft.demo.estore.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pk.habsoft.demo.estore.endpoint.Endpoints;
import pk.habsoft.demo.estore.security.AccountAuthenticationProvider;

@Component
public class StartupLoggingBean implements InitializingBean {

    private static final Logger logger = Logger.getLogger(AccountAuthenticationProvider.class);

    private static final String ENV_KEY = "envTarget";
    private static final String ACTIVE_SPRING_PROFILE = "spring.profiles.active";
    private static final String PERSISTENCE_HOST = "jdbc.url";

    @Autowired
    private Environment env;

    public StartupLoggingBean() {
        super();
    }

    //

    @Override
    public void afterPropertiesSet() {
        logger.debug("============================================================================");
        try {
            logEnvTarget(env);
            logActiveSpringProfile(env);
            logPersistenceData(env);
        } catch (final Exception ex) {
            logger.warn("There was a problem logging data on startup", ex);
        }

        logger.info("============================================================================");

        logger.warn(String.format("Test URL ***************** : %s", Endpoints.CONTEXT_PATH + Endpoints.Test.BASE_URL));
    }

    // UTIL

    private void logEnvTarget(final Environment environment) {
        final String envTarget = getValueOfProperty(environment, ENV_KEY, "local", Lists.newArrayList("local", "dev"));
        logger.info(ENV_KEY + ":" + envTarget);
    }

    private void logActiveSpringProfile(final Environment environment) {
        final String activeSpringProfile = getValueOfProperty(environment, ACTIVE_SPRING_PROFILE, "none", null);
        logger.info(ACTIVE_SPRING_PROFILE + ":" + activeSpringProfile);
    }

    private void logPersistenceData(final Environment environment) {
        final String persistenceHost = getValueOfProperty(environment, PERSISTENCE_HOST, "not-found", null);
        logger.info(PERSISTENCE_HOST + ":" + persistenceHost);
    }

    //

    private final String getValueOfProperty(final Environment environment, final String propertyKey,
            final String propertyDefaultValue, final List<String> acceptablePropertyValues) {
        String propValue = environment.getProperty(propertyKey);
        if (propValue == null) {
            propValue = propertyDefaultValue;
            logger.info(String.format("The %s doesn't have an explicit value; default value is = %s", propertyKey,
                    propertyDefaultValue));
        }

        if (acceptablePropertyValues != null) {
            if (!acceptablePropertyValues.contains(propValue)) {
                logger.warn(String.format("The property = %s has an invalid value = %s", propertyKey, propValue));
            }
        }

        if (propValue == null) {
            logger.warn(String.format("The property = %s is null", propertyKey));
        }

        return propValue;
    }

}
