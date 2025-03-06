package org.example.domain.Pipeline.service.thread;


import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

/**
 * 获取一个软件包的所有依赖
 */
public class ShowAllDependencyTask implements Callable<String> {

    private final IDependencyService dependencyService;
    private final String packageName;

    public ShowAllDependencyTask(IDependencyService dependencyService, String packageName) {
        this.dependencyService = dependencyService;
        this.packageName = packageName;
    }

    @Override
    public String call() throws Exception {
        return dependencyService.getPackageAllDependency(packageName);
    }
}
