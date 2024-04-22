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

@Service
public class BeanModuleGenerator extends BaseGenerateService implements CommonModuleGenerate {

    Logger logger = LoggerFactory.getLogger(BeanModuleGenerator.class);

    public static final String MODULE_SUFFIX = "bean";

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
    public void generatePom(ModuleFtlModel moduleFtlModel, String basePath, String backDirPath) throws Exception {
        ProjectInfo parent = moduleFtlModel.getParent();
        ModuleInfo current = moduleFtlModel.getCurrent();
        File file = new File(basePath + parent.getArtifactId() + File.separator + current.getArtifactId() + File.separator + "pom.xml");

        super.writeFile(file, MODULE_SUFFIX + "/pom.ftl", moduleFtlModel);

        logger.info("Module {} ,Writer project pom.xml {}", MODULE_SUFFIX, file.getPath());
    }

    @Override
    public void generateDir(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {
        ProjectInfo parent = model.getParent();
        ModuleInfo current = model.getCurrent();
        String packageName = model.getPackageName();
        String dirName = packageName.replaceAll("\\.","/");
        File resourseDir = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.MAIN_PATH + File.separator + "resources/");
        File userJavaFile = new File(basePath + parent.getName() + File.separator + current.getArtifactId() + File.separator + CommonConst.JAVA_PATH + File.separator + dirName + "/bean/model/" + "Users.java");
        super.writeFile(userJavaFile, MODULE_SUFFIX + "/users.java.ftl", model);

        if(!resourseDir.exists()){
            resourseDir.mkdir();
        }
        logger.info("Module {} ,Writer project resources's dir {}", MODULE_SUFFIX, resourseDir.getPath());

    }

    @Override
    public void generateApplication(ModuleFtlModel model, String basePath, String backDirPath) throws Exception {

    }
}
