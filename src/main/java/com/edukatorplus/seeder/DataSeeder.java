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

    @PostConstruct
    public void init() {
        prisustvoRepo.deleteAllInBatch();
        polaznikRepo.deleteAllInBatch();
        radionicaRepo.deleteAllInBatch();

        List<String> teme = List.of(
            "održivom razvoju", "recikliranju otpada", "digitalnoj sigurnosti",
            "umjetnoj inteligenciji", "informacijskoj pismenosti", "rodnoj ravnopravnosti",
            "građanskoj edukaciji", "mentalnom zdravlju", "pravu na privatnost", "programiranju mladih",
            "klimatskim promjenama", "zdravim životnim navikama", "volontiranju", "ekološkoj odgovornosti",
            "kritičkom mišljenju", "multikulturalizmu", "društvenoj pravdi", "zelenoj energiji"
        );

        List<String> konteksti = List.of(
            "u zajednici", "na radnom mjestu", "u školama", "za budućnost", "kod mladih",
            "u svakodnevici", "kroz kreativnost", "u digitalnom okruženju", "putem STEM pristupa",
            "za društvenu promjenu", "u civilnom sektoru", "u obrazovanju odraslih"
        );

        Set<String> kombinacije = new HashSet<>();
        while (kombinacije.size() < 10) {
            String tema = teme.get(random.nextInt(teme.size()));
            String kontekst = konteksti.get(random.nextInt(konteksti.size()));
            kombinacije.add("Radionica o " + tema + " " + kontekst);
        }

        List<Radionica> radionice = kombinacije.stream().map(naziv -> {
            Radionica r = new Radionica();
            r.setNaziv(naziv);
            r.setDatum(LocalDate.now().plusDays(random.nextInt(30)));
            return r;
        }).toList();

        radionice = radionicaRepo.saveAll(radionice);

        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String ime = faker.name().firstName();
            String prezime = faker.name().lastName();
            String cleanIme = removeDiacritics(ime);
            String cleanPrezime = removeDiacritics(prezime);

            String broj = random.nextBoolean() ? String.valueOf(random.nextInt(100)) : "";
            String domena = DOMENE.get(random.nextInt(DOMENE.size()));
            String email = (cleanIme + "." + cleanPrezime + broj + "@" + domena).toLowerCase();

            Polaznik p = new Polaznik();
            p.setIme(ime);
            p.setPrezime(prezime);
            p.setEmail(email);
            p.setGodinaRodenja(faker.number().numberBetween(1975, 2010));
            polaznici.add(p);
        }
        polaznici = polaznikRepo.saveAll(polaznici);

        List<Prisustvo> prisustva = new ArrayList<>();
        for (Radionica r : radionice) {
            for (Polaznik p : polaznici) {
                if (random.nextBoolean()) {
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
        return switch (random.nextInt(4)) {
            case 0 -> StatusPrisustva.PRISUTAN;
            case 1 -> StatusPrisustva.IZOSTAO;
            case 2 -> StatusPrisustva.NEPOZNATO;
            default -> StatusPrisustva.ODUSTAO;
        };
    }

    private String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replace("đ", "d").replace("Đ", "D");
    }
}
