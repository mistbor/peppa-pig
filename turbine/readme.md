没有turbine，hystrix dashboard只能显示一个微服务的hystrix API调用统计信息；
turbine是一个hystrix stream聚合器，它复杂收集几个微服务的hystrix调用统计信息，再最终向hystrix dasgboard提供数据。

可以启动多个consumer服务进行检测，使用jmeter进行压力测试