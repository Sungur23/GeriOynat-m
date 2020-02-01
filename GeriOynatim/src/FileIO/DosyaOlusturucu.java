package FileIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import controller.PaketOkuyucu;
import model.GeriOynatimPaketListesi;
import ortak.OrtakSabitler;

public class DosyaOlusturucu {

	private static DosyaOlusturucu instance = null;
	private PaketOkuyucu paketOkuyucu = null;

	private DosyaOlusturucu() {
		paketOkuyucu = new PaketOkuyucu();
	}

	public static synchronized DosyaOlusturucu getInstance() {
		if (instance == null) {
			instance = new DosyaOlusturucu();
		}
		return instance;
	}

	public String getYeniTempKayitDosyasiAdi() {

		File rootFolder = new File(OrtakSabitler.TEMP_DIZINI);
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
		}
		return OrtakSabitler.TEMP_DIZINI + OrtakSabitler.standartFormat.format(new Date()) + OrtakSabitler.EXT;
	}

	public String getYeniKayitDosyasiAdi(String temp) {

		File rootFolder = new File(OrtakSabitler.DIZIN);
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
		}
		return OrtakSabitler.DIZIN + temp;
	}

	public List<File> getGeciciDosyalar() {

		File rootFolder = new File(OrtakSabitler.TEMP_DIZINI);
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
			return null;
		}

		File[] liste = rootFolder.listFiles();
		if (liste == null)
			return null;

		return Arrays.asList(liste);
	}

	public List<File> getDosyalar() {

		File rootFolder = new File(OrtakSabitler.DIZIN);
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
			return null;
		}

		File[] liste = rootFolder.listFiles();
		if (liste == null)
			return null;

		return Arrays.asList(liste);
	}

	public List<GeriOynatimPaketListesi> getTumGeciciDosyaPaketleri() {

		List<GeriOynatimPaketListesi> liste = new ArrayList<GeriOynatimPaketListesi>();
		getGeciciDosyalar().stream().forEach(file -> {
			GeriOynatimPaketListesi paketler = paketOkuyucu.getPaketListeleri(file.getAbsolutePath());
			if (paketler != null)
				liste.add(paketler);
		});
		return liste;
	}

	public List<GeriOynatimPaketListesi> getTumDosyaPaketleri() {

		List<GeriOynatimPaketListesi> liste = new ArrayList<GeriOynatimPaketListesi>();
		getDosyalar().stream().forEach(file -> {
			GeriOynatimPaketListesi paketler = paketOkuyucu.getPaketListeleri(file.getAbsolutePath());
			if (paketler != null) {
				liste.add(paketler);
			}
		});
		return liste;
	}

	public void moveKayit(String path) {

		File yeni = new File(getYeniKayitDosyasiAdi(path.replace(OrtakSabitler.TEMP_DIZINI, "")));

		try {
			Files.move(new File(path).toPath(), yeni.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
