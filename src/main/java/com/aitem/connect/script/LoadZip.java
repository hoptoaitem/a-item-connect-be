package com.aitem.connect.script;

import com.aitem.connect.model.Zip;
import com.aitem.connect.repository.ZipBulkRepository;
import com.aitem.connect.repository.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoadZip {

    private ZipBulkRepository zipBulkRepository;
    private ZipRepository zipRepository;

    private LoadZip(
            @Autowired ZipBulkRepository zipBulkRepository,
            @Autowired ZipRepository zipRepository
    ) {
        this.zipBulkRepository = zipBulkRepository;
        this.zipRepository = zipRepository;
/*
        try {
            loadZip();
        } catch (IOException e) {
            e.printStackTrace();
        }

 */
    }

    public void loadZip() throws IOException {

        List<Zip> zipList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/Users/achyutneupane/" +
                            "Downloads/a-item-connect-be" +
                            "/src/main/resources/sql_queries/test.txt"));
            String line = reader.readLine();
            int count =0;
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    String[] allItems = line.split(";");
                    if (allItems.length >= 5) {
                        //85743;Tucson;AZ;32.335122;-111.14888;-7;0;32.335122,-111.14888
                        /*
                        writeToFile(
                                String.format(
                                        "insert into zip (zip, city, state, lon ,lat)" +
                                                " values ('%s','%s','%s','%s','%s');",
                                        allItems[0],
                                        allItems[1],
                                        allItems[2],
                                        allItems[3],
                                        allItems[4]
                                ));
                        writeToFile("\n");
                        writeToFile("commit;");
                         */

                        Zip item = new Zip();

                        item.setZip(allItems[0]);
                        item.setCity(allItems[1]);
                        item.setState(allItems[2]);
                        item.setLon(allItems[3]);
                        item.setLat(allItems[4]);

                        zipList.add(item);

                        if(zipList.size() == 100){
                            zipBulkRepository.batchInsert(zipList, 100);
                            zipList.clear();
                            System.out.println("Batch done "+count++);
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

    /*
    private  static void writeToFile(String output){
        String str = "World";
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("/Users/achyutneupane/" +
                    "Downloads/a-item-connect-be" +
                    "/src/main/resources/sql_queries/output.txt", true));
            writer.append(output);
            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public static void main(String[] args) {
        //loadZip();
    }
}
