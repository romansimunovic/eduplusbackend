package com.edukatorplus.seeder;

import com.edukatorplus.model.*;
import com.edukatorplus.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

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

    @PostConstruct
    public void init() {
        // Brišemo sve stare podatke
        prisustvoRepo.deleteAllInBatch();
        polaznikRepo.deleteAllInBatch();
        radionicaRepo.deleteAllInBatch();

        // izrazi za nasumične radionice na hrvatskom radi relevantnosti i konteksta, faker ne može nazive na hrvatskom
        List<String> teme = List.of(
                "mentalnom zdravlju", "digitalnim vještinama", "ekološkoj svijesti",
                "društvenim inovacijama", "održivom razvoju", "sigurnosti na internetu",
                "rodnoj ravnopravnosti", "ljudskim pravima", "kritičkom mišljenju", "aktivizmu mladih"
        );

        List<String> ciljevi = List.of(
                "u obrazovanju", "za mlade", "na radnom mjestu", "u zajednici", "u školama",
                "u civilnom društvu", "kroz kreativnost", "putem tehnologije", "u svakodnevici", "za budućnost"
        );

        // Radionice
        List<Radionica> radionice = new ArrayList<>();
        int brojRadionica = 5 + random.nextInt(6); // 5–10 radionica

        for (int i = 0; i < brojRadionica; i++) {
            String tema = teme.get(random.nextInt(teme.size()));
            String cilj = ciljevi.get(random.nextInt(ciljevi.size()));
            String naziv = "Radionica o " + tema + " " + cilj;

            Radionica r = new Radionica();
            r.setNaziv(naziv);
            r.setDatum(LocalDate.now().plusDays(random.nextInt(30)));
            radionice.add(r);
        }
        radionice = radionicaRepo.saveAll(radionice);

        // Polaznici
        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String ime = faker.name().firstName();
            String prezime = faker.name().lastName();

            Polaznik p = new Polaznik();
            p.setIme(ime);
            p.setPrezime(prezime);
            p.setEmail((ime + "." + prezime + "@gmail.com").toLowerCase());
            p.setGodinaRodenja(faker.number().numberBetween(1975, 2010));
            polaznici.add(p);
        }
        polaznici = polaznikRepo.saveAll(polaznici);

        // Prisustva
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
        int pick = random.nextInt(4);
        return switch (pick) {
            case 0 -> StatusPrisustva.PRISUTAN;
            case 1 -> StatusPrisustva.IZOSTAO;
            case 2 -> StatusPrisustva.NEPOZNATO;
            default -> StatusPrisustva.ODUSTAO;
        };
    }
}
