package org.nott.generate.service.module;

import org.apache.commons.lang3.StringUtils;
import org.nott.generate.consts.CommonConst;
import org.nott.generate.model.ModuleFtlModel;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.BaseGenerateService;
import org.nott.generate.service.CommonModuleGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ApiModuleGenerator extends BaseGenerateService implements CommonModuleGenerate {

    Logger logger = LoggerFactory.getLogger(ApiModuleGenerator.class);


    public static final String MODULE_SUFFIX = "api";

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
        this.generateApplication(model, projectsRoot, backDirPath);

    }


    @Override
    public void generatePom(ModuleFtlModel moduleFtlModel, String basePath, String backDirPath) throws Exception {
       super.commonGeneratePom4Module(MODULE_SUFFIX,moduleFtlModel,basePath,backDirPath);
    }

    @Override
    public void generateDir(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {
        ProjectInfo parent = model.getParent();
        ModuleInfo current = model.getCurrent();
        File logBack = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.MAIN_PATH + File.separator + "resources/logback/" + "logback.xml");
        File applicationYml = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.MAIN_PATH + File.separator + "resources/" + "application.yml");
        File bootstrapYml = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.MAIN_PATH + File.separator + "resources/" + "bootstrap.yml");
        super.writeFile(logBack, "logback.ftl", model);
        super.writeFile(applicationYml, MODULE_SUFFIX + "/application.yml.ftl", model);
        super.writeFile(bootstrapYml, MODULE_SUFFIX + "/bootstrap.yml.ftl", model);

        logger.info("Module {} ,Writer project resources's file {}", MODULE_SUFFIX, logBack.getPath());
        logger.info("Module {} ,Writer project resources's file {}", MODULE_SUFFIX, applicationYml.getPath());
        logger.info("Module {} ,Writer project resources's file {}", MODULE_SUFFIX, bootstrapYml.getPath());
    }

    public void generateApplication(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {
        ModuleInfo current = model.getCurrent();
        ProjectInfo parent = model.getParent();
        File file = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.JAVA_PATH + File.separator + backDirPath + File.separator + "Application.java");
        super.writeFile(file, "api/application.ftl", model);
        logger.info("Module {} ,Writer project application.java {}", MODULE_SUFFIX, file.getPath());

    }
}
