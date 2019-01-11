## 使用liquibase插件对数据库版本进行持续更新

### 1. 添加maven 插件
```
   <plugin>
                <groupId>org.liquibase</groupId>
                <artifactId>liquibase-maven-plugin</artifactId>
                <version>3.6.2</version>
                <configuration>
                    <promptOnNonLocalDatabase>false</promptOnNonLocalDatabase>
                    <propertyFile>db/${runtime.env}/liquibase.properties</propertyFile>
                </configuration>
            <!--    <executions>
                    <execution>
                        &lt;!&ndash; 配置什么时候执行数据库变更日志中的sql脚本 &ndash;&gt;
                        <phase>process-resources</phase>
                        <goals>
                            &lt;!&ndash; 执行类型 &ndash;&gt;
                            <goal>update</goal>
                        </goals>
                    </execution>
                </executions>-->
            </plugin>
```
说明：maven plugin 可以配置执行时间，比如在 process-resources 时期进行数据库版本的检查

### 2. maven 生命周期
命令|描述|备注
-----|-----|------
validate|验证项目是否正确，以及所有为了完整构建必要的信息是否可用|
generate-sources|生成所有需要包含在编译过程中的源代码|
process-sources|处理源代码，比如过滤一些值|
generate-resources|生成所有需要包含在打包过程中资源文件|
compile|编译项目的源代码|
process-classes|后处理编译生成的文件，例如对Java类进行字节码增强(bytecode enhancement)|
generate-test-sources|生成所有包含在测试编译过程中的测试源码|
process-test-sources|处理测试源码，比如过滤一些值|
generate-test-resources|生成测试需要的资源文件|
process-test-resources|复制并处理测试资源文件至测试目标目录|
test-compile|编译测试源码至测试目标目录|
test|使用合适的单元测试框架运行测试，这些测试不需要代码被打包或发布|
prepare-package|在真正的打包之前，执行一些准备打包必要的操作，这通常会产生一个包的展开的处理过的版本|
package|将编译好的代码打包成可分发的格式，如jar,war,ear等|
pre-integration-test|执行一些在集成测试运行之前需要的动作，如建立集成测试需要的环境|
integration-test|如果有必要的话，处理包并发布至集成测试可以运行的环境|
post-integration-test|执行一些在继承测试运行之后需要的动作，如清理集成测试环境|
verify|执行所有检查，验证包是有效的，符合质量规范|
install|安装包至本地仓库，以备本地的其他项目作为依赖使用|
deploy|复制最终的包到远程仓库，共享给其他开发人员和项目（通常和一次正式的发布相关）|

### 3. liquibase 插件常用命令
命令|描述|备注
-----|------|-----
mvn liquibase:status|查看脚本状态
mvn liquibase:update|更新脚本到数据库   

说明： 首次执行liquibase命令时，数据库会生成两个表 DATABASECHANGELOG 和 DATABASECHANGELOGLOCK， 会对每次执行的脚本进行记录