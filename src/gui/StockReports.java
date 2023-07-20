/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import model.StockReportData;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author NIDULA
 */
public class StockReports extends javax.swing.JDialog {

    AdminHome ah;

    /**
     * Creates new form StockReports
     */
    public StockReports(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ImageIcon i = new ImageIcon("src/resources/logomain.png");
        Image x = i.getImage();
        setIconImage(x);
        this.ah = (AdminHome) parent;
    }

    public StockReports(java.awt.Frame parent, boolean modal, String stid, String productName) {
        super(parent, modal);
        initComponents();
        ImageIcon i = new ImageIcon("src/resources/logomain.png");
        Image x = i.getImage();
        setIconImage(x);
        jLabel14.setText(stid);
        jLabel15.setText(productName);
        this.ah = (AdminHome) parent;
    }

    public void selectedStockReport() {
        try {
            if (!jLabel14.getText().isBlank()) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);
                if (f.getSelectedFile() != null) {

                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet spreadsheet = workbook.createSheet("Stock - " + jLabel14.getText());
                    XSSFRow row;

                    Map< Integer, Object[]> empinfo = new TreeMap< Integer, Object[]>();
                    empinfo.put(1, new Object[]{
                        "", "Stock id - ", jLabel14.getText()});
                    empinfo.put(2, new Object[]{
                        "", "Product Name - ", jLabel15.getText()});
                    empinfo.put(3, new Object[]{
                        ""});
                    empinfo.put(4, new Object[]{
                        "#", "Description", "Added", "Sold", "Total"});

                    double totalQty = 0;
                    double newQty = 0;
                    double totalAdded = 0;
                    double totalSold = 0;
                    int cou = 5;
                    ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + jLabel14.getText() + "' ORDER BY `stock_changes`.`date_time` DESC;");
                    while (rs.next()) {
                        if (totalQty == 0) {
                            totalQty = Double.parseDouble(rs.getString("stock.qty"));
                            newQty = totalQty;
                        }
                        Vector v = new Vector();

                        if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                            //Qty unit Id
                            ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                            quirs.next();
                            String mul = quirs.getString("multiplication");
                            //Qty Unit Id

                            empinfo.put(cou, new Object[]{
                                String.valueOf(cou - 4), rs.getString("stock_changes.description"), "", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)});
                            newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                            totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")));

                        } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                            //Qty unit Id
                            ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                            quirs.next();
                            String mul = quirs.getString("multiplication");
                            //Qty Unit Id

