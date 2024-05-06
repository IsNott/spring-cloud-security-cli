package org.nott.generate.service;

import org.nott.generate.model.ProjectInfo;

public interface CommonProjectGenerate {

    void generatePom(String basePath, ProjectInfo info) throws Exception;



}
