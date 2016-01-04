package at.hsol.fountainizer.pdfbox.interfaces;

/**
 * @author Lukas Theis
 * @author Felix Batusic
 */
public interface Margins {
	public float getMarginTop();
	public float getMarginLeft();
	public float getMarginRight();
	public float getMarginBottom();
	 
	public void setMarginTop(float top);
	public void setMarginLeft(float left); 
	public void setMarginRight(float right);
	public void setMarginBottom(float bottom);
	public void setMargin(float top,float left, float right,float bottom);
}