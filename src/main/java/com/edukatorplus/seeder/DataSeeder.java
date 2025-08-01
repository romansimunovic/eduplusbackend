package com.edukatorplus.seeder;

import javax.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edukatorplus.model.Polaznik;
import com.edukatorplus.model.Radionica;
import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.repository.PolaznikRepository;
import com.edukatorplus.repository.RadionicaRepository;
import com.edukatorplus.repository.PrisustvoRepository;

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

    @PostConstruct
    public void init() {

        
        prisustvoRepo.deleteAll();
        polaznikRepo.deleteAll();
        radionicaRepo.deleteAll();

        //  10 radionica s temama iz neprofitnog sektora
        List<String> teme = Arrays.asList(
                "ljudskim pravima",
                "rodnoj ravnopravnosti",
                "prevenciji nasilja",
                "mentalnom zdravlju",
                "održivom razvoju",
                "pravima manjina",
                "digitalnoj sigurnosti",
                "inkluziji mladih",
                "zelenim politikama",
                "građanskom obrazovanju"
        );

        List<Radionica> radionice = new ArrayList<>();
        for (int i = 0; i < teme.size(); i++) {
            Radionica r = new Radionica();
            r.setNaziv("Radionica o " + teme.get(i));
            r.setDatum(faker.date().birthday().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate());
            radionice.add(radionicaRepo.save(r));
        }

        // Dodajemo 30 polaznika
        List<Polaznik> polaznici = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Polaznik p = new Polaznik();
            p.setIme(faker.name().firstName());
            p.setPrezime(faker.name().lastName());
            polaznici.add(polaznikRepo.save(p));
        }

       
        for (Radionica r : radionice) {
            for (Polaznik p : polaznici) {
                if (faker.random().nextBoolean()) {
                    Prisustvo prisustvo = new Prisustvo();
                    prisustvo.setRadionica(r);
                    prisustvo.setPolaznik(p);
                    prisustvo.setStatus(randomStatus());
                    prisustvoRepo.save(prisustvo);
                }
            }
        }
    }

    private StatusPrisustva randomStatus() {
        int pick = new Random().nextInt(3);
        return switch (pick) {
            case 0 -> StatusPrisustva.PRISUTAN;
            case 1 -> StatusPrisustva.IZOSTAO;
            default -> StatusPrisustva.NEPOZNATO;
        };
    }
}
