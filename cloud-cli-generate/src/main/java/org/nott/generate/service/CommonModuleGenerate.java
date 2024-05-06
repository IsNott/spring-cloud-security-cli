package org.nott.generate.service;

import org.nott.generate.model.ModuleFtlModel;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;

public interface CommonModuleGenerate {

    void generatePom(ModuleFtlModel moduleFtlModel, String basePath,String backDirPath) throws Exception;

    void generateDir( ModuleFtlModel model,String basePath,String backDirPath) throws Exception;

    void doGeneration(ProjectInfo projectInfo, String projectsRoot, ModuleInfo moduleInfo) throws Exception;

    void generateApplication(ModuleFtlModel model,String basePath,String backDirPath) throws Exception;
}
