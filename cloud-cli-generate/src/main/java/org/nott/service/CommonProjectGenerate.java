package org.nott.service;

import org.nott.model.ApplicationInfo;
import org.nott.model.ProjectInfo;

public interface CommonProjectGenerate {

    void generatePom(String basePath, ProjectInfo info) throws Exception;



}
