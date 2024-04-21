package org.nott.model;

import lombok.Data;

import java.util.List;

@Data
public class ModuleInfo {

    private String artifactId;

    private String version;

    private List<ModuleInfo> dependModuleInfo;
}
