# 法学教育平台相关说明

* 工程开发导入
1. 当前文件夹中的lawedu目录是工程目录，可直接导入到eclipse或IDEA中
2. 要求的环境是：jdk8，Tomcat8.0系列，MySQL 5.6以上
3. 当前文件夹中的lawedu.sql文件是数据库文件，可直接导入到MySQL中，可用可视化工具导入也可以用命令导入。
4. 数据库配置文件在`lawedu\src\main\resources\conf`路径下的`application.properties`文件，打开之后可以编辑配置，配置当前MySQL的用户名和密码。
5. 配置完成之后可以开始调试运行。

* 工程部署导入
1. 在lawedu目录下的target目录中有lawedu.war文件，将这个文件复制到Tomcat目录下的webapps目录下面，开启Tomcat或重启Tomcat，即可解压war包，得到一个lawedu的目录。
2. 要求的环境是：jdk8，Tomcat8.0系列，MySQL 5.6以上
3. 当前文件夹中的lawedu.sql文件是数据库文件，可直接导入到MySQL中，可用可视化工具导入也可以用命令导入。
4. 配置数据库账号和密码：在Tomcat目录下面的webapps目录中的`webapps\lawedu\WEB-INF\classes\conf`下面有一个`application.properties`文件，编辑这个文件，即可设置当前环境的数据库的账号和密码，配置完毕之后即可在浏览器中访问。访问地址是`localhost:8080/lawedu`。后台管理系统的访问地址是`localhost:8080/lawedu/manager/index.jsp`