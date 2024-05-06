package ${parent.groupId}.${parent.childLastPackage}.security.adapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResourceServerAdapter implements AuthConfigAdapter {

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/*");
    }

    @Override
    public List<String> excludePathPatterns() {
        return Arrays.asList("/your-api/test");
    }
}
