package club.dlblog.netty.socketio.auth;

import club.dlblog.netty.socketio.handler.NettySocketHandler;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 默认认证服务实现
 * @author machenike
 */
public class DefaultSocketAuthServiceImpl implements SocketAuthService {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(DefaultSocketAuthServiceImpl.class);

    private final static String QUERY_CLIENT_ID = "clientId";

    static Map<String, SocketIOClient> socketMap;

    static{
        socketMap =  NettySocketHandler.clientMap;
    }

    @Override
    public boolean auth(HandshakeData handshakeData) {
        String clientId = handshakeData.getSingleUrlParam(QUERY_CLIENT_ID);
        if(clientId!=null){
            //若客户端存在
            if(socketMap.get(clientId)!=null){
                logger.debug("current socket clientId - "+clientId+" is repeated");
                //认证失败
                return false;
            }
            logger.debug("socket client auth success [clientId="+clientId+"]");
            return true;
        }
        logger.debug("socket client auth failed [clientId="+clientId+"]");
        return false;
    }
}
