package org.nott.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectInfo {

    private String groupId;

    private String artifactId;

    private String version;

    private String name;

    private String description;

    private List<ModuleInfo> moduleInfos;
}
