package com.manifestors.shinrai.client.utils;

import com.manifestors.shinrai.client.Shinrai;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LoggerInstance {

    Logger logger = LogManager.getLogger(Shinrai.NAME);

}
