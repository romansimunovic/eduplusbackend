package com.edukatorplus.seeder;

import com.edukatorplus.model.*;
import com.edukatorplus.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class DataSeeder {

    @Autowired
    private PolaznikRepository polaznikRepo;

    @Autowired
    private RadionicaRepository radionicaRepo;

    @Autowired
    private PrisustvoRepository prisustvoRepo;

    private final Faker faker = new Faker(new Locale("hr"));
    private final Random random = new Random();

    private static final List<String> DOMENE = List.of("gmail.com", "yahoo.com", "outlook.com", "inet.hr", "net.hr");
    private static final List<String> STATUSI = List.of("student", "zaposlen", "nezaposlen", "učenik");
    private static final List<String> GRADOVI = List.of("Osijek", "Zagreb", "Rijeka", "Split", "Pula", "Varaždin", "Zadar", "Slavonski Brod");

    private static final List<String> TEME = List.of(
            "feminizmu", "programiranju", "umjetnoj inteligenciji", "civilnom društvu",
            "digitalnoj sigurnosti", "kritičkom mišljenju", "informacijskoj pismenosti",
            "održivom razvoju", "građanskoj participaciji", "društvenoj pravdi",
            "ljudskim pravima", "volontiranju", "inkluzivnom obrazovanju", "multikulturalizmu",
            "pravima LGBTQ+ osoba", "zelenoj energiji", "recikliranju otpada", "mentalnom zdravlju",
            "medijskoj pismenosti", "ekološkoj svijesti", "obrazovanju žena u STEM-u"
    );

    private static final List<String> KONTEKSTI = List.of(
            "u zajednici", "u civilnom sektoru", "u digitalnom dobu", "na radnom mjestu",
            "za mlade", "u školama", "u svakodnevnom životu", "putem radionica",
            "kroz neformalno obrazovanje", "u obrazovanju odraslih", "u lokalnim inicijativama",
            "u nevladinim organizacijama", "u knjižnicama", "putem online platformi", "na društvenim mrežama"
    );

    private static final List<String> MUSKA_IMENA = List.of(
            "Roman", "Sebastijan", "Marko", "Luka", "Ivan", "Petar", "Filip", "Karlo", "David", "Ante",
            "Tomislav", "Stjepan", "Domagoj", "Fran", "Josip", "Lovro", "Leon", "Noa", "Nikola", "Borna",
            "Matija", "Tin", "Matej", "Marin", "Kristijan", "Zvonimir", "Jakov", "Emanuel", "Hrvoje", "Viktor"
    );

    private static final List<String> ZENSKA_IMENA = List.of(
            "Romana", "Ines", "Ana", "Marija", "Lucija", "Maja", "Petra", "Martina", "Sara", "Lana",
            "Ivana", "Ema", "Lea", "Nina", "Katarina", "Dora", "Matea", "Laura", "Tena", "Andrea",
            "Mirta", "Tea", "Jelena", "Paula", "Elena", "Gabrijela", "Antonija", "Rebeka", "Helena", "Iva"
    );

    @PostConstruct
    public void init() {
        generateNewData();
    }

    public void generateNewData() {
        prisustvoRepo.deleteAllInBatch();
        polaznikRepo.deleteAllInBatch();
        radionicaRepo.deleteAllInBatch();

        // 1. Radionice
        Set<String> kombinacije = new HashSet<>();
        while (kombinacije.size() < 12) {
            String tema = TEME.get(random.nextInt(TEME.size()));
            String kontekst = KONTEKSTI.get(random.nextInt(KONTEKSTI.size()));
            String naziv = "Radionica o " + tema + " " + kontekst;
            kombinacije.add(naziv);
        }

        List<Radionica> radionice = kombinacije.stream().map(naziv -> {
            Radionica r = new Radionica();
            r.setNaziv(capitalize(naziv.trim()));
            r.setDatum(LocalDate.now().plusDays(random.nextInt(30)));
            return r;
        }).toList();
        radionice = radionicaRepo.saveAll(radionice);

        // 2. Polaznici
        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            boolean zensko = random.nextBoolean();
            String ime = zensko
                    ? ZENSKA_IMENA.get(random.nextInt(ZENSKA_IMENA.size()))
                    : MUSKA_IMENA.get(random.nextInt(MUSKA_IMENA.size()));

            String prezime = faker.name().lastName();
            String cleanIme = removeDiacritics(ime);
            String cleanPrezime = removeDiacritics(prezime);
            String broj = random.nextBoolean() ? String.valueOf(random.nextInt(100)) : "";
            String email = (cleanIme + "." + cleanPrezime + broj + "@" + DOMENE.get(random.nextInt(DOMENE.size()))).toLowerCase();

            Polaznik p = new Polaznik();
            p.setIme(ime);
            p.setPrezime(prezime);
            p.setSpol(zensko ? "ženski" : "muški");
            p.setEmail(email);
            p.setTelefon(faker.phoneNumber().cellPhone());
            p.setGodinaRodenja(faker.number().numberBetween(1975, 2007));
            p.setGrad(GRADOVI.get(random.nextInt(GRADOVI.size())));
            p.setStatus(STATUSI.get(random.nextInt(STATUSI.size())));
            polaznici.add(p);
        }
        polaznici = polaznikRepo.saveAll(polaznici);

        // 3. Prisustva
        List<Prisustvo> prisustva = new ArrayList<>();
        for (Radionica r : radionice) {
            for (Polaznik p : polaznici) {
                if (random.nextDouble() < 0.75) {
                    Prisustvo pr = new Prisustvo();
                    pr.setRadionica(r);
                    pr.setPolaznik(p);
                    pr.setStatus(randomStatus());
                    prisustva.add(pr);
                }
            }
        }
        prisustvoRepo.saveAll(prisustva);
    }

    private StatusPrisustva randomStatus() {
        return StatusPrisustva.values()[random.nextInt(StatusPrisustva.values().length)];
    }

    private String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("")
                .replace("đ", "d").replace("Đ", "D");
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
