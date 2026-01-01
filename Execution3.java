import java.io.File;
import java.util.Scanner;

public class Execution3 {

    static int[] RAM = new int[256];
    static int AX = 0, BX = 0, CX = 0, DX = 0;

    // Bayraklar
    static boolean SifirBayrak = false;
    static boolean IsaretBayrak = false;
    static boolean TasmaBayrak = false;

    static String[] kodSatirlari = new String[1000];
    static int toplamSatir = 0;
    static int sayac = 0;

    public static void main(String[] args) {
        try {
            File dosya = new File("program.txt");
            Scanner okuyucu = new Scanner(dosya);

            while (okuyucu.hasNextLine()) {
                String satir = okuyucu.nextLine();

                if (satir.length() > 0) {
                    kodSatirlari[toplamSatir] = satir;
                    toplamSatir++;
                }
            }
            okuyucu.close();
        } catch (Exception e) {
            System.out.println("Dosya bulunamadi: program.txt");
            return;
        }

        Scanner giris = new Scanner(System.in);

        while (sayac < toplamSatir) {

            String satir = kodSatirlari[sayac];

            if (satir == null) {
                sayac++;
                continue;
            }

            // ayrıştırma
            String[] parcalar = new String[5];
            int parcaSayisi = 0;
            String kelime = "";

            for (int i = 0; i < satir.length(); i++) {
                char harf = satir.charAt(i);
                if (harf != ' ' && harf != '\t') {
                    kelime = kelime + harf;
                } else if (kelime.length() > 0) {
                    parcalar[parcaSayisi] = kelime;
                    parcaSayisi++;
                    kelime = "";
                }
            }
            if (kelime.length() > 0) {
                parcalar[parcaSayisi] = kelime;
                parcaSayisi++;
            }
            if (parcaSayisi == 0) {
                sayac++;
                continue;
            }

            int baslangic = 0;
            String ilkKelime = parcalar[0];

            if (ilkKelime.length() > 0 && ilkKelime.charAt(ilkKelime.length() - 1) == ':') {
                baslangic = 1;
            }
            if (baslangic >= parcaSayisi) {
                sayac++;
                continue;
            }
            String komut = parcalar[baslangic];

            String op1 = "";
            if (baslangic + 1 < parcaSayisi) {
                op1 = parcalar[baslangic + 1];
            }

            String op2 = "";
            if (baslangic + 2 < parcaSayisi) {
                op2 = parcalar[baslangic + 2];
            }

            boolean atlama = false;

            // komutlar
            if (ayniMi(komut, "ATM")) {
                int deger = degerOku(op2);
                sonucYaz(op1, deger);

            } else if (ayniMi(komut, "TOP")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                int sonuc = s1 + s2;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "CRP")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                int sonuc = s1 * s2;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "CIK")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                int sonuc = s1 - s2;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "BOL")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                if (s2 != 0) {
                    int sonuc = s1 / s2;
                    sonucYaz(op1, sonuc);
                }

            } else if (ayniMi(komut, "VE")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                int sonuc = s1 & s2;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "VEY")) {
                int s1 = degerOku(op1);
                int s2 = degerOku(op2);
                int sonuc = s1 | s2;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "DEG")) {
                int s1 = degerOku(op1);
                int sonuc = ~s1;
                sonucYaz(op1, sonuc);

            } else if (ayniMi(komut, "D")) {
                sayac = etiketBul(op1);
                atlama = true;

            } else if (ayniMi(komut, "DE")) {
                if (SifirBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "DED")) {
                if (!SifirBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "DB")) {
                if (!IsaretBayrak && !SifirBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "DBE")) {
                if (!IsaretBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "DK")) {
                if (IsaretBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "DKE")) {
                if (IsaretBayrak || SifirBayrak) {
                    sayac = etiketBul(op1);
                    atlama = true;
                }

            } else if (ayniMi(komut, "OKU")) {
                System.out.print("Bir sayi giriniz (" + op1 + " icin): ");

                if (giris.hasNextInt()) {
                    int girilen = giris.nextInt();
                    sonucYaz(op1, girilen);
                }

            } else if (ayniMi(komut, "YAZ")) {
                int deger = degerOku(op1);
                System.out.println(deger);

            } else if (ayniMi(komut, "SON")) {
                break;
            }

            if (!atlama) {
                sayac++;
            }
        }

        giris.close();

        System.out.println("AX:" + AX);
        System.out.println("BX:" + BX);
        System.out.println("CX:" + CX);
        System.out.println("DX:" + DX);
        System.out.println("TasmaBayrak: " + TasmaBayrak);
    }

    // aynı mı değil mi kontrol yapıyoruz
    static boolean ayniMi(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;
        if (s1.length() != s2.length())
            return false;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                return false;
        }
        return true;
    }

    // sayi yap
    static int sayiYap(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int sonuc = 0;
        int i = 0;
        boolean negatif = false;
        if (s.charAt(0) == '-') {
            negatif = true;
            i = 1;
        }
        for (; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                sonuc = (sonuc * 10) + (c - '0');
            }
        }
        if (negatif)
            return -sonuc;
        return sonuc;
    }

    // değer oku
    static int degerOku(String s) {
        if (s == null || s.length() == 0)
            return 0;

        if (s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
            String icerik = "";
            for (int i = 1; i < s.length() - 1; i++) {
                icerik += s.charAt(i);
            }
            int adres = degerOku(icerik);
            if (adres >= 0 && adres < 256)
                return RAM[adres];
            return 0;
        }

        if (ayniMi(s, "AX"))
            return AX;
        if (ayniMi(s, "BX"))
            return BX;
        if (ayniMi(s, "CX"))
            return CX;
        if (ayniMi(s, "DX"))
            return DX;

        return sayiYap(s);
    }

    // sonuç yaz
    static void sonucYaz(String hedef, int sonuc) {

        int yaz = bayrakAyarla(sonuc);

        degerYaz(hedef, yaz);
    }

    // değer yaz
    static void degerYaz(String s, int deger) {

        if (s == null || s.length() == 0)
            return;

        if (s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
            String icerik = "";
            for (int i = 1; i < s.length() - 1; i++) {
                icerik += s.charAt(i);
            }
            int adres = degerOku(icerik);
            if (adres >= 0 && adres < 256)
                RAM[adres] = deger;

        } else if (ayniMi(s, "AX"))
            AX = deger;
        else if (ayniMi(s, "BX"))
            BX = deger;
        else if (ayniMi(s, "CX"))
            CX = deger;
        else if (ayniMi(s, "DX"))
            DX = deger;
    }

    // bayrak kontrol
    static int bayrakAyarla(int sonuc) {

        int min = -128;
        int max = 127;

        TasmaBayrak = (sonuc < min || sonuc > max);

        if (TasmaBayrak) {

            sonuc = (int) ((byte) sonuc);
        }
        SifirBayrak = (sonuc == 0);

        IsaretBayrak = (sonuc < 0);

        return sonuc;
    }

    static int etiketBul(String arananEtiket) {
        String hedef = arananEtiket + ":";

        for (int i = 0; i < toplamSatir; i++) {
            String satir = kodSatirlari[i];

            String ilkKelime = "";
            boolean kelimeBasladi = false;

            for (int k = 0; k < satir.length(); k++) {
                char c = satir.charAt(k);
                if (c != ' ' && c != '\t') {
                    kelimeBasladi = true;
                    ilkKelime += c;
                } else if (kelimeBasladi) {
                    break;
                }
            }

            if (ayniMi(ilkKelime, hedef)) {
                return i;
            }
        }
        return sayac;
    }
}