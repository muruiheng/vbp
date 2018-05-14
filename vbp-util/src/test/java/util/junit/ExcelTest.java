package util.junit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.sgai.vbp.util.excel.CellType;
import com.sgai.vbp.util.excel.DataScope;
import com.sgai.vbp.util.excel.ExcelExportUtil;
import com.sgai.vbp.util.excel.MetaData;
import com.sgai.vbp.util.excel.Row;

public class ExcelTest{

	@Autowired
	private Environment env;
	
	@Test
	public void excelTest() {
		
		List<MetaData> mateData = new LinkedList<>();
		mateData.add(new MetaData(1, "xuhao", "序号", CellType.String));
		mateData.add(new MetaData(2, "xingming", "姓名", CellType.String));
		mateData.add(new MetaData(3, "xuehao", "学号", CellType.String));
		mateData.add(new MetaData(4, "fenshu", "分数", CellType.Number));
		
		int[] columnWidthArray = {10,20,30,10};
		
		List<Map<String, Object>> datas = new LinkedList<>();
		for (int index =0; index < 10000; index ++) {
			Map<String, Object> data = new HashMap<>();
			datas.add(data);
			data.put("xuhao", index + 1 );
			data.put("xingming", "张三" + index );
			data.put("xuehao", "00110001_" + index );
			data.put("fenshu", 80+1 );
		}
		try {
			ExcelExportUtil.getInstance(env).generateExcel("doc", "test", "穆瑞恒", "测试类", mateData, columnWidthArray, 20, datas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void excelExport2() {
		
		List<MetaData> mateData = new LinkedList<>();
		mateData.add(new MetaData(1, "xuhao", "序号", CellType.String));
		mateData.add(new MetaData(2, "xingming", "姓名", CellType.String));
		mateData.add(new MetaData(3, "xuehao", "学号", CellType.String));
		mateData.add(new MetaData(4, "fenshu", "分数", CellType.Number));
		
		int[] columnWidthArray = {10,20,30,10};
		
		List<Map<String, Object>> datas = new LinkedList<>();
		for (int index =0; index < 100; index ++) {
			Map<String, Object> data = new HashMap<>();
			datas.add(data);
			data.put("xuhao", index + 1 );
			data.put("xingming", "张三" + index );
			data.put("xuehao", "00110001_" + index );
			data.put("fenshu", 80+1 );
		}
		
		
		try {
			ExcelExportUtil.getInstance(env)
					//创建文件
					.createWorkbook("doc", "diyTest", "穆瑞恒")
					.createDefaultStyles()
					.createSheet("diy测试")
					.createSheet("diy测试2")
					//填充数据
					.addTitle(0, mateData, "测试数据")
					.populateData(0, mateData, datas)
					.populateData(1, DataScope.TITLE_DATA, mateData, datas)
					
					//设置表格样式
					.setColumnWidth(0, columnWidthArray)
					.setRowHeight(0, 20)
					.setRowHeight(0, 0, 30)
					.setColumnWidth(1, columnWidthArray)
					.setRowHeight(1, 20)
					//产生文件
					.generateExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void excelExport3() {
		
		
		

		List<MetaData> metaData = new LinkedList<>();
		metaData.add(new MetaData(1, "xuhao", "序号", CellType.String));
		metaData.add(new MetaData(2, "xingming", "姓名", CellType.String));
		metaData.add(new MetaData(3, "xuehao", "学号", CellType.String));
		metaData.add(new MetaData(4, "fenshu", "分数", CellType.Number));
		
		int[] columnWidthArray = {10,20,30,10};
		
		List<Map<String, Object>> datas = new LinkedList<>();
		for (int index =0; index < 100; index ++) {
			Map<String, Object> data = new HashMap<>();
			datas.add(data);
			data.put("xuhao", index + 1 );
			data.put("xingming", "张三" + index );
			data.put("xuehao", "00110001_" + index );
			data.put("fenshu", 80+1 );
		}
		try {
			ExcelExportUtil util = ExcelExportUtil.getInstance(env)
			//创建文件
					.createWorkbook("doc", "diyTest22", "穆瑞恒")
					.createDefaultStyles()
					.createSheet("diy测试");
			Row row = util.createRow(0, 0);
			XSSFCellStyle style = util.createPoiStyle("a1");
			XSSFFont titleFont = util.createPoiFont();
			titleFont.setBold(false);// 粗体显示
			titleFont.setFontHeightInPoints((short) 12);
			style.setFont(titleFont);
			style.setAlignment(HorizontalAlignment.CENTER); // 居中
			style.setVerticalAlignment(VerticalAlignment.CENTER);// 水平全部居中
			style.setWrapText(true);// 设置自动换行
			util.addCell(row, 0, "标题", CellType.String, "a1");
			row = util.createRow(0, 1);
			
			util.populateData(0, DataScope.TITLE_DATA, metaData, datas);
			util.setColumnWidth(0, columnWidthArray);
					//产生文件
			util.generateExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
