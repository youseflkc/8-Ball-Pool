package dataStructures;

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
     * @param word
     */
    public void insert(String word) {
        Link newLink = new Link(word);
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
            array[index] = currentLink.word;
            index += 1;
            currentLink = currentLink.nextLink;
        }

        return array;
    }

    /**
     * Searches for a word inside the linked list
     *
     * @param word takes in the word being searched for
     * @return true if a match exists, false if a match does not exist
     */
    public boolean listContains(String word)
    {
        Link currentLink = first;

        while (currentLink != null)
        {
            if (word.compareTo(currentLink.word) == 0)
                return true;

            currentLink = currentLink.nextLink;
        }

        return false;
    }

    /**
     * Prints out the entire linked list
     */
    public void printList()
    {
        Link currentLink = first;

        while (currentLink != null)
        {
            currentLink.printLink();
            currentLink = currentLink.nextLink;
        }

        System.out.println();
    }
}
