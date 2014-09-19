package com.compare.xls;

import com.compare.bvo.UserBvo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytq on 2014/9/16.
 */
public class XlsReadAndWrite {
    public static Map<String, List<UserBvo>> readXls(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        Map<String, List<UserBvo>> map = new HashMap<String, List<UserBvo>>();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            List<UserBvo> userBvos = new ArrayList<UserBvo>();
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }

            // 循环行Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                if (rowNum > 1) {
                    UserBvo userBvo = new UserBvo();
                    // 循环列Cell
                    for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
                        HSSFCell hssfCell = hssfRow.getCell(cellNum);
                        if (hssfCell == null) {
                            continue;
                        }
                        if (cellNum == 0) {
                            userBvo.setId(getValue(hssfCell));
                        }
                        if (cellNum == 1) {
                            userBvo.setUserName(getValue(hssfCell));
                        }
                        if (cellNum == 2) {
                            userBvo.setIdCard(getValue(hssfCell));
                        }
                        if (cellNum == 3) {
                            userBvo.setDepartment(getValue(hssfCell));
                        }
                        if (cellNum == 4) {
                            userBvo.setWaitExamTime(getValue(hssfCell));
                        }
                        if (cellNum == 5) {
                            userBvo.setAttendTimes(getValue(hssfCell));
                        }
                        if (cellNum == 6) {
                            userBvo.setLateExamTime(getValue(hssfCell));
                        }
                        if (cellNum == 7) {
                            userBvo.setTel(getValue(hssfCell));
                        }
                        System.out.print("    " + getValue(hssfCell));
                    }
                    System.out.println();
                    userBvos.add(userBvo);
                }
            }
            map.put(hssfSheet.getSheetName(), userBvos);
        }
        is.close();
        return map;
    }

    @SuppressWarnings("static-access")
    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue()).replace(".0", "");
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
