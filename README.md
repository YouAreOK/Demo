- 使用单线程运行驱动，选择火狐浏览器

mvn clean verify -Dwebdriver.gecko.driver="C:\Program Files\selenium\geckodriver.exe"

- 使用多线程运行多驱动，根据maven的browser属性选择谷歌浏览器

mvn clean verify -Dthreads=2 -Dbrowser=chrome -Dwebdriver.gecko.driver="C:\Program Files\selenium\chromedriver.exe"

- 执行下载webdriver驱动程序

mvn clean verify -Dthreads=2

- 使用多线程运行火狐浏览器

mvn clean verify -Dthreads=2 -Dbrowser=firefox