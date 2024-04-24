package org.nott.generate.service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.nott.generate.model.ModuleFtlModel;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BaseGenerateService {

    private final static Logger logger = LoggerFactory.getLogger(BaseGenerateService.class);

    private static String ENCODING = "UTF-8";

    public static final String MODULE_NAME = "cloud-cli-generate";

    private static Configuration cfg;


    static {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_31);
            File file = new File(System.getProperty("user.dir") + File.separator + MODULE_NAME + "/src/main/resources/template");
            cfg.setDirectoryForTemplateLoading(file);
            cfg.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new Error("Failed to load FreeMaker template");
        }
    }

    protected Template getTemplate(String ftl) throws IOException {
        return cfg.getTemplate(ftl, ENCODING);
    }

    protected void writeFile(File file, String ftl, Object dataModel) throws IOException, TemplateException {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
        try {
            getTemplate(ftl).process(dataModel, outputStreamWriter);
        } finally {
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
    }

    protected void commonGeneratePom4Module(String moduleSuffix,ModuleFtlModel moduleFtlModel, String basePath, String backDirPath) throws Exception {
        ProjectInfo parent = moduleFtlModel.getParent();
        ModuleInfo current = moduleFtlModel.getCurrent();
        File file = new File(basePath + parent.getArtifactId() + File.separator + current.getArtifactId() + File.separator + "pom.xml");

        this.writeFile(file, moduleSuffix + "/pom.ftl", moduleFtlModel);
        logger.info("Module {} ,Writer project pom.xml {}", moduleSuffix, file.getPath());

    }

}
