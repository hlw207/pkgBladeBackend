package org.example.domain.Package.service;


import org.example.types.ProcessResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.example.domain.Utils.CodeExecutor.runPythonAsync;

@Service
public class DependencyServiceImpl implements IDependencyService{
    @Override
    public String getPackageAllDependency(String packageName) {
        try {
            List<String> params = new ArrayList<>();
            params.add(packageName);
            // 在win下是python，服务器上是python3
            // TODO: win上不同的改成注入
            return runPythonAsync("python3", "./docs/script/get_depend_tree.py", params).get().getOutput();
        } catch (Exception e) {
            return null;
        }

    }
}
