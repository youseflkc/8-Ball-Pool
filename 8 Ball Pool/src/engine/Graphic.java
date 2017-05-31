package engine;

import engine.primitives.Primitive;

/**
 * Class that renders graphics displayed on main screen
 * 
 * @author kolmthom094
 *
 */
public class Graphic {
	
	private Primitive shape;
	
	protected Graphic(Primitive shape)
	{
		shape = null;
	}
	
	protected Graphic(Primitive shape, int[] xPoints, int[] yPoints)
	{
		this.shape = shape;
	}
	
	protected boolean isEmpty()
	{
		if (shape == null)
			return true;
		return false;
	}

	protected void render()
	{
		if (isEmpty() == true)
		{
			System.err.println("Shape cannot be rendered; no data inputted");
			return;
		}
		
		shape.render();
	}
}
