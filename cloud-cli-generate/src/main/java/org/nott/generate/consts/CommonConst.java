package org.nott.generate.consts;

import java.io.File;

/**
 * @author Nott
 * @date 2024-4-22
 */
public class CommonConst {

    public static final String JAVA_PATH = "/src/main/java";
    public static final String MAIN_PATH = "/src/main";
    public static final String UTF8 = "UTF-8";
    public static final String POM_FTL = "pom.ftl";
    public static final String APPLICATION_FTL = "application.ftl";
    public static final String RESOURCES = "resources";

    public interface MODULE {
        String API = "api";
        String SERVICE = "service";
        String GATEWAY = "gateway";
        String SECURITY = "security";
    }

    public interface FILE_SUFFIX {
        String JAVA = ".java";
        String XML = ".xml";
        String YAML = ".yaml";
        String YML = ".yml";
        String FTL = ".ftl";
    }
}
