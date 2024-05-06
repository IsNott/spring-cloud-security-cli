package org.nott.generate.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectInfo {

    /**
     * Project groupId
     */
    private String groupId;

    /**
     * Project artifactId
     */
    private String artifactId;

    /**
     * Project version
     * Default 1.0.0-SNAPSHOT
     */
    private String version;

    /**
     * Project pom description
     */
    private String description;

    /**
     * Child module prefix name like: 'cloud-cli-generate'
     */
    private String childModuleDirPrefix;

    /**
     * Child package append name.
     */
    private String childLastPackage;

    /**
     * Project name in pom.
     */
    private String applicationName;

    /**
     * Project file doc author.
     * Default root
     */
    private String author;

    /**
     * Project initialize path
     * Default cloud-cli-generate/target/projects/{applicationName}/..
     */
    private String root;

    /**
     * 0-standalone 1-microservice
     */
    private String mode;

    private List<ModuleInfo> moduleInfos;
}
