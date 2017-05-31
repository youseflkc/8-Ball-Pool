package dataStructures;

/**
 * Class represents an individual link in a linked list
 * 
 * @author kolmthom094
 *
 */
public class Link {
    public Link nextLink;
    public String word;

    public Link(String word)
    {
        this.word = word;
    }

    public void printLink() {
        System.out.print("{" + word + "} ");
    }
}
