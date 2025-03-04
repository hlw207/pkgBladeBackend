import sys


import subprocess
import re
from collections import deque


def get_package_dependencies(package):
    """获取指定软件包的所有依赖，使用层序遍历（BFS），记录层级和父子关系。"""
    visited = {}
    parent_child_relations = []
    queue = deque([(package, 0)])  # (包名, 层级)
    
    while queue:
        pkg, level = queue.popleft()
        if pkg in visited:
            continue
        visited[pkg] = level
        
        try:
            output = subprocess.check_output(["apt", "show", pkg], stderr=subprocess.DEVNULL, text=True)
        except subprocess.CalledProcessError:
            continue
        
        for line in output.split("\n"):
            if line.startswith("Depends:"):
                deps = line.split("Depends:")[1].strip()
                dependencies = [re.split(r'\s*[|,]\s*', dep)[0] for dep in deps.split(',')]
                dependencies = [re.sub(r'\s*\(.*?\)', '', dep).strip() for dep in dependencies]
                
                for dep in dependencies:
                    if dep and dep not in visited:
                        queue.append((dep, level + 1))
                        parent_child_relations.append(((pkg, level), (dep, level + 1)))
                        
    return visited, parent_child_relations



if __name__ == "__main__":
    # 检查输入参数，这个后续再完善设计
    if len(sys.argv) < 0:
        print("Usage: python extract_symbol.py <symbol> <directory>")
        sys.exit(1)

    # 初始情况下只有一个依赖
    package_name = sys.argv[1]
    dependencies, relations = get_package_dependencies(package_name)
    
    print(dependencies)
    
    print(relations)
