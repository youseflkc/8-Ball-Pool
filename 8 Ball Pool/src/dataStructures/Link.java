package dataStructures;

import engine.Graphic;
import engine.primitives.Primitive;

/**
 * Class represents an individual link in a linked list
 * 
 * @author kolmthom094
 *
 */
public class Link {
    public Link nextLink;
    public Graphic graphic;

    public Link(Graphic graphic)
    {
        this.graphic = graphic;
    }
}
