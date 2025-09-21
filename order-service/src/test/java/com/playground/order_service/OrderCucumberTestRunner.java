package com.playground.order_service;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty,html:target/html-reports,json:target/json-reports/report.json")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.playground.order_service.steps")
//se rassurer que chaque steps defini dans le fichier feature on une implementation dans le steps definition
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
public class OrderCucumberTestRunner {


}
