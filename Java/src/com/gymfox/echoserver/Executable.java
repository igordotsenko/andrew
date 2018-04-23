package com.gymfox.echoserver;

import java.io.IOException;

public interface Executable {
    void start() throws EchoServer.ServerIsAlreadyRunningException, IOException;
}
