- 使用单线程运行驱动，选择火狐浏览器

mvn clean verify -Dwebdriver.gecko.driver="C:\Program Files\selenium\geckodriver.exe"

- 使用多线程运行多驱动，根据maven的browser属性选择谷歌浏览器

mvn clean verify -Dthreads=2 -Dbrowser=chrome -Dwebdriver.gecko.driver="C:\Program Files\selenium\chromedriver.exe"

- 执行下载webdriver驱动程序

mvn clean verify -Dthreads=2

- 使用多线程运行火狐浏览器

mvn clean verify -Dthreads=2 -Dbrowser=firefox

-支持后台运行驱动程序

mvn clean verify -Dthreads=2

-恢复弹窗运行驱动程序
mvn clean verify -Dthreads=2 -Dheadless=false

# 构建持续集成
 ## 1、docker安装
 ## 2、使用dokcer安装jenkins
  `docker run -it --name jenkins-instance -p 8080:8080 -p 50000:50000 -v ~/jenkins:/var/jenkins_home jenkins`
  