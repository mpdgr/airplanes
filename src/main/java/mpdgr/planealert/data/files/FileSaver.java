package mpdgr.planealert.data.files;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import mpdgr.planealert.domain.model.Aircraft;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.ports.data.AircraftRepository;
import mpdgr.planealert.domain.ports.data.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

@Component
public class FileSaver {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    AircraftRepository aircraftRepository;

    @PostConstruct
    public void writeCsvFromBean() throws Exception {
        String pathAc = "C:\\java\\plane_alert\\planealert\\src\\main\\resources\\static\\aircrafts.csv";
        String pathFl = "C:\\java\\plane_alert\\planealert\\src\\main\\resources\\static\\flights.csv";

        Writer writerAc = new FileWriter(pathAc);
        Writer writerFl = new FileWriter(pathFl);

        StatefulBeanToCsv<Aircraft> sbcAc = new StatefulBeanToCsvBuilder<Aircraft>(writerAc)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        StatefulBeanToCsv<Flight> sbcFl = new StatefulBeanToCsvBuilder<Flight>(writerFl)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        List<Flight> flightList = flightRepository.getAll();
        List<Aircraft> aircraftList = aircraftRepository.getAll();

        sbcFl.write(flightList);
        sbcAc.write(aircraftList);

        writerAc.close();
        writerFl.close();
        }
}
