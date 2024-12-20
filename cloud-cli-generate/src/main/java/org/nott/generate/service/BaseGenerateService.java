package org.nott.generate.service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.nott.generate.consts.CommonConst;
import org.nott.generate.enums.TypeEnum;
import org.nott.generate.model.ModuleFtlModel;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BaseGenerateService {

    private final static Logger logger = LoggerFactory.getLogger(BaseGenerateService.class);

    public static final String MODULE_NAME = "cloud-cli-generate";

    private static Configuration cfg;

    private final ResourceLoader resourceLoader;

    public BaseGenerateService(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    static {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_31);
            File file = new File(System.getProperty("user.dir") + File.separator + MODULE_NAME + "/src/main/resources/template");
            cfg.setDirectoryForTemplateLoading(file);
            cfg.setDefaultEncoding(CommonConst.UTF8);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new Error("Failed to load FreeMaker template");
        }
    }

    protected Template getTemplate(String ftl) throws IOException {
        return cfg.getTemplate(ftl, CommonConst.UTF8);
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
        File file = new File(basePath + parent.getApplicationName() + File.separator + current.getArtifactId() + File.separator + "pom.xml");

        this.writeFile(file, moduleSuffix + "/pom.ftl", moduleFtlModel);
        logger.info("Module {} ,Writer project pom.xml {}", moduleSuffix, file.getPath());

    }

    protected void generateFileByLoopTmpDir(String moduleDirName, ModuleFtlModel moduleFtlModel,String basePath, String backDirPath) throws Exception {
        ProjectInfo parent = moduleFtlModel.getParent();

        if(StringUtils.isEmpty(moduleDirName)){
            throw new RuntimeException("Generate module by loop not allow pass null name");
        }
        Resource resource = resourceLoader.getResource("classpath:template/" + moduleDirName);
        File file = resource.getFile();
        File[] files = file.listFiles();
        if(files != null){
            processFiles(files, moduleDirName, moduleFtlModel, basePath, backDirPath, parent);
        }

    }
    private void processFiles(File[] files, String moduleDirName, ModuleFtlModel moduleFtlModel, String basePath, String backDirPath, ProjectInfo parent) throws Exception{
        for (File featFileDir : files) {
            if (featFileDir.isFile() && CommonConst.POM_FTL.equals(featFileDir.getName())) {
                continue;
            }
            processFile(featFileDir, moduleDirName, moduleFtlModel, basePath, backDirPath, parent);
        }
    }

    private void processFile(File file, String moduleDirName, ModuleFtlModel moduleFtlModel, String basePath, String backDirPath, ProjectInfo parent) throws Exception{
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles != null) {
                for (File childFile : childFiles) {
                    processFile(childFile, moduleDirName, moduleFtlModel, basePath, backDirPath, parent);
                }
            }
        } else {
            generateFile(moduleDirName, moduleFtlModel, basePath, backDirPath, file, parent);
        }
    }

    private void generateFile(String moduleDirName, ModuleFtlModel moduleFtlModel, String basePath, String backDirPath, File javaFile, ProjectInfo parent) throws IOException, TemplateException {
        boolean isStandAlone = TypeEnum.STANDALONE.getVal().equals(parent.getMode());

        String javaName = "";
        ModuleInfo current = moduleFtlModel.getCurrent();
        String javaFilePath = javaFile.getPath();
        String javaFileName = javaFile.getName();

        String type = current.getType();

        if((CommonConst.MODULE.API.equals(type)) && isStandAlone && "bootstrap.yml.ftl".equals(javaFileName)){
            logger.debug("Api/Gateway module skip generate bootstrap.yml");
            return;
        }

        if((CommonConst.MODULE.GATEWAY.equals(type)) && isStandAlone){
            logger.debug("Gateway module skip generate file while standalone");
            return;
        }

        boolean inResources = javaFilePath.contains(CommonConst.RESOURCES);
        boolean isJavaApplicationFile = CommonConst.APPLICATION_FTL.equals(javaFileName);

        if(!javaFileName.contains(CommonConst.FILE_SUFFIX.YAML) && !javaFileName.contains(CommonConst.FILE_SUFFIX.XML)
                && !javaFileName.contains(CommonConst.FILE_SUFFIX.YML)){
            javaName = javaFileName.replaceAll(CommonConst.FILE_SUFFIX.FTL, CommonConst.FILE_SUFFIX.JAVA);
        }else {
            javaName = javaFileName.replaceAll(CommonConst.FILE_SUFFIX.FTL, "");
        }
        String parent1 = javaFile.getParent();

        String lastPathName = parent1.substring(parent1.indexOf(moduleDirName));

        if(CommonConst.MODULE.SECURITY.equals(moduleDirName)){
            lastPathName = lastPathName.substring(lastPathName.lastIndexOf(moduleDirName));
        }

        File generateFile = null;

        if(inResources){
            generateFile = new File(basePath + parent.getApplicationName() + File.separator + current.getArtifactId() + File.separator + CommonConst.MAIN_PATH + File.separator + "resources" + File.separator + javaName);
        } else if(isJavaApplicationFile){
            generateFile = new File(basePath + parent.getApplicationName() + File.separator + current.getArtifactId() + File.separator + CommonConst.JAVA_PATH + File.separator + moduleFtlModel.getPackageName().replaceAll("\\.","/") + File.separator + javaName);
        }
        else {
            generateFile = new File(basePath + parent.getApplicationName() + File.separator + current.getArtifactId() + File.separator + CommonConst.JAVA_PATH + File.separator + backDirPath + File.separator
                    + lastPathName + File.separator + javaName);
        }

        writeFile(generateFile,  lastPathName + File.separator + javaFile.getName(), moduleFtlModel);
        logger.info("Module {} ,Writer file {}", moduleDirName, generateFile.getPath());
    }

}
