package com.compare.xls;

import com.compare.bvo.UserBvo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytq on 2014/9/16.
 */
public class XlsxReadAndWrite {
    public static Map<String, List<UserBvo>> readXlsx(String filePath) throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
        Map<String, List<UserBvo>> map = new HashMap<String, List<UserBvo>>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            List<UserBvo> userBvos = new ArrayList<UserBvo>();
            if (xssfSheet == null) {
                continue;
            }

            // 循环行Row
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }
                if (rowNum > 1) {
                    UserBvo userBvo = new UserBvo();

                    // 循环列Cell
                    for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
                        XSSFCell xssfCell = xssfRow.getCell(cellNum);
                        if (xssfCell == null) {
                            continue;
                        }
                        if (cellNum == 0) {
                            userBvo.setId(getValue(xssfCell));
                        }
                        if (cellNum == 1) {
                            userBvo.setUserName(getValue(xssfCell));
                        }
                        if (cellNum == 2) {
                            userBvo.setIdCard(getValue(xssfCell));
                        }
                        if (cellNum == 3) {
                            userBvo.setDepartment(getValue(xssfCell));
                        }
                        if (cellNum == 4) {
                            userBvo.setWaitExamTime(getValue(xssfCell));
                        }
                        if (cellNum == 5) {
                            userBvo.setAttendTimes(getValue(xssfCell));
                        }
                        if (cellNum == 6) {
                            userBvo.setLateExamTime(getValue(xssfCell));
                        }
                        if (cellNum == 7) {
                            userBvo.setTel(getValue(xssfCell));
                        }
                        System.out.print("   " + getValue(xssfCell));
                    }
                    userBvos.add(userBvo);
                    System.out.println();
                }
            }
            map.put(xssfSheet.getSheetName(), userBvos);
        }
        return map;
    }

    @SuppressWarnings("static-access")
    public static String getValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }
}
