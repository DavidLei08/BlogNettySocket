package club.dlblog.netty.socketio.util;

import club.dlblog.netty.socketio.handler.NettySocketHandler;
import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.Set;

/**
 * socket发送消息用工具类
 * @author machenike
 */
public class NettySocketUtil {

    /**
     * 保存client资源Map
     */
    static Map<String, SocketIOClient> socketMap;

    static{
        //client资源Map赋值
        socketMap =  NettySocketHandler.clientMap;
    }

    /**
     * 发送消息 指定客户端 指定event
     * @param clientId
     * @param event
     * @param message
     */
    public static void sendMessage(String clientId,String event,Object message){
        socketMap =  NettySocketHandler.clientMap;
        socketMap.get(clientId).sendEvent(event,message);
    }

    /**
     * 发送消息 指定客户端
     * @param clientId
     * @param message
     */
    public static void sendMessage(String clientId,Object message){
        socketMap =  NettySocketHandler.clientMap;
        socketMap.get(clientId).sendEvent("message",message);
    }

    /**
     * 发送消息 全部客户端
     * @param message
     */
    public static void sendNotice(Object message){
        Set<String> clientIdSet = socketMap.keySet();
        for(String clientId:clientIdSet){
            socketMap.get(clientId).sendEvent("message",message);
        }
    }

    /**
     * 发送消息 指定event 全部客户端
     * @param message
     */
    public static void sendNotice(Object message,String event){
        Set<String> clientIdSet = socketMap.keySet();
        for(String clientId:clientIdSet){
            socketMap.get(clientId).sendEvent(event,message);
        }
    }
}
