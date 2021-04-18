# NetworkInterface
  
  
  
### Utils about NetworkInterface Using Java Native Library
- Usage 
```java
/* 모든 네트워크 인터페이스 출력 */
NetworkInterfaceUtils.getInstance().printNetworkInterfaces();

    Printing all network interfaces... This may take a few seconds.

    Interface Name  : lo
    Display Name    : Software Loopback Interface 1
    Host Name       : kubernetes.docker.internal
    Host Address    : 127.0.0.1
    Host Name       : 0:0:0:0:0:0:0:1
    Host Address    : 0:0:0:0:0:0:0:1
    ====================================================================
    Interface Name  : eth1
    Display Name    : Realtek PCIe GBE Family Controller
    Host Name       : host.docker.internal
    Host Address    : 123.456.789.0
    Host Name       : DESKTOP-BKFDLJR
    Host Address    : ab12:3:4:5:67c8:90de:fghi:jklm%eth1
    ====================================================================
    Interface Name  : eth22
    Display Name    : Hyper-V Virtual Ethernet Adapter
    Host Name       : DESKTOP-BKFDLJR.mshome.net
    Host Address    : 223.456.789.0
    Host Name       : DESKTOP-BKFDLJR
    Host Address    : bb12:3:4:5:67c8:90de:fghi:jklm%eth22
    ====================================================================
    Interface Name  : eth29
    Display Name    : Hyper-V Virtual Ethernet Adapter #2
    Host Name       : DESKTOP-BKFDLJR
    Host Address    : 323.456.789.0
    Host Name       : DESKTOP-BKFDLJR
    Host Address    : cb12:3:4:5:67c8:90de:fghi:jklm%eth29
    ====================================================================



/* 123.456.789.0 */
String ipv4 = NetworkInterfaceUtils.getInstance().getIPv4HostAddressByInterfaceName("eth1");    
/* ab12:3:4:5:67c8:90de:fghi:jklm%eth1 */
String macAddress = NetworkInterfaceUtils.getInstance().getMacAddressByInterfaceName("eth1");
```

- core code 
```java
/* 모든 네트워크 인터페이스 정보를 가져옴 */
Enumeration<NetworkInterface> niEnums = NetworkInterface.getNetworkInterfaces();

while(niEnums.hasMoreElements()) {
    /* 각 인터페이스 객체를 가져온다. */
    NetworkInterface ni = niEnums.nextElement();
    /* 해당 인터페이스에 해당하는 InetAddress 컬렉션 */
    Enumeration<InetAddress> iaEnums = ni.getInetAddresses();

    boolean isInetAddressExists = iaEnums.hasMoreElements();

    if(!isInetAddressExists)
        continue;

    /* 해당 인터페이스의 정보 */
    // String.format("%-15s : %s", "Interface Name", ni.getName())
    // String.format("%-15s : %s", "Display Name", ni.getDisplayName())
    
    /* 해당 인터페이스의 맥주소 */
    String macAddress = "";
    byte[] macByteArray = ni.getHardwareAddress();
    int macByteArrayLength = macByteArray.length;
    for(int i = 0 ; i < macByteArrayLength ; ++i)
        macAddress += String.format("%02X%s", macByteArray[i], (i < macByteArrayLength - 1) ? "-" : "");



    while(iaEnums.hasMoreElements()) {
        InetAddress inetAddress = iaEnums.nextElement();

        /* 각 InetAddress의 정보 */
        // String.format("%-15s : %s", "Host Name", inetAddress.getHostName())
        // String.format("%-15s : %s", "Host Address", inetAddress.getHostAddress())
        
        /* IPv4 - 루프백이 아니고, 링크로컬이 아니면서 사이트로컬인 주소를 선택 */
        final boolean isTarget =
                !inetAddress.isLoopbackAddress() &&
                !inetAddress.isLinkLocalAddress() &&
                inetAddress.isSiteLocalAddress();

        if(isTarget)
            String hostAddress = inetAddress.getHostAddress();    /* IPv4 주소 */
    }

}
```
