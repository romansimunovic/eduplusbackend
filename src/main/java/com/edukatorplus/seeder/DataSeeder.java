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

    private static final Map<String, String> TEMA_U_GENITIVU = Map.ofEntries(
            Map.entry("feminizmu", "feminizma"),
            Map.entry("informacijskoj pismenosti", "informacijske pismenosti"),
            Map.entry("digitalnoj inkluziji", "digitalne inkluzije"),
            Map.entry("društvenom aktivizmu", "društvenog aktivizma"),
            Map.entry("umjetnoj inteligenciji", "umjetne inteligencije"),
            Map.entry("informacijskoj etici", "informacijske etike"),
            Map.entry("sigurnosti na internetu", "sigurnosti na internetu"),
            Map.entry("rodnoj ravnopravnosti", "rodne ravnopravnosti"),
            Map.entry("klimatskim promjenama", "klimatskih promjena"),
            Map.entry("zelenim politikama", "zelenih politika"),
            Map.entry("kritičkom promišljanju", "kritičkog promišljanja"),
            Map.entry("volontiranju", "volontiranja"),
            Map.entry("društvenoj pravdi", "društvene pravde"),
            Map.entry("mentalnom zdravlju", "mentalnog zdravlja"),
            Map.entry("građanskoj edukaciji", "građanske edukacije")
    );

    private static final List<String> TEME = new ArrayList<>(TEMA_U_GENITIVU.keySet());
    private static final List<String> KONTEKSTI = List.of(
            "u zajednici", "za mlade", "u civilnom sektoru", "u obrazovanju odraslih",
            "na internetu", "u svakodnevici", "na radnom mjestu", "u knjižnicama",
            "u STEM obrazovanju", "putem radionica", "kroz neformalno učenje"
    );

    @PostConstruct
    public void init() {
        generateNewData();
    }

    public void generateNewData() {
        prisustvoRepo.deleteAllInBatch();
        polaznikRepo.deleteAllInBatch();
        radionicaRepo.deleteAllInBatch();

        Set<String> kombinacije = new HashSet<>();
        while (kombinacije.size() < 10) {
            String tema = TEME.get(random.nextInt(TEME.size()));
            String kontekst = KONTEKSTI.get(random.nextInt(KONTEKSTI.size()));
            kombinacije.add("Radionica o " + tema + " " + kontekst);
        }

        List<Radionica> radionice = kombinacije.stream().map(naziv -> {
            String tema = Arrays.stream(TEME.toArray(new String[0]))
                    .filter(naziv::contains).findFirst().orElse("feminizmu");
            String kontekst = naziv.substring(naziv.indexOf(tema) + tema.length()).trim();
            Radionica r = new Radionica();
            r.setNaziv(naziv);
            r.setDatum(LocalDate.now().plusDays(random.nextInt(30)));
            r.setOpis(generirajOpis(tema, kontekst));
            return r;
        }).toList();

        radionice = radionicaRepo.saveAll(radionice);

        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String ime = faker.name().firstName();
            String prezime = faker.name().lastName();
            String spol = ime.toLowerCase().endsWith("a") ? "ženski" : "muški";
            String email = (removeDiacritics(ime) + "." + removeDiacritics(prezime) +
                    (random.nextBoolean() ? random.nextInt(100) : "") + "@" +
                    DOMENE.get(random.nextInt(DOMENE.size()))).toLowerCase();
            Polaznik p = new Polaznik();
            p.setIme(ime);
            p.setPrezime(prezime);
            p.setEmail(email);
            p.setGodinaRodenja(faker.number().numberBetween(1970, 2006));
            p.setSpol(spol);
            p.setTelefon(faker.phoneNumber().cellPhone());
            p.setGrad(GRADOVI.get(random.nextInt(GRADOVI.size())));
            p.setStatus(STATUSI.get(random.nextInt(STATUSI.size())));
            polaznici.add(p);
        }

        polaznici = polaznikRepo.saveAll(polaznici);

        List<Prisustvo> prisustva = new ArrayList<>();
        for (Radionica r : radionice) {
            for (Polaznik p : polaznici) {
                if (random.nextDouble() < 0.7) {
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

    private String generirajOpis(String tema, String kontekst) {
        String genitiv = TEMA_U_GENITIVU.getOrDefault(tema, tema);
        return "Ova radionica usmjerena je na " + tema + " " + kontekst +
                ", s ciljem osnaživanja sudionika kroz razmjenu znanja, praktične primjere i grupni rad. " +
                "Sudionici će razviti vještine i razumijevanje važnosti " + genitiv +
                " u svakodnevnom i profesionalnom kontekstu, uz naglasak na inkluziju, solidarnost i aktivno građanstvo.";
    }
}
