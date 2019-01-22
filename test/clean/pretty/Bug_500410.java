package pretty;

/**
 *  Description of the Class
 *
 *@author     Mike Atkinson (Mike)
 *@created    07 July 2003
 */
class Bug_500410 {

    /**
     *  Constructor for the Bug_500410 object
     */
    Bug_500410()
    {
        Closures.apply(values,
            new Closure() {
                /**
                 *  Description of the Method
                 *
                 *@param  item  Description of Parameter
                 *@param  last  Description of Parameter
                 */
                public void apply(
                        Object item, boolean last)
                {
                    asString.append(item);
                    if (!last) {
                        asString.append(", ");
                    }
                }
            }
                );
    }

}

