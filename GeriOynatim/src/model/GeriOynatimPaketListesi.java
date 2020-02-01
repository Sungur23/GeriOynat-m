package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ortak.OrtakSabitler;

public class GeriOynatimPaketListesi {

	private List<GeriOynatimPaketi> geriOynatimPaketleri = null;
	private long baslangicZamani = 0;
	private long bitisZamani = 0;

	public GeriOynatimPaketListesi() {
		geriOynatimPaketleri = new ArrayList<GeriOynatimPaketi>();
	}

	public long getBaslangicZamani() {
		return baslangicZamani;
	}

	public void setBaslangicZamani(long baslangicZamani) {
		this.baslangicZamani = baslangicZamani;
	}

	public long getBitisZamani() {
		return bitisZamani;
	}

	public void setBitisZamani(long bitisZamani) {
		this.bitisZamani = bitisZamani;
	}

	public void paketEkle(GeriOynatimPaketi p) {
		geriOynatimPaketleri.add(p);
	}

	public List<GeriOynatimPaketi> getGeriOynatimPaketleri() {

		return this.geriOynatimPaketleri;
	}

	public void show() {

		System.err.print("Liste baþlangýç:" + OrtakSabitler.gosterimFormat.format(new Date(baslangicZamani)));
		System.err.println(" - Liste bitis: " + OrtakSabitler.gosterimFormat.format(new Date(bitisZamani)));
//		geriOynatimPaketleri.stream().forEach(s -> {
//			System.err.println(s.getPcapPaketi());
////			try {
////				Thread.sleep(2000);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		});
	}
}
