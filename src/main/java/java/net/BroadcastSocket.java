package java.net;

import java.io.IOException;
import java.util.Enumeration;

public class BroadcastSocket extends MulticastSocket {

    public BroadcastSocket() throws IOException {
    }

    public BroadcastSocket(int var1) throws IOException {
        super(new InetSocketAddress(var1));
    }

    @Override
    public void joinGroup(InetAddress var1) throws IOException {
    }

    @Override
    public void leaveGroup(InetAddress var1) throws IOException {
    }

    @Override
    public void joinGroup(SocketAddress var1, NetworkInterface var2) throws IOException {
    }

    @Override
    public void leaveGroup(SocketAddress var1, NetworkInterface var2) throws IOException {
    }

//    @Override
//    public void setInterface(InetAddress var1) throws SocketException {
//        super.setInterface(var1);
//        this.getImpl().setOption(SocketOptions.SO_BROADCAST, var1);
//    }

//    @Override
//    public InetAddress getInterface() throws SocketException {
//        if (this.isClosed()) {
//            throw new SocketException("Socket is closed");
//        } else {
//            synchronized (this.infLock) {
//                InetAddress var2 = (InetAddress) this.getImpl().getOption(SocketOptions.SO_BROADCAST);
//                if (this.infAddress == null) {
//                    return var2;
//                } else if (var2.equals(this.infAddress)) {
//                    return var2;
//                } else {
//                    InetAddress var10000;
//                    try {
//                        NetworkInterface var3 = NetworkInterface.getByInetAddress(var2);
//                        Enumeration var4 = var3.getInetAddresses();
//
//                        InetAddress var5;
//                        do {
//                            if (!var4.hasMoreElements()) {
//                                this.infAddress = null;
//                                var10000 = var2;
//                                return var10000;
//                            }
//
//                            var5 = (InetAddress) var4.nextElement();
//                        } while (!var5.equals(this.infAddress));
//
//                        var10000 = this.infAddress;
//                    } catch (Exception var7) {
//                        return var2;
//                    }
//
//                    return var10000;
//                }
//            }
//        }
//    }

//    @Override
//    public void setNetworkInterface(NetworkInterface var1) throws SocketException {
//        super.setNetworkInterface(var1);
//        this.getImpl().setOption(SocketOptions.SO_BROADCAST, var1);
//    }

    /**
     * @deprecated
     */
    @Deprecated
    public void send(DatagramPacket var1, byte var2) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        } else {
            this.checkAddress(var1.getAddress(), "send");
                synchronized (var1) {
                    if (this.connectState == 0) {
                        SecurityManager var15 = System.getSecurityManager();
                        if (var15 != null) {
//                            if (var1.getAddress().isMulticastAddress()) {
//                                var15.checkMulticast(var1.getAddress(), var2);
//                            } else {
//                                var15.checkConnect(var1.getAddress().getHostAddress(), var1.getPort());
//                            }
                            if (isBroadcastAddress(var1.getAddress())) {
                                this.checkBroadcast(var1.getAddress(), var2);
                            } else {
                                var15.checkConnect(var1.getAddress().getHostAddress(), var1.getPort());
                            }
                        }
                    } else {
                        InetAddress var5 = null;
                        var5 = var1.getAddress();
                        if (var5 == null) {
                            var1.setAddress(this.connectedAddress);
                            var1.setPort(this.connectedPort);
                        } else if (!var5.equals(this.connectedAddress) || var1.getPort() != this.connectedPort) {
                            throw new SecurityException("connected address and packet address differ");
                        }
                    }

                    byte var16 = this.getTTL();

                    try {
                        if (var2 != var16) {
                            this.getImpl().setTTL(var2);
                        }

                        this.getImpl().send(var1);
                    } finally {
                        if (var2 != var16) {
                            this.getImpl().setTTL(var16);
                        }

                    }
                }
        }
    }

    private boolean isBroadcastAddress(InetAddress address) {
        String hostname = address.getHostName();
        if (hostname.startsWith("255")) {
            return true;
        }

        return false;
    }

    private void checkBroadcast(InetAddress var1, byte var2) {
//        String var3 = var1.getHostAddress();
//        if (!var3.startsWith("[") && var3.indexOf(58) != -1) {
//            var3 = "[" + var3 + "]";
//        }
//
//        this.checkPermission(new SocketPermission(var3, "connect,accept"));
    }
}
