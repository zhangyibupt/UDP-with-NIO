package server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

import core.Worker;

public class ServerWorker extends Worker<RequestContext, ServerHandler, ServerContext, Server> {

    protected ServerWorker(final Server server, final DatagramChannel channel) throws IOException {
        super(server, channel);
    }

    protected SocketAddress receiveData(final DatagramChannel channel) throws IOException {
        SocketAddress clientAddr = null;
        do {
            clientAddr = channel.receive(this.receiveBuff);
        }
        while (clientAddr == null);
        this.context.clientChannel.put(clientAddr, channel);
        return clientAddr;
    }

    protected RequestContext createHandlerContext(final Server server, final DatagramChannel channel, final SocketAddress addr) {
        return new RequestContext(server, channel, addr, this.context);
    }

}
