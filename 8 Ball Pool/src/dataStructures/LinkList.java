package dataStructures;

import engine.Graphic;

/**
 * Class emulates a linked list data structure that can
 * dynamically grow in size
 * 
 * @author kolmthom094
 *
 */
public class LinkList
{
    public int length;
    private Link first;

    public LinkList() {
        first = null;
        length = 0;
    }

    /**
     * Checks if the linked list is empty
     *
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        if (first == null)
            return true;

        return false;
    }

    /**
     * Inserts an object into the linked list
     *
     * @param graphic
     */
    public void insert(Graphic graphic) {
        Link newLink = new Link(graphic);
        newLink.nextLink = first;
        first = newLink;
        length += 1;
    }

    /**
     * Converts the linked list into an array
     *
     * @return array converted from a linked list
     */
    public String[] convertToArray() {
        // Create array with the length of the list
        String[] array = new String[length];

        // Sets the current position to the start of the list, sets array index to 0
        Link currentLink = first;
        int index = 0;

        // Iterates through the list, moving elements into the array
        while (currentLink != null) {
//            array[index] = currentLink.word;
            index += 1;
            currentLink = currentLink.nextLink;
        }

        return array;
    }
}
