package org.nott.generate.service.module;

import org.apache.commons.lang3.StringUtils;
import org.nott.generate.model.ModuleFtlModel;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.BaseGenerateService;
import org.nott.generate.service.CommonModuleGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Nott
 * @date 2024-4-24
 */
@Service
public class CommonModuleGenerator extends BaseGenerateService implements CommonModuleGenerate {

    Logger logger = LoggerFactory.getLogger(BeanModuleGenerator.class);

    public static final String MODULE_SUFFIX = "common";


    @Override
    public void generatePom(ModuleFtlModel moduleFtlModel, String basePath, String backDirPath) throws Exception {
        super.commonGeneratePom4Module(MODULE_SUFFIX,moduleFtlModel,basePath,backDirPath);
    }

    @Override
    public void generateDir(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {
        // todo generateDir

    }

    @Override
    public void doGeneration(ProjectInfo projectInfo, String projectsRoot, ModuleInfo moduleInfo) throws Exception {
        String version = moduleInfo.getVersion();
        if (StringUtils.isEmpty(version)) {
            moduleInfo.setVersion(projectInfo.getVersion());
        }
        String basePackage = projectInfo.getGroupId() + "." + projectInfo.getChildLastPackage();
        String backDirPath = basePackage.replaceAll("\\.", "/");
        ModuleFtlModel model = new ModuleFtlModel();
        model.setPackageName(basePackage);
        model.setParent(projectInfo);
        model.setCurrent(moduleInfo);
        this.generatePom(model, projectsRoot, backDirPath);
        this.generateDir(model, projectsRoot, backDirPath);
    }

    @Override
    public void generateApplication(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {
        logger.info("Module {} , don't need application.java",MODULE_SUFFIX);
    }
}
