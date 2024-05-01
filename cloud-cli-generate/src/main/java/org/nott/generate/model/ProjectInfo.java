package org.nott.generate.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectInfo {

    private String groupId;

    private String artifactId;

    private String version;

    private String name;

    private String description;

    private String childModuleDirPrefix;

    private String childLastPackage;

    private String applicationName;

    private String author;

    private List<ModuleInfo> moduleInfos;
}
