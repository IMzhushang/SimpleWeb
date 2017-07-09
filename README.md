# SimpleWeb
一个简单的web框架


### 2017.9.7 
修正了若干的bug

- 解决了 com.simpleweb.framework.DispatchServlet cannot be cast to javax.servlet.Servlet
 解决方法在测试的工程和simpleweb的工程的pom文件的java.servlet.api 添加 <scope>provided</scope>
 

-  解决 getHandler 为null 的问题
  解决方法 ： 因为Url 对 handler的Mapping 是通过getHandle(Request) 来完成的，在Map中以可变对象为Key ,需要重写对象的hascode和equals方法
  
