package org.nott.generate.model;

import lombok.Data;

/**
 * @author Nott
 * @date 2024-4-22
 */
@Data
public class ModuleFtlModel {

    private ProjectInfo parent;

    private ModuleInfo current;

    private String packageName;
}
