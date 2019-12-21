package com.chexin.simple.development.support.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * get ip
 * @author wl
 */
public class IpUtil {
	private static final Logger logger = LogManager.getLogger(IpUtil.class);

	private static final String ANYHOST = "0.0.0.0";
	private static final String LOCALHOST = "172.22.5.24";
	public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	private static volatile InetAddress LOCAL_ADDRESS = null;

	/**
	 * valid address
	 * @param address
	 * @return
	 */
	private static boolean isValidAddress(InetAddress address) {
		if (address == null || address.isLoopbackAddress()) {
			return false;
		}
		String name = address.getHostAddress();
		return (name != null
				&& ! ANYHOST.equals(name)
				&& ! LOCALHOST.equals(name)
				&& IP_PATTERN.matcher(name).matches());
	}

	/**
	 * get first valid addredd
	 * @return
	 */
	private static InetAddress getFirstValidAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (Throwable e) {
			logger.error("Failed to retriving ip address, " + e.getMessage(), e);
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					try {
						NetworkInterface network = interfaces.nextElement();
						Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									InetAddress address = addresses.nextElement();
									if (isValidAddress(address)) {
										return address;
									}
								} catch (Throwable e) {
									logger.error("Failed to retriving ip address, " + e.getMessage(), e);
								}
							}
						}
					} catch (Throwable e) {
						logger.error("Failed to retriving ip address, " + e.getMessage(), e);
					}
				}
			}
		} catch (Throwable e) {
			logger.error("Failed to retriving ip address, " + e.getMessage(), e);
		}
		logger.error("Could not get local host ip address, will use 172.22.5.24 instead.");
		return localAddress;
	}

	/**
	 * get address
	 * @return
	 */
	private static InetAddress getAddress() {
		if (LOCAL_ADDRESS != null) {
			return LOCAL_ADDRESS;
		}
		InetAddress localAddress = getFirstValidAddress();
		LOCAL_ADDRESS = localAddress;
		return localAddress;
	}

	/**
	 * get ip
	 * @return
	 */
	public static String getIp(){
		InetAddress address = getAddress();
		if (address==null) {
			return null;
		}
		return address.getHostAddress();
	}

	/**
	 * get ip:port
	 * @param port
	 * @return
	 */
	public static String getIpPort(int port){
		String ip = getIp();
		if (ip==null) {
			return null;
		}
		return ip.concat(":").concat(String.valueOf(port));
	}
	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getIpAddress(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isInfoEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	/**
	 * 获取本地IP
	 *
	 * @return
	 */
	public static String getLocalhostIp() {
		try {
			String localIp = null;// 本地IP，如果没有配置外网IP则返回它
			String netIp = null;// 外网IP
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netIp = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localIp = ip.getHostAddress();
					}
				}
			}

			if (netIp != null && !"".equals(netIp)) {
				return netIp;
			} else {
				return localIp;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(getIp());
		System.out.println(getIpPort(8080));
	}

}