                            empinfo.put(cou, new Object[]{
                                String.valueOf(cou - 4), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), "", String.valueOf(newQty)});
                            newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                            totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")));
                        }
                        cou++;
                    }

                    empinfo.put(cou, new Object[]{
                        "TOTAL ADDED -", String.valueOf(totalAdded)});
                    cou++;
                    empinfo.put(cou, new Object[]{
                        "TOTAL SOLD -", String.valueOf(totalSold)});

                    Set< Integer> keyid = empinfo.keySet();
                    int rowid = 1;

                    for (int key : keyid) {
                        row = spreadsheet.createRow(rowid++);
                        Object[] objectArr = empinfo.get(key);
                        int cellid = 0;

                        for (Object obj : objectArr) {

                            Cell cell = row.createCell(cellid++);
                            cell.setCellValue((String) obj);
                            CellStyle cellStylewrap = cell.getCellStyle();
                            cellStylewrap.setWrapText(true);

                            if (key > 3) {
                                CellRangeAddress region = new CellRangeAddress(rowid - 1, rowid - 1, cellid - 1, cellid - 1);
                                RegionUtil.setBorderTop(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderBottom(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderLeft(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderRight(BorderStyle.THIN, region, spreadsheet);
                            }

                            if (rowid == 5) {
                                CellStyle cellStyle = cell.getCellStyle();
                                if (cellStyle == null) {
                                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                }
                                cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                font.setFontName("Courier New");
                                font.setBold(true);
                                font.setUnderline(org.apache.poi.ss.usermodel.Font.U_NONE);
                                font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                                cellStyle.setFont(font);
                                cell.setCellStyle(cellStyle);
                                cellStyle = null;
                            } else {
                                CellStyle cellStyle = cell.getCellStyle();
                                if (cellStyle == null) {
                                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                }
                                cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                cellStyle.setFillPattern(FillPatternType.NO_FILL);
                                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                font.setFontName("Calibri");
                                cellStyle.setFont(font);
                                font.setBold(false);
                                font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                                cell.setCellStyle(cellStyle);
                                cellStyle = null;
                            }

                        }

                    }

                    //Write the workbook in file system
                    FileOutputStream out = new FileOutputStream(
                            new File(f.getSelectedFile().toString() + "/stock-" + jLabel14.getText() + ".xlsx"));

                    workbook.write(out);
                    out.close();
                    System.out.println("Writesheet.xlsx written successfully");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectedStocks() {
        try {
            if (jTable1.getRowCount() != 0) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);
                if (f.getSelectedFile() != null) {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    String name = "";
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        name += jTable1.getValueAt(i, 0).toString() + ",";
                        XSSFSheet spreadsheet = workbook.createSheet("Stock - " + jTable1.getValueAt(i, 0).toString());
                        XSSFRow row;

                        Map< Integer, Object[]> empinfo = new TreeMap< Integer, Object[]>();
                        empinfo.put(1, new Object[]{
                            "", "Stock id - ", jTable1.getValueAt(i, 0).toString()});
                        empinfo.put(2, new Object[]{
                            "", "Product Name - ", jTable1.getValueAt(i, 1).toString()});
                        empinfo.put(3, new Object[]{
                            ""});
                        empinfo.put(4, new Object[]{
                            "#", "Description", "Added", "Sold", "Total"});

                        double totalQty = 0;
                        double newQty = 0;
                        double totalAdded = 0;
                        double totalSold = 0;
                        int cou = 5;
                        ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + jTable1.getValueAt(i, 0).toString() + "' ORDER BY `stock_changes`.`date_time` DESC;");
                        while (rs.next()) {
                            if (totalQty == 0) {
                                totalQty = Double.parseDouble(rs.getString("stock.qty"));
                                newQty = totalQty;
                            }

                            if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                                //Qty unit Id
                                ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                                quirs.next();
                                String mul = quirs.getString("multiplication");
                                //Qty Unit Id

                                empinfo.put(cou, new Object[]{
                                    String.valueOf(cou - 4), rs.getString("stock_changes.description"), "", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)});
                                newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                                totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));

                            } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                                //Qty unit Id
                                ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                                quirs.next();
                                String mul = quirs.getString("multiplication");
                                //Qty Unit Id

                                empinfo.put(cou, new Object[]{
                                    String.valueOf(cou - 4), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), "", String.valueOf(newQty)});
                                newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                                totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                            }
                            cou++;
                        }
                        cou++;
                        empinfo.put(cou, new Object[]{
                            "", "TOTAL ADDED -", String.valueOf(totalAdded)});
                        cou++;
                        empinfo.put(cou, new Object[]{
                            "", "TOTAL SOLD -", String.valueOf(totalSold)});

                        Set< Integer> keyid = empinfo.keySet();
                        int rowid = 1;

                        for (int key : keyid) {
                            row = spreadsheet.createRow(rowid++);
                            Object[] objectArr = empinfo.get(key);
                            int cellid = 0;

                            for (Object obj : objectArr) {

                                Cell cell = row.createCell(cellid++);
                                cell.setCellValue((String) obj);
                                CellStyle cellStylewrap = cell.getCellStyle();
                                cellStylewrap.setWrapText(true);
                                if (key > 3) {
                                    CellRangeAddress region = new CellRangeAddress(rowid - 1, rowid - 1, cellid - 1, cellid - 1);
                                    RegionUtil.setBorderTop(BorderStyle.THIN, region, spreadsheet);
                                    RegionUtil.setBorderBottom(BorderStyle.THIN, region, spreadsheet);
                                    RegionUtil.setBorderLeft(BorderStyle.THIN, region, spreadsheet);
                                    RegionUtil.setBorderRight(BorderStyle.THIN, region, spreadsheet);
                                }

                                if (rowid == 5) {
                                    CellStyle cellStyle = cell.getCellStyle();
                                    if (cellStyle == null) {
                                        cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                    }
                                    cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                                    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                    font.setFontName("Courier New");
                                    font.setBold(true);
                                    font.setUnderline(org.apache.poi.ss.usermodel.Font.U_NONE);
                                    font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                                    cellStyle.setFont(font);
                                    cell.setCellStyle(cellStyle);
                                    cellStyle = null;
                                } else {
                                    CellStyle cellStyle = cell.getCellStyle();
                                    if (cellStyle == null) {
                                        cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                    }
                                    cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                    cellStyle.setFillPattern(FillPatternType.NO_FILL);
                                    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                    font.setFontName("Calibri");
                                    cellStyle.setFont(font);
                                    font.setBold(false);
                                    font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                                    cell.setCellStyle(cellStyle);
                                    cellStyle = null;
                                }

                            }
//                        row = null;
                        }

                    }
                    //Write the workbook in file system
                    FileOutputStream out = new FileOutputStream(
                            new File(f.getSelectedFile().toString() + "/stock-" + name + ".xlsx"));

                    workbook.write(out);
                    out.close();
                    System.out.println("Writesheet.xlsx written successfully");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select Stocks", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void allStocks() {
        try {
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showSaveDialog(null);
            if (f.getSelectedFile() != null) {
                ResultSet srs = MySQL.search("SELECT * FROM `stock` INNER JOIN `product` ON `product`.`id` = `stock`.`product_id` ");
                XSSFWorkbook workbook = new XSSFWorkbook();
                while (srs.next()) {

                    XSSFSheet spreadsheet = workbook.createSheet("Stock - " + srs.getString("stock.id"));
                    XSSFRow row;

                    Map< Integer, Object[]> empinfo = new TreeMap< Integer, Object[]>();
                    empinfo.put(1, new Object[]{
                        "", "Stock id - ", srs.getString("stock.id")});
                    empinfo.put(2, new Object[]{
                        "", "Product Name - ", srs.getString("product.name")});
                    empinfo.put(3, new Object[]{
                        ""});
                    empinfo.put(4, new Object[]{
                        "#", "Description", "Added", "Sold", "Total"});

                    double totalQty = 0;
                    double newQty = 0;
                    double totalAdded = 0;
                    double totalSold = 0;
                    int cou = 5;
                    ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + srs.getString("stock.id") + "' ORDER BY `stock_changes`.`date_time` DESC;");
                    while (rs.next()) {
                        if (totalQty == 0) {
                            totalQty = Double.parseDouble(rs.getString("stock.qty"));
                            newQty = totalQty;
                        }

                        if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                            //Qty unit Id
                            ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                            quirs.next();
                            String mul = quirs.getString("multiplication");
                            //Qty Unit Id

                            empinfo.put(cou, new Object[]{
                                String.valueOf(cou - 4), rs.getString("stock_changes.description"), "", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)});
                            newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                            totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));

                        } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                            //Qty unit Id
                            ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                            quirs.next();
                            String mul = quirs.getString("multiplication");
                            //Qty Unit Id

                            empinfo.put(cou, new Object[]{
                                String.valueOf(cou - 4), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), "", String.valueOf(newQty)});
                            newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                            totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                        }
                        cou++;
                    }
                    cou++;
                    empinfo.put(cou, new Object[]{
                        "", "TOTAL ADDED -", String.valueOf(totalAdded)});
                    cou++;
                    empinfo.put(cou, new Object[]{
                        "", "TOTAL SOLD -", String.valueOf(totalSold)});

                    Set< Integer> keyid = empinfo.keySet();
                    int rowid = 1;

                    for (int key : keyid) {
                        row = spreadsheet.createRow(rowid++);
                        Object[] objectArr = empinfo.get(key);
                        int cellid = 0;

                        for (Object obj : objectArr) {

                            Cell cell = row.createCell(cellid++);
                            cell.setCellValue((String) obj);
                            CellStyle cellStylewrap = cell.getCellStyle();
                            cellStylewrap.setWrapText(true);
                            if (key > 3) {
                                CellRangeAddress region = new CellRangeAddress(rowid - 1, rowid - 1, cellid - 1, cellid - 1);
                                RegionUtil.setBorderTop(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderBottom(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderLeft(BorderStyle.THIN, region, spreadsheet);
                                RegionUtil.setBorderRight(BorderStyle.THIN, region, spreadsheet);
                            }

                            if (rowid == 5) {
                                CellStyle cellStyle = cell.getCellStyle();
                                if (cellStyle == null) {
                                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                }
                                cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                font.setFontName("Courier New");
                                font.setBold(true);
                                font.setUnderline(org.apache.poi.ss.usermodel.Font.U_NONE);
                                font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                                cellStyle.setFont(font);
                                cell.setCellStyle(cellStyle);
                                cellStyle = null;
                            } else {
                                CellStyle cellStyle = cell.getCellStyle();
                                if (cellStyle == null) {
                                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                                }
                                cellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
                                cellStyle.setFillPattern(FillPatternType.NO_FILL);
                                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                                font.setFontName("Calibri");
                                cellStyle.setFont(font);
                                font.setBold(false);
                                font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                                cell.setCellStyle(cellStyle);
                                cellStyle = null;
                            }

                        }
//                        row = null;
                    }

                }
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(
                        new File(f.getSelectedFile().toString() + "/stock-All.xlsx"));

                workbook.write(out);
                out.close();
                System.out.println("Writesheet.xlsx written successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectedStockpdf() {
        try {
            Vector vReport = new Vector();

            double totalQty = 0;
            double newQty = 0;
            double totalAdded = 0;
            double totalSold = 0;
            int cou = 1;
            ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + jLabel14.getText() + "' ORDER BY `stock_changes`.`date_time` DESC;");
            while (rs.next()) {
                if (totalQty == 0) {
                    totalQty = Double.parseDouble(rs.getString("stock.qty"));
                    newQty = totalQty;
                }

                if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                    //Qty unit Id
                    ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                    quirs.next();
                    String mul = quirs.getString("multiplication");
                    //Qty Unit Id
                    vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), " ", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)));

                    newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                    totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));

                } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                    //Qty unit Id
                    ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                    quirs.next();
                    String mul = quirs.getString("multiplication");
                    //Qty Unit Id
                    vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), " ", String.valueOf(newQty)));

                    newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                    totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                }
                cou++;
            }

            JRBeanCollectionDataSource datasourse = new JRBeanCollectionDataSource(vReport);
            HashMap parameters = new HashMap();
            parameters.put("stockid", jLabel14.getText());
            parameters.put("productname", jLabel15.getText());
            parameters.put("added", String.valueOf(totalAdded));
            parameters.put("sold", String.valueOf(totalSold));

            parameters.put("tabledatasourse", datasourse);

            String filepath = "src//reports//stockreports.jasper";
