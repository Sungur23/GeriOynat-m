package test;

import java.util.List;

import FileIO.DosyaOlusturucu;
import controller.PaketDinleyici;
import model.GeriOynatimPaketListesi;

public class Test {

	public static void main(String[] args) {

		try {
			PaketDinleyici pk = new PaketDinleyici();

//			pk.baslat();
//			System.err.println("Baslatildi");
//			Thread.sleep(10000);
//			System.err.println("Bitti");
//			pk.close();
			List<GeriOynatimPaketListesi> liste = DosyaOlusturucu.getInstance().getTumDosyaPaketleri();

			liste.stream().forEach(paketler -> paketler.show());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
