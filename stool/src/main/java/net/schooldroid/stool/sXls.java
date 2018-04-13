package net.schooldroid.stool;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import net.schooldroid.stool.Utils.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class sXls {

    private Context context;

    public sXls(Context context) {
        this.context = context;
    }

    public ArrayList<String> baca (String path){

        String extension = FileUtils.getExtension(path);

        if (!extension.equals(".xlsx")) {
            //Toast.makeText(context, "Masukkan file berekstensi xlsx", Toast.LENGTH_LONG).show();
            return null;
        }

        try{
            File file = new File(path);
            FileInputStream myInput = new FileInputStream(file);

            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);

            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            Iterator rowIter = mySheet.rowIterator();

            ArrayList<String> listHasil = new ArrayList<>();

            DataFormatter fmt = new DataFormatter();

            while(rowIter.hasNext()){

                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();

                String result = "";
                while(cellIter.hasNext()){
                    XSSFCell myCell = (XSSFCell) cellIter.next();

                    result = result + Character.toString((char) 240) + fmt.formatCellValue(myCell).trim();
                }
                result = result.substring(1);
                Log.d("HASIL",result);
                listHasil.add(result);
            }

            return listHasil;
        }

        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(context, "Gagal Import", Toast.LENGTH_LONG).show();
            return null;
        }
    }


    public void write (String fileName, String sheetName, ArrayList<Map<String,String>> dataArrayList) {

        if (dataArrayList!=null && !dataArrayList.isEmpty()) {

            Workbook workbook = new HSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet(sheetName);

            int rowNum = 0;

            for (Map<String, String> map : dataArrayList) {

                Row row = sheet.createRow(rowNum++);

                for (int i = 1; i <= map.size(); i++) {
                    Log.d("" + i, map.get(String.valueOf(i)));

                    Cell cell = row.createCell(i);
                    cell.setCellValue(map.get(String.valueOf(i)));

                }

                //sheet.autoSizeColumn(rowNum);
            }

            File filepath = Environment.getExternalStorageDirectory();

            // Create a new folder in SD Card
            File dir = new File(filepath.getPath()
                    + "/SCHOOLDROID/XLS");
            dir.mkdir();

            // Create a name for the file
            File file = new File(dir, fileName+".xls");

            FileOutputStream fileOut;

            try {
                fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
                workbook.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }



        }
    }

}
