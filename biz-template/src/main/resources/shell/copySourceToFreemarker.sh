#!/bin/bash

:<<EOF
shell字符串操作
https://www.cnblogs.com/chengmo/archive/2010/10/02/1841355.html
EOF

#shellcheck disable=SC2034
copySourceToFreemarker(){

  projectSlashPath=$1
  moduleName=$2
  projectDotTmpPath=${projectSlashPath//\//\.}
  projectDotPath=${projectDotTmpPath:1:${#projectDotTmpPath}}
  currentPath=$(pwd)
  basePath="${currentPath%/*/*}"

  sourcePath=${basePath}/java${projectSlashPath}/${moduleName}
  freemarkerTargetPath=${basePath}/resources/${moduleName}

  mkdir "$freemarkerTargetPath"

  echo "清空模板文件路径下文件"
  rm -fv "${freemarkerTargetPath}"/*

  echo "准备执行搬迁java原文件到模板文件中"
  echo "-------------------------------------"
  echo "当前存在的原文件"
fileNamePath=$(ls "${sourcePath}")

  for f in $fileNamePath; do
    echo "复制并修改模板文件 ${f}"
    cp "${sourcePath}/${f}" "$freemarkerTargetPath/$f.ftl"
    sed -i "s/${projectDotPath}/\${base}/g" "$freemarkerTargetPath/$f.ftl"
  done
}

copySourceToFreemarker /ks/fintech/user base
copySourceToFreemarker /ks/fintech/user config
