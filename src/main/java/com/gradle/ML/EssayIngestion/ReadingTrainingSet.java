package com.gradle.ML.EssayIngestion;

import com.google.common.base.Utf8;
import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadingTrainingSet {

    private static String filePath = "/Users/zhanshu/workspace/WebDemo/TrainingEssay/training_set_rel3.xlsx";

    public ImmutableList<Essay> readingTrainingSet() {

        List<Essay> essays = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            rows.next(); // first line is the head

            while (rows.hasNext()) {
                Row nextRow = rows.next();

                Essay essay = Essay.builder()
                    .essayId((long) nextRow.getCell(0).getNumericCellValue())
                    .essaySet((int) nextRow.getCell(1).getNumericCellValue())
                    .essayBody(nextRow.getCell(2).getStringCellValue())
                    .rater1Domain1(nextRow.getCell(3) != null ? (int) nextRow.getCell(3).getNumericCellValue() : 0)
                    .rater2Domain1(nextRow.getCell(4) != null ? (int) nextRow.getCell(4).getNumericCellValue() : 0)
                    .rater3Domain1(nextRow.getCell(5) != null ? (int) nextRow.getCell(5).getNumericCellValue() : 0)
                    .domain1Score(nextRow.getCell(6) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Domain2(nextRow.getCell(7) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Domain2(nextRow.getCell(8) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .domain2Score(nextRow.getCell(9) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait1(nextRow.getCell(10) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait2(nextRow.getCell(11) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait3(nextRow.getCell(12) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait4(nextRow.getCell(13) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait5(nextRow.getCell(14) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater1Trait6(nextRow.getCell(15) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait1(nextRow.getCell(16) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait2(nextRow.getCell(17) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait3(nextRow.getCell(18) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait4(nextRow.getCell(19) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait5(nextRow.getCell(20) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater2Trait6(nextRow.getCell(21) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait1(nextRow.getCell(22) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait2(nextRow.getCell(23) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait3(nextRow.getCell(24) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait4(nextRow.getCell(25) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait5(nextRow.getCell(26) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .rater3Trait6(nextRow.getCell(27) != null ? (int) nextRow.getCell(6).getNumericCellValue() : 0)
                    .build();

                essays.add(essay);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ImmutableList.copyOf(essays);
    }
}
