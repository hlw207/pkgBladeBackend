package org.example.domain.Package.service;

import org.example.types.ProcessResult;

import java.util.concurrent.Future;

public interface IDependencyService {
    public String getPackageAllDependency(String packageName);
}
