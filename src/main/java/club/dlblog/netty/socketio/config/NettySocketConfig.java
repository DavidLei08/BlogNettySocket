package club.dlblog.netty.socketio.config;

import club.dlblog.netty.socketio.auth.SocketAuthListener;
import club.dlblog.netty.socketio.handler.NettySocketHandler;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * socket服务配置
 * @author machenike
 */
@Configuration
public class NettySocketConfig {


    @Value("${club.dlblog.socketio.host}")
    private String host;

    @Value("${club.dlblog.socketio.port}")
    private Integer port;

    /**
     * netty-socketio服务器
     * @return
     **/
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        //设置host
        config.setHostname(host);
        //设置端口
        config.setPort(port);
        //初始化认证监听器
        AuthorizationListener SocketAuthListener = new SocketAuthListener();
        //设置认证监听器
        config.setAuthorizationListener(SocketAuthListener);
        SocketIOServer server = new SocketIOServer(config);
        //启动socket服务
        server.start();
        return server;
    }

    /**
     *用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     *
     **/
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer());
    }

    @Bean
    public NettySocketHandler nettySocketHandler(){
        return new NettySocketHandler();
    }



}
