package org.nott.service;

import org.nott.model.ModuleInfo;
import org.nott.model.ProjectInfo;

public interface CommonModuleGenerate {

    void generatePom(ProjectInfo parentInfo, ModuleInfo info, String rootPath);

    void generateDir(Boolean needResources, ModuleInfo info, String rootPath);
}
