package com.sgai.vbp.util.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.core.env.Environment;

import com.sgai.vbp.log.Logger;
import com.sgai.vbp.util.AssertUtil;

/**
 * excel导出工具类
 * 
 * @author mrh
 */
public class ExcelExportUtil {
	private static final Logger LOGGER = Logger.getLogger(ExcelExportUtil.class);
	public final static String XLSX = ".xlsx";
	public final static String ZIP = ".zip";

	private final static String ENCODING_UTF_8 = "UTF-8";

	private Workbook workbook = null;
	private static ComparatorRow comparatorRow = null;
	private static ComparatorCell comparatorCell = null;
	private Map<String, Integer> shardStringsIndexMap = null;
	private List<String> shardStringsList = null;
	private Map<String, List<File>> xmlFileMap = null;

	
	private static final String FILE_KEY = "env.file.download.path";
	
	private static final String FILE_PATH_DEFUALT="/home/deploy/file/download/";
	
	private String basePath;
    private Environment env;
	/**
	 * 私有构造函数
	 */
	private ExcelExportUtil(Environment env) {
		this.env = env;
		comparatorRow = new ComparatorRow();
		comparatorCell = new ComparatorCell();
		
		shardStringsIndexMap = new HashMap<String, Integer>();
		shardStringsList = new ArrayList<String>();
	}

	/**
	 * 取得产生excel工具类实例
	 * 
	 * @return
	 */
	public static ExcelExportUtil getInstance(Environment env) {
		return new ExcelExportUtil(env);
	}
	