//            JasperReport jr = JasperCompileManager.compileReport(filepath);
            JasperPrint jp = JasperFillManager.fillReport(filepath, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectedStockpdfs() {
        try {
            String stids = "";
            Vector vReport = new Vector();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double totalQty = 0;
                double newQty = 0;
                double totalAdded = 0;
                double totalSold = 0;
                int cou = 1;
                stids += jTable1.getValueAt(i, 0).toString() + ", ";
                ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + jTable1.getValueAt(i, 0).toString() + "' ORDER BY `stock_changes`.`date_time` DESC;");
                vReport.add(new StockReportData("Stock Id - ", jTable1.getValueAt(i, 0).toString(), " ", " ", " "));
                vReport.add(new StockReportData("Product - ", jTable1.getValueAt(i, 1).toString(), " ", " ", " "));
                while (rs.next()) {
                    if (totalQty == 0) {
                        totalQty = Double.parseDouble(rs.getString("stock.qty"));
                        newQty = totalQty;
                    }

                    if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                        //Qty unit Id
                        ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                        quirs.next();
                        String mul = quirs.getString("multiplication");
                        //Qty Unit Id
                        vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), " ", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)));

                        newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                        totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));

                    } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                        //Qty unit Id
                        ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                        quirs.next();
                        String mul = quirs.getString("multiplication");
                        //Qty Unit Id
                        vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), " ", String.valueOf(newQty)));

                        newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                        totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                    }
                    cou++;
                }
                vReport.add(new StockReportData(" ", "Added - ", String.valueOf(totalAdded), " ", " "));
                vReport.add(new StockReportData(" ", "Sold - ", String.valueOf(totalSold), " ", " "));
                vReport.add(new StockReportData(" ", " ", " ", " ", " "));
            }
            JRBeanCollectionDataSource datasourse = new JRBeanCollectionDataSource(vReport);
            HashMap parameters = new HashMap();
            parameters.put("stockid", stids);

            parameters.put("tabledatasourse", datasourse);

            String filepath = "src//reports//stockReport2.jasper";
