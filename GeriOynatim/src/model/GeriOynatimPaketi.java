package model;

import org.pcap4j.packet.Packet;

public class GeriOynatimPaketi {

	private long paketZamani = 0;
	private Packet pcapPaketi = null;

	public GeriOynatimPaketi(long paketZamani, Packet pcapPaketi) {
		super();
		this.paketZamani = paketZamani;
		this.pcapPaketi = pcapPaketi;
	}

	public long getPaketZamani() {
		return paketZamani;
	}

	public void setPaketZamani(long paketZamani) {
		this.paketZamani = paketZamani;
	}

	public Packet getPcapPaketi() {
		return pcapPaketi;
	}

	public void setPcapPaketi(Packet pcapPaketi) {
		this.pcapPaketi = pcapPaketi;
	}

}