	/**
	 * 组装excel文件数据
	 * @param sheetIndex 内部sheet索引
	 * @param dataScope
	 * @param metaData
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public ExcelExportUtil populateData(int sheetIndex, final DataScope dataScope, List<MetaData> metaData, List<Map<String, Object>> datas)throws Exception {
		Sheet sheet = this.workbook.getSheetList().get(sheetIndex);
		this.populateData(sheet, dataScope, metaData, datas);
		return this;
	}
	/**
	 * 组装excel文件数据
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param sql
	 *            查询SQL语句
	 * @param dataScope
	 *            数据范围，标题加数据或数据
	 * @throws Exception
	 */
	public void populateData(final Sheet sheet, final DataScope dataScope, List<MetaData> metaData, List<Map<String, Object>> datas)throws Exception {
		try {
			if (dataScope == DataScope.TITLE_DATA) {
				this.addTitle(sheet, metaData, null);
			}
			this.populateData(sheet, metaData, datas);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * 只填充数据
	 * @param sheet
	 * @param metaData
	 * @param datas
	 * @throws Exception
	 */
	public ExcelExportUtil populateData(int sheetIndex, List<MetaData> metaData, List<Map<String, Object>> datas)throws Exception  {
		Sheet sheet = workbook.getSheetList().get(sheetIndex);
		this.populateData(sheet, metaData, datas);
		return this;
	}
	
	/**
	 * 组装excel文件数据
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param sql
	 *            查询SQL语句
	 * @param dataScope
	 *            数据范围，标题加数据或数据
	 * @throws Exception
	 */
	public void populateData(final Sheet sheet, List<MetaData> metaData, List<Map<String, Object>> datas)throws Exception {
		try {
			if (!AssertUtil.isVal(metaData)) {
				throw new IllegalArgumentException("the metaData can not be null");
			}
			
			int columnNum = metaData.size();
			Row row = null;
			int startRow = sheet.getRows();
			for (Map<String, Object> data : datas) {
				row = createRow(sheet, startRow++);
				for (int num = 0; num < columnNum; num++) {
					String value = "";
					MetaData meta = metaData.get(num);
					if (CellType.Number == metaData.get(num).getCellType()) {
						value = isInval(data.get(meta.getEnName())) ? 
								BigDecimal.ZERO.toString() : data.get(meta.getEnName()).toString();
						addCell(row, num, value, CellType.Number, StyleKey.Right);
					} else {
						value = isInval(data.get(meta.getEnName())) ?  
								"" : data.get(meta.getEnName()).toString().replaceAll("null", "");
						addCell(row, num, value, CellType.String, StyleKey.Left);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	/**
	 * 产生excel文件，只适用于根据查询SQL语句产生一个excel一个sheet的情况
	 * 
	 * @param filePath
	 *            产生文件的相对路径
	 * @param fileName
	 *            文件名，不含后缀
	 * @param creator
	 *            创建者
	 * @param sheetName
	 *            sheet名称
	 * @param sql
	 *            查询SQL语句
	 * @throws Exception
	 */
	public File generateExcel(String filePath, String fileName, String creator,
			String sheetName, List<MetaData> metaData, int[] columnWidthArray, int rowHeightArray, List<Map<String, Object>> datas) throws Exception {
		this.createWorkbook(filePath, fileName, creator);
		this.createDefaultStyles();
		this.createSheet(sheetName);
		this.setColumnWidth(0, columnWidthArray);
		this.setRowHeight(0, rowHeightArray);
		this.populateData(0, DataScope.TITLE_DATA, metaData, datas);
		return this.generateExcel();
	}

	/**
	 * 创建内部workbook
	 * 
	 * @param filePath
	 *            产生文件的相对路径
	 * @param fileName
	 *            文件名，不含后缀
	 * @param creator
	 *            创建者
	 */
	public ExcelExportUtil createWorkbook(String filePath, String fileName, String creator) {
		
		workbook = new Workbook();
		workbook.setFilePath(filePath);
		workbook.setFileName(fileName);
		if (isVal(creator)) {
			workbook.getPoiWorkbook().getProperties().getCoreProperties().setCreator(creator);
		}
		return this;
	}
	
	/**
	 * 判断是否为空字符串
	 * @param string
	 * @return
	 */
    static boolean isVal(String string) {
        if (string == null || "null".equals(string) || string.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 添加title
     * @param sheetIndex
     * @param metaData
     * @param title
     * @return
     */
	public ExcelExportUtil addTitle(int sheetIndex, List<MetaData> metaData, String title) {
		Sheet sheet = this.workbook.getSheetList().get(sheetIndex);
		this.addTitle(sheet, metaData, title);
		return this;
	}
	
	/**
	 * 添加表头
	 * @param sheet
	 * @param metaData
	 * @param title
	 */
	public void addTitle(final Sheet sheet, List<MetaData> metaData, String title) {
		if (!AssertUtil.isVal(metaData)) {
			throw new IllegalArgumentException("the metaData can not be null");
		}
		int rowIndex = sheet.getRows();
		if (AssertUtil.isVal(title)) {
			Row row = createRow(sheet, rowIndex++);
			addCell(row, 0, title,  CellType.String, StyleKey.Title);
			addMerge(sheet, row.getRowIndex(), row.getRowIndex(), 0, metaData.size() - 1);
		}
		
		Row row = createRow(sheet, rowIndex++);
		for (int index =0; index < metaData.size(); index++) {
			MetaData meta = metaData.get(index);
			addCell(row, index, meta.getCnName(),  CellType.String, StyleKey.Title);
		}
	}
	
	/**
	 * 创建POI样式对象
	 * 
	 * @param styleKey
	 *            样式名称
	 * @return
	 */
	public XSSFCellStyle createPoiStyle(StyleKey styleKey) {
		return this.createPoiStyle(styleKey.getName());
	}
	
	/**
	 * 创建POI样式对象
	 * @param styleKey 样式名称
	 * @return
	 */
	public XSSFCellStyle createPoiStyle(String styleKey) {
		XSSFCellStyle poiStyle = workbook.getPoiWorkbook().createCellStyle();
		workbook.getPoiStyleMap().put(styleKey, poiStyle);
		return poiStyle;
	}

	/**
	 * 创建POI默认样式
	 * 
	 * @return
	 */
	public ExcelExportUtil createDefaultStyles() {
		// 标题样式
		XSSFCellStyle titleStyle = this.createPoiStyle(StyleKey.Title);
		XSSFFont titleFont = this.createPoiFont();
		titleFont.setBold(true);// 粗体显示
		titleFont.setFontHeightInPoints((short) 12);
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
		titleStyle.setWrapText(true);// 设置自动换行
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);

		// 居左样式
		XSSFCellStyle leftStyle = this.createPoiStyle(StyleKey.Left);
		XSSFFont leftFont = this.createPoiFont();
		leftFont.setFontHeightInPoints((short) 10);
		leftStyle.setFont(leftFont);
		leftStyle.setAlignment(HorizontalAlignment.LEFT); // 居左
		leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
		leftStyle.setWrapText(true);// 设置自动换行
		leftStyle.setBorderBottom(BorderStyle.THIN);
		leftStyle.setBorderLeft(BorderStyle.THIN);
		leftStyle.setBorderRight(BorderStyle.THIN);
		leftStyle.setBorderTop(BorderStyle.THIN);

		// 居中样式
		XSSFCellStyle centerStyle = this.createPoiStyle(StyleKey.Center);
		XSSFFont centerFont = this.createPoiFont();
		centerFont.setFontHeightInPoints((short) 10);
		centerStyle.setFont(centerFont);
		centerStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
		centerStyle.setWrapText(true);// 设置自动换行
		centerStyle.setBorderBottom(BorderStyle.THIN);
		centerStyle.setBorderLeft(BorderStyle.THIN);
		centerStyle.setBorderRight(BorderStyle.THIN);
		centerStyle.setBorderTop(BorderStyle.THIN);

		// 居右样式
		XSSFCellStyle rightStyle = this.createPoiStyle(StyleKey.Right);
		XSSFFont rightFont = this.createPoiFont();
		rightFont.setFontHeightInPoints((short) 10);
		rightStyle.setFont(rightFont);
		rightStyle.setAlignment(HorizontalAlignment.RIGHT); // 居右
		rightStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
		rightStyle.setWrapText(true);// 设置自动换行
		rightStyle.setBorderBottom(BorderStyle.THIN);
		rightStyle.setBorderLeft(BorderStyle.THIN);
		rightStyle.setBorderRight(BorderStyle.THIN);
		rightStyle.setBorderTop(BorderStyle.THIN);

		// 小数位数样式
		for (int index = 0; index <= 10; index++) {
			XSSFCellStyle numberStyle = this.createPoiStyle(String.valueOf(index));
			XSSFFont numberFont = this.createPoiFont();

			numberFont.setFontHeightInPoints((short) 10);
			numberStyle.setFont(numberFont);
			numberStyle.setAlignment(HorizontalAlignment.RIGHT); // 居右
			numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
			numberStyle.setWrapText(true);// 设置自动换行
			numberStyle.setBorderBottom(BorderStyle.THIN);
			numberStyle.setBorderLeft(BorderStyle.THIN);
			numberStyle.setBorderRight(BorderStyle.THIN);
			numberStyle.setBorderTop(BorderStyle.THIN);
			XSSFDataFormat df = this.createPoiFormat();
			String format = "";
			if (index == 0) {
				format = "#0";
			} else {
				format = "#0." + String.format("%0" + index + "d", 0);
			}
			numberStyle.setDataFormat(df.getFormat(format));
		}
		
		return this;
	}

	/**
	 * 创建POI字体对象
	 * 
	 * @return
	 */
	public XSSFFont createPoiFont() {
		XSSFFont poiFont = workbook.getPoiWorkbook().createFont();
		return poiFont;
	}

	/**
	 * 创建POI数据格式对象
	 * 
	 * @return
	 */
	public XSSFDataFormat createPoiFormat() {
		XSSFDataFormat poiFormat = workbook.getPoiWorkbook().createDataFormat();
		return poiFormat;
	}

	/**
	 * 创建内部sheet
	 * 
	 * @param sheetName
	 * @return
	 */
	public ExcelExportUtil createSheet(String sheetName) {
		Sheet sheet = new Sheet();
		sheet.setSheetName(sheetName);
		workbook.getSheetList().add(sheet);
		return this;
	}

	/**
	 * 设置列宽
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param columnIndex
	 *            列索引，从0开始
	 * @param columnWidth
	 *            列宽度
	 */
	public void setColumnWidth(Sheet sheet, int columnIndex, int columnWidth) {
		sheet.getColumnWidthMap().put(columnIndex, columnWidth);
	}

	/**
	 * 设置行高
	 * 
	 * @param sheetIndex
	 *            内部sheet
	 * @param rowIndex
	 *            行索引，从0开始
	 * @param rowHeight
	 *            行高度
	 */
	public ExcelExportUtil setRowHeight(int sheetIndex, int rowIndex, int rowHeight) {
		Sheet sheet = workbook.getSheetList().get(sheetIndex);
		Row row = sheet.getRow(rowIndex);
		if (isInval(row)) {
			row = this.createRow(sheet, rowIndex);
		}
		row.setRowHeight(rowHeight);
		return this;
	}
	/**
	 * 设置行高
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param rowIndex
	 *            行索引，从0开始
	 * @param rowHeight
	 *            行高度
	 */
	public void setRowHeight(Sheet sheet, int rowIndex, int rowHeight) {
		Row row = sheet.getRow(rowIndex);
		if (isInval(row)) {
			row = this.createRow(sheet, rowIndex);
		}
		row.setRowHeight(rowHeight);
	}
	
	/**
	 * 设置列宽
	 * @param sheetIndex 
	 * @param columnWidthArray
	 */
	public ExcelExportUtil setColumnWidth(int sheetIndex, int[] columnWidthArray) {
		Sheet sheet = this.workbook.getSheetList().get(sheetIndex);
		for (int index = 0; isVal(columnWidthArray) && index < columnWidthArray.length; index++) {
			setColumnWidth(sheet, index, columnWidthArray[index]);
		}
		return this;
	}
	
	/**
	 * 
	 * @param sheet
	 * @param columnWidthArray
	 */
	public void setColumnWidth(Sheet sheet, int[] columnWidthArray) {
		for (int index = 0; isVal(columnWidthArray) && index < columnWidthArray.length; index++) {
			setColumnWidth(sheet, index, columnWidthArray[index]);
		}
	}

	/**
	 * 设置了行高
	 * @param sheetIndex
	 * @param rowHeight
	 */
	public ExcelExportUtil setRowHeight(int sheetIndex, int rowHeight) {
		if (rowHeight <= 0) {
			rowHeight = 20;
		}
		Sheet sheet = this.workbook.getSheetList().get(sheetIndex);
		for (int index = 0; index < sheet.getRows(); index++) {
			setRowHeight(sheet, index, rowHeight);
		}
		return this;
	}
	
	/**
	 * 设置行高
	 * @param sheet
	 * @param rowHeight
	 */
	public void setRowHeight(Sheet sheet, int rowHeight) {
		if (rowHeight <= 0) {
			rowHeight = 20;
		}
		for (int index = 0; index < sheet.getRows(); index++) {
			setRowHeight(sheet, index, rowHeight);
		}
	}
	
    public static boolean isInval(Object object) {
        return ! isVal(object);
    }
    public static boolean isVal(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }
    /**
	 * 添加合并单元格范围
	 * 
	 * @param sheetIndex
	 *            内部sheet
	 * @param rowIndexBegin
	 *            开始行索引，从0开始
	 * @param rowIndexEnd
	 *            结束行索引，从0开始
	 * @param cellIndexBegin
	 *            开始列索引，从0开始
	 * @param cellIndexEnd
	 *            结束列索引，从0开始
	 */
	public ExcelExportUtil addMerge(int sheetIndex, int rowIndexBegin, int rowIndexEnd, int cellIndexBegin, int cellIndexEnd) {
		Sheet sheet = this.workbook.getSheetList().get(sheetIndex);
		this.addMerge(sheet, rowIndexBegin, rowIndexEnd, cellIndexBegin, cellIndexEnd);
		return this;
	}
	/**
	 * 添加合并单元格范围
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param rowIndexBegin
	 *            开始行索引，从0开始
	 * @param rowIndexEnd
	 *            结束行索引，从0开始
	 * @param cellIndexBegin
	 *            开始列索引，从0开始
	 * @param cellIndexEnd
	 *            结束列索引，从0开始
	 */
	public void addMerge(Sheet sheet, int rowIndexBegin, int rowIndexEnd, int cellIndexBegin, int cellIndexEnd) {
		Merge merge = new Merge();
		merge.setRowIndexBegin(rowIndexBegin);
		merge.setRowIndexEnd(rowIndexEnd);
		merge.setCellIndexBegin(cellIndexBegin);
		merge.setCellIndexEnd(cellIndexEnd);
		sheet.getMergeList().add(merge);
	}

	/**
	 * 创建内部row
	 * 
	 * @param sheetIndex
	 *            内部sheet
	 * @param rowIndex
	 *            行号，从0开始
	 * @return
	 */
	public Row createRow(int sheetIndex, int rowIndex) {
		Sheet sheet = workbook.getSheetList().get(sheetIndex);
		return createRow(sheet, rowIndex);
	}
	/**
	 * 创建内部row
	 * 
	 * @param sheet
	 *            内部sheet
	 * @param rowIndex
	 *            行号，从0开始
	 * @return
	 */
	public Row createRow(Sheet sheet, int rowIndex) {
		Row row = sheet.getRowMap().get(rowIndex);
		if (isVal(row)) {
			return row;
		}
		row = new Row();
		row.setRowIndex(rowIndex);
		sheet.getRowList().add(row);
		sheet.getRowMap().put(rowIndex, row);
		sheet.setRows(rowIndex + 1);
		return row;
	}

	/**
	 * 添加内部cell
	 * 
	 * @param row
	 *            内部row
	 * @param cellIndex
	 *            列索引，从0开始
	 * @param cellValue
	 *            单元格值
	 * @param cellType
	 *            单元格类型，文本或数字
	 * @param styleKey
	 *            样式名称
	 */
	public void addCell(Row row, int cellIndex, String cellValue,
			CellType cellType, String styleKey) {
		Cell cell = row.getCellMap().get(cellIndex);
		if (isInval(cell)) {
			cell = new Cell();
			cell.setRow(row);
			cell.setCellIndex(cellIndex);
			row.getCellList().add(cell);
			row.getCellMap().put(cellIndex, cell);
		}
		cell.setCellValue(cellValue);
		cell.setCellType(cellType);
		cell.setStyleKey(styleKey);
		
		// 收集以文本格式显示的单元格值
		if (CellType.String == cellType) {
			if (!shardStringsIndexMap.containsKey(cellValue)) {
				shardStringsList.add(cellValue);
				shardStringsIndexMap.put(cellValue, shardStringsList.size() - 1);
			}
		}
	}
	
	/**
	 * 添加单元格
	 * @param row
	 * @param cellIndex
	 * @param cellValue
	 * @param cellType
	 * @param styleKey
	 */
	public void addCell(Row row, int cellIndex, String cellValue, CellType cellType, StyleKey styleKey) {
		this.addCell(row, cellIndex, cellValue, cellType, styleKey.getName());
	}
	
	public StringBuffer getFilePath(String relativePath) {
		StringBuffer realFilePath = new StringBuffer();
		try {
			if (AssertUtil.isVal(basePath)) {
				realFilePath.append(basePath);
				if (!realFilePath.toString().endsWith(File.separator)) {
					realFilePath.append(File.separator);
				}
			} else {
				basePath = this.env.getProperty(FILE_KEY, FILE_PATH_DEFUALT);
				realFilePath.append(File.separator);
			}
		} catch (Exception e) {
			realFilePath.append(File.separator);
		}
		
		realFilePath.append(relativePath);
		return realFilePath;
	}

	/**
	 * 产生excel文件
	 * 
	 * @throws Exception
	 */
	public File generateExcel() throws Exception {
		File templateFile = null;
		xmlFileMap = new HashMap<String, List<File>>();
		try {
			StringBuffer realFilePath = this.getFilePath(workbook.getFilePath());
			boolean exists = (new File(realFilePath.toString())).exists();
			if (!exists) 
				new File(realFilePath.toString()).mkdirs();
			
			
			String fileFullName = realFilePath.append(File.separator).append(workbook.getFileName()).append(XLSX).toString();

			// 创建一个临时的excel模板
			templateFile = new File(getUUID() + "_template.xlsx");
			templateFile.createNewFile();
			List<Sheet> sheetList = workbook.getSheetList();
			this.createPoiSheet(templateFile, sheetList);

			// 创建sheet.xml
			this.createSheetXML(sheetList);

			// 创建sharedStrings.xml
			this.createSharedStringsXML();

			// 用创建sheet.xml和sharedStrings.xml文件替换掉临时excel模板中对应文件
			this.substitute(templateFile, xmlFileMap, fileFullName);
			return new File(fileFullName);
		} catch (Exception e) {
			LOGGER.error("ExcelExportUtil.generateExcel()", e);
			throw e;
		} finally {
			this.delTempFile(templateFile, xmlFileMap);
			workbook = null;
		}
	}
	/**
	 * 产生excel文件
	 * @param realFilePath 路径
	 * @throws Exception
	 */
	public void generateExcel(String realFilePath) throws Exception {
		File templateFile = null;
		xmlFileMap = new HashMap<String, List<File>>();
		try {
			String fileFullName = realFilePath + "/" + workbook.getFileName() + XLSX;
			boolean exists = (new File(realFilePath)).exists();
			if (!exists) {
				(new File(realFilePath)).mkdirs();
			}

			// 创建一个临时的excel模板
			templateFile = new File(getUUID() + "_template.xlsx");
			templateFile.createNewFile();
			List<Sheet> sheetList = workbook.getSheetList();
			this.createPoiSheet(templateFile, sheetList);

			// 创建sheet.xml
			this.createSheetXML(sheetList);

			// 创建sharedStrings.xml
			this.createSharedStringsXML();

			// 用创建sheet.xml和sharedStrings.xml文件替换掉临时excel模板中对应文件
			this.substitute(templateFile, xmlFileMap, fileFullName);
		} catch (Exception e) {
			LOGGER.error("ExcelExportUtil.generateExcel()", e);
			throw e;
		} finally {
			this.delTempFile(templateFile, xmlFileMap);
			workbook = null;
		}
	}

	/**
	 * 利用poi创建空sheet
	 * 
	 * @param templateFile
	 * @param sheetList
	 * @throws IOException
	 */
	private void createPoiSheet(File templateFile, List<Sheet> sheetList)throws IOException {
		FileOutputStream templateOS = new FileOutputStream(templateFile);
		for (int sheetIndex = 0; isVal(sheetList) && sheetIndex < sheetList.size(); sheetIndex++) {
			workbook.getPoiWorkbook().createSheet(sheetList.get(sheetIndex).getSheetName());
		}
		workbook.getPoiWorkbook().write(templateOS);
		templateOS.flush();
		templateOS.close();
	}

	/**
	 * 创建sheet.xml
	 * 
	 * @param sheetList
	 * @throws IOException
	 */
	private void createSheetXML(List<Sheet> sheetList) throws IOException {
		// 创建sheetN.xml
		for (int sheetIndex = 0; isVal(sheetList) && sheetIndex < sheetList.size(); sheetIndex++) {
			Sheet sheet = sheetList.get(sheetIndex);
			// 将没有塞数据的单元格用空数据填充满，否则会出现没有边框情况
			this.fillSheet(sheet);
			Collections.sort(sheet.getRowList(), comparatorRow);
			
			// 持有电子表格数据的xml文件名 例如 /xl/worksheets/sheet1.xml
			XSSFSheet poiSheet = workbook.getPoiWorkbook().getSheetAt(sheetIndex);
			String sheetRef = poiSheet.getPackagePart().getPartName().getName();
			sheet.setSheetRef(sheetRef.substring(1));
			
			List<File> sheetFileList = new ArrayList<File>();
			xmlFileMap.put(sheetRef.substring(1), sheetFileList);

			// 利用多线程新增sheet中行元素
			XMLWriterManager manager = new XMLWriterManager(sheet);
			for (int i = 0; i < manager.MAXTHREADCOUNT; i++){
				sheetFileList.add(File.createTempFile("sheet", ".xml"));
			}
			manager.excuteStartSheetWriter();
		}
	}

	/**
	 * 新增列宽元素
	 * 
	 * @param sheetWriter
	 * @param sheet
	 * @throws IOException
	 */
	private void createSheetColsXML(SheetWriter sheetWriter, Sheet sheet) throws IOException {
		Map<Integer, Integer> columnWidthMap = sheet.getColumnWidthMap();
		if (isVal(columnWidthMap) && !columnWidthMap.isEmpty()) {
			Set<Integer> columnIndexSet = columnWidthMap.keySet();
			sheetWriter.beginCols();
			for (Integer columnIndex : columnIndexSet) {
				sheetWriter.createCols(columnIndex, columnWidthMap.get(columnIndex));
			}
			sheetWriter.endCols();
		}
	}

	/**
	 * 多线程新增行元素
	 * @param thisThreadNum 当前线程
	 * @param threadCount   总并发线程数
	 * @param sheet
	 * @param rowList
	 * @throws IOException
	 */
	private void multithreadCreateRowXML(int thisThreadNum, int threadCount, Sheet sheet, List<Row> rowList) throws IOException {
		// 创建sheetN.xml文件
		List<File> sheetFileList = xmlFileMap.get(sheet.getSheetRef());
		File sheetFile = sheetFileList.get(thisThreadNum - 1);

		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sheetFile), ENCODING_UTF_8));
		SheetWriter sheetWriter = new SheetWriter(writer);
		RowWriter rowWriter = new RowWriter(writer);
		CellWriter cellWriter = new CellWriter(writer);
		// 第一个线程执行时
		if (thisThreadNum == 1) {
			sheetWriter.beginWorksheet();

			// 新增sheet.xml中列宽元素数据
			createSheetColsXML(sheetWriter, sheet);

			sheetWriter.beginSheetData();
		}
		int rowListSize = isVal(rowList) ? rowList.size() : 0;
		for (int rowIndex = 0; rowIndex < rowListSize; rowIndex++) {
			Row row = rowList.get(rowIndex);
			List<Cell> cellList = row.getCellList();
			Collections.sort(cellList, comparatorCell);
			rowWriter.insertRow(row.getRowIndex(), row.getRowHeight());
			int cellListSize = cellList.size();
			for (int columnIndex = 0; columnIndex < cellListSize; columnIndex++) {
				Cell cell = cellList.get(columnIndex);
				String cellValue = cell.getCellValue();
				String styleKey = cell.getStyleKey();
				CellType cellType = cell.getCellType();
				int cellIndex = cell.getCellIndex();

				// 样式信息
				XSSFCellStyle poiStyle = workbook.getPoiStyleMap().get(styleKey);
				int poiStyleIndex = isVal(poiStyle) ? poiStyle.getIndex() : -1;

				// 单元格信息
				if (CellType.String == cellType) {
					Integer textIndex = isVal(cellValue) ? shardStringsIndexMap.get(cellValue) : 0;
					cellWriter.createCell(cell.getRow().getRowIndex(), cellIndex, cellValue, textIndex, poiStyleIndex);
				} else if (CellType.Number == cellType) {
					cellWriter.createCell(cell.getRow().getRowIndex(), cellIndex,
							getDecimal(cellValue).doubleValue(), poiStyleIndex);
				}
			}
			rowWriter.endRow();
		}
		// 最后一个线程执行时
		if (thisThreadNum == threadCount) {
			sheetWriter.endSheetData();

			// 创建sheet.xml中合并单元格元素
			createMergeXML(writer, sheet);

			sheetWriter.endWorksheet();
		}

		writer.flush();
		writer.close();
	}
	
	private BigDecimal getDecimal(String amt) {
		if ("".equals(amt) || null == amt) {
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(amt.replaceAll(",", "").trim());
		}
	}

	/**
	 * 新增合同单元格元素
	 * 
	 * @param writer
	 * @param sheet
	 * @throws IOException
	 */
	private void createMergeXML(Writer writer, Sheet sheet) throws IOException {
		// 合并单元格
		List<Merge> mergeList = sheet.getMergeList();
		MergeCellWriter mregeWriter = new MergeCellWriter(writer);
		if (isVal(mergeList) && ! mergeList.isEmpty()) {
			mregeWriter.beginMerge(mergeList.size());
			for (Merge merge : mergeList) {
				mregeWriter.merge(merge.getRowIndexBegin(),
						merge.getRowIndexEnd(), merge.getCellIndexBegin(),
						merge.getCellIndexEnd());
			}
			mregeWriter.endMerge();
		}
	}
	
	/**
	 * 创建sharedStrings.xml
	 * 
	 * @throws IOException
	 */
	private void createSharedStringsXML() throws IOException {
		// 持有电子表格数据的xml文件名 例如 xl/sharedStrings.xml
		List<File> sharedStringsFileList = new ArrayList<File>();
		xmlFileMap.put("xl/sharedStrings.xml", sharedStringsFileList);

		// 利用多线程新增sharedStrings.xml中文本元素
		XMLWriterManager manager = new XMLWriterManager(shardStringsList);
		for (int i = 0; i < manager.MAXTHREADCOUNT; i++){
			sharedStringsFileList.add(File.createTempFile("shardStrings", ".xml"));
		}
		manager.excuteStartSharedStringWriter();
	}

	/**
	 * 多线程创建sharedStrings.xml
	 * @param thisThreadNum 当前线程
	 * @param threadCount   总并发线程数
	 * @param textList
	 * @throws IOException
	 */
	private void multithreadCreateSharedStringsXML(int thisThreadNum, int threadCount, List<String> sharedStringsList) throws IOException {
		// 创建sharedStrings.xml
		List<File> sharedStringsFileList = xmlFileMap.get("xl/sharedStrings.xml");
		File sharedStringsFile = sharedStringsFileList.get(thisThreadNum - 1);

		Writer stringWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sharedStringsFile), ENCODING_UTF_8));
		SharedStringWriter sharedStringWriter = new SharedStringWriter(stringWriter);
		// 第一个线程执行时
		if (thisThreadNum == 1) {
			sharedStringWriter.beginSharedString();
		}
		for (int index = 0; isVal(sharedStringsList) && index < sharedStringsList.size(); index++) {
			sharedStringWriter.createSharedString(sharedStringsList.get(index));
		}
		// 最后一个线程执行时
		if (thisThreadNum == threadCount) {
			sharedStringWriter.endSharedString();
		}
		stringWriter.flush();
		stringWriter.close();
	}

	/**
	 * 删除临时文件方法
	 * 
	 * @param templateFile
	 *            临时excel文件
	 * @param xmlFileMap
	 *            临时xml文件集合
	 */
	private void delTempFile(File templateFile,Map<String, List<File>> xmlFileMap) {
		// 删除文件之前调用一下垃圾回收器，否则无法删除模板文件
		System.gc();
		if (templateFile.isFile() && templateFile.exists()) {
			templateFile.delete();
		}
		if (isVal(xmlFileMap)) {
			Set<String> xmlFileNameSet = xmlFileMap.keySet();
			for (String xmlFileName : xmlFileNameSet) {
				List<File> xmlFileList = xmlFileMap.get(xmlFileName);
				for (File xmlFile : xmlFileList) {
					if (xmlFile != null && xmlFile.isFile() && xmlFile.exists()) {
						xmlFile.delete();
					}
				}
			}
		}
	}

	/**
	 * 替换excel模板
	 * 
	 * @param templateFile
	 *            临时模板文件
	 * @param xmlFileMap
	 *            xml文件集合
	 * @param targetFileOutputStream
	 *            目标文件流
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void substitute(File templateFile, Map<String, List<File>> xmlFileMap, String fileFullName)
			throws Exception {
		FileOutputStream targetFileOutputStream = null;
		ZipFile zipFile =null;
		ZipOutputStream zos = null;
		try {
			targetFileOutputStream = new FileOutputStream(fileFullName);
			zipFile = new ZipFile(templateFile);
			zos = new ZipOutputStream(targetFileOutputStream);
			Set<String> xmlFileNameSet = xmlFileMap.keySet();
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zipFile.entries();
			// 循环将sheet.xml 和 sharedStrings.xml 以外的其他文件写入到 $(fileFullName)中
			while (en.hasMoreElements()) {
				ZipEntry ze = en.nextElement();
				if (!xmlFileNameSet.contains(ze.getName())) {
					zos.putNextEntry(new ZipEntry(ze.getName()));
					InputStream is = zipFile.getInputStream(ze);
					copyStream(is, zos);
					is.close();
				}
			}
			
			// 循环将sheet.xml 和 sharedStrings.xml 文件写入到 $(fileFullName)中
			for (String xmlFileName : xmlFileNameSet) {
				zos.putNextEntry(new ZipEntry(xmlFileName));
				List<File> xmlFileList = xmlFileMap.get(xmlFileName);
				for (File xmlFile : xmlFileList) {
					InputStream is = new FileInputStream(xmlFile);
					copyStream(is, zos);
					is.close();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (AssertUtil.isVal(zos))
				zos.close();
			if (AssertUtil.isVal(zipFile))
				zipFile.close();
			if (AssertUtil.isVal(targetFileOutputStream))
				targetFileOutputStream.close();
		}
		
		
	}

	/**
	 * 复制
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * @throws IOException
	 */
	private void copyStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >= 0) {
			out.write(chunk, 0, count);
		}
	}

