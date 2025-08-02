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

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        // Briše stare podatke!
        prisustvoRepo.deleteAllInBatch();
        polaznikRepo.deleteAllInBatch();
        radionicaRepo.deleteAllInBatch();

        // Kreira radionice
        List<String> teme = Arrays.asList(
                "ljudskim pravima", "rodnoj ravnopravnosti", "prevenciji nasilja", "mentalnom zdravlju",
                "održivom razvoju", "pravima manjina", "digitalnoj sigurnosti", "inkluziji mladih",
                "zelenim politikama", "građanskom obrazovanju"
        );

        List<Radionica> radionice = new ArrayList<>();
        for (String tema : teme) {
            Radionica r = new Radionica();
            r.setNaziv("Radionica o " + tema);
            r.setDatum(LocalDate.now().minusDays(random.nextInt(30)));
            radionice.add(r);
        }
        radionice = radionicaRepo.saveAll(radionice);

        // Kreira polaznike
        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Polaznik p = new Polaznik();
            p.setIme(faker.name().firstName());
            p.setPrezime(faker.name().lastName());
            polaznici.add(p);
        }
        polaznici = polaznikRepo.saveAll(polaznici);

        // Generira prisustva
        List<Prisustvo> prisustva = new ArrayList<>();
        for (Radionica r : radionice) {
            for (Polaznik p : polaznici) {
                if (random.nextBoolean()) {
                    Prisustvo prisustvo = new Prisustvo();
                    prisustvo.setRadionica(r);
                    prisustvo.setPolaznik(p);
                    prisustvo.setStatus(randomStatus());
                    prisustva.add(prisustvo);
                }
            }
        }
        prisustvoRepo.saveAll(prisustva);
    }

    private StatusPrisustva randomStatus() {
        int pick = random.nextInt(4); // uključuje i ODUSTAO
        return switch (pick) {
            case 0 -> StatusPrisustva.PRISUTAN;
            case 1 -> StatusPrisustva.IZOSTAO;
            case 2 -> StatusPrisustva.NEPOZNATO;
            default -> StatusPrisustva.ODUSTAO;
        };
    }
}
