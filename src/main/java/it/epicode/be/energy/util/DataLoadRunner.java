package it.epicode.be.energy.util;

import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.opencsv.CSVReader;

import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.repository.ComuneRepository;
import it.epicode.be.energy.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataLoadRunner implements CommandLineRunner {

	@Autowired
	ComuneRepository comuneRepository;
	@Autowired
	ProvinciaRepository provinciaRepository;

	@Override
	public void run(String... args) throws Exception {
		log.info("*** Inizio caricamento dati ***");
		initProvincia();
		initComune();
		log.info("*** Fine caricamento dati ***");

	}

	private void initProvincia() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));) {
			String[] values = null;
			csvReader.readNext();
			while ((values = csvReader.readNext()) != null) {
				provinciaRepository.save(new Provincia(values[0], values[1], values[2]));
			}
		}

	}

	private void initComune() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
			String[] values = null;
			csvReader.readNext();
			Provincia provincia;
			Comune comune;
			while ((values = csvReader.readNext()) != null) {
				comune= new Comune(values[2], values[3]);
				provincia= new Provincia();
				provincia.setId(Long.valueOf(values[0]));
				comune.setProvincia(provincia);
				comuneRepository.save(comune);
			}
		}
	}
}
