package org.nott.generate.service.module;


import org.nott.generate.model.ProjectInfo;
import org.nott.generate.service.BaseGenerateService;
import org.nott.generate.service.CommonProjectGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ProjectGenerator extends BaseGenerateService implements CommonProjectGenerate {

    Logger logger = LoggerFactory.getLogger(ProjectGenerator.class);

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
