package org.nott.generate.service;

import jakarta.annotation.Resource;
import org.nott.generate.config.SpringContextHolder;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.module.ApiModuleGenerator;
import org.nott.generate.service.module.ProjectGenerator;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class CodeGenerator {

    @Resource
    private ProjectGenerator projectGenerator;
    @Resource
    private ApiModuleGenerator apiModuleGenerator;

    public void generator(ProjectInfo projectInfo, List<ModuleInfo> moduleInfos) throws Exception{

        URL resource = this.getClass().getResource("/");
        
        String projectsRoot = resource.getFile() + "/projects/";

        projectGenerator.doGeneration(projectInfo,projectsRoot);

//        for (ModuleInfo r : moduleInfos) {
//            String name = r.getName();
//            CommonModuleGenerate bean = (CommonModuleGenerate) SpringContextHolder.getBean(name + "ModuleGenerator");
//            bean.doGeneration(projectInfo,projectsRoot,r);
//        }

        for (ModuleInfo r : moduleInfos) {
            String type = r.getType();
            switch (type) {
                case "api":
                    apiModuleGenerator.doGeneration(projectInfo, projectsRoot, r);
            }
        }

    }
}