//            JasperReport jr = JasperCompileManager.compileReport(filepath);
            JasperPrint jp = JasperFillManager.fillReport(filepath, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectedStockpdfall() {
        try {
            String stids = "";
            Vector vReport = new Vector();
            ResultSet srs = MySQL.search("SELECT * FROM `stock` INNER JOIN `product` ON `product`.`id` = `stock`.`product_id` ");
            while (srs.next()) {
                double totalQty = 0;
                double newQty = 0;
                double totalAdded = 0;
                double totalSold = 0;
                int cou = 1;
                stids += srs.getString("stock.id") + ", ";
                ResultSet rs = MySQL.search("SELECT * FROM `stock_changes` INNER JOIN `stock` ON `stock`.`id` = `stock_changes`.`stock_id` INNER JOIN `qty_units` ON `qty_units`.`id` = `stock_changes`.`qty_units_id` WHERE `stock`.`id` = '" + srs.getString("stock.id") + "' ORDER BY `stock_changes`.`date_time` DESC;");
                vReport.add(new StockReportData("Stock Id - ", srs.getString("stock.id"), " ", " ", " "));
                vReport.add(new StockReportData("Product - ", srs.getString("product.name"), " ", " ", " "));
                while (rs.next()) {
                    if (totalQty == 0) {
                        totalQty = Double.parseDouble(rs.getString("stock.qty"));
                        newQty = totalQty;
                    }

                    if (Double.parseDouble(rs.getString("stock_changes.qty")) < 0) {
                        //Qty unit Id
                        ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                        quirs.next();
                        String mul = quirs.getString("multiplication");
                        //Qty Unit Id
                        vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), " ", rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), String.valueOf(newQty)));

                        newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                        totalSold = totalSold - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));

                    } else if (Double.parseDouble(rs.getString("stock_changes.qty")) > 0) {
                        //Qty unit Id
                        ResultSet quirs = MySQL.search("SELECT * FROM `qty_units` WHERE `id` = '" + rs.getString("stock_changes.qty_units_id") + "';");
                        quirs.next();
                        String mul = quirs.getString("multiplication");
                        //Qty Unit Id
                        vReport.add(new StockReportData(String.valueOf(cou), rs.getString("stock_changes.description"), rs.getString("stock_changes.qty") + " " + rs.getString("qty_units.name"), " ", String.valueOf(newQty)));

                        newQty = newQty - (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                        totalAdded = totalAdded + (Double.parseDouble(rs.getString("stock_changes.qty")) * Double.parseDouble(mul));
                    }
                    cou++;
                }
                vReport.add(new StockReportData(" ", "Added - ", String.valueOf(totalAdded), " ", " "));
                vReport.add(new StockReportData(" ", "Sold - ", String.valueOf(totalSold), " ", " "));
                vReport.add(new StockReportData(" ", " ", " ", " ", " "));
            }
            JRBeanCollectionDataSource datasourse = new JRBeanCollectionDataSource(vReport);
            HashMap parameters = new HashMap();
            parameters.put("stockid", stids);

            parameters.put("tabledatasourse", datasourse);

            String filepath = "src//reports//stockReport2.jasper";
//            JasperReport jr = JasperCompileManager.compileReport(filepath);
            JasperPrint jp = JasperFillManager.fillReport(filepath, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stock Reports");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Stock Id");

        jLabel14.setBackground(new java.awt.Color(102, 102, 102));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setToolTipText("Stock Id");
        jLabel14.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Product Name");

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setToolTipText("Product Name");
        jLabel15.setOpaque(true);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("Get Report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Selected Stock", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id", "Product Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Selected Stocks");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setText("Get Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setText("Select");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jTabbedPane1.addTab("Selected Stocks", jPanel4);

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Get All Report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("All Stocks", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        SelectStock ss = new SelectStock(ah, false, this);
        ss.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ReportType rt = new ReportType(ah, false, this, 1);
        rt.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ReportType rt = new ReportType(ah, false, this, 2);
        rt.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ReportType rt = new ReportType(ah, false, this, 3);
        rt.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StockReports.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockReports.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockReports.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockReports.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                StockReports dialog = new StockReports(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
