package club.dlblog.netty.socketio.handler;

import club.dlblog.netty.socketio.util.NettySocketUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * socket处理拦截器
 * @author machenike
 */
public class NettySocketHandler implements CommandLineRunner {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(NettySocketHandler.class);

    /**
     * 客户端保存用Map
     */
    public static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 连接数
     */
     public static AtomicInteger onlineCount = new AtomicInteger(0);

    private final static String QUERY_CLIENT_ID = "clientId";

    /**
     * 客户端连上socket服务器时执行此事件
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
        logger.debug("onConnect: [clientId="+clientId+"]");
        if(clientId!=null) {
            clientMap.put(clientId, client);
            onlineCount.addAndGet(1);
            logger.debug("connect success: [clientId="+clientId+",onlineCount="+onlineCount.get()+"]");
        }
    }


    /**
     * 客户端断开socket服务器时执行此事件
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
        if (clientId != null) {
            clientMap.remove(clientId);
            client.disconnect();
            onlineCount.addAndGet(-1);
            logger.debug("disconnect success: [clientId="+clientId+",onlineCount="+onlineCount.get()+"]");
        }
    }

    /**
     *
     * @param client
     */
    @OnEvent( value = "message")
    public void onMessage(SocketIOClient client, AckRequest request, Object data) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
        logger.debug("onMessage: [clientId="+clientId+",data="+data+"]");
        //request.sendAckData("message is revived");
        NettySocketUtil.sendNotice("test message");
        client.sendEvent("ack",1);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.debug("socketHandler start-------------------------------");
    }
}
