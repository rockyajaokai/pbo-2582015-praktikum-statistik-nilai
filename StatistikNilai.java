import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class StatistikNilai {
    
    // Sentinel untuk menandakan selesai memasukkan nilai
    private static final int SELESAI = -1;
    
    public static void main(String[] args) {
        // Locale Indonesia agar pemisah desimal menggunakan koma (sesuai contoh: 75,50)
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
        
        Scanner scanner = new Scanner(System.in);
        // Menyimpan nilai-nilai yang valid (0-100) ke dalam ArrayList<Integer>
        ArrayList<Integer> daftarNilai = new ArrayList<Integer>();
        
        System.out.println("===== STATISTIK NILAI KELAS =====");
        System.out.println("Ketik -1 kalau sudah selesai.");
        
        int nomorInput = 1;
        // Pembacaan nilai menggunakan do-while dengan sentinel SELESAI = -1
        do {
            System.out.print("Nilai ke-" + nomorInput + " : ");
            String input = scanner.nextLine().trim();
            
            int nilai;
            // Validasi input apakah berupa angka
            try {
                nilai = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Jika input bukan angka, tolak dan tidak menambah daftar
                // Nomor input tetap sama karena nilai yang ditolak tidak masuk daftar
                System.out.println("  Ditolak, harus 0-100");
                continue;
            }
            
            // Jika nilai sama dengan sentinel, maka selesai memasukkan nilai
            if (nilai == SELESAI) {
                break;
            }
            
            // Validasi rentang nilai 0-100
            // Jika di luar rentang, tolak dengan continue dan tidak di-add ke daftar
            if (nilai < 0 || nilai > 100) {
                System.out.println("  Ditolak, harus 0-100");
                // Nomor prompt tetap sama karena nilai yang ditolak tidak masuk daftar
                // dan tidak dilakukan increment pada nomorInput
                continue;
            }
            
            // Nilai valid (0-100), tambahkan ke daftar
            daftarNilai.add(nilai);
            // Nomor prompt diambil dari size() + 1 (sesuai ketentuan)
            nomorInput = daftarNilai.size() + 1;
            
        } while (true);
        
        scanner.close();
        
        // Kasus tidak ada nilai sah sama sekali (langsung diketik -1)
        if (daftarNilai.isEmpty()) {
            System.out.println();
            System.out.println("Tidak ada nilai yang tersimpan.");
            return;
        }
        
        // Mencetak isi daftar nilai (urutan asli sesuai input)
        System.out.println();
        System.out.println("Nilai tersimpan : " + daftarNilai.toString());
        System.out.println("Jumlah          : " + daftarNilai.size());
        
        // Menghitung total dan rata-rata
        double totalNilai = 0.0;
        for (int i = 0; i < daftarNilai.size(); i++) {
            totalNilai += daftarNilai.get(i);
        }
        double rataRata = totalNilai / (double) daftarNilai.size();
        System.out.println("Rata-rata       : " + decimalFormat.format(rataRata));
        
        // Mencari nilai tertinggi dengan loop sendiri
        // ALASAN NILAI AWAL:
        // Nilai tertinggi diinisialisasi dengan elemen pertama daftar (indeks 0).
        // Hal ini karena daftar sudah dipastikan tidak kosong, sehingga kita memiliki
        // nilai pembanding yang valid untuk memulai perbandingan. Dengan cara ini,
        // loop dapat dimulai dari indeks 1 dan aman (tidak terjadi akses ke indeks yang salah).
        int nilaiTertinggi = daftarNilai.get(0);
        for (int i = 1; i < daftarNilai.size(); i++) {
            if (daftarNilai.get(i) > nilaiTertinggi) {
                nilaiTertinggi = daftarNilai.get(i);
            }
        }
        System.out.println("Tertinggi       : " + nilaiTertinggi);
        
        // Mencari nilai terendah dengan loop sendiri
        // ALASAN NILAI AWAL:
        // Sama seperti pencarian nilai tertinggi, nilai terendah diinisialisasi
        // dengan elemen pertama (indeks 0) karena daftar tidak kosong.
        // Ini memastikan perbandingan dimulai dari data yang nyata dan terjamin kebenarannya.
        int nilaiTerendah = daftarNilai.get(0);
        for (int i = 1; i < daftarNilai.size(); i++) {
            if (daftarNilai.get(i) < nilaiTerendah) {
                nilaiTerendah = daftarNilai.get(i);
            }
        }
        System.out.println("Terendah        : " + nilaiTerendah);
        
        // Menghitung jumlah mahasiswa di atas rata-rata (putaran kedua)
        // PENJELASAN KENAPA TIDAK BISA DIHITUNG DI PUTARAN PERTAMA:
        // Rata-rata tidak dapat diketahui saat kita sedang membaca nilai satu per satu.
        // Kita baru bisa menghitung rata-rata setelah seluruh nilai valid telah dibaca
        // dan dijumlahkan (total dibagi jumlah data). Oleh karena itu, perbandingan
        // setiap nilai terhadap rata-rata harus dilakukan pada putaran kedua,
        // yaitu setelah rata-rata diketahui.
        int jumlahDiAtasRataRata = 0;
        for (int i = 0; i < daftarNilai.size(); i++) {
            if (daftarNilai.get(i) > rataRata) {
                jumlahDiAtasRataRata++;
            }
        }
        System.out.println("Di atas rata2   : " + jumlahDiAtasRataRata + " orang");
        
        // Distribusi grade disimpan di array int[] jumlahGrade = new int[5]
        // Indeks 0 = A, 1 = B, 2 = C, 3 = D, 4 = E
        int[] jumlahGrade = new int[5];
        for (int i = 0; i < daftarNilai.size(); i++) {
            int nilai = daftarNilai.get(i);
            if (nilai >= 90) {
                // Grade A
                jumlahGrade[0]++;
            } else if (nilai >= 80) {
                // Grade B
                jumlahGrade[1]++;
            } else if (nilai >= 70) {
                // Grade C
                jumlahGrade[2]++;
            } else if (nilai >= 60) {
                // Grade D
                jumlahGrade[3]++;
            } else {
                // nilai < 60 -> Grade E
                jumlahGrade[4]++;
            }
        }
        
        // Mencetak distribusi grade dengan loop, bukan lima baris println terpisah
        System.out.print("Distribusi      : ");
        String[] labelGrade = {"A", "B", "C", "D", "E"};
        StringBuilder sbDistribusi = new StringBuilder();
        for (int i = 0; i < jumlahGrade.length; i++) {
            if (i > 0) {
                sbDistribusi.append(" ");
            }
            sbDistribusi.append(labelGrade[i]).append("=").append(jumlahGrade[i]);
        }
        System.out.println(sbDistribusi.toString());
        
        // Membuat daftar terurut dari salinan (agar urutan asli tidak ikut terurut)
        ArrayList<Integer> terurut = new ArrayList<Integer>(daftarNilai);
        Collections.sort(terurut);
        
        // Mencetak daftar terurut dan urutan asli untuk membuktikan tidak berubah
        System.out.println("Terurut         : " + terurut.toString());
        System.out.println("Urutan asli     : " + daftarNilai.toString());
    }
}
