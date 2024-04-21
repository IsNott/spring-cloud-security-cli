package org.nott.service.module;

import org.nott.model.ApplicationInfo;
import org.nott.model.ModuleInfo;
import org.nott.model.ProjectInfo;
import org.nott.service.BaseGenerateService;
import org.nott.service.CommonModuleGenerate;
import org.nott.service.CommonProjectGenerate;

public class ApiModuleGenerate extends BaseGenerateService implements CommonModuleGenerate {

    public static final String MODUEL_SURFIX = "api";

    public void doGeneration(ProjectInfo projectInfo, String projectsRoot, String lastPackageName, StringBuffer applicationJavaName,ModuleInfo moduleInfo) throws Exception {

        this.generatePom(projectInfo,moduleInfo,projectsRoot);

        this.generateDir(true,moduleInfo,projectsRoot);
    }



    @Override
    public void generatePom(ProjectInfo parentInfo, ModuleInfo info, String rootPath) {

    }

    @Override
    public void generateDir(Boolean needResources, ModuleInfo info, String rootPath) {

    }
}
