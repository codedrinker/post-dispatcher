package com.github.codedrinker.util;

import com.github.codedrinker.config.Configuration;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by codedrinker on 02/08/2017.
 */
public class Loader {
    private static final String DEFAULT_CONFIGURATION_FILE = "post-dispatcher.properties";
    private static Lock lock = new ReentrantLock(true);

    public static Configuration load() {
        lock.lock();
        Configuration configuration = new Configuration();

        ClassLoader classLoader = Loader.class.getClassLoader();
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            String property = System.getProperty("custom-config");
            if (StringUtils.isNotBlank(property)) {
                inputStream = classLoader.getResourceAsStream(property);
            } else {
                inputStream = classLoader.getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
            }
            props.load(inputStream);
            BeanUtils.copyProperties(configuration, props);
            inputStream.close();
        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return configuration;
        } finally {
            lock.unlock();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (InterruptedIOException ignore) {
                    Thread.currentThread().interrupt();
                } catch (Throwable ignore) {
                }

            }
        }
        return configuration;
    }
}
