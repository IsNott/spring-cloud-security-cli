package org.nott.generate.service;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.nott.generate.config.GeneratorConfig;
import org.nott.generate.config.SpringContextHolder;
import org.nott.generate.enums.TypeEnum;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.module.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeGenerator {

    @Resource
    private ProjectGenerator projectGenerator;
    @Resource
    private ApiModuleGenerator apiModuleGenerator;

    @Resource
    private BeanModuleGenerator beanModuleGenerator;

    @Resource
    private CommonModuleGenerator commonModuleGenerator;

    @Resource
    private SecurityModuleGenerator securityModuleGenerator;

    @Resource
    private ServiceModuleGenerator serviceModuleGenerator;

    @Resource
    private GatewayModuleGenerator gatewayModuleGenerator;

    @Resource
    private GeneratorConfig generatorConfig;

    public void generator(ProjectInfo projectInfo) throws Exception {

        if(projectInfo.getMode() == null){
            projectInfo.setMode(TypeEnum.MICROSERVICE.getVal());
        }

        List<String> moduleList = generatorConfig.getModules();

        URL resource = this.getClass().getResource("/");

        String root = projectInfo.getRoot();

        root = root.endsWith("/") ? root : root + "/";

        String projectsRoot = StringUtils.isNotEmpty(root) ? root : resource.getFile() + generatorConfig.getProjectRoot();

        String projectName = projectInfo.getApplicationName();

        File file = new File(projectsRoot + projectName);

        file.deleteOnExit();

        String author = projectInfo.getAuthor();
        if (author == null) {
            projectInfo.setAuthor(generatorConfig.getDefaultAuthor());
        }

        List<ModuleInfo> moduleInfos = new ArrayList<>();
        for (String name : moduleList) {
            ModuleInfo r = new ModuleInfo();
            r.setType(name);
            r.setArtifactId(projectInfo.getChildModuleDirPrefix() + "-" + name);
            moduleInfos.add(r);
        }

        projectInfo.setModuleInfos(moduleInfos);

        projectGenerator.doGeneration(projectInfo, projectsRoot);

        for (ModuleInfo r : moduleInfos) {
            switch (r.getType()) {
                case "api":
                    apiModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
                case "bean":
                    beanModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
                case "common":
                    commonModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
                case "security":
                    securityModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
                case "service":
                    serviceModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
                case "gateway":
                    gatewayModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
                    break;
            }
        }

    }
}
