package at.hacksolutions.f2p.pdfbox;

public interface I_HasMargin {
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
