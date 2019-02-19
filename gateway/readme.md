
                用户中心
                      |
客户端 --> gateway <-- 微服务1， 微服务2， 微服务3...
                      |
                灰度规则



gateway作用：
1. 统一对外接口
2. 统一鉴权
3. 服务授权
4. 负载均衡
5. 灰度
（认证、安全、转发、负载均衡、灰度发布..）

启动consul  consumer  provider  gateway服务
consul  端口  8500
consumer  端口  8090
provider  端口 8083
gateway 端口 9999

request: http://localhost:9999/consumer/test
response: {"id":441,"name":"consumer","port":8090,"providerPOJO":{"id":290,"name":"hello","port":8083}}

request: http://localhost:8090/test
response: {"id":442,"name":"consumer","port":8090,"providerPOJO":{"id":291,"name":"hello","port":8083}}



request: http://localhost:9999/provider/test
response: {"id":289,"name":"provider","port":8083}

request: http://localhost:8083/test
response: {"id":292,"name":"provider","port":8083}

=========================================================================

gateway 总原理：
gateway client
|
gateway handler mapping
|
gateway web handler
|
filter1
filter2
filter3
...
proxy filter
|
proxied service

1. 从gateway handler mapping开始到proxy filter为止，是Spring cloud gateway的原理
2. 多个filter形成链
3. filter分两种类型
- GatewayFilter：作用于某些特定请求响应，必须配置使用条件（对哪些请求使用该filter）
- GlobalFilter：全局filter，作用于所有请求响应，无需配置使用条件
4. 一个filter可以在两个阶段做事情：
- pre：处理更改请求，典型如增加http header
- post：处理更改响应
5. filter在链中的顺序，取决于Ordered接口返回的值：
- 值越小，越靠前
- 没实现Ordered接口，则order为null，放到链的末尾，最后处理
- 同order值的filter暂时尚不清楚先后顺序
