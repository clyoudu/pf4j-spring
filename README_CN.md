# pf4j-spring
spring-boot结合[PF4J](https://github.com/pf4j)实现插件定义、开发、打包、上传、加载、卸载、启动、停止、启用、停用、使用等功能

## Modules
- [pf4j-spring-core](pf4j-spring-core): 和[pf4j-spring](https://github.com/pf4j/pf4j-spring)代码一样，修改了部分实现.
- [pf4j-manager-spring-boot-starter](pf4j-manager-spring-boot-starter): Spring boot stater
- [demo](demo): 基于spring-boot的完整示例
    - [manager](demo/manager): 主应用(spring boot)
    - [sdk](demo/sdk): 扩展点定义，主应用和插件都需要依赖sdk
    - [plugins](demo/plugins): 3个示例插件(实现4个扩展功能)

## Build & Run
```shell
git clone https://github.com/clyoudu/pf4j-spring.git
```
编辑pf4j-spring/demo/manager/src/main/resources/application.yml, 重新指定`spring.pf4j.path`，该路径为插件保存位置
```shell
cd pf4j-spring
mvn clean package
cd demo/manager/target
java -jar manager-1.0.0-SNAPSHOT.jar
```
浏览器访问`http://localhost:8080`
![](snapshot/index.png)

Plugin Management:
1. 上传插件，插件位置：demo/plugins/\*/target/\*.jar
2. 点击'Reload'按钮, 插件列表会自动刷新
3. Load/Unload/Start/Stop/Enable/Disable/Delete等操作会根据不同的插件状态变化

Business Feature:
1. 插件状态变化，扩展功能列表也会变化
2. 点击插件按钮，弹出的消息和插件实现预期一致

## TODO
[] 通过spring context管理和使用插件未测试

## Plugin Framework for Java
[PF4J](https://github.com/pf4j)