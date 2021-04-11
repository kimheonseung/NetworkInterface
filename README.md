# NetworkInterface
  
  
  
## 네트워크 인터페이스명 관련 기능 수행 자바 클래스
- 주요 코드 
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
