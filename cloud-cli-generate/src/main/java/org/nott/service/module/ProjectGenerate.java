package org.nott.service.module;


import org.nott.model.ApplicationInfo;
import org.nott.model.ProjectInfo;
import org.nott.service.BaseGenerateService;
import org.nott.service.CommonProjectGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ProjectGenerate extends BaseGenerateService implements CommonProjectGenerate {

    Logger logger = LoggerFactory.getLogger(ProjectGenerate.class);

    public void doGeneration(ProjectInfo projectInfo, String rootPath) throws Exception {
        this.generatePom(rootPath, projectInfo);
    }

    @Override
    public void generatePom(String basePath, ProjectInfo info) throws Exception {
        File file = new File(basePath + info.getArtifactId() + File.separator + "pom.xml");

        super.writeFile(file, "project/pom.ftl", info);

        logger.info("Writer project pom.xml {}", file.getPath());
    }


}
