# 从零开始学习netty

1. 从netty.io上的教程开始；
	* 一个服务器，收到客户端（telnet）发来的请求后可以打印出请求内容；
	* 一个服务器，收到客户端（telnet）发来的请求后原样发回；
	* 服务器和客户端，服务器会向客户端返回一个时间戳并关闭连接；
	* 加入编码解码器来解决粘包拆包问题；
	* 编解码器可以把请求解析成对象；
2. 开始构建一个简单的聊天室应用；
	* 聊天人数上限固定的聊天室；