package controller;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import model.GeriOynatimPaketListesi;
import model.GeriOynatimPaketi;

public class PaketOkuyucu {

	private PcapHandle handle = null;

	public PaketOkuyucu() {

	}

	public GeriOynatimPaketListesi getPaketListeleri(String dosya) {

		GeriOynatimPaketListesi kaydedilenPaketler = new GeriOynatimPaketListesi();
		try {
			handle = Pcaps.openOffline(dosya);

			Packet paket = getPacket();
			if (paket == null) {
				handle.close();
				return kaydedilenPaketler;
			}
			long time = handle.getTimestamp().getTime();
			kaydedilenPaketler.setBaslangicZamani(time);
			kaydedilenPaketler.paketEkle(new GeriOynatimPaketi(time, paket));

			while (true) {

				paket = getPacket();
				if (paket == null) {
					break;
				}
				time = handle.getTimestamp().getTime();
				kaydedilenPaketler.setBitisZamani(time);
				kaydedilenPaketler.paketEkle(new GeriOynatimPaketi(time, paket));
			}
		} catch (Exception e) {
			e.printStackTrace();
			handle.close();
			return null;
		}

		handle.close();
		return kaydedilenPaketler;
	}

	private Packet getPacket() {
		if (handle == null || !handle.isOpen()) {
			return null;
		}

		try {
			return handle.getNextPacket();
		} catch (NotOpenException e) {
			return null;
		}
	}
}
