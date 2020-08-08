package club.dlblog.netty.socketio.auth;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;

/**
 * 认证监听器
 * @author machenike
 */
public class SocketAuthListener implements AuthorizationListener {

    private SocketAuthService authService;

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {

        return authService.auth(handshakeData);
    }

    public SocketAuthListener(SocketAuthService authService) {
        this.authService = authService;
    }


    public SocketAuthListener() {
        this.authService = new DefaultSocketAuthServiceImpl();
    }
}
