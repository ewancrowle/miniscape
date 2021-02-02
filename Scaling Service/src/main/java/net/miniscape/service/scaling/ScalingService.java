package net.miniscape.service.scaling;

import net.miniscape.server.ServerRequest;

public interface ScalingService {
    String requestServer(ServerRequest request);
}