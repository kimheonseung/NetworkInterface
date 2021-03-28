package com.devheon.modules.networkinterface;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <pre>
 * Description : 
 *     네트워크 인터페이스 관련 유틸
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-03-28
 * </pre>
 */
public class NIUtils {
	
	/**
     * <pre>
     * Description
     *     네트워크 인터페이스 목록 출력
     * ===============================================
     * Parameters
     *     
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/28
     * </pre>
     */
	public void printNetworkInterfaces() {

        try {
            Enumeration<NetworkInterface> niEnums = NetworkInterface.getNetworkInterfaces();

            while(niEnums.hasMoreElements()) {
                NetworkInterface ni = niEnums.nextElement();
                Enumeration<InetAddress> inetEnums = ni.getInetAddresses();
                boolean isInetExists = inetEnums.hasMoreElements();

                if(!isInetExists)
                    continue;

                System.out.println(String.format("%-15s : %s", "Interface Name", ni.getName()));
                System.out.println(String.format("%-15s : %s", "Display Name", ni.getDisplayName()));

                while(inetEnums.hasMoreElements()) {
                    InetAddress inetAddress = inetEnums.nextElement();
                    System.out.println(String.format("%-15s : %s", "Host Name", inetAddress.getHostName()));
                    System.out.println(String.format("%-15s : %s", "Host Address", inetAddress.getHostAddress()));
                }

                System.out.println("===============================================================");
            }

        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Network Interface 조회 중 예외 발생. " + e.getMessage());
        }
    }

	/**
     * <pre>
     * Description
     *     네트워크 인터페이스 이름을 통해 IPv4 주소 반환
     * ===============================================
     * Parameters
     *     String interfaceName
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/28
     * </pre>
     */
    public String getHostAddressByInterfaceName(String interfaceName) {
        String hostAddress = null;

        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetEnums = ni.getInetAddresses();

            while(inetEnums.hasMoreElements()) {
                InetAddress inetAddress = inetEnums.nextElement();
                boolean isTarget = !inetAddress.isLoopbackAddress() &&
                        !inetAddress.isLinkLocalAddress() &&
                        inetAddress.isSiteLocalAddress();
                if(isTarget)
                    hostAddress = inetAddress.getHostAddress();
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Network Interface 조회 중 예외 발생. " + e.getMessage());
        }

        return hostAddress;
    }
}
