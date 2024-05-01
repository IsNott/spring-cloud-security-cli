package org.nott.generate.service;

import jakarta.annotation.Resource;
import org.nott.generate.config.SpringContextHolder;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.module.*;
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

    public void generator(ProjectInfo projectInfo) throws Exception {

        List<String> moduleList = Arrays.asList("common", "api", "service", "security", "bean");

        URL resource = this.getClass().getResource("/");

        String projectsRoot = resource.getFile() + "/projects/";

        String projectName = projectInfo.getName();

        File file = new File(projectsRoot + projectName);

        file.deleteOnExit();

        String author = projectInfo.getAuthor();
        if (author == null) {
            projectInfo.setAuthor("default");
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
            }
        }

    }
}