	/**
	 * 填充Sheet
	 * 
	 */
	private void fillSheet(Sheet sheet) {
		List<Merge> mergeList = sheet.getMergeList();
		Merge merge = null;
		for (int i = 0; i < mergeList.size(); i++) {
			merge = mergeList.get(i);
			for (int rowIndex = merge.getRowIndexBegin(); rowIndex <= merge.getRowIndexEnd(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				for (int cellIndex = merge.getCellIndexBegin(); cellIndex <= merge.getCellIndexEnd(); cellIndex++) {
					if (isInval(row)) {
						row = this.createRow(sheet, rowIndex);
					}
					if (isInval(row.getCell(cellIndex))) {
						this.addCell(row, cellIndex, "", CellType.String, StyleKey.Left);
					}
				}
			}
		}
	}

	/**
	 * cellIndex大小比较实现类 一个Sheet中row排序用
	 */
	private class ComparatorRow implements Comparator<Row> {
		public int compare(Row row0, Row row1) {
			return row0.getRowIndex() - row1.getRowIndex();
		}
	}

	/**
	 * cellIndex大小比较实现类 一个row中cell排序用
	 */
	private class ComparatorCell implements Comparator<Cell> {
		public int compare(Cell cell0, Cell cell1) {
			return cell0.getCellIndex() - cell1.getCellIndex();
		}
	}

	/**
	 * xml写入器父类
	 */
	private static class XMLWriter {
		public final Writer _out;
		public static String LINE_SEPARATOR = System.getProperty("line.separator");

		public XMLWriter(Writer out) {
			_out = out;
		}
	}

	/**
	 * sheet.xml头、尾写入器
	 */
	private static class SheetWriter extends XMLWriter {
		public SheetWriter(Writer _out) {
			super(_out);
		}

		public void beginWorksheet() throws IOException {
			_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			_out.write("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
		}

		public void endWorksheet() throws IOException {
			_out.write("</worksheet>");
		}

		public void beginSheetData() throws IOException {
			_out.write("<sheetData>" + LINE_SEPARATOR);
		}

		public void endSheetData() throws IOException {
			_out.write("</sheetData>");
		}

		public void beginCols() throws IOException {
			_out.write("<cols>");
		}

		public void endCols() throws IOException {
			_out.write("</cols>");
		}

		public void createCols(int columnIndex, int columnWidth)
				throws IOException {
			_out.write("<col min=\"" + (columnIndex + 1) + "\"" + " max=\"" + (columnIndex + 1) + "\" ");
			_out.write("width=\"" + columnWidth + "\" ");
			_out.write("bestFit=\"" + 1 + "\"" + " customWidth=\"" + 1 + "\" ");
			_out.write("/>");
		}
	}

	/**
	 * sheet.xml中行元素写入器
	 */
	private static class RowWriter extends XMLWriter {
		public RowWriter(Writer _out) {
			super(_out);
		}

		public void insertRow(int rownum, int rowHeight) throws IOException {
			_out.write("<row r=\"" + (rownum + 1) + "\" ");
			if (rowHeight > 0) {
				_out.write("ht=\"" + rowHeight + "\" customHeight=\"1" + "\"");
			}
			_out.write(">" + LINE_SEPARATOR);
		}

		public void endRow() throws IOException {
			_out.write("</row>" + LINE_SEPARATOR);
		}
	}

	/**
	 * sheet.xml中单元格元素写入器
	 */
	private static class CellWriter extends XMLWriter {
		public CellWriter(Writer _out) {
			super(_out);
		}

		public void createCell(int rowIndex, int columnIndex, String value, int textIndex, int styleIndex) throws IOException {
			String ref = changeToLetter(columnIndex + 1) + (rowIndex + 1);
			_out.write("<c r=\"" + ref + "\"");
			if (styleIndex != -1) {
				_out.write(" s=\"" + styleIndex + "\"");
			}
			if (isVal(value)) {
				_out.write(" t=\"s\"");
				_out.write(">");
				_out.write("<v>" + textIndex + "</v>");
				_out.write("</c>");
			} else {
				_out.write("/>");
			}
		}

		public void createCell(int rowIndex, int columnIndex, double value, int styleIndex) throws IOException {
			String ref = changeToLetter(columnIndex + 1) + (rowIndex + 1);
			_out.write("<c r=\"" + ref + "\"");
			if (isVal(value)) {
				_out.write(" t=\"n\"");
			}
			if (styleIndex != -1) {
				_out.write(" s=\"" + styleIndex + "\"");
			}
			_out.write(">");
			if (isVal(value)) {
				_out.write("<v>" + value + "</v>");
			}
			_out.write("</c>");
		}
	}

	/**
	 * sheet.xml中合并单元格元素写入器
	 */
	private static class MergeCellWriter extends XMLWriter {
		public MergeCellWriter(Writer _out) {
			super(_out);
		}

		public void beginMerge(int count) throws IOException {
			_out.write("<mergeCells count=\"" + count + "\">");
		}

		public void merge(int rowNoBegin, int rowNoEnd, int columnNoBegin, int columnNoEnd) throws IOException {
			String range = new CellRangeAddress(rowNoBegin, rowNoEnd,
					columnNoBegin, columnNoEnd).formatAsString();
			_out.write("<mergeCell ref=\"" + range + "\" />");
		}

		public void endMerge() throws IOException {
			_out.write("</mergeCells>");
		}
	}

	/**
	 * sharedString.xml写入器
	 */
	private static class SharedStringWriter extends XMLWriter {
		private static final String[] xmlCode = new String[256];
		static {
			xmlCode['\''] = "\'"; // 单引号
			xmlCode['\"'] = "\""; // 双引号
			xmlCode['&'] = "&amp;"; // 与
			xmlCode['<'] = "&lt;"; // 小于号
			xmlCode['>'] = "&gt;"; // 大于号
		}

		public SharedStringWriter(Writer _out) {
			super(_out);
		}

		public void beginSharedString() throws IOException {
			_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			_out.write("<sst count=\"490\" uniqueCount=\"182\" xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
		}

		public void endSharedString() throws IOException {
			_out.write("</sst>");
		}

		public void createSharedString(String content) throws IOException {
			_out.write("<si><t>" + encode(content) + "</t></si>");
		}

		public static String encode(String string) throws UnsupportedEncodingException {
			if (string == null) {
				return "";
			}
			int n = string.length();
			char character;
			String xmlchar;
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < n; i++) {
				character = string.charAt(i);
				try {
					xmlchar = xmlCode[character];
					if (xmlchar == null) {
						buffer.append(character);
					} else {
						buffer.append(xmlCode[character]);
					}
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					buffer.append(character);
				}
			}
			return buffer.toString();
		}
	}

	/**
	 * xmlWriter 管理器
	 */
	public class XMLWriterManager {
		// 最大并发生成XML文件的线程数
		public final int MAXTHREADCOUNT = 10;
		
		//实际并发线程数
		private int threadCount;

		// 当前sheet
		private Sheet sheet;

		// Excel行数据
		private List<Row> rowList;
		
		// sharedString.xml中文本集合
		private List<String> sharedStringsList;

		// 用于监视何时合并文件存放Thread的list
		private List<XMLWriterThread> XMLWriterThreadList = new ArrayList<XMLWriterThread>();

		/**
		 * 构造函数 For write sheet.xml 
		 * @param sheet
		 */
		public XMLWriterManager(Sheet sheet) {
			super();
			this.sheet = sheet;
		}
		
		/**
		 * 构造函数 For write sharedStrings.xml 
		 * @param sharedStringsList
		 */
		public XMLWriterManager(List<String> sharedStringsList) {
			super();
			this.sharedStringsList = sharedStringsList;
		}

		/**
		 * sheet.xml  文件写入执行入口
		 */
		public void excuteStartSheetWriter() {
			startSheetWriter();
			checkIsFinish();
			LOGGER.info("sheetName:" + sheet.getSheetName() + ", startSheetWriter 结束， 共并发执行" +threadCount+ "个线程");
		}
		
		/**
		 * sharedStrings.xml 文件写入执行入口
		 */
		public void excuteStartSharedStringWriter() {
			startSharedStringWriter();
			checkIsFinish();
			LOGGER.info("startSharedStringWriter 结束， 共并发执行" +threadCount+ "个线程");
		}
		
		/**
		 * 并发线程是否执行完成检查
		 */
		private void checkIsFinish() {
			while (true) {
				// 初始化认为所有线程下载完成，逐个检查
				boolean finish = true;
				// 如果有任何一个没完成，说明下载没完成，不能合并文件
				for (XMLWriterThread thread : XMLWriterThreadList) {
					if (!thread.isFinish()) {
						finish = false;
						break;
					}
				}
				// 全部下载时
				if (finish) {
					break;
				}
				// 休息一会 ， 减少cpu消耗
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
		

		private void startSheetWriter() {
			this.rowList = sheet.getRowList();
			int rowSize = this.rowList.size();
			//根据资料数确定实际启用的线程数
			threadCount = rowSize / 20 + (rowSize % 20 > 0 ? 1 : 0);
			// 如果线程数大于默认线程，线程数是默认线程数
			threadCount = threadCount >= MAXTHREADCOUNT ? MAXTHREADCOUNT : threadCount;
			
			LOGGER.info("sheetName:" + sheet.getSheetName() + ", startSheetWriter 开始， 共并发执行" +threadCount+ "个线程");
			
			List<Row> perThreadRowList = null;
			// 创建每个下载线程，并且启动他们。
			int perThreadRowCount = threadCount > 0 ? rowSize / threadCount : 0;
			int mod = threadCount > 0 ? rowSize % threadCount : 0;
			for (int i = 0; i < threadCount; i++) {
				if (i == threadCount - 1) {
					perThreadRowList = this.rowList.subList(i * perThreadRowCount, perThreadRowCount * (i + 1) + mod);
				} else {
					perThreadRowList = this.rowList.subList(i * perThreadRowCount, perThreadRowCount * (i + 1));
				}
				XMLWriterThread XMLWriterThread = new XMLWriterThread(i + 1, threadCount, sheet, perThreadRowList);
				new Thread(XMLWriterThread).start();
				XMLWriterThreadList.add(XMLWriterThread);
			}
		}

		private void startSharedStringWriter() {
			int sharedStringsSize = this.sharedStringsList.size();
			//根据资料数确定实际启用的线程数
			threadCount = sharedStringsSize / 20 + (sharedStringsSize % 20 > 0 ? 1 : 0); 
			// 如果线程数大于默认线程，线程数是默认线程数
			threadCount = threadCount >= MAXTHREADCOUNT ? MAXTHREADCOUNT : threadCount;  
			
			LOGGER.info("startSharedStringWriter 开始， 共并发执行" +threadCount+ "个线程");
			
			List<String> perThreadTextList = null;
			// 创建每个下载线程，并且启动他们。
			int perThreadTextCount = threadCount > 0 ? sharedStringsSize / threadCount : 0;
			int mod = threadCount > 0 ? sharedStringsSize % threadCount : 0;
			for (int i = 0; i < threadCount; i++) {
				if (i == threadCount - 1) {
					perThreadTextList = this.sharedStringsList.subList(i
							* perThreadTextCount, perThreadTextCount * (i + 1)
							+ mod);
				} else {
					perThreadTextList = this.sharedStringsList.subList(i
							* perThreadTextCount, perThreadTextCount * (i + 1));
				}
				XMLWriterThread XMLWriterThread = new XMLWriterThread(i + 1, threadCount, perThreadTextList);
				new Thread(XMLWriterThread).start();
				XMLWriterThreadList.add(XMLWriterThread);
			}
		}
	}

	/**
	 * xml文件生成线程
	 */
	public class XMLWriterThread implements Runnable {
		// 当前第几个线程
		private int thisThreadNum;

		// 一共几个线程
		private int threadCount;

		// 当前sheet
		private Sheet sheet;

		// 每个线程对应处理的excel行数据
		private List<Row> rowList;
		
		// 每个线程对应处理的sharedString.xml中文本元素集合
		private List<String> sheardStringsList;

		// 监听单一线程下载是否完成
		private boolean isFinish;
		
		/**
		 * sheet.xml 多线程写入构造器
		 * @param thisThreadNum
		 * @param threadCount
		 * @param sheet
		 * @param perThreadRowList
		 */
		public XMLWriterThread(int thisThreadNum, int threadCount, Sheet sheet, List<Row> rowList) {
			super();
			this.thisThreadNum = thisThreadNum;
			this.threadCount = threadCount;
			this.sheet = sheet;
			this.rowList = rowList;
		}
		
		/**
		 * sheardStrings.xml 多线程写入构造器
		 * @param thisThreadNum
		 * @param threadCount
		 * @param textList
		 */
		public XMLWriterThread(int thisThreadNum, int threadCount, List<String> sheardStringsList) {
			super();
			this.thisThreadNum = thisThreadNum;
			this.threadCount = threadCount;
			this.sheardStringsList = sheardStringsList;
		}

		public void run() {
			try {
				if (rowList != null) {
					LOGGER.info("sheetName:" + sheet.getSheetName() + ", startSheetWriter 第" +thisThreadNum+ "个线程执行开始！执行数量为：" + rowList.size());
					
					multithreadCreateRowXML(thisThreadNum, threadCount, sheet, rowList);
					
					LOGGER.info("sheetName:" + sheet.getSheetName() + ", startSheetWriter 第" +thisThreadNum+ "个线程执行完毕！");
				} 
				if (sheardStringsList != null) {
					LOGGER.info("startSharedStringWriter 第" +thisThreadNum+ "个线程执行开始！执行数量为：" + sheardStringsList.size());
					
					multithreadCreateSharedStringsXML(thisThreadNum, threadCount, sheardStringsList);
					
					LOGGER.info("startSharedStringWriter 第" +thisThreadNum+ "个线程执行完毕！");
				} 
				// 将监视变量设置为true
				isFinish = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean isFinish() {
			return isFinish;
		}
	}

	/**
	 * 数字转字母
	 * 
	 * @param columnNo
	 *            数字
	 * @return 字母
	 */
	private static String changeToLetter(int columnNo) {
		String letter = "";
		int div = columnNo / 26;
		int mod = columnNo % 26;

		if (columnNo <= 26) {
			letter = (char) (columnNo + 64) + "";
		} else {
			if (mod == 0) {
				mod = 26;
				div = div - 1;
			}
			letter = (char) (div + 64) + "" + (char) (mod + 64) + "";
		}
		return letter;
	}

	/**
	 * 产生UUID
	 * 
	 * @return Strng UUID
	 */
	private static final synchronized String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}

