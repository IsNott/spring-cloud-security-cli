package org.nott;

import jakarta.annotation.Resource;
import org.nott.model.ModuleInfo;
import org.nott.model.ProjectInfo;
import org.nott.service.module.ProjectGenerate;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class CodeGenerator {

    @Resource
    private ProjectGenerate projectGenerate;

    public void generator(ProjectInfo projectInfo, List<ModuleInfo> moduleInfos) throws Exception{

        URL resource = this.getClass().getResource("/");
        
        String projectsRoot = resource.getFile() + "/projects/";

        projectInfo.setModuleInfos(moduleInfos);

        projectGenerate.doGeneration(projectInfo,projectsRoot);


    }
}
