package com.sgai.vbp.util.excel;

/**
 * Excel 单元格
 * @author mrh
 *
 */
public class Cell
{
  private int cellIndex = 0;
  private String cellValue = "";
  private String styleKey = "";
  private CellType cellType = CellType.String;
  private Row row;

  public int getCellIndex()
  {
    return this.cellIndex;
  }

  public void setCellIndex(int cellIndex)
  {
    this.cellIndex = cellIndex;
  }

  public String getCellValue()
  {
    return this.cellValue;
  }

  public void setCellValue(String cellValue)
  {
    this.cellValue = ((cellValue == null) ? "" : cellValue);
  }

  public String getStyleKey()
  {
    return this.styleKey;
  }

  public void setStyleKey(String styleKey)
  {
    this.styleKey = styleKey;
  }

  public CellType getCellType()
  {
    return this.cellType;
  }

  public void setCellType(CellType cellType)
  {
    this.cellType = cellType;
  }

  public Row getRow()
  {
    return this.row;
  }

  public void setRow(Row row)
  {
    this.row = row;
  }
}