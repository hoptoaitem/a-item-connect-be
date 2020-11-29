package com.aitem.connect.script;

import com.aitem.connect.model.Zip;
import com.aitem.connect.repository.ZipBulkRepository;
import com.aitem.connect.repository.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoadZip1 {

    private ZipBulkRepository zipBulkRepository;
    private ZipRepository zipRepository;

    public LoadZip1(
            @Autowired ZipBulkRepository zipBulkRepository,
            @Autowired ZipRepository zipRepository
    ) {
        this.zipBulkRepository = zipBulkRepository;
        this.zipRepository = zipRepository;
    }

    @Async("threadPoolTaskExecutor")
    public void loadZip() {

        List<Zip> zipList = new ArrayList<>();
        BufferedReader reader;
        try {

            reader = new BufferedReader(new FileReader(
                    "/tmp/zip.txt"));

            String line = reader.readLine();
            int count = 0;
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    String[] allItems = line.split(";");
                    if (allItems.length >= 5) {
                        Zip item = new Zip();

                        item.setZip(allItems[0]);
                        item.setCity(allItems[1]);
                        item.setState(allItems[2]);
                        item.setLon(allItems[4]);
                        item.setLat(allItems[3]);

                        zipList.add(item);

                        if (zipList.size() == 100) {
                            zipBulkRepository.batchInsert(zipList, 100);
                            zipList.clear();
                            System.out.println("Batch done " + count++);
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        zipBulkRepository.batchInsert(zipList, 100);
    }
}
