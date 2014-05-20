package org.mcavallo.opencloud.util;

/**
 * Unordered pair of objects (two pairs containing equal objects in different
 * order are considered equal).
 */
public class Pair<E>
{
    private E first = null;
    private E second = null;
    
    /**
     * Constructs a pair with null objects
     */
    public Pair()
    {
        super();
    }

    /**
     * Constructs a pair with the specified objects
     */
   public Pair(E first, E second)
    {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * @param object
     * @return True if the pair contains the specified object
     */
    public boolean contains(E object)
    {
        if (object == null) {
            if (first == null || second == null) {
                return true;
            } else {
                return false;
            }
        }
        
        if (object.equals(first) || object.equals(second)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The first object of the pair
     */
    public E getFirst()
    {
        return first;
    }

    /**
     * Sets the first object of the pair
     * @param first First object of the pair
     */
    public void setFirst(E first)
    {
        this.first = first;
    }

    /**
     * @return The second object of the pair
     */
    public E getSecond()
    {
        return second;
    }

    /**
     * Sets the second object of the pair
     * @param second Second object of the pair
     */
   public void setSecond(E second)
    {
        this.second = second;
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        
        if (object == null)
            return false;
        
        if (getClass() != object.getClass())
            return false;
        
        final Pair<?> other = (Pair<?>) object;

        if (first == null && second == null) {
            if (other.first == null && other.second == null)
                return true;

            return false;
        }
        
        if (first == null && second != null) {
            if ((other.first == null && second.equals(other.second)) ||
                (other.second == null && second.equals(other.first)))
                return true;
            
            return false;
        }

        if (first != null && second == null) {
            if ((first.equals(other.first) && other.second == null) ||
                (first.equals(other.second) && other.first == null))
                return true;
            
            return false;
        }

        if ((first.equals(other.first) && second.equals(other.second)) ||
            (first.equals(other.second) && second.equals(other.first))) {
            return true;
        }

        return false;
    }
    
}