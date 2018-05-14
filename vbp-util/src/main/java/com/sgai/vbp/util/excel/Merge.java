package com.sgai.vbp.util.excel;

/**
 * 对齐
 * @author mrh
 *
 */
class Merge
{
  private int rowIndexBegin = 0;
  private int rowIndexEnd = 0;
  private int cellIndexBegin = 0;
  private int cellIndexEnd = 0;

  public int getRowIndexBegin()
  {
    return this.rowIndexBegin;
  }

  public void setRowIndexBegin(int rowIndexBegin)
  {
    this.rowIndexBegin = rowIndexBegin;
  }

  public int getRowIndexEnd()
  {
    return this.rowIndexEnd;
  }

  public void setRowIndexEnd(int rowIndexEnd)
  {
    this.rowIndexEnd = rowIndexEnd;
  }

  public int getCellIndexBegin()
  {
    return this.cellIndexBegin;
  }

  public void setCellIndexBegin(int cellIndexBegin)
  {
    this.cellIndexBegin = cellIndexBegin;
  }

  public int getCellIndexEnd()
  {
    return this.cellIndexEnd;
  }

  public void setCellIndexEnd(int cellIndexEnd)
  {
    this.cellIndexEnd = cellIndexEnd;
  }
}