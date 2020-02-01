package ortak;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

public class CihazYonetici {

	private CihazYonetici() {

	}

	private static Predicate<PcapNetworkInterface> isNotLoopBack = (dev) -> !dev.isLoopBack();

	private static BiFunction<PcapNetworkInterface, String, Boolean> ipControl = (dev, ip) -> {
		for (PcapAddress pAd : dev.getAddresses()) {
			if (pAd.getAddress() instanceof Inet4Address) {
				return pAd.getAddress().getHostAddress().equalsIgnoreCase(ip);
			}
		}
		return false;
	};

	private static BiPredicate<PcapNetworkInterface, String> ipFilter = (dev, ip) -> ipControl.apply(dev, ip);

	public static PcapNetworkInterface getCurrentDevice() {
		PcapNetworkInterface selected = null;
		try {
			List<PcapNetworkInterface> allDevs = null;

			allDevs = Pcaps.findAllDevs();
			String ip = Inet4Address.getLocalHost().getHostAddress();
			List<PcapNetworkInterface> filteredList = allDevs.stream().filter(isNotLoopBack)
					.filter(dev -> ipFilter.test(dev, ip)).filter(Objects::nonNull).collect(Collectors.toList());
			if (filteredList != null && filteredList.size() == 1) {
				selected = filteredList.get(0);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (PcapNativeException e) {
			e.printStackTrace();
		}

		return selected;
	}

}
