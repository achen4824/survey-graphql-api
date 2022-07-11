package org.technology.consilium;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.technology.consilium.data.model.Survey;

import javax.xml.validation.Validator;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

//    @Autowired
//    private Validator validator;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        if(args.length == 1 && args[0] instanceof String) {
//            if(Files.exists(Path.of(args[0]))) {
//                log.info("Reading from file: " + args[0]);
//                CSVReader csvReader = new CSVReaderBuilder(new FileReader(args[0])).withSkipLines(3).build();
//
//                List<String[]> fileContents = csvReader.readAll();
//
//            }else{
//                log.error("File not found at: " + args[0] + " continuing...");
//            }
//        }
    }

//    public Survey parseRowToObject(String[]) {
//
//    }
}
