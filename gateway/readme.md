
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
