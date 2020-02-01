package controller;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;

import FileIO.DosyaOlusturucu;
import ortak.CihazYonetici;

public class PaketDinleyici {

	private PcapNetworkInterface device = null;
	private PcapHandle handle = null;
	private int maxPaketSayici = -1;
	private PcapDumper dumper = null;
	private CihazDinleyici listener = null;
	private String filename;

	public PaketDinleyici() throws PcapNativeException {

		device = CihazYonetici.getCurrentDevice();
		handle = device.openLive(65536, PromiscuousMode.PROMISCUOUS, 50);
	}

	public void baslat() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				dinleyiciDongusuBaslat();
			}
		}).start();
	}

	private void dinleyiciDongusuBaslat() {

		try {
			filename = DosyaOlusturucu.getInstance().getYeniTempKayitDosyasiAdi();
			dumper = handle.dumpOpen(filename);
//			while (handle.isOpen()) {
//
//				paket = handle.getNextPacket();
//				dumper.dump(paket);
//			}
			listener = new CihazDinleyici(dumper);
			handle.loop(maxPaketSayici, listener);

		} catch (Exception e) {
			// e.printStackTrace();
		}
		DosyaOlusturucu.getInstance().moveKayit(filename);
	}

	public void filtreEkle(String filtre) {
		try {
			handle.setFilter(filtre, BpfCompileMode.OPTIMIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {

		try {
			handle.breakLoop();
			dumper.close();
			handle.close();
		} catch (NotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getMaxPaketSayici() {
		return maxPaketSayici;
	}

	public void setMaxPaketSayici(int maxPaketSayici) {
		this.maxPaketSayici = maxPaketSayici;
	}

}
