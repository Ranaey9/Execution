# Execution3 – Basit Assembly Benzeri Yorumlayıcı

## Proje Özeti

Bu proje, Java ile yazılmış **basit bir assembly / makine dili yorumlayıcısıdır**. `program.txt` dosyasında yazılan komutları satır satır okuyarak çalıştırır. CPU mantığını taklit eden **registerlar**, **RAM**, **bayraklar** ve **atlama (jump)** komutlarını içerir.

Amaç, düşük seviyeli komutların nasıl işlendiğini ve bir komut yorumlayıcısının temel çalışma mantığını göstermektir.

---

## Temel Bileşenler

### Registerlar

* `AX`, `BX`, `CX`, `DX`
* Aritmetik ve mantıksal işlemler bu registerlar üzerinde yapılır.

### RAM

* 256 elemanlı integer dizi
* `[adres]` formatı ile bellek erişimi yapılır

### Bayraklar (Flags)

* `SifirBayrak (ZF)` → Sonuç 0 ise true
* `IsaretBayrak (SF)` → Sonuç negatif ise true
* `TasmaBayrak (OF)` → Sonuç -128 / 127 aralığı dışına çıkarsa true

### Komut Sayacı

* `sayac` değişkeni çalıştırılan satırı tutar
* Atlama komutları ile değiştirilebilir

---

## Desteklenen Komutlar

### Aritmetik Komutlar

* `ATM hedef kaynak` → Atama
* `TOP a b` → Toplama
* `CIK a b` → Çıkarma
* `CRP a b` → Çarpma
* `BOL a b` → Bölme

### Mantıksal Komutlar

* `VE a b` → AND
* `VEY a b` → OR
* `DEG a` → NOT

### Giriş / Çıkış

* `OKU AX` → Kullanıcıdan değer alır
* `YAZ AX` → Değeri ekrana yazdırır

### Kontrol (Jump) Komutları

* `D etiket` → Koşulsuz atlama
* `DE etiket` → Sonuç sıfırsa
* `DED etiket` → Sonuç sıfır değilse
* `DB etiket` → Büyükse
* `DBE etiket` → Büyük veya eşitse
* `DK etiket` → Küçükse
* `DKE etiket` → Küçük veya eşitse

### Program Sonu

* `SON` → Programı bitirir

---

## Etiket Kullanımı

```text
basla:
TOP AX 5
D basla
```

Etiketler `:` ile tanımlanır ve jump komutları tarafından kullanılır.

---

## Programın Çalışma Mantığı

1. `program.txt` dosyası okunur
2. Her satır parçalanarak komut ve operandlar ayrılır
3. Komut yorumlanır ve çalıştırılır
4. Bayraklar güncellenir
5. Gerekirse etiketlere atlama yapılır

---

## Amaç ve Kullanım Alanı

* Assembly mantığını öğrenmek
* Interpreter (yorumlayıcı) yapısını anlamak
* CPU, register ve bellek simülasyonu
* Eğitim ve akademik amaçlı kullanım

---

## Notlar

* Bu proje gerçek bir işlemciyi değil, **eğitsel bir modeli** simüle eder
* Kod okunabilirliği ve algoritmik mantık ön plandadır

---

## Geliştirme Fikirleri

* Yeni komutlar eklenebilir
* Stack yapısı eklenebilir
* Fonksiyon çağrıları desteklenebilir
* Debug modu eklenebilir
