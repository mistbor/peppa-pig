1. 浏览器访问http://localhost:9000/hystrix
第一个输入框：http://localhost:8090/actuator/hystrix.stream
第二个输入框：2000
第三个输入框：consumer

点击Monitor Stream，看到监控盘，浏览器再访问http://localhost:8090/test和http://localhost:8090/test?name=io，各刷新几次，观察dashboard仪表盘的变化。